package TicTacToe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import TicTacToe.sounds.sounds;
import java.io.IOException;

public class TicTacToe extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicTacMainMenu2.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("TicTacToe!");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        sounds.initialize();
        sounds.playBackgroundMusic();
        launch();
    }
}