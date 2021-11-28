package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Queen extends Piece {


    //TODO
    int[][] cost = new int[8][8];


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
     * @param board _dice_chess.Board
     * @param spot The spot where is located the piece
     * @return all possible legal moves
     */
    @Override
    public ArrayList<Move> allLegalMoves(Board board, Spot spot, int[][] costDynamic) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();


        //Extend moves from Bishop
        Bishop moveLikeBishop = new Bishop(black);

        //   Q
        //  /
        // /
        moveLikeBishop.moveBishop(board, legalMoves, x, y, true, true, this, costDynamic);
        // \
        //  \
        //    Q
        moveLikeBishop.moveBishop(board, legalMoves, x, y, true, false, this, costDynamic);
        //Q
        //  \
        //   \
        moveLikeBishop.moveBishop(board, legalMoves, x, y, false, true, this, costDynamic);
        //   /
        //  /
        // Q
        moveLikeBishop.moveBishop(board, legalMoves, x, y, false, false, this, costDynamic);

        //Extend moves from Rook
        Rook moveLikeRook = new Rook(black);

        //--Q
        moveLikeRook.moveRook(board, legalMoves, x, y, true, true, true, this, costDynamic);
        //Q--
        moveLikeRook.moveRook(board, legalMoves, x, y, false, false, true, this, costDynamic);
        //  Q
        //  |
        //  |
        moveLikeRook.moveRook(board, legalMoves, x, y, false, true, false, this, costDynamic) ;
        //  |
        //  |
        //  Q
        moveLikeRook.moveRook(board, legalMoves, x, y, false, false, false, this, costDynamic);

        return legalMoves;
    }
}
