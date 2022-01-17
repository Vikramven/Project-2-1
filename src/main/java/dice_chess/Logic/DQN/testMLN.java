package dice_chess.Logic.DQN;

import dice_chess.Board.Board;
import dice_chess.Logic.LogicGame;
import dice_chess.Players.AI;
import dice_chess.Players.Human;
import javafx.application.Platform;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

import static dice_chess.Constant.Constant.*;
import static dice_chess.Constant.Constant.EVALUATION_FUNCTION_EXPECTI_MINIMAX;

public class testMLN {

    public testMLN() throws IOException {

        String userDir = System.getProperty("user.dir");
        System.out.println(userDir + "/dqn.bin");

        Board board = new Board();

        LogicGame logicGame = new LogicGame(board, true);

        logicGame.dicePiece = 3;

        DQNPolicy<LogicGame> policy = DQNPolicy.load(userDir + "\\dqn.bin");

        for (int i = 0; i < 50; i++) {

            Integer action = policy.nextAction(Nd4j.expandDims(Nd4j.create(logicGame.toArray()), 0));

            System.out.println(action);
        }

    }

    public static void main(String[] args) {
        Platform.startup(() ->
        {
            try {
                setUpSettings();
                new testMLN();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

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
