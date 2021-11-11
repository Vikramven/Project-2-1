package Logic.GameLogic;

import Logic.AI.ExecuteMovesAI;
import Logic.AI.MiniMax;
import Logic.LogicGame;

public class ChangePlayer {

    private final MiniMax miniMax = new MiniMax();
    private final ExecuteMovesAI executeMovesAI = new ExecuteMovesAI();

    /**
     * Change the player in the logic and in the GUI
     */
    public void changePlayer(LogicGame l) {
        l.currentSpot = null;
        l.allLegalMoves = null;

        // Change the player in the logic and in the GUI
        if(!l.blackMove) {
            l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 65px 65px to 100px 100px, #ff8000, #32cd32);");
            l.blackMove = true;

            if(!l.playerWhite.isHuman())
                System.out.println("Check White AI");
                // TODO
                // executeMovesAI.executeMovesAI(l, miniMax.calculateBestMoves(l));
        } else {
            l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
            l.blackMove = false;

            if(!l.playerBlack.isHuman())
                System.out.println("Check Black AI");
                // TODO
                //executeMovesAI.executeMovesAI(l, miniMax.calculateBestMoves(l));
        }

        l.bh.removeHighlightButtons(l); // Remove Highlight buttons
    }
}
