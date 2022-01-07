package dice_chess.Logic.MoveLogic;

import dice_chess.Board.Spot;
import dice_chess.Logic.*;
import dice_chess.Logic.AdditionalPieceLogic.KingCastling;
import dice_chess.Logic.AdditionalPieceLogic.PawnPromotion;
import dice_chess.Logic.GameLogic.WinGui;
import dice_chess.Pieces.*;

import java.util.concurrent.atomic.AtomicInteger;

public class ExecuteMove {

    //Parts of _dice_chess.Logic
    private final GuiMove gm = new GuiMove();
    private final PawnPromotion pp = new PawnPromotion();
    private final KingCastling kc = new KingCastling();

    /**
     * Execution the move
     * @param iniX _dice_chess.GUI x
     * @param iniY _dice_chess.GUI y
     * @param finalX where player moves x coordinate
     * @param finalY where player moves y coordinate
     */
    public void executeMove(AtomicInteger iniX, AtomicInteger iniY, int finalX, int finalY, LogicGame l, boolean noGuiForAI) {
        boolean flag = movePiece(finalX, finalY, l, true, false, noGuiForAI);
        if(flag) {
            gm.movePieceGUI(iniX, iniY, finalX, finalY, l);
//            for (int j = 0; j < 6; j++) {
//                LinkedList<Coordinate> coordinates = l.board.pieceMap.getAllPieces(j, !l.player.isBlackSide());
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
    public boolean movePiece(int x, int y, LogicGame l, boolean GUI, boolean AI, boolean guiForAI) {

        for (int i = 0; i < l.allLegalMoves.size(); i++) {
            if(x == l.allLegalMoves.get(i).getX() && y == l.allLegalMoves.get(i).getY()){

                int oldX = l.currentSpot.getX();
                int oldY = l.currentSpot.getY();
                l.board.setSpot(null, oldX, oldY);

                Spot win = l.board.getSpot(x, y);
                if(win != null){
                    Piece winPiece = win.getPiece();
                    if(winPiece.getName().equals("King")){
                        if(!AI) {
                            l.winFlag = true;
                        }
                    }
                    l.board.pieceMap.popPiece(winPiece.getNameInt(), !l.blackMove, x, y);
                }

                l.currentSpot.setX(x);
                l.currentSpot.setY(y);
                l.board.setSpot(l.currentSpot, x, y);
                l.board.pieceMap.changeCoordinate(l.currentSpot.getPiece().getNameInt(),
                        l.currentSpot.getPiece().getColor(), oldX, oldY, x, y);

                if(l.currentSpot.getPiece().getName().equals("Pawn")){
                    pp.checkPawnPromotion(l.currentSpot, l, guiForAI);
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
