package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public class Pawn extends Piece {


    //TODO promotion and 2 possible moves in the beginning of the game are the best moves, and the square before promotion is good good move
    //DOUBT:WHICH IS THE BLACK SIDE

    private int[][] blackCost = {{0 , 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {15, 0, 15, 0, 0, 15, 0, 15},
            {0, 15, 0, 15, 15, 0, 15, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},//DOUBT: NEUTRAL OR CHANGE THE VALUES?
            {5,  5,  5,  5,  5,  5,  5,  5},
            {10, 10, 10, 10, 10, 10, 10, 10},
            { 15, 15, 15, 15, 15, 15, 15, 15}};

    private int[][] whiteCost = {{ 15, 15, 15, 15, 15, 15, 15, 15},
            {10, 10, 10, 10, 10, 10, 10, 10},
            {5,  5,  5,  5,  5,  5,  5,  5},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 15, 0, 15, 15, 0, 15, 0},
            {15, 0, 15, 0, 0, 15, 0, 15},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0 , 0, 0, 0, 0, 0, 0, 0}};


    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Pawn(boolean black) {
        super(black);
        name = "Pawn";
        nameInt = 3;
        if(black) { imageURL = "/black_pawn.png"; }
        else { imageURL = "/white_pawn.png"; }
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

        //3 variants of moves

        //first and second:             *
        //first one is an usual one     P
        //                                                 *
        //                                                 *
        //second one is if a pawn stay on initial position P
        movePawn(x, y, board,  legalMoves, costDynamic);

        //                                                              \ /
        //third variant is where enemy piece is located on it diagonal   P
        takeEnemyPiece(x, y, board, legalMoves, costDynamic);

        return legalMoves;
    }

    /**
     *
     * @param board Board
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void movePawn(int x, int y, Board board, ArrayList<Move> legalMoves, int[][]costDynamic){
        int point = 1;

        if(checkInitialPosition(x))
            point++;

        for (int i = 1; i <= point; i++) {

            int newX;

            if(black)
                newX = x-i;
            else
                newX = x+i;

            if(isBoardBounds(newX))
                break;

            if(isObstaclePawn(board.getSpot(newX, y)))
                break;

            int costMove = costDynamic[newX][y] + blackCost[newX][y];


            legalMoves.add(new Move(newX, y, this, costMove, x, y));
        }
    }

    /**
     * Check if the pawn can take some enemy piece
     * @param x X coordinate
     * @param y Y coordinate
     * @param board Board
     * @param legalMoves all possible legal moves
     */
    private void takeEnemyPiece(int x, int y, Board board, ArrayList<Move> legalMoves, int[][] costDynamic){
        int newX = x+1;
        if(black)
            newX = x-1;

        if(isBoardBounds(newX))
            return;

        int left = y - 1;

        if(!isBoardBounds(left)) {
            int costMove = costDynamic[newX][left] + blackCost[newX][left];
            isObstacle(board.getSpot(newX, left), legalMoves, costMove, x, y);
        }

        int right = y + 1;

        if(!isBoardBounds(right)) {
            int costMove = costDynamic[newX][right] + blackCost[newX][right];
            isObstacle(board.getSpot(newX, right), legalMoves, costMove, x, y);
        }
    }

    /**
     * Check if the pawn is stayed on the initial position
     * @param x line
     * @return true = stay: false = not stay
     */
    private boolean checkInitialPosition(int x){
        boolean initialPosition = false;

        if(!black && x == 1){
            initialPosition = true;
        } else if(black && x == 6) {
            initialPosition = true;
        }

        return initialPosition;
    }

    /**
     * Obstacle for the pawn
     * @param spot The Spot
     * @return true = obstacle: false = no obstacle
     */
    private boolean isObstaclePawn(Spot spot){
        return spot != null;
    }
}
