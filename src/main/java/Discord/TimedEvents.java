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
                embed.setDescription(Online.getNickname() + " I have auto bumped your thread.");
                Online.getUser().openPrivateChannel().complete().sendMessage(embed.build()).queue();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
