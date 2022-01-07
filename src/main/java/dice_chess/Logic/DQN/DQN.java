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

        int stepPerEpoch = 1000;
        int maxGames = 1000;

        QLearningConfiguration qConfig = QLearningConfiguration.builder()
                .seed(1337L)
                .maxEpochStep(stepPerEpoch)
                .maxStep(stepPerEpoch * maxGames)
                .updateStart(10)
                .rewardFactor(1.0)
                .gamma(0.99)
                .errorClamp(1.0)
                .batchSize(16)
                .minEpsilon(0.0)
                .epsilonNbStep(128)
                .expRepMaxSize(128 * 16)
                .doubleDQN(false)
                .build();


        DQNDenseNetworkConfiguration conf = DQNDenseNetworkConfiguration.builder()
                .updater(new Adam(0.4))
                .numHiddenNodes(64)
                .numLayers(10)
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
