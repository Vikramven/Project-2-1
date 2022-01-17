package dice_chess.Logic.GameLogic;
import dice_chess.Board.Board;
import dice_chess.GUI.GameScene;
import dice_chess.Logic.Hybrid.QLearner;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.TestSimulations.ExcelWriter;
import dice_chess.TestSimulations.GameInfo;
import javafx.stage.Stage;

import java.io.IOException;

import static dice_chess.Constant.Constant.*;

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

                if(GAME_TRACKER) {
                    showGameResult(false);
                }

                if(DQN_SIMULATION) return;

                if(Q_LEARNER){
                    ql.updateProbs();
                    try {
                        ql.hm.updateTxt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ql = new QLearner(GAMMA);
                }

                if(l.GUI) {
                    if (PLAYER_WHITE.isHuman()) {
                        new WinGui().winGui(l, "White");
                    } else if (!PLAYER_WHITE.isHuman() && !PLAYER_BLACK.isHuman()) {
                        reset(l);
                    } else if (!PLAYER_WHITE.isHuman() && PLAYER_BLACK.isHuman()) {
                        new WinGui().winGui(l, "White AI");
                    }
                } else {
                    if (!PLAYER_WHITE.isHuman() && !PLAYER_BLACK.isHuman()) {
                        reset(l);
                    }
                }
                return;
            }

            //AI move
            if(!PLAYER_BLACK.isHuman() && !l.winFlag) {
                Move AImove = calculateAIMove(AI_BLACK, l);

                if(AImove != null && !l.winFlag) {
                    TOTAL_STEP_BLACK_IN_THE_GAME++;
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

                if(GAME_TRACKER) {
                    showGameResult(true);
                }

                if(DQN_SIMULATION) return;

                if(Q_LEARNER){
                    ql.updateProbs();
                    try {
                        ql.hm.updateTxt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //creating a new agent
                    ql = new QLearner(GAMMA);
                }

                if(l.GUI) {
                    if (PLAYER_BLACK.isHuman()) {
                        new WinGui().winGui(l, "Black");
                    } else if (!PLAYER_BLACK.isHuman() && !PLAYER_WHITE.isHuman()) {
                        reset(l);
                    } else if (!PLAYER_BLACK.isHuman() && PLAYER_WHITE.isHuman()) {
                        new WinGui().winGui(l, "Black AI");
                    }
                } else {
                    if (!PLAYER_BLACK.isHuman() && !PLAYER_WHITE.isHuman()) {
                        reset(l);
                    }
                }
                return;
            }


            //AI move
            if(!PLAYER_WHITE.isHuman() && !l.winFlag) {
                Move AImove = calculateAIMove(AI_WHITE, l);
                if(AImove != null && !l.winFlag) {
                    TOTAL_STEP_WHITE_IN_THE_GAME++;
                    l.executeMovesAI.executeMovesAI(l, AImove);
                } else {
                    l.dl.rollDice(l);
                    l.cp.changePlayer(l);
                }
            }
        }

        if(l.GUI) l.bh.removeHighlightButtons(l); // Remove Highlight buttons
    }

    private Move calculateAIMove(int AI, LogicGame l){
        Move AImove = null;
        switch (AI) {
            case 0 : {
                AImove = l.randomAgent.executeRandomMove(l.clone(), l.dicePiece, l.blackMove);
                break;
            }
            case 1 : {
                AImove = l.expectimax.calculateBestMoves(l.clone(), DEPTH_WHITE);
                break;
            }
            case 2 : {
                AImove = l.miniMax.calculateBestMoves(l.clone(), DEPTH_WHITE);
                break;
            }
            case 4 : {
                AImove = l.expectiMinimax.calculateBestMoves(l.clone(), DEPTH_WHITE);
                break;
            }
            case 5 :{
                AImove = l.expectimax.calculateBestMoves(l.clone(), DEPTH_WHITE);
            }
        }
        return AImove;
    }

    private void reset(LogicGame logicGame){
        if(logicGame.GUI) {
            GameScene gameSc = logicGame.getGameSc();
            Stage mainStage = logicGame.getMainStage();
            gameSc.setGameScene(gameSc.getPlayers());
            mainStage.setScene(gameSc.getGameScene());
            mainStage.setFullScreen(true);
            mainStage.setResizable(false);
        }
    }

    private void showGameResult(boolean blackWins) {
        GAME_COUNTER++;
        BLACK_WINS = blackWins;
        System.out.println("~~~~~~~~~~~~~~~~~~~ GAME NUMBER# " + GAME_COUNTER + "~~~~~~~~~~~~~~~~~~~~~~~~");
        if(BLACK_WINS) {
            System.out.println("BLACK WINS");
            TOTAL_WIN_BLACK++;
        } else {
            System.out.println("WHITE WINS");
            TOTAL_WIN_WHITE++;
        }


        System.out.println("WHITE steps in the game: " + TOTAL_STEP_WHITE_IN_THE_GAME);

        System.out.println("BLACK steps in the game: " + TOTAL_STEP_BLACK_IN_THE_GAME);

        System.out.println("Total number of Wins: WHITE# " + TOTAL_WIN_WHITE + "   BLACK# " + TOTAL_WIN_BLACK);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        if(AI_SIMULATIONS) LIST_GAMES.add(new GameInfo(GAME_COUNTER, TOTAL_STEP_WHITE_IN_THE_GAME, TOTAL_STEP_BLACK_IN_THE_GAME, BLACK_WINS));

        if(GAME_COUNTER == 1000 && AI_SIMULATIONS) {
            try {
                new ExcelWriter().writeExcel();
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TOTAL_STEP_BLACK_IN_THE_GAME = 0;

        TOTAL_STEP_WHITE_IN_THE_GAME = 0;
    }
}
