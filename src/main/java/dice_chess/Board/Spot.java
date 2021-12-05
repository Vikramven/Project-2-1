package dice_chess.Board;

import dice_chess.Pieces.Piece;

import javafx.scene.control.*;

public class Spot extends Button{

    private int x;

    private int y;

    private Piece piece;

    public Spot(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    /**
     * Get X coordinate
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Set X coordinate
     * @param x y coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get Y coordinate
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Set Y coordinate
     * @param y y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the piece
     * @return the piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Set the piece
     * @param piece piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }


    /**
     * Clone the object
     * @return clone of the Spot
     */
    public Spot clone(){
        return new Spot(x, y, piece);
    }
}
