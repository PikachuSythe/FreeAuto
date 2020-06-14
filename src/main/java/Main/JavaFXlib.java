/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
	/*
 * @author Clinton
 */
public class JavaFXlib {

    private final Pane rootPane;

    public JavaFXlib(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public JavaFXlib() {
        this.rootPane = null;
    }

    public void rootPaneCss(String Css) {
        rootPane.setStyle(Css);
    }

    public Text text(String textInput, int xValue, int yValue) {
        Text text = new Text();
        text.setText(textInput);
        text.setTranslateX(xValue);
        text.setTranslateY(yValue);
        return text;
    }

    public Text text(String textInput, int xValue, int yValue, String Css) {
        Text text = new Text();
        text.setText(textInput);
        text.setTranslateX(xValue);
        text.setTranslateY(yValue);
        text.setStyle(Css);
        return text;
    }

    //Usernames
    public TextField textBox(String Text, int XMaxSize, int YMaxSize, int TranslateX, int TranslateY, String Css) {
        TextField textArea = new TextField();
        textArea.setMaxSize(XMaxSize, YMaxSize);
        textArea.setTranslateY(TranslateX);
        textArea.setTranslateX(TranslateY);
        textArea.setPromptText(Text);
        textArea.setStyle(Css);
        return textArea;
    }

    public TextField textBox(String Text, int XMaxSize, int YMaxSize, int TranslateX, int TranslateY) {
        TextField textArea = new TextField();
        textArea.setMaxSize(XMaxSize, YMaxSize);
        textArea.setTranslateY(TranslateX);
        textArea.setTranslateX(TranslateY);
        textArea.setPromptText(Text);
        return textArea;
    }

    //Password
    public PasswordField passwordBox(String Text, int XMaxSize, int YMaxSize, int TranslateX, int TranslateY, String Css) {
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxSize(XMaxSize, YMaxSize);
        passwordField.setTranslateY(TranslateX);
        passwordField.setTranslateX(TranslateY);
        passwordField.setPromptText(Text);
        passwordField.setStyle(Css);
        return passwordField;
    }

    public PasswordField passwordBox(String Text, int XMaxSize, int YMaxSize, int TranslateX, int TranslateY) {
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxSize(XMaxSize, YMaxSize);
        passwordField.setTranslateY(TranslateX);
        passwordField.setTranslateX(TranslateY);
        passwordField.setPromptText(Text);
        return passwordField;
    }

    //Button
    public Button button(String Text, int XMaxSize, int YMaxSize, int TranslateX, int TranslateY) {
        Button But = new Button(Text);
        But.setMaxSize(XMaxSize, YMaxSize);
        But.setTranslateY(TranslateX);
        But.setTranslateX(TranslateY);
        return But;
    }

    public Button button(String Text, int XMaxSize, int YMaxSize, int TranslateX, int TranslateY, String css) {
        Button But = new Button(Text);
        But.setMaxSize(XMaxSize, YMaxSize);
        But.setTranslateY(TranslateY);
        But.setTranslateX(TranslateX);
        But.setStyle(css);
        return But;
    }

    //Icon
    public void IconFileDirectory(Stage stage, String ImageDirClink) {
//        try {
//        stage.getIcons().add(new Image(ImageDirClink));
//        } catch (Exception e) {
//        }
    }

    //Image
    public ImageView Image(String imgLink, int TranslateX, int TranslateY) {
        ImageView img = new ImageView(imgLink);
        img.setTranslateX(TranslateX);
        img.setTranslateY(TranslateY);
        return img;
    }

}
