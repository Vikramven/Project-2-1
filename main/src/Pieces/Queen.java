package Pieces;

import Board.Board;
import Board.Spot;

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
        if(black) { imageURL = "/res/black_queen.png"; }
        else { imageURL = "/res/white_queen.png"; }
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


        //Extend moves from Bishop
        Bishop moveLikeBishop = new Bishop(black);

        //   Q
        //  /
        // /
        moveLikeBishop.moveBishop(board, legalMoves, x, y, true, true);
        // \
        //  \
        //    Q
        moveLikeBishop.moveBishop(board, legalMoves, x, y, true, false);
        //Q
        //  \
        //   \
        moveLikeBishop.moveBishop(board, legalMoves, x, y, false, true);
        //   /
        //  /
        // Q
        moveLikeBishop.moveBishop(board, legalMoves, x, y, false, false);

        //Extend moves from Rook
        Rook moveLikeRook = new Rook(black);

        //--Q
        moveLikeRook.moveRook(board, legalMoves, x, y, true, true, true);
        //Q--
        moveLikeRook.moveRook(board, legalMoves, x, y, false, false, true);
        //  Q
        //  |
        //  |
        moveLikeRook.moveRook(board, legalMoves, x, y, false, true, false) ;
        //  |
        //  |
        //  Q
        moveLikeRook.moveRook(board, legalMoves, x, y, false, false, false);

        return legalMoves;
    }
}
