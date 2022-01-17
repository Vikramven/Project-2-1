package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.EvaluationFunction.EvaluationFunction;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Knight extends Piece {


    private final int[][] blackCost = {{0, 0, 0, 15, 15, 0, 0, 0},
                                 {0, 15, 15, 0, 0, 15, 15, 0},
                            {0, 0, 10, 10, 10, 10, 0, 0},
                            {5, 10, 0, 10, 0, 10, 0, 10},
                            {5, 0, 0, 0, 0, 0, 0, 0},
                            {15, 0, 15, 0, 0, 15, 0, 15},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            { 0, 0, 0, 0, 0, 0, 0, 0}};

    private final int[][] whiteCost = {{ 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {15, 0, 15, 0, 0, 15, 0, 15},
            {5, 0, 0, 0, 0, 0, 0, 0},
            {5, 10, 0, 10, 0, 10, 0, 10},
            {0, 0, 10, 10, 10, 10, 0, 0},
            {0, 15, 15, 0, 0, 15, 15, 0},
            {0, 0, 0, 15, 15, 0, 0, 0}};

    public int[][] getPositionCost(){
        if(black)
            return blackCost;

        return whiteCost;
    }

    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Knight(boolean black) {
        super(black);
        name = "Knight";
        nameInt = 1;
        if(black) { imageURL = "/black_knight.png"; }
        else { imageURL = "/white_knight.png"; }
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

        //    - -
        //      |
        //      |
        //      K
        moveKnight(l, legalMoves, x, y, true, true, true, true, ef);

        //      K
        //      |
        //      |
        //    - -
        moveKnight(l, legalMoves, x, y, false, false, true, true, ef);


        //  |
        //  - - - K
        moveKnight(l, legalMoves, x, y, false, true, false, true, ef) ;

        //        |
        //  K - - -
        moveKnight(l, legalMoves, x, y, false, false, false, true, ef);

        //      - -
        //      |
        //      |
        //      K
        moveKnight(l, legalMoves, x, y, true, true, true, false, ef);

        //      K
        //      |
        //      |
        //      - -
        moveKnight(l, legalMoves, x, y, false, false, true, false, ef);

        //  - - - K
        //  |
        moveKnight(l, legalMoves, x, y, false, true, false, false, ef) ;

        //  K - - -
        //        |
        moveKnight(l, legalMoves, x, y, false, false, false, false, ef);


        return legalMoves;
    }

    /**
     *
     * @param l Logic Game
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     * @param minusX goes minus X coordinate or not
     * @param minusY goes minus Y coordinate or not
     * @param horizontal goes horizontal or not
     * @param rotation define in which direction rotate the knight
     */
    private void moveKnight(LogicGame l, ArrayList<Move> legalMoves, int x, int y, boolean minusX, boolean minusY,
                            boolean horizontal, boolean rotation, EvaluationFunction ef){

        int newX;
        int newY;

        if(horizontal) {
            if (minusX)
                newX = x - 2;
            else
                newX = x + 2;

            if(rotation)
                newY = y - 1;
            else
                newY = y + 1;

            if(isBoardBounds(newY) || isBoardBounds(newX))
                return;

        } else {
            if (minusY)
                newY = y - 2;
            else
                newY = y + 2;

            if(rotation)
                newX = x - 1;
            else
                newX = x + 1;

            if(isBoardBounds(newY) || isBoardBounds(newX))
                return;
        }


        if(isObstacle(l.board.getSpot(newX, newY), legalMoves, x, y, this, ef, l))
            return;

        double costMove = ef.evaluateMove(x, y, this, black, newX, newY, l);

        legalMoves.add(new Move(newX, newY, this, costMove, x, y));
    }
}
