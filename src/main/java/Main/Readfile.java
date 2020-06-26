/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Discord.App;
import Sythe.SytheGUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Clinton
 */
public class Readfile {

    public static void data() {
        String FullString = "";
        try {
            File myObj = new File("ReadFile.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                FullString += data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(FullString);
        String[] parts = FullString.split("\"");
      
        SytheGUI.discordLogInID = parts[1];
        SytheGUI.ServerId = parts[3];
        App.discordId = parts[5];

        System.out.println(parts[1]);
        System.out.println(parts[3]);
        System.out.println(parts[5]);
    }
}
