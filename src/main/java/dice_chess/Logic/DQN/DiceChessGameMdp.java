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
    private ArrayList<Move> actionSpace = new ArrayList<>();
    private LogicGame logicGame;
    private boolean blackSide;
    private ExecuteMovesAI executeMovesAI = new ExecuteMovesAI();
    private ActionSpace as = new ActionSpace();
    private int gameCount = 0;


    public DiceChessGameMdp(LogicGame logicGame, boolean blackSide) {
        this.logicGame = logicGame;
        this.blackSide = blackSide;
        gameArrayObservationSpace = new ArrayObservationSpace<>(new int[]{65});
    }

    @Override
    public ObservationSpace<LogicGame> getObservationSpace() {
        return gameArrayObservationSpace;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        actionSpace = as.actionSpace(logicGame, blackSide);

        return new DiscreteSpace(139);
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
            System.out.println("GGGGGGGGGGGGGGGGGGGGQQQQQQ");
            logicGame.board.print();
            logicGame.whiteWin++;
            new StepReply<>(logicGame, -1000, isDone(), null);
        }


        if(actionSpace.size() == 0 || actionSpace.size() <= action){
            logicGame.dl.rollDice(logicGame);
            logicGame.cp.changePlayer(logicGame);
            if(isDone()) {
                logicGame.board.print();
                logicGame.whiteWin++;
                return new StepReply<>(logicGame, -1000, isDone(), "Skip");
            }

            return new StepReply<>(logicGame, -10, isDone(), "Skip");
        }

        Move move = actionSpace.get(action);

        actionSpace = new ArrayList<>();

        double reward = move.getCost();

        logicGame.currentSpot = logicGame.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

        //Add the move to the logic game
        logicGame.allLegalMoves = new ArrayList<>();
        logicGame.allLegalMoves.add(move);

        executeMovesAI.executeMovesAI(logicGame, move);

//        logicGame.board.print();

        if(isDone()) {
            logicGame.board.print();
            logicGame.blackWin++;
        }

//        logicGame.board.print();

        if(logicGame.board.pieceMap.getAllPieces(2, blackSide).size() == 0) {
            reward = -1000;
            logicGame.whiteWin++;
            logicGame.blackWin--;
        }


        return new StepReply<>(logicGame, reward, isDone(), "Move");
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
