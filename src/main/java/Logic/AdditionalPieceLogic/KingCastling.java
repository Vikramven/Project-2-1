package Logic.AdditionalPieceLogic;

import Board.Spot;
import Logic.LogicGame;
import Pieces.King;
import Pieces.Rook;

public class KingCastling {

    public void kingCastling(LogicGame l, int oldY){
        King piece = (King) l.currentSpot.getPiece();
        int kingX = l.currentSpot.getX();
        int kingY = l.currentSpot.getY();
        boolean black = (kingX == 7);

        if(piece.isCastling()) {
            int longOrShort = kingY - oldY;
            if (longOrShort == 2) { // Long Castling
                changeRook(7, 4, kingX, black, l);
                l.getGameSc().addMoveToHist("CASTLING: Long");
            } else if(longOrShort == -2){ // Short Castling
                changeRook(0, 2, kingX, black, l);
                l.getGameSc().addMoveToHist("CASTLING: Short");
            }
        }
        piece.setCastling(false);
    }

    /**
     * Change the rook
     * @param oldRookY old rook position Y
     * @param newRookY new rook position Y
     * @param kingX king position X
     * @param black color of the piece
     */
    public void changeRook(int oldRookY, int newRookY, int kingX, boolean black, LogicGame l) {
        l.board.setSpot(null, kingX, oldRookY);
        Spot newRookSpot = new Spot(kingX, newRookY, new Rook(black));
        l.board.setSpot(newRookSpot, kingX, newRookY);
        l.board.pieceHeap.changeCoordinate(5, black, kingX, oldRookY, kingX, newRookY);
    }
}
