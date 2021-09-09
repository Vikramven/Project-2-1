package Pieces;

import Board.Board;
import Board.Spot;

import java.util.ArrayList;

public class Knight extends Piece{

    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Knight(boolean black) {
        super(black);
        name = "Knight";
    }

    /**
     *
     * @param board Board
     * @param spot The spot where is located the piece
     * @return all possible legal moves
     */
    @Override
    public ArrayList<Spot> allLegalMoves(Board board, Spot spot) {
        ArrayList<Spot> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();

        //  - - - K
        //  |
        moveKnight(board, legalMoves, x, y, true, true, true, true);

        //  K - - -
        //        |
        moveKnight(board, legalMoves, x, y, false, false, true, true);

        //      K
        //      |
        //      |
        //    - -
        moveKnight(board, legalMoves, x, y, false, true, false, true) ;

        //    - -
        //      |
        //      |
        //      K
        moveKnight(board, legalMoves, x, y, false, false, false, true);

        //  |
        //  - - - K
        moveKnight(board, legalMoves, x, y, true, true, true, false);

        //        |
        //  K - - -
        moveKnight(board, legalMoves, x, y, false, false, true, false);

        //      K
        //      |
        //      |
        //      - -
        moveKnight(board, legalMoves, x, y, false, true, false, false) ;

        //      - -
        //      |
        //      |
        //      K
        moveKnight(board, legalMoves, x, y, false, false, false, false);


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
    private void moveKnight(Board board, ArrayList<Spot> legalMoves, int x, int y, boolean minusX, boolean minusY, boolean horizontal, boolean rotation){

        int newX;
        int newY;

        if(horizontal) {
            if (minusX)
                newX = x - 3;
            else
                newX = x + 3;

            if(rotation)
                newY = y - 1;
            else
                newY = y + 1;

            if(isBoardBounds(newY) && isBoardBounds(newX))
                return;

        } else {
            if (minusY)
                newY = y - 3;
            else
                newY = y + 3;

            if(rotation)
                newX = x - 1;
            else
                newX = x + 1;

            if(isBoardBounds(newY) && isBoardBounds(newX))
                return;
        }


        legalMoves.add(new Spot(newX, newY, null));
    }
}
