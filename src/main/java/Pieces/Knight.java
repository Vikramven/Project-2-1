package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Knight extends Piece {

    //TODO
    private int[][] blackCost = {{0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {15, 0, 15, 0, 0, 15, 0, 15},
                            {-5, 0, 0, 0, 0, 0, 0, -5},
                            {-5, 0, 0, 0, 0, 0, 0, -5},
                            {-5, 0, 0, 0, 0, 0, 0, -5},
                            {-5, 0, 0, 0, 0, 0, 0, -5},
                            { 0, 0, 0, 15, 15, 0, 0, 0}};

    private int[][] whiteCost = {{0, 0, 0, 15, 15, 0, 0, 0},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {15, 0, 15, 0, 0, 15, 0, 15},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0 }};

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
     * @param board Board
     * @param spot The spot where is located the piece
     * @return all possible legal moves
     */
    @Override
    public ArrayList<Move> allLegalMoves(Board board, Spot spot, int[][] costDynamic) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();


        //    - -
        //      |
        //      |
        //      K
        moveKnight(board, legalMoves, x, y, true, true, true, true, costDynamic);

        //      K
        //      |
        //      |
        //    - -
        moveKnight(board, legalMoves, x, y, false, false, true, true, costDynamic);


        //  |
        //  - - - K
        moveKnight(board, legalMoves, x, y, false, true, false, true, costDynamic) ;

        //        |
        //  K - - -
        moveKnight(board, legalMoves, x, y, false, false, false, true, costDynamic);

        //      - -
        //      |
        //      |
        //      K
        moveKnight(board, legalMoves, x, y, true, true, true, false, costDynamic);

        //      K
        //      |
        //      |
        //      - -
        moveKnight(board, legalMoves, x, y, false, false, true, false, costDynamic);

        //  - - - K
        //  |
        moveKnight(board, legalMoves, x, y, false, true, false, false, costDynamic) ;

        //  K - - -
        //        |
        moveKnight(board, legalMoves, x, y, false, false, false, false, costDynamic);


        return legalMoves;
    }

    /**
     *
     * @param board Board
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     * @param minusX goes minus X coordinate or not
     * @param minusY goes minus Y coordinate or not
     * @param horizontal goes horizontal or not
     * @param rotation define in which direction rotate the knight
     */
    private void moveKnight(Board board, ArrayList<Move> legalMoves, int x, int y, boolean minusX, boolean minusY,
                            boolean horizontal, boolean rotation, int[][]costDynamic){

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

        int costMove = 0;
        if(black)
            costMove = costDynamic[newX][newY] + blackCost[newX][newY];
        else
            costMove = costDynamic[newX][newY] + whiteCost[newX][newY];

        if(isObstacle(board.getSpot(newX, newY), legalMoves, costMove, x, y))
            return;

        legalMoves.add(new Move(newX, newY, this, costMove, x, y));
    }
}
