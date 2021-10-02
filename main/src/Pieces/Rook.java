package Pieces;

import Board.Board;
import Board.Spot;

import java.util.ArrayList;

public class Rook extends Piece{


    private boolean castling = true;
    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Rook(boolean black) {
        super(black);
        name = "Rook";
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

        //--R
        moveRook(board, legalMoves, x, y, true, true, true);
        //R--
        moveRook(board, legalMoves, x, y, false, false, true);
        //  R
        //  |
        //  |
        moveRook(board, legalMoves, x, y, false, true, false) ;
        //  |
        //  |
        //  R
        moveRook(board, legalMoves, x, y, false, false, false);

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
     */
    protected void moveRook(Board board, ArrayList<Spot> legalMoves, int x, int y, boolean minusX, boolean minusY, boolean horizontal){
        for (int i = 1; i < 8; i++) {

                int newX = x;
                int newY = y;

                if(horizontal) {
                    if (minusX)
                        newX = x - i;
                    else
                        newX = x + i;

                    if(isBoardBounds(newX))
                        return;

                } else {
                    if (minusY)
                        newY = y - i;
                    else
                        newY = y + i;

                    if(isBoardBounds(newY))
                        return;
                }

                if(isObstacle(board.getSpot(newX, newY), legalMoves))
                    return;

                legalMoves.add(new Spot(newX, newY, null));
        }
    }

    public boolean isCastling() {
        return castling;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }
}
