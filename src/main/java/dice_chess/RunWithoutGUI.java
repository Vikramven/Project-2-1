package dice_chess;

import dice_chess.Board.Board;
import dice_chess.Logic.LogicGame;
import dice_chess.Players.AI;
import javafx.application.Platform;

public class RunWithoutGUI {

    public static void main(String[] args) {

        Platform.startup(() ->
        {
            new LogicGame(new Board(), new AI(false), new AI(true), 2, 1, 3, 0, 0);
        });
    }
}
