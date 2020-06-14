/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Discord;

import static Discord.App.channel;
import static Discord.Discord.jda;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

/**
 *
 * @author Clinton
 */
public class Builders {

    public void messageBuilder(String Thumbnail, String footer, String description, String Title, String color, User user) {
        EmbedBuilder embed = new EmbedBuilder();

        if (Title != null) {
            embed.setTitle(Title);
        }

        if (user != null) {
            embed.setAuthor(user.getName(), null, user.getEffectiveAvatarUrl());
        }

        if (Thumbnail != null) {
            embed.setThumbnail(Thumbnail);
        }

        //sets the color of the embed
        if (color != null) {
            try {
                switch (color) {
                    case "red":
                        embed.setColor(Color.red);
                        break;
                    case "oranage":
                        embed.setColor(Color.ORANGE);
                        break;
                    case "yellow":
                        embed.setColor(Color.yellow);
                        break;
                    case "blue":
                        embed.setColor(Color.blue);
                        break;
                    case "pestal":
                        embed.setColor(Color.CYAN);
                        break;
                    case "purple":
                        embed.setColor(Color.PINK);
                        break;
                    case "rainbow":
                        embed.setColor(Color.TRANSLUCENT);
                        break;
                    case "black":
                        embed.setColor(Color.black);
                        break;
                    case "white":
                        embed.setColor(Color.white);
                        break;
                    case "green":
                        embed.setColor(Color.GREEN);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error with Color.");
                System.out.println(e);
            }
        }

        embed.setDescription(description);
//     
        if (footer != null) {
            embed.setFooter(footer, null);
        }

        channel.sendMessage(embed.build()).queue();
    }

    public String Time() {
        final Date currentTime = new Date();

        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        // Give it to me in GMT time.
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(currentTime) + " GMT";

    }

    public void messageBuilderChan(String Thumbnail, String footer, String description, String Title, String color, User user, String txt) {
        System.out.println("start of message builder.");
        EmbedBuilder embed = new EmbedBuilder();

        if (Title != null) {
            embed.setTitle(Title);
        }

        if (user != null) {
            embed.setAuthor(user.getName(), null, user.getEffectiveAvatarUrl());
        }

        if (Thumbnail != null) {
            embed.setThumbnail(Thumbnail);
        }

        //sets the color of the embed
        if (color != null) {
            try {
                switch (color) {
                    case "red":
                        embed.setColor(Color.red);
                        break;
                    case "oranage":
                        embed.setColor(Color.ORANGE);
                        break;
                    case "yellow":
                        embed.setColor(Color.yellow);
                        break;
                    case "blue":
                        embed.setColor(Color.blue);
                        break;
                    case "pestal":
                        embed.setColor(Color.CYAN);
                        break;
                    case "purple":
                        embed.setColor(Color.PINK);
                        break;
                    case "rainbow":
                        embed.setColor(Color.TRANSLUCENT);
                        break;
                    case "black":
                        embed.setColor(Color.black);
                        break;
                    case "white":
                        embed.setColor(Color.white);
                        break;
                    case "green":
                        embed.setColor(Color.GREEN);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error with Color.");
                System.out.println(e);
            }
        }

        embed.setDescription(description);
//     
        if (footer != null) {
            embed.setFooter(footer, null);
        }
        jda.getTextChannelsByName("bumpbot", true).get(0).sendMessage(embed.build()).queue();
    }
}
