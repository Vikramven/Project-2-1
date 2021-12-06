package dice_chess.Logic.GameLogic;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

public class ChangePlayer {

    /**
     * Change the player in the logic and in the GUI
     * @param l LogicGame object
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

            //AI move
            if(!l.playerBlack.isHuman()) {
                System.out.println("Black AI");
                Move AImove = null;
                if(l.AIblack == 1)
                    AImove = l.expectimax.calculateBestMoves(l, l.depth);
                else if(l.AIblack == 0)
                    AImove = l.randomAgent.executeRandomMove(l, l.dicePiece, l.blackMove);
                else if(l.AIblack == 2)
                    AImove = l.miniMax.calculateBestMoves(l, l.depth);

                if(AImove != null) {
                    l.executeMovesAI.executeMovesAI(l, AImove);
                } else {
                    l.dl.rollDice(l);
                    l.cp.changePlayer(l);
                }
            }


        } else {
            l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
            l.blackMove = false;


            //AI move
            if(!l.playerWhite.isHuman()) {
                Move AImove = null;
                System.out.println("White AI");
                if(l.AIwhite == 1)
                    AImove = l.expectimax.calculateBestMoves(l, l.depth);
                else if(l.AIwhite == 0)
                    AImove = l.randomAgent.executeRandomMove(l, l.dicePiece, l.blackMove);
                else if(l.AIwhite == 2)
                    AImove = l.miniMax.calculateBestMoves(l, l.depth);

                if(AImove != null) {
                    l.executeMovesAI.executeMovesAI(l, AImove);
                } else {
                    l.dl.rollDice(l);
                    l.cp.changePlayer(l);
                }
            }

        }

        l.bh.removeHighlightButtons(l); // Remove Highlight buttons
    }
}
