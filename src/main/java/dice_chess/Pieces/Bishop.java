package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.EvaluationFunction.EvaluationFunction;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Bishop extends Piece {


    //TODO improve this in the PHASE 3
    private final int[][] blackCost = {{10, 0, 0, 15, 15, 0, 0, 10},//bishop can't move in the first row, so keep it 0
                                 {5, 10, 15, 15, 15, 15, 10, 5},
                                 {-5, 10, 0, 0, 15, 0, 10, -5},
                                 {5, 10, 0, 0, 0, 15, 0, 5},
                                 {5, 0, 10, 0, 0, 0, 15, 5},
                                 {-5, 15, 10, 0, 0, 10, 10, -5},
                                 {-5, 5, 15, 10, 15, 10, 5, -5},
                                 { 0, 0, 0, 0, 0, 0, 0, 0}};


    private final int[][] whiteCost = {{ 0, 0, 0, 0, 0, 0, 0, 0},//bishop can't move in the first row, so keep it 0
            {-5, 5, 15, 10, 15, 10, 5, -5},
            {-5, 15, 10, 0, 0, 10, 10, -5},
            {5, 0, 10, 0, 0, 0, 15, 5},
            {5, 10, 0, 0, 0, 15, 0, 5},
            {-5, 10, 0, 0, 15, 0, 10, -5},
            {5, 10, 15, 15, 15, 15, 10, 5},
            {10, 0, 0, 15, 15, 0, 0, 10}};

    public int[][] getPositionCost(){
        if(black)
            return blackCost;

        return whiteCost;
    }

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

        //   B
        //  /
        // /
        moveBishop(l, legalMoves, x, y, true, true, this, ef);
        // \
        //  \
        //    B
        moveBishop(l, legalMoves, x, y, true, false, this, ef);
        //B
        //  \
        //   \
        moveBishop(l, legalMoves, x, y, false, true, this, ef);
        //   /
        //  /
        // B
        moveBishop(l, legalMoves, x, y, false, false, this, ef);
        
        return legalMoves;
    }

    /**
     *
     * @param l Logic Game
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     * @param minusX goes minus X coordinate
     * @param minusY goes minus Y coordinate
     */
    protected void moveBishop(LogicGame l, ArrayList<Move> legalMoves, int x, int y, boolean minusX, boolean minusY, Piece piece, EvaluationFunction ef){
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

                if(isObstacle(l.board.getSpot(newX, newY), legalMoves, x, y, this, ef, l))
                    return;

                double costMove = ef.evaluateMove(x, y, this, black, newX, newY, l);

                legalMoves.add(new Move(newX, newY, piece, costMove, x, y));
        }
    }
    
}
