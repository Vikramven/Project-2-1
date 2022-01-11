package dice_chess.Logic.DQN;

import dice_chess.Board.Board;
import dice_chess.Logic.LogicGame;
import dice_chess.Players.AI;
import dice_chess.Players.Human;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.rl4j.learning.ILearning;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.QLearning;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.deeplearning4j.rl4j.util.DataManager;
import org.deeplearning4j.rl4j.util.IDataManager;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nadam;


import java.io.File;
import java.io.IOException;

public class DQN {


    public DQN() {

        int stepPerEpoch = 16;

        QLearningConfiguration qConfig = QLearningConfiguration.builder()
                .seed(123L)
                .maxEpochStep(3000)
                .maxStep(800000)
                .expRepMaxSize(100000)
                .batchSize(32)
                .targetDqnUpdateFreq(100)
                .updateStart(10)
                .rewardFactor(1.0)
                .gamma(0.99)
                .errorClamp(100.0)
                .minEpsilon(0.1f)
                .epsilonNbStep(10)
                .doubleDQN(true)
                .build();


        DQNDenseNetworkConfiguration conf = DQNDenseNetworkConfiguration.builder()
                .updater(new Adam(0.7))
                .numHiddenNodes(64)
                .numLayers(4)
                .build();


        Board board = new Board();
        LogicGame logicGame = new LogicGame(board, new AI(false), new Human(true), 1, 3, 3, 0, 0);

        DiceChessGameMdp mdp = new DiceChessGameMdp(logicGame, true);

//        MultiLayerNetwork multiLayerNetwork = MultiLayerNetwork.load(new File("dice-chess-dqn.bin"), true);
//
//        DQNPolicy<LogicGame> policy = DQNPolicy.load("dice-chess-dqn.bin");


        QLearningDiscreteDense<LogicGame> dqn = new QLearningDiscreteDense<LogicGame>(mdp, conf, qConfig);

        System.out.println();
        dqn.train();

        mdp.close();

        try {
            dqn.getPolicy().save("dice-chess-dqn.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
