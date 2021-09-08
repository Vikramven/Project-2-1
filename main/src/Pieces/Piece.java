package Pieces;

import Board.Board;
import Board.Spot;

import java.util.ArrayList;

public abstract class Piece{

    protected boolean black;

    protected String name;

    public Piece(boolean black) {
        this.black = black;
    }

    public String isColor() {
        if(black)
            return "Black";
        return "White";
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

    /**
     * Define if piece have obstacles on their moves
     * @param spot The spot
     * @param legalMoves Arraylist with all possible legal move
     * @return true = obstacle: false = no obstacle
     */
    protected boolean isObstacle(Spot spot, ArrayList<Spot> legalMoves){

        if(!black && spot.getPiece() != null) {
            isEnemy(spot, legalMoves, "Black");
            return true;

        } else if(black && spot.getPiece() != null) {
            isEnemy(spot, legalMoves, "White");
            return true;
        }

        return false;
    }

    /**
     * Define if the enemy piece on the spot
     * @param spot The spot
     * @param legalMoves Arraylist with all possible legal move
     * @param name define the color name of the enemy
     */
    protected void isEnemy(Spot spot, ArrayList<Spot> legalMoves, String name){
        if (spot.getPiece().name.equals(name))
            legalMoves.add(new Spot(spot.getX(), spot.getY(), spot.getPiece()));
    }

    /**
     * Check if the piece goes out of the board
     * @param x coordinate
     * @return true = goes out: false = stay on the board
     */
    protected boolean isBoardBounds(int x){
        return x < 0 || x > 7;
    }
}
