package dice_chess;

import dice_chess.Board.Board;
import dice_chess.Logic.LogicGame;
import dice_chess.Players.AI;
import javafx.application.Platform;

import static dice_chess.Constant.Constant.*;
import static dice_chess.Constant.Constant.EVALUATION_FUNCTION_EXPECTI_MINIMAX;

public class RunWithoutGUI {

    public static void main(String[] args) {

        Platform.startup(() ->
        {
            setUpSettings();

            for (int i = 0; i < 1005; i++) {
                new LogicGame(new Board(), true);
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
        AI_SIMULATIONS = true;
        GAME_TRACKER = true;


        PLAYER_WHITE = new AI(false);
        DEPTH_WHITE = 3;
        AI_WHITE = 0;


        PLAYER_BLACK = new AI(true);
        DEPTH_BLACK = 3;
        AI_BLACK = 1;


        EVALUATION_FUNCTION_MINIMAX = 1;

        EVALUATION_FUNCTION_EXPECTI_MAX = 2;

        EVALUATION_FUNCTION_EXPECTI_MINIMAX = 2;
    }
}
