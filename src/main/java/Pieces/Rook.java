package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Rook extends Piece {


    private boolean castling = true;

    //TODO
    int[][] cost = new int[8][8];

    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Rook(boolean black) {
        super(black);
        name = "Rook";
        nameInt = 5;
        if(black) { imageURL = "/black_rook.png"; }
        else { imageURL = "/white_rook.png"; }
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

        //--R
        moveRook(board, legalMoves, x, y, true, true, true, this, costDynamic);
        //R--
        moveRook(board, legalMoves, x, y, false, false, true, this, costDynamic);
        //  R
        //  |
        //  |
        moveRook(board, legalMoves, x, y, false, true, false, this, costDynamic) ;
        //  |
        //  |
        //  R
        moveRook(board, legalMoves, x, y, false, false, false, this, costDynamic);

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
    protected void moveRook(Board board, ArrayList<Move> legalMoves, int x, int y,
                            boolean minusX, boolean minusY, boolean horizontal, Piece piece, int[][] costDynamic){
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


                int costMove = costDynamic[newX][newY] + cost[newX][newY];

                if(isObstacle(board.getSpot(newX, newY), legalMoves, costMove, x, y))
                    return;

                legalMoves.add(new Move(newX, newY, piece, costMove, x, y));
        }
    }

    public boolean isCastling() {
        return castling;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }
}
