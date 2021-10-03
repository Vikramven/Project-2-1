package Pieces;

import Board.Board;
import Board.Spot;

import java.util.ArrayList;

public class Pawn extends Piece {

    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public Pawn(boolean black) {
        super(black);
        name = "Pawn";
        if(black) { imageURL = "/res/black_pawn.png"; }
        else { imageURL = "/res/white_pawn.png"; }
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

        //3 variants of moves

        //first and second:             *
        //first one is an usual one     P
        //                                                 *
        //                                                 *
        //second one is if a pawn stay on initial position P
        movePawn(x, y, board,  legalMoves);

        //                                                              \ /
        //third variant is where enemy piece is located on it diagonal   P
        takeEnemyPiece(x, y, board, legalMoves);

        return legalMoves;
    }

    /**
     *
     * @param board Board
     * @param legalMoves all possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void movePawn(int x, int y, Board board, ArrayList<Spot> legalMoves){
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

            legalMoves.add(new Spot(newX, y, null));
        }
    }

    /**
     * Check if the pawn can take some enemy piece
     * @param x X coordinate
     * @param y Y coordinate
     * @param board Board
     * @param legalMoves all possible legal moves
     */
    private void takeEnemyPiece(int x, int y, Board board, ArrayList<Spot> legalMoves){
        int newX = x+1;
        if(black)
            newX = x-1;

        if(isBoardBounds(newX))
            return;

        int left = y - 1;
        if(!isBoardBounds(left)) {
            isObstacle(board.getSpot(newX, left), legalMoves);
        }

        int right = y + 1;
        if(!isBoardBounds(right)) {
            isObstacle(board.getSpot(newX, right),  legalMoves);
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
