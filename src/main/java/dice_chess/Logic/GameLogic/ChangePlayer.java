package dice_chess.Logic.GameLogic;

import dice_chess.Logic.AI.ExecuteMovesAI;
import dice_chess.Logic.AI.MiniMax;
import dice_chess.Logic.LogicGame;

public class ChangePlayer {

    private final MiniMax miniMax = new MiniMax();
    private final ExecuteMovesAI executeMovesAI = new ExecuteMovesAI();

    /**
     * Change the player in the logic and in the _dice_chess.GUI
     */
    public void changePlayer(LogicGame l) {
        l.currentSpot = null;
        l.allLegalMoves = null;

        // Change the player in the logic and in the _dice_chess.GUI
        if(!l.blackMove) {
            l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 65px 65px to 100px 100px, #ff8000, #32cd32);");
            l.blackMove = true;


            //AI move
            if(!l.playerBlack.isHuman()) {
                System.out.println("Check Black AI");
                miniMax.calculateBestMoves(l);
                l.dl.rollDice(l);
                l.cp.changePlayer(l);
            }
                // TODO
                // executeMovesAI.executeMovesAI(l, miniMax.calculateBestMoves(l));
        } else {
            l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
            l.blackMove = false;


            //AI move
            if(!l.playerWhite.isHuman()) {
                System.out.println("Check White AI");
                miniMax.calculateBestMoves(l);
                l.dl.rollDice(l);
                l.cp.changePlayer(l);
            }
                // TODO
                //executeMovesAI.executeMovesAI(l, miniMax.calculateBestMoves(l));
        }

        l.bh.removeHighlightButtons(l); // Remove Highlight buttons
    }
}
