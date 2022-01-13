package dice_chess;

import dice_chess.Logic.DQN.DQN;
import javafx.application.Platform;

import java.io.IOException;

public class RunDQNagainstRandom {

    public static void main(String[] args) {
        Platform.startup(() ->
        {
            try {
                new DQN(0, "dqn-chess-game-random.bin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
