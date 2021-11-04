package Logic.MoveLogic;

import Pieces.Piece;

public class Move {

    private int x;

    private int y;

    private Piece piece;

    private int cost;

    private int pieceSpotX;

    private int pieceSpotY;

    public Move(int x, int y, Piece piece, int cost, int pieceSpotX, int pieceSpotY) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.cost = cost;
        this.pieceSpotX = pieceSpotX;
        this.pieceSpotY = pieceSpotY;
    }

    public int getPieceSpotX() {
        return pieceSpotX;
    }

    public void setPieceSpotX(int pieceSpotX) {
        this.pieceSpotX = pieceSpotX;
    }

    public int getPieceSpotY() {
        return pieceSpotY;
    }

    public void setPieceSpotY(int pieceSpotY) {
        this.pieceSpotY = pieceSpotY;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Clone the object
     * @return clone of the Move
     */
    public Move clone(){
        return new Move(x, y, piece, cost, pieceSpotX, pieceSpotY);
    }
}
