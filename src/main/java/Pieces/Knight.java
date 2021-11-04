package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Knight extends Piece {

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
    public ArrayList<Move> allLegalMoves(Board board, Spot spot, int[][] cost) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();


        //    - -
        //      |
        //      |
        //      K
        moveKnight(board, legalMoves, x, y, true, true, true, true, cost);

        //      K
        //      |
        //      |
        //    - -
        moveKnight(board, legalMoves, x, y, false, false, true, true, cost);


        //  |
        //  - - - K
        moveKnight(board, legalMoves, x, y, false, true, false, true, cost) ;

        //        |
        //  K - - -
        moveKnight(board, legalMoves, x, y, false, false, false, true, cost);

        //      - -
        //      |
        //      |
        //      K
        moveKnight(board, legalMoves, x, y, true, true, true, false, cost);

        //      K
        //      |
        //      |
        //      - -
        moveKnight(board, legalMoves, x, y, false, false, true, false, cost);

        //  - - - K
        //  |
        moveKnight(board, legalMoves, x, y, false, true, false, false, cost) ;

        //  K - - -
        //        |
        moveKnight(board, legalMoves, x, y, false, false, false, false, cost);


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
    private void moveKnight(Board board, ArrayList<Move> legalMoves, int x, int y, boolean minusX, boolean minusY, boolean horizontal, boolean rotation, int[][]cost){

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

        int costMove = cost[newX][newY];

        if(isObstacle(board.getSpot(newX, newY), legalMoves, costMove, x, y))
            return;

        legalMoves.add(new Move(newX, newY, this, costMove, x, y));
    }
}
