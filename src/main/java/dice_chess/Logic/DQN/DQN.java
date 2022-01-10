package dice_chess.Logic.DQN;

import dice_chess.Board.Board;
import dice_chess.Logic.LogicGame;
import dice_chess.Players.AI;
import dice_chess.Players.Human;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nadam;


import java.io.IOException;

public class DQN {


    public DQN() {

        int stepPerEpoch = 16;

        QLearningConfiguration qConfig = QLearningConfiguration.builder()
                .seed(123L)
                .maxEpochStep(200)
                .maxStep(15000)
                .expRepMaxSize(150000)
                .batchSize(128)
                .targetDqnUpdateFreq(500)
                .updateStart(10)
                .rewardFactor(0.01)
                .gamma(0.99)
                .errorClamp(1.0)
                .minEpsilon(0.1f)
                .epsilonNbStep(1000)
                .doubleDQN(true)
                .build();


        DQNDenseNetworkConfiguration conf = DQNDenseNetworkConfiguration.builder()
                .updater(new Adam(0.7))
                .numHiddenNodes(764)
                .numLayers(4)
                .build();

        Board board = new Board();
        LogicGame logicGame = new LogicGame(board, new AI(false), new Human(true), 1, 3, 3, 0, 0);

        DiceChessGameMdp mdp = new DiceChessGameMdp(logicGame, true);


        QLearningDiscreteDense<LogicGame> dqn = new QLearningDiscreteDense<LogicGame>(mdp, conf, qConfig);

        dqn.train();

        mdp.close();

        try {
            dqn.getPolicy().save("dice-chess-dqn.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
