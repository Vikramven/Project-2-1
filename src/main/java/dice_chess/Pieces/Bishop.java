package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
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
     * @param board _dice_chess.Board
     * @param spot The spot where is located the piece
     * @return all possible legal moves
     */
    @Override
    public ArrayList<Move> allLegalMoves(Board board, Spot spot, int[][] costDynamic) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();

        //   B
        //  /
        // /
        moveBishop(board, legalMoves, x, y, true, true, this, costDynamic);
        // \
        //  \
        //    B
        moveBishop(board, legalMoves, x, y, true, false, this, costDynamic);
        //B
        //  \
        //   \
        moveBishop(board, legalMoves, x, y, false, true, this, costDynamic);
        //   /
        //  /
        // B
        moveBishop(board, legalMoves, x, y, false, false, this, costDynamic);
        
        return legalMoves;
    }

    /**
     *
     * @param board _dice_chess.Board
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     * @param minusX goes minus X coordinate
     * @param minusY goes minus Y coordinate
     */
    protected void moveBishop(Board board, ArrayList<Move> legalMoves, int x, int y, boolean minusX, boolean minusY, Piece piece, int[][] costDynamic){
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

                int costMove=0;
                if(black){
                    costMove = costDynamic[newX][newY] + blackCost[newX][newY];
                }
                else{
                    costMove = costDynamic[newX][newY] + whiteCost[newX][newY];
                }

                costMove += pythagorasKingEvaluation(board, black, newX, newY);

                if(isObstacle(board.getSpot(newX, newY), legalMoves, costMove, x, y, this))
                    return;

                legalMoves.add(new Move(newX, newY, piece, costMove, x, y));
        }
    }
    
}
