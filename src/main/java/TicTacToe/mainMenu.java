package TicTacToe;

import TicTacToe.tempForData.TempForData;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import TicTacToe.sounds.sounds;
import javafx.util.Duration;

import java.io.IOException;
import java.net.Socket;

public class mainMenu
{
    @FXML
    Label exitMessage;

    @FXML
    Button onePlayerButton;

    @FXML
    Label errorLabel;

    @FXML
    protected void onePlayerModeClicked()
    {
        //code to switch screen to game screen, connect to the server, and start the right mode for 1 player
        TempForData.mode = 1;
        try {
            Socket socket = new Socket("localhost", 80);
            Stage stage = (Stage) onePlayerButton.getScene().getWindow();
            stage.setResizable(false);
            FXMLLoader onePlayer = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
            Parent root = null;
            try {
                root = onePlayer.load();
                gameScreen gameScreen = (gameScreen) onePlayer.getController();
                gameScreen.connectToServer(socket);
                Scene scene = new Scene(root);
                stage.setWidth(615);
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
   ;
            sounds.playButtonClickSound();
        } catch (IOException e) {
            errorLabel.setText("We're sorry but there was a problem connecting to the server. Please try again later.");
        }

    }

    @FXML
    protected void twoPlayerMode()
    {
        //code to switch screen to game screen, connect to the server, and start the right mode for 2 player
        TempForData.mode = 2;
        try {
            Socket socket = new Socket("localhost", 80);
            Stage stage = (Stage) onePlayerButton.getScene().getWindow();
            FXMLLoader onePlayer = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
            Parent root = null;
            try {
                root = onePlayer.load();
                gameScreen gameScreen = (gameScreen) onePlayer.getController();
                gameScreen.connectToServer(socket);
                Scene scene = new Scene(root);
                stage.setWidth(615);
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            sounds.playButtonClickSound();
        } catch (IOException e) {
            errorLabel.setText("We're sorry but there was a problem connecting to the server. Please try again later.");
        }

    }

    @FXML
    protected void exitGame()
    {
        sounds.playButtonClickSound();
        exitMessage.setText("Exiting Tic Tac Toe... Have a nice day!");

        // Delay closing the stage for 2 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> {
            Stage stage = (Stage) exitMessage.getScene().getWindow();
            stage.close();
        });
        delay.play();
    }
}
