package Pieces;

import Board.Board;
import Board.Spot;

import java.util.ArrayList;

public class King extends Piece {


    private boolean castling = true;

    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public King(boolean black) {
        super(black);
        name = "King";
        if(black) { imageURL = "/res/black_king.png"; }
        else { imageURL = "/res/white_king.png"; }
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

        int top = x+1;
        int down = x-1;
        int left = y-1;
        int right = y+1;

        //   |
        //   K
        moveKing(board, legalMoves, top, y);

        //    /
        //   K
        moveKing(board, legalMoves, top, right);

        //  \
        //   K
        moveKing(board, legalMoves, top, left);

        //   K
        //   |
        moveKing(board, legalMoves, down, y);

        //   K
        //    \
        moveKing(board, legalMoves, down, right);

        //   K
        //  /
        moveKing(board, legalMoves, down, left);

        // - K
        moveKing(board, legalMoves, x, left);

        //   K -
        moveKing(board, legalMoves, x, right);

        if(castling)
            castlingMove(board, legalMoves, x, y);

        return legalMoves;
    }

    /**
     *
     * @param board Board
     * @param legalMoves All possible legal move
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void moveKing(Board board, ArrayList<Spot> legalMoves, int x, int y){

        if(isBoardBounds(x) || isBoardBounds(y))
            return;

        if(isObstacle(board.getSpot(x, y), legalMoves))
            return;

        legalMoves.add(new Spot(x, y, null));
    }

    /**
     * All possible castling
     * @param board Board
     * @param legalMoves All possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void castlingMove(Board board, ArrayList<Spot> legalMoves, int x, int y){

        moveShortCastling(board, legalMoves, x, y);

        moveLongCastling(board, legalMoves, x, y);

    }

    /**
     * Short castling
     * @param board Board
     * @param legalMoves all possible legal moves
     * @param x X coordinates
     * @param y Y coordinates
     */
    private void moveShortCastling(Board board, ArrayList<Spot> legalMoves, int x, int y){
        if(checkRooks(board, false))
            return;

        for (int i = 1; i < y; i++) {
            Spot spot = board.getSpot(x, i);
            if(spot != null)
                return;
        }

        if(!black){
            legalMoves.add(new Spot(0, 1, null));
        } else {
            legalMoves.add(new Spot(7, 1, null));
        }

    }

    /**
     * Long castling
     * @param board Board
     * @param legalMoves all possible legal moves
     * @param x X coordinates
     * @param y Y coordinates
     */
    private void moveLongCastling(Board board, ArrayList<Spot> legalMoves, int x, int y){
        if(checkRooks(board, true))
            return;

        for (int i = y+1; i < 7; i++) {
            Spot spot = board.getSpot(x, i);
            if(spot != null)
                return;
        }

        if(!black){
            legalMoves.add(new Spot(0, 5, null));
        } else {
            legalMoves.add(new Spot(7, 5, null));
        }
    }

    /**
     *
     * @param board Board
     * @param longCastling if its long castling or short
     * @return false - rook is there: true - rook isnot there
     */
    private boolean checkRooks(Board board, boolean longCastling){

        if(!black){
            if(longCastling)
                return isRook(board.getSpot(0, 7));
            else
                return isRook(board.getSpot(0, 0));

        } else {
            if(longCastling)
                return isRook(board.getSpot(7, 7));
            else
                return isRook(board.getSpot(7, 0));

        }
    }

    /**
     * Check if it is the rook on the spot
     * @param piece Unknown piece
     * @return true - not rook: false - rook
     */
    private boolean isRook(Spot piece){
            if (piece == null) {
                return true;
            } else if (!piece.getPiece().name.equals("Rook")) {
                return true;
            } else if (piece.getPiece().name.equals("Rook")) {
                Rook rook = (Rook) piece.getPiece();
                return !rook.isCastling();
            }

        return true;
    }


    public boolean isCastling() {
        return castling;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }
}
