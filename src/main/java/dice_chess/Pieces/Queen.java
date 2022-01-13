package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.EvaluationFunction.EvaluationFunction;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

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
     * @param l LogicGame
     * @param spot The spot where is located the piece
     * @return all possible legal moves
     */
    @Override
    public ArrayList<Move> allLegalMoves(LogicGame l, Spot spot, int evalFunction) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();

        EvaluationFunction ef = new EvaluationFunction(evalFunction, black, l);

        //Extend moves from Bishop
        Bishop moveLikeBishop = new Bishop(black);

        //   Q
        //  /
        // /
        moveLikeBishop.moveBishop(l, legalMoves, x, y, true, true, this, ef);
        // \
        //  \
        //    Q
        moveLikeBishop.moveBishop(l, legalMoves, x, y, true, false, this, ef);
        //Q
        //  \
        //   \
        moveLikeBishop.moveBishop(l, legalMoves, x, y, false, true, this, ef);
        //   /
        //  /
        // Q
        moveLikeBishop.moveBishop(l, legalMoves, x, y, false, false, this, ef);

        //Extend moves from Rook
        Rook moveLikeRook = new Rook(black);

        //--Q
        moveLikeRook.moveRook(l, legalMoves, x, y, true, true, true, this, ef);
        //Q--
        moveLikeRook.moveRook(l, legalMoves, x, y, false, false, true, this, ef);
        //  Q
        //  |
        //  |
        moveLikeRook.moveRook(l, legalMoves, x, y, false, true, false, this, ef) ;
        //  |
        //  |
        //  Q
        moveLikeRook.moveRook(l, legalMoves, x, y, false, false, false, this, ef);

        return legalMoves;
    }
}
