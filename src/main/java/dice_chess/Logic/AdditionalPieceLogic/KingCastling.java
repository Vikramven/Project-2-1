package dice_chess.Logic.AdditionalPieceLogic;

import dice_chess.Board.Spot;
import dice_chess.Logic.LogicGame;
import dice_chess.Pieces.King;
import dice_chess.Pieces.Rook;

public class KingCastling {

    /**
     * Execute the castling, if we can it
     * @param l LogicGame object
     * @param oldY Y coordinate of the king
     */
    public void kingCastling(LogicGame l, int oldY){
        King piece = (King) l.currentSpot.getPiece();
        int kingX = l.currentSpot.getX();
        int kingY = l.currentSpot.getY();
        boolean black = (kingX == 7);

        if(piece.isCastling()) {
            int longOrShort = kingY - oldY;
            if (longOrShort == 2) { // Long Castling
                changeRook(7, 4, kingX, black, l);
                if(l.GUI) l.getGameSc().addMoveToHist("CASTLING: Long");
            } else if(longOrShort == -2){ // Short Castling
                changeRook(0, 2, kingX, black, l);
                if(l.GUI) l.getGameSc().addMoveToHist("CASTLING: Short");
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
        l.board.pieceMap.changeCoordinate(5, black, kingX, oldRookY, kingX, newRookY);
    }
}
