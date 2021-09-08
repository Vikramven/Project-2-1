package Pieces;

import Board.Board;
import Board.Spot;

import java.util.ArrayList;

public class Bishop extends Piece{


    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Bishop(boolean black) {
        super(black);
        name = "Bishop";
    }

    /**
     *
     * @param board Board
     * @param spot The spot where is located the piece
     * @return all possible legal moves
     */
    public ArrayList<Spot> allLegalMoves(Board board, Spot spot) {
        ArrayList<Spot> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();

        //   B
        //  /
        // /
        moveBishop(board, legalMoves, x, y, true, true);
        // \
        //  \
        //    B
        moveBishop(board, legalMoves, x, y, true, false);
        //B
        //  \
        //   \
        moveBishop(board, legalMoves, x, y, false, true);
        //   /
        //  /
        // B
        moveBishop(board, legalMoves, x, y, false, false);
        
        return legalMoves;
    }

    /**
     *
     * @param board Board
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     * @param minusX goes minus X coordinate
     * @param minusY goes minus Y coordinate
     */
    protected void moveBishop(Board board, ArrayList<Spot> legalMoves, int x, int y, boolean minusX, boolean minusY){
        for (int i = 0; i < 8; i++) {
                int newX;
                int newY;

                if(minusX)
                    newX = x-i;
                else
                    newX = x+i;

                if(minusY)
                    newY = y-i;
                else
                    newY = y+i;

                if(isBoardBounds(newX) && isBoardBounds(newY))
                    return;

                if(isObstacle(board.getSpot(newX, newY), legalMoves))
                    return;

                legalMoves.add(new Spot(newX, newY, null));
        }
    }
    
}
