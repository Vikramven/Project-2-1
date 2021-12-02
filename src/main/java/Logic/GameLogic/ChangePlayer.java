package Logic.GameLogic;
import Logic.LogicGame;
import Logic.MoveLogic.Move;

public class ChangePlayer {

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


            Move AImove = null;
            //AI move
            if(!l.playerBlack.isHuman()) {
                System.out.println("Black AI");
                if(l.AIblack == 1)
                    AImove = l.expectimax.calculateBestMoves(l);
                else if(l.AIblack == 0)
                    AImove = l.randomAgent.executeRandomMove(l, l.dicePiece, l.blackMove);
            }
            if(AImove != null)
                l.executeMovesAI.executeMovesAI(l, AImove);

        } else {
            l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
            l.blackMove = false;


            Move AImove = null;
            //AI move
            if(!l.playerWhite.isHuman()) {
                System.out.println("White AI");
                if(l.AIwhite == 1)
                    AImove = l.expectimax.calculateBestMoves(l);
                else if(l.AIwhite == 0)
                    AImove = l.randomAgent.executeRandomMove(l, l.dicePiece, l.blackMove);
            }
            if(AImove != null)
                l.executeMovesAI.executeMovesAI(l, AImove);

        }

        l.bh.removeHighlightButtons(l); // Remove Highlight buttons
    }
}
