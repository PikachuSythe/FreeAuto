package Discord;

import static Discord.Discord.jda;

import java.awt.Color;

import Sythe.SytheGUI;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;

public class TimedEvents {

    public static int NormalBump = 0;
    public static int Bumptime = 4;
    public static int BumpTime = 48;
    public static int x = 0;
    public static int delay = 0;
    static boolean buy = false;
    static boolean sell = false;
    public static boolean MessagePrint = true;
    

    public static class autoBump implements Runnable {

        @Override
        public void run() {
            try {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                App.bump();
                Member Online = jda.getGuildById(SytheGUI.ServerId).getMemberById(App.OnlineUser);
                embed.setDescription(SytheGUI.SytheUsername + " I have auto bumped your thread.");
                Online.getUser().openPrivateChannel().complete().sendMessage(embed.build()).queue();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static class SytheStatus implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("\n ***** Status Bump ***** \n");
                Member Online = jda.getGuildById(SytheGUI.ServerId).getMemberById(App.OnlineUser);
                String status = Online.getOnlineStatus().toString();
                System.out.println(status);
                EmbedBuilder embed = new EmbedBuilder();

                if (BumpTime == 48) {
                    if (status.equalsIgnoreCase("online") || status.equalsIgnoreCase("idle")
                            || status.equalsIgnoreCase("DO_NOT_DISTURB")) {
                        embed.setDescription("Hey " + Online.getEffectiveName() + ", I have autobumped your threads.");
                        MessagePrint = false;
                        TimedEvents.BumpTime = 0;
                        App.bump();
                        embed.setColor(Color.RED);
                        Online.getUser().openPrivateChannel().complete().sendMessage(embed.build()).queue();
                    } else if (status.equalsIgnoreCase("OFFLINE")) {
                        BumpTime = 47;
                        System.out.println("Bumping and user is offline");
                    } else {
                        System.out.println("Some sort of fucking error.");
                    }
                }

                BumpTime++;

                try {
//                embed.messageBuilderChan(null, embed.Time() + " ", "" + BumpTime, "!Status", "red", null, "general");
                } catch (Exception e) {
                }
            } catch (Exception e) {
                Builders build = new Builders();
                build.messageBuilderChan(null, build.Time() + " " + e,
                        "Error with Status Bump (sythe) Line number: " + e.getStackTrace()[0], "!Status", "red", null,
                        "logs");
            }
        }

    }
}
