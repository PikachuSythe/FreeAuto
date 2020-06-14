package Main;

import Sythe.SytheGUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

//Configure in Sythe
    @Override
    public void start(Stage stage) throws Exception {
        SytheGUI game = new SytheGUI(stage);
        stage.getScene().setRoot(game.getRootPane());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
