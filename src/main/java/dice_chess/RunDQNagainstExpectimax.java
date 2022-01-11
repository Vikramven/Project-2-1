package dice_chess;

import dice_chess.Logic.DQN.DQN;
import javafx.application.Platform;

import java.io.IOException;

public class RunDQNagainstExpectimax {

    public static void main(String[] args) {
        Platform.startup(() ->
        {
            try {
                new DQN(1, "dqn-chess-game-expectimax");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
