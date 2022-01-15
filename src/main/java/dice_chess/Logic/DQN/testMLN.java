package dice_chess.Logic.DQN;

import dice_chess.Board.Board;
import dice_chess.Logic.LogicGame;
import dice_chess.Players.AI;
import dice_chess.Players.Human;
import javafx.application.Platform;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;

public class testMLN {

    public testMLN() throws IOException {

        String path = System.getProperty("user.dir");

        MultiLayerNetwork multiLayerNetwork = MultiLayerNetwork.load(new File(path + "\\dqn-chess-game-expectimax.bin"), true);

        Board board = new Board();

        LogicGame logicGame = new LogicGame(board, true);

        logicGame.dicePiece = 3;

        for (int i = 0; i < 50; i++) {
            INDArray output = multiLayerNetwork.output(logicGame.getData());

            int maxValueIndex = getMaxValueIndex(output.data().asDouble());

            System.out.println(maxValueIndex);
        }

    }

    public static int getMaxValueIndex(final double[] values) {
        int maxAt = 0;

        for (int i = 0; i < values.length; i++) {
            maxAt = values[i] > values[maxAt] ? i : maxAt;
        }

        return maxAt;
    }

    public static void main(String[] args) {
        Platform.startup(() ->
        {
            try {
                new testMLN();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
