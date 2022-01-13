package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.EvaluationFunction.EvaluationFunction;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Rook extends Piece {


    private boolean castling = true;

    //TODO improve this in the PHASE 3
    private final int[][] whiteCost = {{0, 0, 0, 0, 0, 0, 0, 0},
                                 {0, 0, 0, 0, 0, 0, 0, 0},
                                 {15, 5, 10, 0, 0, 10, 5, 15},
                                 {15, 5, 10, 0, 0, 10, 5, 15},
                                 {15, 5, 10, 0, 0, 10, 5, 15},
                                 {15, 5, 10, 0, 0, 10, 5, 15},
                                 {15, 5, 15, 15, 15, 15, 5, 15},
                                 {15, 5, 15, 15, 15, 15, 5, 15}};

    private final int[][] blackCost = {{15, 5, 15, 15, 15, 15, 5, 15},
            {15, 5, 15, 15, 15, 15, 15, 15},
            {15, 5, 10, 0, 0, 10, 5, 15},
            {15, 5, 10, 0, 0, 10, 5, 15},
            {15, 5, 10, 0, 0, 10, 5, 15},
            {15, 5, 10, 0, 0, 10, 5, 15},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    public int[][] getPositionCost(){
        if(black)
            return blackCost;

        return whiteCost;
    }

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

        //--R
        moveRook(l, legalMoves, x, y, true, true, true, this, ef);
        //R--
        moveRook(l, legalMoves, x, y, false, false, true, this, ef);
        //  R
        //  |
        //  |
        moveRook(l, legalMoves, x, y, false, true, false, this, ef) ;
        //  |
        //  |
        //  R
        moveRook(l, legalMoves, x, y, false, false, false, this, ef);

        return legalMoves;
    }

    /**
     *
     * @param l Logic Game
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     * @param minusX goes minus X coordinate or not
     * @param minusY goes minus Y coordinate or not
     * @param horizontal goes horizontal or not
     */
    protected void moveRook(LogicGame l, ArrayList<Move> legalMoves, int x, int y,
                            boolean minusX, boolean minusY, boolean horizontal, Piece piece, EvaluationFunction ef){
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

                if(isObstacle(l.board.getSpot(newX, newY), legalMoves, x, y, this, ef, l))
                    return;

                double costMove = ef.evaluateMove(x, y, this, black, newX, newY, l);

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
