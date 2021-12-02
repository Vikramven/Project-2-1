package Pieces;

import Board.Board;
import Board.Spot;
import Logic.EvaluationFunction.EvaluationFunction;
import Logic.MoveLogic.Move;
import Board.*;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Piece implements PieceMove {

    protected boolean black;

    protected String name, imageURL;

    protected int nameInt;

    public Piece(boolean black) {
        this.black = black;
    }

    public String isColor() {
        if(black)
            return "Black";
        return "White";
    }

    public boolean getColor(){
        return black;
    }

    public void setWhite() {
        black = false;
    }

    public void setBlack() {
        black = true;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() { return imageURL; }

    public int getNameInt() {
        return nameInt;
    }

    /**
     * Define if piece have obstacles on their moves
     * @param spot The spot
     * @param legalMoves Arraylist with all possible legal move
     * @return true = obstacle: false = no obstacle
     */
    protected boolean isObstacle(Spot spot, ArrayList<Move> legalMoves, int costPiece, int x, int y){

        if(spot != null) {
            if (!spot.getPiece().isColor().equals(isColor())) {
                legalMoves.add(new Move(spot.getX(), spot.getY(), spot.getPiece(), costPiece, x, y));
            }
            return true;
        }

        return false;
    }


    /**
     * Check if the piece goes out of the board
     * @param x coordinate
     * @return true = goes out: false = stay on the board
     */
    protected boolean isBoardBounds(int x){
        return x < 0 || x > 7;
    }

    /**
     * Check if player move with the correct piece
     */
    public ArrayList<Move> checkPlayerMove(Board board, Spot spot, boolean player, PieceHeap enemyPieces, boolean costAI){
        if(spot.getPiece().getColor() == player){

            int[][] cost = new int[8][8];

            if(costAI)
                cost = new EvaluationFunction().evaluateTheBoard(enemyPieces, player,board, spot.getPiece());

            return allLegalMoves(board, spot, cost);
        }
        return null;
    }
}
