package dice_chess.Logic.DQN;

import dice_chess.Board.Board;
import dice_chess.Board.Coordinate;
import dice_chess.Logic.AI.Algorithms.ExpectiMax;
import dice_chess.Logic.AI.HelpersAI.Node;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.ExecuteMovesAI;
import dice_chess.Logic.MoveLogic.Move;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;


import java.util.ArrayList;
import java.util.LinkedList;

public class DiceChessGameMdp implements MDP<LogicGame, Integer, DiscreteSpace> {


    private ArrayObservationSpace<LogicGame> gameArrayObservationSpace;
    private ArrayList<Move> actionSpace = new ArrayList<>();
    private LogicGame logicGame;
    private boolean blackSide;
    private ExecuteMovesAI executeMovesAI = new ExecuteMovesAI();
    private ActionSpace as = new ActionSpace();
    private int gameCount = 0;
    private int[][] arrayCoordinate;

    private final int MULTIPLIER = 100;


    public DiceChessGameMdp(LogicGame logicGame, boolean blackSide, int[][] arrayCoordinate) {
        this.logicGame = logicGame;
        this.blackSide = blackSide;
        this.arrayCoordinate = arrayCoordinate;
        gameArrayObservationSpace = new ArrayObservationSpace<>(new int[]{65});
    }

    @Override
    public ObservationSpace<LogicGame> getObservationSpace() {
        return gameArrayObservationSpace;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        actionSpace = as.actionSpace(logicGame, blackSide);

        return new DiscreteSpace(64);
    }

    @Override
    public LogicGame reset() {
        logicGame = new LogicGame(new Board(), true);
        gameCount++;

        System.out.println("GAME #" + gameCount);

        return logicGame;
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<LogicGame> step(Integer action) {

        int x = arrayCoordinate[action][0];
        int y = arrayCoordinate[action][1];



        if(actionSpace.size() == 0) {
            LinkedList<Coordinate> coordinatesPiece = logicGame.board.pieceMap.getAllPieces(logicGame.dicePiece, blackSide);

            if (coordinatesPiece.size() == 0) {
                logicGame.dl.rollDice(logicGame);
                logicGame.cp.changePlayer(logicGame);

                if(isDone()){
                    logicGame.board.print();
                    return new StepReply<>(logicGame, 0, isDone(), "Skip");
                }

                return new StepReply<>(logicGame, 0, isDone(), "Skip");
            }

            for (int i = 0; i < coordinatesPiece.size(); i++) {
                Coordinate pieceCord = coordinatesPiece.get(i);
                if(pieceCord.x == x && pieceCord.y == y) {
                    logicGame.dl.rollDice(logicGame);
                    logicGame.cp.changePlayer(logicGame);
                    if(isDone()){
                        logicGame.board.print();
                        return new StepReply<>(logicGame, 0, isDone(), "Skip");
                    }
                    return new StepReply<>(logicGame, 0, isDone(), "Skip");
                }
            }
        }

        Move executiveMove = null;
        for (int i = 0; i < actionSpace.size(); i++) {
            Move move = actionSpace.get(i);
            if(move.getX() == x && move.getY() == y) {
                executiveMove = move;
                break;
            }
        }

        if(executiveMove == null){
            return new StepReply<>(logicGame, -2, isDone(), "Illegal");
        }

        double reward = executeAction(executiveMove);


        if(isDone() && logicGame.board.pieceMap.getAllPieces(2, !blackSide).size() == 0) {
            logicGame.board.print();
        } else if(logicGame.board.pieceMap.getAllPieces(2, blackSide).size() == 0 && isDone()) {
            logicGame.board.print();
        }


        return new StepReply<>(logicGame, reward, isDone(), "Move");
    }

    @Override
    public boolean isDone() {
        return logicGame.winFlag;
    }

    @Override
    public MDP<LogicGame, Integer, DiscreteSpace> newInstance() {
        return new DiceChessGameMdp(new LogicGame(new Board(), true), blackSide, arrayCoordinate);
    }

    private ExpectiMax em = new ExpectiMax();

    private double executeAction(Move move){

        Node node = new Node();

        Node nodeMove = new Node(move, node);

        LogicGame cloneLogic = logicGame.clone();

        Node tmp = em.createTree(nodeMove, cloneLogic, !blackSide, cloneLogic.board, cloneLogic.dicePiece, 3, false);

        double cost = move.getCost();

        if(tmp != null) {
            if(tmp.getParent() != null) {
                while (!tmp.getParent().isRoot()) {
                    if (!tmp.isChanceNode()) {
                        if (tmp.getMove().getPiece().getColor() == blackSide)
                            cost += tmp.getCost();
                        else
                            cost += -tmp.getCost();
                    }

                    tmp = tmp.getParent();
                }
            }
        }

        actionSpace = new ArrayList<>();

        logicGame.currentSpot = logicGame.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

        //Add the move to the logic game
        logicGame.allLegalMoves = new ArrayList<>();
        logicGame.allLegalMoves.add(move);

        executeMovesAI.executeMovesAI(logicGame, move);

        return cost;
    }
}
