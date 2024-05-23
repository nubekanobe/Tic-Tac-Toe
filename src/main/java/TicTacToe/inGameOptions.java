package TicTacToe;

import TicTacToe.server.Notifier;
import TicTacToe.tempForData.TempForData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import TicTacToe.sounds.sounds;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;

public class inGameOptions {


    @FXML
    private ToggleButton sidesOnButton;

    @FXML
    private ToggleButton sidesOffButton;

    @FXML
    private ToggleButton normalButton;

    @FXML
    private ToggleButton easyButton;

    @FXML
    Slider musicSlider;
    @FXML
    Slider soundSlider;
    @FXML
    Button clearButton;

    @FXML
    AnchorPane optionsPane;

    private gameScreen gameScreenController;
    private Socket socket;


    public void setGameScreenController(gameScreen gameScreenController, Socket socket) {

        this.gameScreenController = gameScreenController;
        this.socket = socket;

    }
    public void isVSRealPlayer (boolean vsRealPlayer)
    {
        if (vsRealPlayer)
        {
            sidesOnButton.setDisable(true);
            sidesOffButton.setDisable(true);
            normalButton.setDisable(true);
            easyButton.setDisable(true);
            clearButton.setDisable(true);
        }
    }

    private void setToggleButtonColors()
    {
        if (normalButton.isSelected())
        {
            normalButton.setStyle("-fx-background-color: lightblue;");
            easyButton.setStyle("-fx-background-color: transparent;");
        }
        else
        {
            easyButton.setStyle("-fx-background-color: lightblue;");
            normalButton.setStyle("-fx-background-color: transparent;");
        }

        if (sidesOnButton.isSelected())
        {
            sidesOnButton.setStyle("-fx-background-color: lightgreen; ");
            sidesOffButton.setStyle("-fx-background-color: transparent;");
        }
        else
        {
            sidesOffButton.setStyle("-fx-background-color: pink;");
            sidesOnButton.setStyle("-fx-background-color: transparent;");
        }
    }


    public void initialize(){
        easyButton.setSelected(TempForData.easyButton);
        sidesOffButton.setSelected(TempForData.sidesOffButton);
        normalButton.setSelected(TempForData.normalButton);
        sidesOnButton.setSelected(TempForData.sidesOnButton);
    

        setToggleButtonColors();

        soundSlider.setValue(TempForData.soundVolume);
        musicSlider.setValue(TempForData.musicVolume);

        soundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            TempForData.soundVolume = newValue.intValue();
        });
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            TempForData.musicVolume = newValue.intValue();
            sounds.updateMusicVolume();
        });

        optionsPane.setStyle(TempForData.theme[TempForData.currentTheme]);

    }

    public void creditsButtonClicked()
    {
            sounds.playButtonClickSound();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Credits.fxml"));

                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL); // Set as modal dialog
                stage.setTitle("Credits");
                stage.setScene(scene);
                stage.showAndWait();

            } catch (IOException e){
                e.printStackTrace();
            }
    }

    public void sidesOnButtonClicked(ActionEvent event) {
        sounds.playButtonClickSound();
        TempForData.sidesOffButton = false;
        TempForData.sidesOnButton = true;
        new Thread(new Notifier(socket, "/switchSidesY", gameScreenController)).start();


        if (sidesOnButton.isSelected()) {
            // Change color when selected
            sidesOnButton.setStyle("-fx-background-color: lightgreen;");
            sidesOffButton.setStyle("-fx-background-color: transparent;");
        }
    }

        public void sidesOffButtonClicked (ActionEvent event) {
            sounds.playButtonClickSound();
            TempForData.sidesOffButton = true;
            TempForData.sidesOnButton = false;
            new Thread(new Notifier(socket, "/switchSidesN", gameScreenController)).start();


            if (sidesOffButton.isSelected()) {
                // Change color when selected
                sidesOnButton.setStyle("-fx-background-color: transparent;");
                sidesOffButton.setStyle("-fx-background-color: pink; ");
            }

        }


    public void easyButtonClicked (ActionEvent event) {
        sounds.playButtonClickSound();
        TempForData.easyButton = true;
        TempForData.normalButton = false;
        new Thread(new Notifier(socket, "/difficultyE", gameScreenController)).start();

        if(easyButton.isSelected()){
            easyButton.setStyle("-fx-background-color: lightblue;");
            normalButton.setStyle("-fx-background-color: transparent;");
        }

    }

    public void normalButtonClicked (ActionEvent event) {
        sounds.playButtonClickSound();
        TempForData.easyButton = false;
        TempForData.normalButton = true;
        new Thread(new Notifier(socket, "/difficultyN", gameScreenController)).start();

        if(normalButton.isSelected()){
            easyButton.setStyle("-fx-background-color: transparent;");
            normalButton.setStyle("-fx-background-color: lightblue;");
        }
    }

    public void quitButtonClicked(ActionEvent event) throws IOException {
        sounds.playButtonClickSound();

        Stage optionsStage = (Stage) (easyButton.getScene().getWindow());
        optionsStage.close();

        if (!gameScreenController.quit)
        {
//            System.out.println("game screen socket is connected");
            gameScreenController.handleOptionsQuit();
            ((Stage) easyButton.getScene().getWindow()).close();
        }
    }

    public void clearButtonClicked(ActionEvent event){
        sounds.playButtonClickSound();

        new Thread(new Notifier(socket, "/clearBoard", gameScreenController)).start();

        Stage optionsStage = (Stage) (easyButton.getScene().getWindow());

        optionsStage.close();
    }
}
