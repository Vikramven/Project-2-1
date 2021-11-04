package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Queen extends Piece {
    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Queen(boolean black) {
        super(black);
        name = "Queen";
        nameInt = 4;
        if(black) { imageURL = "/black_queen.png"; }
        else { imageURL = "/white_queen.png"; }
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


        //Extend moves from Bishop
        Bishop moveLikeBishop = new Bishop(black);

        //   Q
        //  /
        // /
        moveLikeBishop.moveBishop(board, legalMoves, x, y, true, true, this, cost);
        // \
        //  \
        //    Q
        moveLikeBishop.moveBishop(board, legalMoves, x, y, true, false, this, cost);
        //Q
        //  \
        //   \
        moveLikeBishop.moveBishop(board, legalMoves, x, y, false, true, this, cost);
        //   /
        //  /
        // Q
        moveLikeBishop.moveBishop(board, legalMoves, x, y, false, false, this, cost);

        //Extend moves from Rook
        Rook moveLikeRook = new Rook(black);

        //--Q
        moveLikeRook.moveRook(board, legalMoves, x, y, true, true, true, this, cost);
        //Q--
        moveLikeRook.moveRook(board, legalMoves, x, y, false, false, true, this, cost);
        //  Q
        //  |
        //  |
        moveLikeRook.moveRook(board, legalMoves, x, y, false, true, false, this, cost) ;
        //  |
        //  |
        //  Q
        moveLikeRook.moveRook(board, legalMoves, x, y, false, false, false, this, cost);

        return legalMoves;
    }
}
