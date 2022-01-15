package dice_chess;

import dice_chess.Logic.DQN.DQN;
import dice_chess.Players.AI;
import dice_chess.Players.Human;
import javafx.application.Platform;
import static dice_chess.Constant.Constant.*;

import java.io.IOException;

public class RunDQN {

    public static void main(String[] args) {


        Platform.startup(() ->
        {
            try {
                setUpSettings();
                new DQN("dqn-chess-game-expectimax100.bin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /*
    AI:
    0 Random
    1 Expecti max
    2 Minimax
    4 Expecti minimax

    Evaluation Function:
    0 - no evaluation at all
    1 - first old evaluation
    2 - Shannon evaluation
     */
    private static void setUpSettings(){
        DQN_SIMULATION = true;


        PLAYER_WHITE = new AI(false);
        DEPTH_WHITE = 3;
        AI_WHITE = 1;


        PLAYER_BLACK = new Human(true);
        DEPTH_BLACK = 3;
        AI_BLACK = 10;


        EVALUATION_FUNCTION_MINIMAX = 1;

        EVALUATION_FUNCTION_EXPECTI_MAX = 2;

        EVALUATION_FUNCTION_EXPECTI_MINIMAX = 2;
    }
}
