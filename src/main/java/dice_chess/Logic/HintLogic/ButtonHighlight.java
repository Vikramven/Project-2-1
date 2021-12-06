package dice_chess.Logic.HintLogic;

import dice_chess.Board.Spot;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

public class ButtonHighlight {

    /**
     * Highlight buttons (Hints)
     */
    public void highlightButtons(LogicGame l) {
        for (Move spot : l.allLegalMoves) {
            int x = spot.getX();
            int y = spot.getY();
            l.buttonBoard[x][y].setStyle("-fx-background-color: rgb(204, 204, 255)");
        }
    }

    /**
     * Remove all hints from the board
     */
    public void removeHighlightButtons(LogicGame l) {
        Spot spot;
        String c;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                spot = l.board.getSpot(x, y);
                if ((x + y) % 2 != 0) { c = "green"; }
                else { c = "white"; }
                if(spot == null) {
                    l.buttonBoard[x][y].setStyle("-fx-background-color: " + c + ";");
                } else {
                    l.buttonBoard[x][y].setStyle(
                            "-fx-background-color: " + c + ";" +
                                    "-fx-background-image: url('" + spot.getPiece().getImageURL() + "');" +
                                    "-fx-background-size: 70px;" +
                                    "-fx-background-repeat: no-repeat;" +
                                    "-fx-background-position: 50%;");
                }
            }
        }
    }
}
