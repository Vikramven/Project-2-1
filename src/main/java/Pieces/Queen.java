package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Queen extends Piece {


    public int[][] cost = {{-20,-10,-10, -5, -5,-10,-10,-20},
                            {-10,  0,  0,  0,  0,  0,  0,-10},
                            {-10,  0,  5,  5,  5,  5,  0,-10},
                            {-5,  0,  5,  5,  5,  5,  0, -5},
                            {0,  0,  5,  5,  5,  5,  0, -5},
                            {-10,  5,  5,  5,  5,  5,  0,-10},
                            {-10,  0,  5,  0,  0,  0,  0,-10},
                            {-20,-10,-10, -5, -5,-10,-10,-20}};

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
    public ArrayList<Move> allLegalMoves(Board board, Spot spot) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();


        //Extend moves from Bishop
        Bishop moveLikeBishop = new Bishop(black);

        //   Q
        //  /
        // /
        moveLikeBishop.moveBishop(board, legalMoves, x, y, true, true, this);
        // \
        //  \
        //    Q
        moveLikeBishop.moveBishop(board, legalMoves, x, y, true, false, this);
        //Q
        //  \
        //   \
        moveLikeBishop.moveBishop(board, legalMoves, x, y, false, true, this);
        //   /
        //  /
        // Q
        moveLikeBishop.moveBishop(board, legalMoves, x, y, false, false, this);

        //Extend moves from Rook
        Rook moveLikeRook = new Rook(black);

        //--Q
        moveLikeRook.moveRook(board, legalMoves, x, y, true, true, true, this);
        //Q--
        moveLikeRook.moveRook(board, legalMoves, x, y, false, false, true, this);
        //  Q
        //  |
        //  |
        moveLikeRook.moveRook(board, legalMoves, x, y, false, true, false, this) ;
        //  |
        //  |
        //  Q
        moveLikeRook.moveRook(board, legalMoves, x, y, false, false, false, this);

        return legalMoves;
    }
}
