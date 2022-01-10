package dice_chess;

import dice_chess.Board.Board;
import dice_chess.Logic.DQN.DQN;
import javafx.application.Platform;

public class RunDQN {

    public static void main(String[] args) {
        Platform.startup(DQN::new);
    }
}
