package Logic.MoveLogic;

import Board.Spot;
import Logic.LogicGame;

import java.util.concurrent.atomic.AtomicInteger;

public class GuiMove {

    /**
     * Repaint the board to the current state of the game
     * @param iniX GUI x
     * @param iniY GUI y
     * @param finalX where player moves x coordinate
     * @param finalY where player moves y coordinate
     */
    protected void movePieceGUI(AtomicInteger iniX, AtomicInteger iniY, int finalX, int finalY, LogicGame l) {
        Spot finalSpot = l.board.getSpot(finalX,finalY);
        String c;

        if ((finalX + finalY) % 2 != 0) {
            c = "green";
        } else {
            c = "white";
        }

        if(finalSpot != null) {
            l.buttonBoard[iniX.intValue()][iniY.intValue()].setStyle("-fx-background-color: " + c + ";");
            l.buttonBoard[finalX][finalY].setStyle(
                    "-fx-background-color: " + c + ";" +
                            "-fx-background-image: url('" + finalSpot.getPiece().getImageURL() + "');" +
                            "-fx-background-size: 70px;" +
                            "-fx-background-repeat: no-repeat;" +
                            "-fx-background-position: 50%;");
        }
        l.bh.removeHighlightButtons(l);
        l.currentSpot = null;
        l.allLegalMoves = null;
    }

}
