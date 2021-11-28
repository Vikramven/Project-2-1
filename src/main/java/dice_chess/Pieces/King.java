package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;

public class King extends Piece {


    public boolean castling = true;

    //Todo
    private int[][] cost = {{0, 0, 0, 0, 0, 0, 0, 0},
            {5, 10, 10, 10, 10, 10, 10, 5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            { 0, 0, 0, 5, 5, 0, 0, 0}};

    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public King(boolean black) {
        super(black);
        name = "King";
        nameInt = 2;
        if(black) { imageURL = "/black_king.png"; }
        else { imageURL = "/white_king.png"; }
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

        int spotX = spot.getX();
        int spotY = spot.getY();

        int top = spotX+1;
        int down = spotX-1;
        int left = spotY-1;
        int right = spotY+1;

        //   |
        //   K
        moveKing(board, legalMoves, top, spotY, spotX, spotY, costDynamic);

        //    /
        //   K
        moveKing(board, legalMoves, top, right, spotX, spotY, costDynamic);

        //  \
        //   K
        moveKing(board, legalMoves, top, left, spotX, spotY, costDynamic);

        //   K
        //   |
        moveKing(board, legalMoves, down, spotY, spotX, spotY, costDynamic);

        //   K
        //    \
        moveKing(board, legalMoves, down, right, spotX, spotY, costDynamic);

        //   K
        //  /
        moveKing(board, legalMoves, down, left, spotX, spotY, costDynamic);

        // - K
        moveKing(board, legalMoves, spotX, left, spotX, spotY, costDynamic);

        //   K -
        moveKing(board, legalMoves, spotX, right, spotX, spotY, costDynamic);

        if(castling)
            castlingMove(board, legalMoves, spotX, spotY);

        return legalMoves;
    }

    /**
     *
     * @param board _dice_chess.Board
     * @param legalMoves All possible legal move
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void moveKing(Board board, ArrayList<Move> legalMoves, int x, int y, int spotX, int spotY, int[][] costDynamic){

        if(isBoardBounds(x) || isBoardBounds(y))
            return;

        int costMove = costDynamic[x][y] + cost[x][y];

        if(isObstacle(board.getSpot(x, y), legalMoves, costMove, spotX, spotY))
            return;

        legalMoves.add(new Move(x, y, this, costMove, spotX, spotY));
    }

    /**
     * All possible castling
     * @param board _dice_chess.Board
     * @param legalMoves All possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void castlingMove(Board board, ArrayList<Move> legalMoves, int x, int y){

        moveShortCastling(board, legalMoves, x, y);

        moveLongCastling(board, legalMoves, x, y);

    }

    /**
     * Short castling
     * @param board _dice_chess.Board
     * @param legalMoves all possible legal moves
     * @param x X coordinates
     * @param y Y coordinates
     */
    private void moveShortCastling(Board board, ArrayList<Move> legalMoves, int x, int y){
        if(checkRooks(board, false))
            return;

        for (int i = 1; i < y; i++) {
            Spot spot = board.getSpot(x, i);
            if(spot != null)
                return;
        }

        if(!black){
            addCastling(legalMoves, x, y, 0, 1);
        } else {
            addCastling(legalMoves, x, y, 7, 1);
        }

    }

    /**
     * Long castling
     * @param board _dice_chess.Board
     * @param legalMoves all possible legal moves
     * @param x X coordinates
     * @param y Y coordinates
     */
    private void moveLongCastling(Board board, ArrayList<Move> legalMoves, int x, int y){
        if(checkRooks(board, true))
            return;

        for (int i = y+1; i < 7; i++) {
            Spot spot = board.getSpot(x, i);
            if(spot != null)
                return;
        }

        if(!black){
            addCastling(legalMoves, x, y, 0, 5);
        } else {
            addCastling(legalMoves, x, y, 7, 5);
        }
    }


    private void addCastling(ArrayList<Move> legalMoves, int x, int y, int MoveX, int MoveY){
        int costMove = cost[MoveX][MoveY];

        legalMoves.add(new Move(MoveX, MoveY, this, costMove, x, y));
    }

    /**
     *
     * @param board _dice_chess.Board
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
