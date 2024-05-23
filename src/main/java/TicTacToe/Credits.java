package TicTacToe;


import TicTacToe.tempForData.TempForData;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Credits
{
    @FXML
    AnchorPane creditsPane;

    public void initialize()
    {
        creditsPane.setStyle(TempForData.theme[TempForData.currentTheme]);

    }
}
