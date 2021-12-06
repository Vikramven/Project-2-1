package dice_chess.Logic.MoveLogic;

import dice_chess.Pieces.Piece;

public class Move {

    //X coordinate where to move
    private int x;

    //Y coordinate where to move
    private int y;

    //The piece with which we move
    private Piece piece;

    //The cost of this move
    private int cost;

    //X coordinate where the piece is located
    private int pieceSpotX;

    //Y coordinate where the piece is located
    private int pieceSpotY;

    public Move(int x, int y, Piece piece, int cost, int pieceSpotX, int pieceSpotY) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.cost = cost;
        this.pieceSpotX = pieceSpotX;
        this.pieceSpotY = pieceSpotY;
    }

    /**
     * Get the piece X coordinate
     * @return X coordinate of the piece
     */
    public int getPieceSpotX() {
        return pieceSpotX;
    }

    /**
     * Set the piece X coordinates
     * @param pieceSpotX X coordinate of the piece
     */
    public void setPieceSpotX(int pieceSpotX) {
        this.pieceSpotX = pieceSpotX;
    }

    /**
     * Get the piece Y coordinate
     * @return Y coordinate of the piece
     */
    public int getPieceSpotY() {
        return pieceSpotY;
    }

    /**
     * Set the piece Y coordinates
     * @param pieceSpotY Y coordinate of the piece
     */
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

    /**
     * Get the cost
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Set the cost
     * @param cost the cost
     */
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
