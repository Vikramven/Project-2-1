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
import org.deeplearning4j.rl4j.util.DataManagerTrainingListener;
import org.deeplearning4j.rl4j.util.IDataManager;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nadam;


import java.io.File;
import java.io.IOException;

public class DQN {


    public DQN(int AI, String text) throws IOException {

        int stepPerEpoch = 16;

        QLearningConfiguration qConfig = QLearningConfiguration.builder()
                .seed(123L)
                .maxEpochStep(900)
                .maxStep(900000)
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
                .numHiddenNodes(65)
                .numLayers(3)
                .build();


        Board board = new Board();
        LogicGame logicGame = new LogicGame(board, new AI(false), new Human(true), AI, 3, 3, 0, 0, true);

        DiceChessGameMdp mdp = new DiceChessGameMdp(logicGame, true);

        IDataManager dataManager = new DataManager(true);


        QLearningDiscreteDense<LogicGame> dqn = new QLearningDiscreteDense<LogicGame>(mdp, conf, qConfig);
        dqn.addListener(new DataManagerTrainingListener(dataManager));

        System.out.println();
        dqn.train();

        mdp.close();

        try {
            dqn.getPolicy().save(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
