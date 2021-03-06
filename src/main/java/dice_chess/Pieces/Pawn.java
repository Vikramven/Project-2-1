package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.EvaluationFunction.EvaluationFunction;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import org.apache.commons.math3.analysis.function.Log;

import java.util.ArrayList;

public class Pawn extends Piece {


    //TODO improve this in the PHASE 3
    private final int[][] whiteCost = {{0 , 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {10, 0, 10, 0, 0, 10, 0, 10},
            {0, 5, 0, 15, 15, 0, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},//DOUBT: NEUTRAL OR CHANGE THE VALUES?
            {5,  5,  5,  5,  5,  5,  5,  5},
            {10, 10, 10, 10, 10, 10, 10, 10},
            { 15, 15, 15, 15, 15, 15, 15, 15}};

    private final int[][] blackCost = {{ 15, 15, 15, 15, 15, 15, 15, 15},
            {10, 10, 10, 10, 10, 10, 10, 10},
            {5,  5,  5,  5,  5,  5,  5,  5},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 5, 0, 15, 15, 0, 5, 0},
            {10, 0, 10, 0, 0, 10, 0, 10},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0 , 0, 0, 0, 0, 0, 0, 0}};

    public int[][] getPositionCost(){
        if(black)
            return blackCost;

        return whiteCost;
    }


    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Pawn(boolean black) {
        super(black);
        name = "Pawn";
        nameInt = 3;
        if(black) { imageURL = "/black_pawn.png"; }
        else { imageURL = "/white_pawn.png"; }
    }

    /**
     *
     * @param l LogicGame
     * @param spot The spot where is located the piece
     * @return all possible legal moves
     */
    @Override
    public ArrayList<Move> allLegalMoves(LogicGame l, Spot spot, int evalFunction) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();

        EvaluationFunction ef = new EvaluationFunction(evalFunction, black, l);
        //3 variants of moves

        //first and second:             *
        //first one is an usual one     P
        //                                                 *
        //                                                 *
        //second one is if a pawn stay on initial position P
        movePawn(x, y, l,  legalMoves, ef);

        //                                                              \ /
        //third variant is where enemy piece is located on it diagonal   P
        takeEnemyPiece(x, y, l, legalMoves, ef);

        return legalMoves;
    }

    /**
     *
     * @param l LogicGame
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void movePawn(int x, int y, LogicGame l, ArrayList<Move> legalMoves, EvaluationFunction ef){
        int point = 1;

        if(checkInitialPosition(x))
            point++;

        for (int i = 1; i <= point; i++) {

            int newX;

            if(black)
                newX = x-i;
            else
                newX = x+i;

            if(isBoardBounds(newX))
                break;

            if(isObstaclePawn(l.board.getSpot(newX, y)))
                break;

            double costMove = ef.evaluateMove(x, y, this, black, newX, y, l);

            legalMoves.add(new Move(newX, y, this, costMove, x, y));
        }
    }

    /**
     * Check if the pawn can take some enemy piece
     * @param x X coordinate
     * @param y Y coordinate
     * @param l LogicGame
     * @param legalMoves all possible legal moves
     */
    private void takeEnemyPiece(int x, int y, LogicGame l, ArrayList<Move> legalMoves, EvaluationFunction ef){
        int newX = x+1;
        if(black)
            newX = x-1;

        if(isBoardBounds(newX))
            return;

        int left = y - 1;

        if(!isBoardBounds(left)) {
            isObstacle(l.board.getSpot(newX, left), legalMoves, x, y, this, ef, l);
        }

        int right = y + 1;

        if(!isBoardBounds(right)) {
            isObstacle(l.board.getSpot(newX, right), legalMoves, x, y, this, ef, l);
        }
    }

    /**
     * Check if the pawn is stayed on the initial position
     * @param x line
     * @return true = stay: false = not stay
     */
    private boolean checkInitialPosition(int x){
        boolean initialPosition = false;

        if(!black && x == 1){
            initialPosition = true;
        } else if(black && x == 6) {
            initialPosition = true;
        }

        return initialPosition;
    }

    /**
     * Obstacle for the pawn
     * @param spot The Spot
     * @return true = obstacle: false = no obstacle
     */
    private boolean isObstaclePawn(Spot spot){
        return spot != null;
    }
}
