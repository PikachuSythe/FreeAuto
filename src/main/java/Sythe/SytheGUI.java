/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sythe;

import Discord.Discord;
import Main.JavaFXlib;
import Main.Readfile;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.security.auth.login.LoginException;

/**
 *
 * @author Clinton
 */
public class SytheGUI {

    public static String SytheUsername;
    public static String ServerId;
    public static String discordLogInID;

    private final Pane rootPane;

    JavaFXlib FX = new JavaFXlib();

    private final ImageView img[] = {
        FX.Image("https://img.sythe.org/newlogo1.png", 0, -130)
    };
    private final TextField txt[] = {
        FX.textBox("Usernamel", 250, 25, -10, -0),
        FX.textBox("2FA code", 250, 25, 90, -0), //        FX.textBox("xfToken", 250, 25, 110, -0)
    };
    private final PasswordField pass[] = {
        FX.passwordBox("Passowrd", 250, 25, 40, -0)
    };
    private final Button but[] = {
        FX.button("Sign In", 80, 30, 140, 0)
    };
    private final Text text[] = {
        FX.text("Sythe Username:", -65, -35, " -fx-font-size: 15px;" + "\n" + "-fx-fill: white;"),
        FX.text("Sythe Password:", -68, 15, " -fx-font-size: 15px;" + "\n" + "-fx-fill: white;"),
        FX.text("Sythe 2FA:", -85, 65, " -fx-font-size: 15px;" + "\n" + "-fx-fill: white;")
    };
    public static PostRequests PostRequest = new PostRequests("", "", "");

    private static boolean login = false;

    public SytheGUI(Stage primaryStage) {
        Readfile.data();

        rootPane = new StackPane();
        FX = new JavaFXlib(rootPane);

        if (login == true) {
            rootPane.getChildren().add(FX.text("Failed log in attempt:", 0, 170, " -fx-font-size: 13px;" + "\n" + "-fx-fill: red;"));
        }

        FX.rootPaneCss("-fx-background-image: url(\"https://i.gyazo.com/b23ded78c222744fb775ec578b220ba3.jpg\");"
                + "-fx-background-size:cover;"
                + "-fx-background-position: center center;");
        FX.Image("https://img.sythe.org/newlogo1.png", 0, -160);
        FX.IconFileDirectory(primaryStage, "http://www.minhomedica.pt/_v3_/includes/img/partners_logos/partner_logo_1.png");

        for (int i = 0; i < text.length; i++) {
            rootPane.getChildren().add(text[i]);
        }
        for (int i = 0; i < txt.length; i++) {
            rootPane.getChildren().add(txt[i]);
        }
        for (int i = 0; i < img.length; i++) {
            rootPane.getChildren().add(img[i]);
        }
        for (int i = 0; i < pass.length; i++) {
            rootPane.getChildren().add(pass[i]);
        }
        for (int i = 0; i < but.length; i++) {
            rootPane.getChildren().add(but[i]);
        }

        but[0].setOnAction((ActionEvent value) -> {
            try {

                SytheUsername = txt[0].getText();

                primaryStage.hide();
                System.out.println("Sign in process has started.");
                PostRequest = new PostRequests(txt[0].getText(), pass[0].getText(), txt[1].getText());
                PostRequest.Login();
                if (!txt[1].getText().isEmpty() || txt[1].getText() != null) {
                    PostRequest.releaseConnection();
                    PostRequest.twofa();
                    PostRequest.printMessage();
                    PostRequest.print(PostRequest.ReturncsrfToken());
                    PostRequest.releaseConnection();
                } else {
                    PostRequest.printMessage();
                    PostRequest.print(PostRequest.ReturncsrfToken());
                    PostRequest.releaseConnection();
                }
            } catch (IOException ex) {
                Logger.getLogger(SytheGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!PostRequest.Token().isEmpty()) {
                try {
                    Discord.Discord();
                } catch (LoginException ex) {
                    Logger.getLogger(SytheGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SytheGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                primaryStage.hide();
                System.out.println("Failed login.");
                login = true;
                SytheGUI game = new SytheGUI(primaryStage);
                primaryStage.getScene().setRoot(game.getRootPane());
            }

        });

        Scene scene = new Scene(rootPane, 400, 425);
        primaryStage.setResizable(false);
        primaryStage.setTitle("AutoBumper");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public Pane getRootPane() {
        return rootPane;
    }

    public static PostRequests CurrentSesion() {
        return PostRequest;
    }
}
