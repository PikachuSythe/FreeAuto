/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Discord;

import static Discord.Discord.jda;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import Sythe.SytheGUI;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class App extends ListenerAdapter {

	public static ArrayList<String> VouchsBuy = new ArrayList<String>();
	public static ArrayList<String> VouchsSell = new ArrayList<>();
	private final String prexfix = "!";
	private static HashMap<String, String> ThreadsMessage = new HashMap<>();
	private User Author;
	public static String OnlineUser;
	private Member mem;
	public static MessageChannel channel;
	private Message objMsg;
	private static ScheduledExecutorService RegularBump;
	public static Guild guild;
	public static ScheduledExecutorService StatusBump;
	private static boolean BumpCycle = false;
	private Builders build = new Builders();
	public static boolean sytheBump = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent evt) {

		objMsg = evt.getMessage();
		Author = evt.getAuthor();
		guild = evt.getGuild();
		channel = evt.getChannel();

		if (!Author.getId().equalsIgnoreCase(jda.getSelfUser().getId()) && Author.getName().equalsIgnoreCase(SytheGUI.SytheUsername)) {

			if (objMsg.getContentRaw().contains(prexfix + "addmulti ")) {
				try {
					System.out.println("\n ***** adding a thread ***** \n");
					String[] parts = objMsg.getContentRaw().split(" ");
					String[] threads = parts[1].split(",");

					if (!ThreadsMessage.containsKey(parts[1])) {
						for (int j = 0; j < threads.length; j++) {
							if (threads[j].matches("[0-9]+")) {
								if (parts.length >= 3) {
									String fullMessage = "";
									for (int i = 2; i < parts.length; i++) {
										fullMessage = fullMessage + " " + parts[i];
									}
									ThreadsMessage.put(threads[j], fullMessage);
								} else {
									ThreadsMessage.put(threads[j], "Bump");
								}
								build.messageBuilder(null, build.Time(),
										Author.getAsMention() + " has added the following thread "
												+ "https://www.sythe.org/threads/" + threads[j] + "/",
										null, "white", Author);

							} else {
								channel.sendMessage("Your thread must only cointain thread id").queue();
							}
						}
					} else {
						build.messageBuilder(null, build.Time(),
								Author.getAsMention() + ", you can't bump same thread twice. ", null, "white", Author);
					}
					System.out.println("\n ------------------------------------- \n");
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("error with thread !addmulti");
				}
			}

			if (objMsg.getContentRaw().contains(prexfix + "add ")) {
				try {
					System.out.println("\n ***** adding a thread ***** \n");
					String[] parts = objMsg.getContentRaw().split(" ");
					if (!ThreadsMessage.containsKey(parts[1])) {
						if (parts[1].matches("[0-9]+")) {
							if (parts.length >= 3) {
								String fullMessage = "";
								for (int i = 2; i < parts.length; i++) {
									fullMessage = fullMessage + " " + parts[i];
								}
								ThreadsMessage.put(parts[1], fullMessage);
							} else {
								ThreadsMessage.put(parts[1], "Bump");
							}
//                        objMsg.delete().reason("none").queue();
							build.messageBuilder(null, build.Time(),
									Author.getAsMention() + " has added the following thread "
											+ "https://www.sythe.org/threads/" + parts[1] + "/",
									null, "white", Author);
						} else {
							channel.sendMessage("Your thread must only cointain thread id").queue();
						}
					} else {
						build.messageBuilder(null, build.Time(),
								Author.getAsMention() + ", you can't bump same thread twice. ", null, "white", Author);
					}
					System.out.println("\n ------------------------------------- \n");
				} catch (Exception e) {
					System.out.println("error with thread !add");
				}
			}

			if (objMsg.getContentRaw().contains(prexfix + "remove")) {
				System.out.println("\n ***** remove a thread ***** \n");
				String[] parts = objMsg.getContentRaw().split(" ");
				if (ThreadsMessage.keySet().contains(parts[1]) == true) {
					ThreadsMessage.remove(parts[1]);
				}
//                objMsg.delete().reason("none").queue();
				build.messageBuilder(null, build.Time(), Author.getAsMention() + " has removed the following thread "
						+ "https://www.sythe.org/threads/" + parts[1] + "/", null, "white", Author);
				System.out.println("\n ------------------------------------- \n");
			}

			if (objMsg.getContentRaw().equalsIgnoreCase(prexfix + "bump")) {
				sytheBump = true;
				System.out.println("\n ***** Single Bump ***** \n");
				Thread thread = new Thread() {
					@Override
					public void run() {
						TimedEvents.NormalBump = 0;
						TimedEvents.BumpTime = 0;
						try {
							bump();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("\n ------------------------------------- \n");
						build.messageBuilder(null, build.Time(), Author.getAsMention() + " has mannually bumped", null,
								"white", Author);
						TimedEvents.MessagePrint = true;
					}
				};
				thread.start();
				sytheBump = false;
			}

			if (objMsg.getContentRaw().equalsIgnoreCase(prexfix + "Shutdown")) {
				System.exit(0);
				channel.sendMessage("The bot has shutdown").queue();
			}

			
			if (objMsg.getContentRaw().equalsIgnoreCase(prexfix + "autobumper") && BumpCycle == false) {
				OnlineUser = Author.getId();
				StatusBump = Executors.newScheduledThreadPool(1);
				StatusBump.scheduleWithFixedDelay(new TimedEvents.autoBump(), 0, 300, SECONDS);
				BumpCycle = true;
				build.messageBuilder(null, build.Time(), Author.getAsMention() + " has started the auto bump process",
						null, "white", Author);
			}

			if (objMsg.getContentRaw().equalsIgnoreCase(prexfix + "List")) {
				System.out.println("\n ***** List of threads ***** \n");
				String threads = "";
				for (int i = 0; i < ThreadsMessage.size(); i++) {
					threads = threads + (i + 1) + ". " + "https://www.sythe.org/threads/"
							+ ThreadsMessage.keySet().toArray()[i].toString() + "" + "\n";
					String first = ThreadsMessage.keySet().toArray()[i].toString();
					String second = ThreadsMessage.values().toArray()[i].toString();
					System.out.println("key: " + first);
					System.out.println("value: " + second);
				}
				build.messageBuilder(null, build.Time(), threads, null, "white", Author);
				System.out.println("\n ------------------------------------- \n");
			}

			if (objMsg.getContentRaw().equalsIgnoreCase(prexfix + "end") && BumpCycle == true) {
				System.out.println("\n ***** End Bump's ***** \n");
				try {
					try {
						if (!RegularBump.awaitTermination(800, TimeUnit.MILLISECONDS)) {
							RegularBump.shutdown();
						}
					} catch (InterruptedException e) {
						RegularBump.shutdownNow();
					}

				} catch (Exception e) {
					System.out.println("Regular bump wasn't started");
				}
				try {
					try {
						if (!StatusBump.awaitTermination(800, TimeUnit.MILLISECONDS)) {
							StatusBump.shutdown();
						}
					} catch (InterruptedException e) {
						StatusBump.shutdownNow();
					}
				} catch (Exception e) {
					System.out.println("Regular StatusBump wasn't started");
				}
				TimedEvents.BumpTime = 0;
				TimedEvents.NormalBump = 0;
				BumpCycle = false;
				System.out.println("\n ------------------------------------- \n");
				// build.messageBuilderChan(null, build.Time(), Author.getAsMention() + " has
				// ended bump process.", "!end",
				// "white", null, "logs");
				build.messageBuilder(null, build.Time(), Author.getAsMention() + " has ended bump process.", null,
						"white", Author);
			}

			if (objMsg.getContentRaw().equalsIgnoreCase(prexfix + "cmd")) {
				build.messageBuilder(null, build.Time(),
						"!addmulti <threadid,threadid, etc> - add multiple thread with message bump "
								+ "\n!addmulti <threadid,threadid, etc> <message> - add multiple thread with custum message"
								+ "\n !add <Thread id>  - Adds the thread to bump"
								+ "\n !edit <thread number + 1> <message> - Updates the thread message"
								// + "\n !bumptime - Show how long you have bump time set to (only effects
								// autobump)"
								+ "\n !remove <thread id>"
								+ "\n !add <threadid> <message> - Bump thread with spefic message"
								+ "\n !hours <Time> -Set how many hours your auto bump  (only effects autobump)"
								+ "\n !checkmessage - Displayed all thread messages" + "\n !end - stop all bumps"
								+ "\n !list - lists all the threads you are bumping"
								// + "\n !autobump - bumps every 8 hours"
								+ "\n !statusbump - only bumps when you are online" + "\n !bump - bumps all threads"
								+ "\n !shutdown - shutdown the whole system.",
						"comands", "white", Author);
			}

			if (objMsg.getContentRaw().contains(prexfix + "checkmessage")) {
				System.out.println("\n ***** List of threads ***** \n");
				String threads = "";
				for (int i = 0; i < ThreadsMessage.size(); i++) {
					threads = threads + (i + 1) + ". " + ThreadsMessage.values().toArray()[i].toString() + "" + "\n";
					String first = ThreadsMessage.keySet().toArray()[i].toString();
					String second = ThreadsMessage.values().toArray()[i].toString();
					System.out.println("key: " + first);
					System.out.println("value: " + second);
				}
				build.messageBuilder(null, build.Time(), threads, null, "white", Author);
				System.out.println("\n ------------------------------------- \n");
			}

			if (objMsg.getContentRaw().contains(prexfix + "edit")) {
				String[] parts = objMsg.getContentRaw().split(" ");
				String fullMessage = "";
				for (int i = 2; i < parts.length; i++) {
					fullMessage = fullMessage + " " + parts[i];
				}
				ThreadsMessage.put(ThreadsMessage.keySet().toArray()[Integer.parseInt(parts[1]) - 1].toString(),
						fullMessage);
				build.messageBuilder(null, build.Time(),
						Author.getAsMention() + " You have edited thread "
								+ ThreadsMessage.keySet().toArray()[Integer.parseInt(parts[1]) - 1].toString() + " to "
								+ ThreadsMessage.values().toArray()[Integer.parseInt(parts[1]) - 1].toString(),
						null, "white", Author);
			}
		}
	}

	static void bump() throws IOException {
		for (int i = 0; i < ThreadsMessage.size(); i++) {
			try {
				Random rn = new Random();
				Thread.sleep(1000 * 5);
				String first = ThreadsMessage.keySet().toArray()[i].toString();
				String second = ThreadsMessage.values().toArray()[i].toString();
				SytheGUI.PostRequest.bump(first, second);
			} catch (InterruptedException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	static void end() {
		System.out.println("\n ***** End Bump's ***** \n");
		try {
			try {
				if (!RegularBump.awaitTermination(800, TimeUnit.MILLISECONDS)) {
					RegularBump.shutdown();
				}
			} catch (InterruptedException e) {
				RegularBump.shutdownNow();
			}

		} catch (Exception e) {
			System.out.println("Regular bump wasn't started");
		}
		try {
			try {
				if (!StatusBump.awaitTermination(800, TimeUnit.MILLISECONDS)) {
					StatusBump.shutdown();
				}
			} catch (InterruptedException e) {
				StatusBump.shutdownNow();
			}
		} catch (Exception e) {
			System.out.println("Regular StatusBump wasn't started");
		}
		TimedEvents.BumpTime = 0;
		TimedEvents.NormalBump = 0;
		BumpCycle = false;
	}
}
