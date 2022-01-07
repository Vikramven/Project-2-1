package dice_chess.Logic.GameLogic;
import dice_chess.Board.Board;
import dice_chess.GUI.GameScene;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import javafx.stage.Stage;

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
            if(l.GUI) l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 65px 65px to 100px 100px, #ff8000, #32cd32);");
            l.blackMove = true;


            if(l.winFlag) {
                if(l.GUI) {
                    GameScene gameSc = l.getGameSc();
                    gameSc.whiteWin++;
                    if (l.playerWhite.isHuman()) {
                        new WinGui().winGui(l, "White");
                    } else if (!l.playerWhite.isHuman() && !l.playerBlack.isHuman()) {
                        System.out.println("White AI win");
                        reset(l);
                    } else if (!l.playerWhite.isHuman() && l.playerBlack.isHuman()) {
                        new WinGui().winGui(l, "White AI");
                    }
                } else {
                    if (!l.playerWhite.isHuman() && !l.playerBlack.isHuman()) {
                        System.out.println("White AI win");
                        l.whiteWin++;
                        reset(l);
                    }
                }
                return;
            }

            //AI move
            if(!l.playerBlack.isHuman() && !l.winFlag) {
                //System.out.println("Black AI");
                Move AImove = null;
                if(l.AIblack == 1)
                    AImove = l.expectimax.calculateBestMoves(l, l.depth);
                else if(l.AIblack == 0)
                    AImove = l.randomAgent.executeRandomMove(l, l.dicePiece, l.blackMove);
                else if(l.AIblack == 2)
                    AImove = l.miniMax.calculateBestMoves(l, l.depth);

                if(AImove != null && !l.winFlag) {
                    l.executeMovesAI.executeMovesAI(l, AImove);
                } else {
                    l.dl.rollDice(l);
                    l.cp.changePlayer(l);
                }
            }

        } else {
            if(l.GUI) l.playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
            l.blackMove = false;

            if(l.winFlag) {
                if(l.GUI) {
                    GameScene gameSc = l.getGameSc();
                    gameSc.blackWin++;
                    if (l.playerBlack.isHuman()) {
                        new WinGui().winGui(l, "Black");
                    } else if (!l.playerBlack.isHuman() && !l.playerWhite.isHuman()) {
                        System.out.println("Black AI win");
                        reset(l);
                    } else if (!l.playerBlack.isHuman() && l.playerWhite.isHuman()) {
                        new WinGui().winGui(l, "Black AI");
                    }
                } else {
                    if (!l.playerBlack.isHuman() && !l.playerWhite.isHuman()) {
                        System.out.println("Black AI win");
                        l.blackWin++;
                        reset(l);
                    }
                }
                return;
            }


            //AI move
            if(!l.playerWhite.isHuman() && !l.winFlag) {
                Move AImove = null;
                //System.out.println("White AI");
                if(l.AIwhite == 1)
                    AImove = l.expectimax.calculateBestMoves(l, l.depth);
                else if(l.AIwhite == 0)
                    AImove = l.randomAgent.executeRandomMove(l, l.dicePiece, l.blackMove);
                else if(l.AIwhite == 2)
                    AImove = l.miniMax.calculateBestMoves(l, l.depth);

                if(AImove != null && !l.winFlag) {
                    l.executeMovesAI.executeMovesAI(l, AImove);
                } else {
                    l.dl.rollDice(l);
                    l.cp.changePlayer(l);
                }
            }
        }

        if(l.GUI) l.bh.removeHighlightButtons(l); // Remove Highlight buttons
    }

    private void reset(LogicGame logicGame){
        if(logicGame.GUI) {
            GameScene gameSc = logicGame.getGameSc();
            Stage mainStage = logicGame.getMainStage();
            gameSc.setGameScene(gameSc.getPlayers());
            mainStage.setScene(gameSc.getGameScene());
            mainStage.setFullScreen(true);
            mainStage.setResizable(false);
        } else {
            new LogicGame(new Board(), logicGame.playerWhite.clone(), logicGame.playerBlack.clone(),
                    logicGame.AIwhite, logicGame.AIblack, logicGame.depth, logicGame.whiteWin, logicGame.blackWin);
        }
    }
}
