package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.EvaluationFunction.EvaluationFunction;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;
import java.util.logging.Logger;

public class King extends Piece {


    public boolean castling = true;

    //TODO improve this in the PHASE 3
    private final int[][] whiteCost = {{10, 15, 10, 50, 10, 15, 10, 10},
                            {10, -10, 0, 0, 0, 0, 0, 10},
                            {5, -10, -15, -15, -15, -15, -10, 5},
                            {5, -10, -15, -15, -15, -15, -10, 5},
                            {-5, -10, -15, -15, -15, -15, -10, -5},
                            {-5, -10, -15, -15, -15, -15, -10, -5},
                            {-5, -10, -10, -10, -10, -10, -10, -5},
                            { -5, -5, -5, -5, -5, -5, -5, -5}};

    private final int[][] blackCost = {{-5, -5, -5, -5, -5, -5, -5, -5},
            {-5, -10, -10, -10, -10, -10, -10, -5},
            {-5, -10, -15, -15, -15, -15, -10, -5},
            {-5, -10, -15, -15, -15, -15, -10, -5},
            {5, -10, -15, -15, -15, -15, -10, 5},
            {5, -10, -15, -15, -15, -15, -10, 5},
            {10, 0, 0, 0, 0, 0, 0, 10},
            {10, 15, 10, 50, 10, 15, 10, 10 }};

    public int[][] getPositionCost(){
        if(black)
            return blackCost;

        return whiteCost;
    }
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
     * @param l LogicGame
     * @param spot The spot where is located the piece
     * @return all possible legal moves
     */
    @Override
    public ArrayList<Move> allLegalMoves(LogicGame l, Spot spot, int evalFunction) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        int spotX = spot.getX();
        int spotY = spot.getY();

        EvaluationFunction ef = new EvaluationFunction(evalFunction, black, l);

        int top = spotX+1;
        int down = spotX-1;
        int left = spotY-1;
        int right = spotY+1;

        //   |
        //   K
        moveKing(l, legalMoves, top, spotY, spotX, spotY, ef);

        //    /
        //   K
        moveKing(l, legalMoves, top, right, spotX, spotY, ef);

        //  \
        //   K
        moveKing(l, legalMoves, top, left, spotX, spotY, ef);

        //   K
        //   |
        moveKing(l, legalMoves, down, spotY, spotX, spotY, ef);

        //   K
        //    \
        moveKing(l, legalMoves, down, right, spotX, spotY, ef);

        //   K
        //  /
        moveKing(l, legalMoves, down, left, spotX, spotY, ef);

        // - K
        moveKing(l, legalMoves, spotX, left, spotX, spotY, ef);

        //   K -
        moveKing(l, legalMoves, spotX, right, spotX, spotY, ef);

        if(castling)
            castlingMove(l.board, legalMoves, spotX, spotY, ef, l);

        return legalMoves;
    }

    /**
     *
     * @param l Logic Game
     * @param legalMoves All possible legal move
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void moveKing(LogicGame l, ArrayList<Move> legalMoves, int x, int y, int spotX, int spotY, EvaluationFunction ef){

        if(isBoardBounds(x) || isBoardBounds(y))
            return;

        if(isObstacle(l.board.getSpot(x, y), legalMoves, spotX, spotY, this, ef, l))
            return;

        double costMove = ef.evaluateMove(spotX, spotY, this, black, x, y, l);

        legalMoves.add(new Move(x, y, this, costMove, spotX, spotY));
    }

    /**
     * All possible castling
     * @param board _dice_chess.Board
     * @param legalMoves All possible legal moves
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void castlingMove(Board board, ArrayList<Move> legalMoves, int x, int y, EvaluationFunction ef, LogicGame l){

        moveShortCastling(board, legalMoves, x, y, ef, l);

        moveLongCastling(board, legalMoves, x, y, ef, l);

    }

    /**
     * Short castling
     * @param board _dice_chess.Board
     * @param legalMoves all possible legal moves
     * @param x X coordinates
     * @param y Y coordinates
     */
    private void moveShortCastling(Board board, ArrayList<Move> legalMoves, int x, int y, EvaluationFunction ef, LogicGame l){
        if(checkRooks(board, false))
            return;

        for (int i = 1; i < y; i++) {
            Spot spot = board.getSpot(x, i);
            if(spot != null)
                return;
        }

        if(!black){
            addCastling(legalMoves, x, y, 0, 1, ef, l);
        } else {
            addCastling(legalMoves, x, y, 7, 1, ef, l);
        }

    }

    /**
     * Long castling
     * @param board _dice_chess.Board
     * @param legalMoves all possible legal moves
     * @param x X coordinates
     * @param y Y coordinates
     */
    private void moveLongCastling(Board board, ArrayList<Move> legalMoves, int x, int y, EvaluationFunction ef, LogicGame l){
        if(checkRooks(board, true))
            return;

        for (int i = y+1; i < 7; i++) {
            Spot spot = board.getSpot(x, i);
            if(spot != null)
                return;
        }

        if(!black){
            addCastling(legalMoves, x, y, 0, 5, ef, l);
        } else {
            addCastling(legalMoves, x, y, 7, 5, ef, l);
        }
    }


    private void addCastling(ArrayList<Move> legalMoves, int x, int y, int MoveX, int MoveY, EvaluationFunction ef, LogicGame l){
        double costMove = ef.evaluateMove(x, y, this, black, MoveX, MoveY, l);

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
