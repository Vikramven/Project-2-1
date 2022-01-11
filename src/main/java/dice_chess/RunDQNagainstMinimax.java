package dice_chess;

import dice_chess.Logic.DQN.DQN;
import javafx.application.Platform;

import java.io.IOException;

public class RunDQNagainstMinimax {

    public static void main(String[] args) {
        Platform.startup(() ->
        {
            try {
                new DQN(2, "dqn-chess-game-minimax");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
