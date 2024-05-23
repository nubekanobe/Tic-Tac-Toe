module TicTacToe {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.media;

    opens TicTacToe to javafx.fxml;
    exports TicTacToe;
    exports TicTacToe.Game;
    opens TicTacToe.Game to javafx.fxml;
}