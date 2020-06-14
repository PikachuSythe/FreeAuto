/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Discord;

import javax.security.auth.login.LoginException;

import Sythe.SytheGUI;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class Discord {

    public static JDA jda;

    public static void Discord() throws LoginException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT).setToken(SytheGUI.discordLogInID).build().awaitReady();
        jda.addEventListener(new App());
        jda.getPresence().setGame(Game.playing("Created by Pikachu#2419"));
    }
}
