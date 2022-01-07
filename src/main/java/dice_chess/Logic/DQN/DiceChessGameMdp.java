package dice_chess.Logic.DQN;

import dice_chess.Board.Coordinate;
import dice_chess.Board.Spot;
import dice_chess.GUI.GameScene;
import dice_chess.GUI.IntroScene;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.ExecuteMovesAI;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;
import javafx.stage.Stage;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import java.util.ArrayList;

public class DiceChessGameMdp implements MDP<LogicGame, Integer, DiscreteSpace> {


    private ArrayObservationSpace<LogicGame> gameArrayObservationSpace;
    private ArrayList<Move> actionSpace;
    private LogicGame logicGame;
    private boolean blackSide;
    private ExecuteMovesAI executeMovesAI = new ExecuteMovesAI();
    private ActionSpace as = new ActionSpace();


    public DiceChessGameMdp(LogicGame logicGame, boolean blackSide) {
        this.logicGame = logicGame;
        this.blackSide = blackSide;
        gameArrayObservationSpace = new ArrayObservationSpace<>(new int[]{64});
    }

    @Override
    public ObservationSpace<LogicGame> getObservationSpace() {
        return gameArrayObservationSpace;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        if(logicGame.blackMove == blackSide){

            actionSpace = as.actionSpace(logicGame, blackSide);

            return new DiscreteSpace(actionSpace.size());
        }
        return new DiscreteSpace(0);
    }

    @Override
    public LogicGame reset() {
        GameScene gameSc = logicGame.getGameSc();
        Stage mainStage = logicGame.getMainStage();
        gameSc.setGameScene(gameSc.getPlayers());
        mainStage.setScene(gameSc.getGameScene());
        mainStage.setFullScreen(true);
        mainStage.setResizable(false);
        return logicGame;
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<LogicGame> step(Integer action) {
        if(isDone()){ //TODO when we can start game with DQN  !!!!CHANGE WIN CONDITION!!!!
            new StepReply<>(logicGame, -50, isDone(), null);
        }

        Move move = actionSpace.get(action);

        double reward = move.getCost();

        executeMovesAI.executeMovesAI(logicGame, move);

        return new StepReply<>(logicGame, reward, isDone(), null);
    }

    @Override
    public boolean isDone() {
        return logicGame.winFlag;
    }

    @Override
    public MDP<LogicGame, Integer, DiscreteSpace> newInstance() {
        return new DiceChessGameMdp(reset(), blackSide);
    }
}
