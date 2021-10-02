package Pieces;

import Board.Board;
import Board.Spot;
import Players.Player;

import java.util.ArrayList;

public abstract class Piece implements PieceMove {

    protected boolean black;

    protected String name, imageURL;

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

    public String getImageURL() { return imageURL; }

    /**
     * Define if piece have obstacles on their moves
     * @param spot The spot
     * @param legalMoves Arraylist with all possible legal move
     * @return true = obstacle: false = no obstacle
     */
    protected boolean isObstacle(Spot spot, ArrayList<Spot> legalMoves){

        if(spot != null) {
            if (!spot.getPiece().isColor().equals(isColor())) {
                legalMoves.add(new Spot(spot.getX(), spot.getY(), spot.getPiece()));
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
    public ArrayList<Spot> checkPlayerMove(Board board, Spot spot, Player player){
        if(spot.getPiece().isColor().equals(player.isColorSide())){
            return allLegalMoves(board, spot);
        }
        return null;
    }
}
