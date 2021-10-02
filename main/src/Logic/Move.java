package Logic;

import Board.Board;
import Board.Spot;
import Pieces.Piece;

import java.util.ArrayList;

public class Move {


    /**
     * Move the piece
     * @param board Board
     * @param spot Spot
     * @param legalMoves all legal possible moves for the piece
     * @param x X coordinate which choose the player
     * @param y Y coordinate which choose the player
     */
    public boolean movePiece(Board board, Spot spot, ArrayList<Spot> legalMoves, int x, int y){

        for (int i = 0; i < legalMoves.size(); i++) {
            if(x == legalMoves.get(i).getX() && y == legalMoves.get(i).getY()){
                int oldX = spot.getX();
                int oldY = spot.getY();
                board.setSpot(null, oldX, oldY);

                spot.setX(x);
                spot.setY(y);
                board.setSpot(spot, x, y);

                if(spot.getPiece().getName().equals("Pawn")){
                    checkEnPassant(spot);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Check on EnPassant
     * @param spot Spot of the pawn
     */
    private void checkEnPassant(Spot spot){
        Piece pawn = spot.getPiece();
        if(pawn.isColor().equals("White")){
            if(spot.getX() == 7)
                enPassant(spot);
        } else {
            if(spot.getX() == 0)
                enPassant(spot);
        }
    }

    /**
     * Do EnPassant
     * @param spot The spot of the pawn
     */
    private void enPassant(Spot spot){
       //TODO: CALL THE GUI and player choose which piece he/she wants
    }
}
