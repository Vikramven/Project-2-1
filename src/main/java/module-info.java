module Project_2_1 {

    requires javafx.controls;
    requires javafx.fxml;

    //opens dice_chess to javafx.fxml;
    opens dice_chess.GUI to javafx.fxml;

    //exports dice_chess;
    exports dice_chess.GUI;
}