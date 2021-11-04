package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Bishop extends Piece {
    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Bishop(boolean black) {
        super(black);
        name = "Bishop";
        nameInt = 0;
        if(black) { imageURL = "/black_bishop.png"; }
        else { imageURL = "/white_bishop.png"; }
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

        //   B
        //  /
        // /
        moveBishop(board, legalMoves, x, y, true, true, this, cost);
        // \
        //  \
        //    B
        moveBishop(board, legalMoves, x, y, true, false, this, cost);
        //B
        //  \
        //   \
        moveBishop(board, legalMoves, x, y, false, true, this, cost);
        //   /
        //  /
        // B
        moveBishop(board, legalMoves, x, y, false, false, this, cost);
        
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
    protected void moveBishop(Board board, ArrayList<Move> legalMoves, int x, int y, boolean minusX, boolean minusY, Piece piece, int[][] cost){
        for (int i = 1; i < 8; i++) {
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

                if(isBoardBounds(newX) || isBoardBounds(newY))
                    return;

                int costMove = cost[newX][newY];

                if(isObstacle(board.getSpot(newX, newY), legalMoves, costMove, x, y))
                    return;

                legalMoves.add(new Move(newX, newY, piece, costMove, x, y));
        }
    }
    
}
