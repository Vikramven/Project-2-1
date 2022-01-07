package dice_chess.Logic.DQN;

import dice_chess.Board.Board;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.ExecuteMovesAI;
import dice_chess.Logic.MoveLogic.Move;
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
    private int gameCount = 0;


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

            if(actionSpace.size() == 0)
                return new DiscreteSpace(1);

            return new DiscreteSpace(actionSpace.size());
        }
        return new DiscreteSpace(1);
    }

    @Override
    public LogicGame reset() {
        logicGame = new LogicGame(new Board(), logicGame.playerWhite.clone(), logicGame.playerBlack.clone(),
                logicGame.AIwhite, logicGame.AIblack, logicGame.depth, logicGame.whiteWin, logicGame.blackWin);
        gameCount++;

        System.out.println("GAME #" + gameCount);

        return logicGame;
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<LogicGame> step(Integer action) {
        if(isDone()){ //TODO when we can start game with DQN  !!!!CHANGE WIN CONDITION!!!!
            logicGame.board.print();
            new StepReply<>(logicGame, -50, isDone(), null);
        }


        if(actionSpace.size() == 0){
            logicGame.dl.rollDice(logicGame);
            logicGame.cp.changePlayer(logicGame);
            logicGame.board.print();
            return new StepReply<>(logicGame, 0, isDone(), null);
        }

        Move move = actionSpace.get(action);


        double reward = move.getCost();

        logicGame.currentSpot = logicGame.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

        //Add the move to the logic game
        logicGame.allLegalMoves = new ArrayList<>();
        logicGame.allLegalMoves.add(move);

        executeMovesAI.executeMovesAI(logicGame, move);

        logicGame.board.print();


        return new StepReply<>(logicGame, reward, isDone(), null);
    }

    @Override
    public boolean isDone() {
        return logicGame.winFlag;
    }

    @Override
    public MDP<LogicGame, Integer, DiscreteSpace> newInstance() {
        return new DiceChessGameMdp(new LogicGame(new Board(), logicGame.playerWhite.clone(), logicGame.playerBlack.clone(),
                logicGame.AIwhite, logicGame.AIblack, logicGame.depth, logicGame.whiteWin, logicGame.blackWin), blackSide);
    }
}
