package Logic.MoveLogic;

import Board.Coordinate;
import Board.Spot;
import Logic.*;
import Logic.AdditionalPieceLogic.KingCastling;
import Logic.AdditionalPieceLogic.PawnPromotion;
import Logic.GameLogic.WinGui;
import Pieces.*;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecuteMove {

    //Parts of Logic
    private GuiMove gm = new GuiMove();
    private PawnPromotion pp = new PawnPromotion();
    private KingCastling kc = new KingCastling();

    /**
     * Execution the move
     * @param iniX GUI x
     * @param iniY GUI y
     * @param finalX where player moves x coordinate
     * @param finalY where player moves y coordinate
     */
    public void executeMove(AtomicInteger iniX, AtomicInteger iniY, int finalX, int finalY, LogicGame l) {
        boolean flag = movePiece(finalX, finalY, l, true, false);
        if(flag) {
            gm.movePieceGUI(iniX, iniY, finalX, finalY, l);
//            for (int j = 0; j < 6; j++) {
//                LinkedList<Coordinate> coordinates = l.board.pieceHeap.getAllPieces(j, !l.player.isBlackSide());
//                System.out.println("PIECE = " + j);
//                for (int i = 0; i < coordinates.size(); i++) {
//                    Coordinate coordinate = coordinates.get(i);
//                    System.out.println("COORDINATES = " + coordinate.x + "   " + coordinate.y);
//                }
//                System.out.println("\n");
//            }
                l.dl.rollDice(l);
                l.cp.changePlayer(l);
        }
    }

    /**
     * Move the piece
     * @param x X coordinate which choose the player
     * @param y Y coordinate which choose the player
     * @return true = player chose (clicked on) a legal move / false = player did not choose a legal move
     */
    public boolean movePiece(int x, int y, LogicGame l, boolean GUI, boolean AI) {

        for (int i = 0; i < l.allLegalMoves.size(); i++) {
            if(x == l.allLegalMoves.get(i).getX() && y == l.allLegalMoves.get(i).getY()){

                int oldX = l.currentSpot.getX();
                int oldY = l.currentSpot.getY();
                l.board.setSpot(null, oldX, oldY);

                Spot win = l.board.getSpot(x, y);
                if(win != null){
                    Piece winPiece = win.getPiece();
                    if(winPiece.getName().equals("King")){
                        if(!AI)
                            new WinGui().winGui(l, oldY, oldX, y, x);
                    }
                    l.board.pieceHeap.popPiece(winPiece.getNameInt(), !l.blackMove, x, y);
                }

                l.currentSpot.setX(x);
                l.currentSpot.setY(y);
                l.board.setSpot(l.currentSpot, x, y);
                l.board.pieceHeap.changeCoordinate(l.currentSpot.getPiece().getNameInt(),
                        l.currentSpot.getPiece().getColor(), oldX, oldY, x, y);

                if(l.currentSpot.getPiece().getName().equals("Pawn")){
                    pp.checkPawnPromotion(l.currentSpot, l, AI);
                }

                if(l.currentSpot.getPiece().getName().equals("King")){
                    kc.kingCastling(l, oldY);
                }

                if(l.currentSpot.getPiece().getName().equals("Rook")){
                    Rook rook = (Rook) l.currentSpot.getPiece();
                    rook.setCastling(false);
                }

                if(!l.winFlag && GUI) {
                    l.getGameSc().addMoveToHist(l.currentSpot.getPiece().isColor() + " " + l.currentSpot.getPiece().getName() +
                            " -> " + (oldY+1) + " " + (oldX+1) + " to " + (y+1) + " " + (x+1));
                }

                return true;
            }
        }
        return false;
    }
}
