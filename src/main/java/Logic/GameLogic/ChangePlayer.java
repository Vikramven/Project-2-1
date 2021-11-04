package Logic.GameLogic;

import Logic.AI.MiniMax;
import Logic.LogicGame;

public class ChangePlayer {

    /**
     * Change the player in the logic and in the GUI
     */
    public void changePlayer(LogicGame l) {
        l.numberMoves = 3; // Number of moves the player is allowed per turn.
        l.currentSpot = null;
        l.allLegalMoves = null;

        // Change the player in the logic and in the GUI
        if(l.player.isColorSide().equals("White")) {
            l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 65px 65px to 100px 100px, #ff8000, #32cd32);");
            l.player.setColorSide(true);

        } else {
            l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
            l.player.setColorSide(false);
        }

        l.bh.removeHighlightButtons(l); // Remove Highlight buttons
    }
}
