package dice_chess.Logic;

import dice_chess.Board.*;
import dice_chess.GUI.GUIMain;
import dice_chess.GUI.GameScene;
import dice_chess.GUI.IntroScene;
import dice_chess.Logic.AI.Algorithms.ExpectiMax;
import dice_chess.Logic.AI.Algorithms.ExpectiMinimax;
import dice_chess.Logic.DQN.ActionSpace;
import dice_chess.Logic.MoveLogic.ExecuteMovesAI;
import dice_chess.Logic.AI.Algorithms.MiniMax;
import dice_chess.Logic.GameLogic.ChangePlayer;
import dice_chess.Logic.GameLogic.DiceLogic;
import dice_chess.Logic.HintLogic.ButtonHighlight;
import dice_chess.Logic.MoveLogic.ExecuteMove;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Logic.RandomAgent.RandomAgent;
import dice_chess.Pieces.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.deeplearning4j.rl4j.space.Encodable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import static dice_chess.Constant.Constant.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LogicGame extends GUIMain implements Encodable {

    // Current spot
    public Spot currentSpot = null;

    // All legal possible moves
    public ArrayList<Move> allLegalMoves = null;

    public boolean blackMove = false;


    // Variables from the GUI
    // State of the game
    public Board board;

    // Clickable spots on the board
    public Button[][] buttonBoard = null;

    // Player label which shows who move
    public Label playerPass = null;

    // Pieces from rolling dice
    public int dicePiece;

    // Images of pieces
    public ImageView diceImgViews = null;
    public ArrayList<Image> images = null;

    // Pass button (Change the player move)
    public Button passButton = null;

    // Boolean that allows other methods to know whether we have a winner or not
    public boolean winFlag = false;

    public boolean GUI = true;


    //Parts of logic
    public ChangePlayer cp = new ChangePlayer();
    public ButtonHighlight bh = new ButtonHighlight();
    public DiceLogic dl = new DiceLogic();
    public ExecuteMove em = new ExecuteMove();


    public final MiniMax miniMax = new MiniMax();
    public final ExecuteMovesAI executeMovesAI = new ExecuteMovesAI();
    public final ExpectiMax expectimax = new ExpectiMax();
    public final ExpectiMinimax expectiMinimax = new ExpectiMinimax();
    public final RandomAgent randomAgent = new RandomAgent();

    private void checkGameSettings(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~" + "\n");
        System.out.println("WHITE:");
        System.out.println("PLAYER is human: " + PLAYER_WHITE.isHuman());
        System.out.println("AI: " + AI_WHITE);
        System.out.println("Depth: " + DEPTH_WHITE + "\n");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~" + "\n");
        System.out.println("BLACK:");
        System.out.println("PLAYER is human: " + PLAYER_BLACK.isHuman());
        System.out.println("AI: " + AI_BLACK);
        System.out.println("Depth: " + DEPTH_BLACK + "\n");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~" + "\n");
        System.out.println("EVALUATION FUNCTIONS FOR AI");
        System.out.println("EXPECTI_MAX = " + EVALUATION_FUNCTION_EXPECTI_MAX + "\n");
        System.out.println("EXPECTI_MINIMAX = " + EVALUATION_FUNCTION_EXPECTI_MINIMAX + "\n");
        System.out.println("MINIMAX = " + EVALUATION_FUNCTION_MINIMAX + "\n");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~" + "\n");
    }

    public LogicGame(Board board, boolean notCLONE){
        this.board = board;
        this.GUI = false;

        if(notCLONE) {

            if(DEBUG_GAME_SETTINGS) {
                checkGameSettings();
                DEBUG_GAME_SETTINGS = false;
            }

            //Set Up the game = roll dice and set an action to the pass button
            setUpGame();

            //Assign action to every clickable spot on the board
            startLogicGameAction();
        }
    }

    /**
     * Constructor for the logic game
     * @param board State of the game
     * @param buttonBoard Clickable spots on the board
     * @param playerPass Player label which shows who move
     * @param diceImgViews Images of pieces
     * @param images Images of pieces
     * @param passButton Pass button (Change the player move)
     */
    public LogicGame(Board board, Button[][] buttonBoard, Label playerPass,
                     ImageView diceImgViews, ArrayList<Image> images, Button passButton) {
        this.board = board;
        this.buttonBoard = buttonBoard;
        this.playerPass = playerPass;
        this.diceImgViews = diceImgViews;
        this.images = images;
        this.passButton = passButton;

        if(DEBUG_GAME_SETTINGS){
            checkGameSettings();
            DEBUG_GAME_SETTINGS = false;
        }

        //Set Up the game = roll dice and set an action to the pass button
        setUpGame();

        //Assign action to every clickable spot on the board
        startLogicGameAction();
    }


    private void setUpGame() {
        //Rolling dice
        dl.rollDice(this);

        //Action to the pass button
        if(GUI) passButton.setOnAction(e -> {
            dl.rollDice(this);
            cp.changePlayer(this);
        });
    }

    public AtomicInteger iniX = new AtomicInteger();
    public AtomicInteger iniY = new AtomicInteger();

    private void startLogicGameAction() {

        if(GUI) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    int finalX = x;
                    int finalY = y;

                    buttonBoard[x][y].setOnAction(e -> {

                        if (currentSpot == null) { //currentSpot = null means that player did not choose the piece yet
                            currentSpot = board.getSpot(finalX, finalY); // Get the spot from the board
                            if (currentSpot != null) { // Check if it is not null
                                // Check if this piece is from the dice pieces
                                if (dl.rightPiece(currentSpot.getPiece(), this)) {
                                    iniX.set(currentSpot.getX());
                                    iniY.set(currentSpot.getY());
                                    choicePiece(); // Assign the choice of the player
                                } else {
                                    currentSpot = null;
                                }
                            }
                        } else {

                            Spot tmp_spot = board.getSpot(finalX, finalY); // Get the spot from the board

                            // Check if it is not the same piece
                            if (currentSpot.getX() != finalX || currentSpot.getY() != finalY) {
                                if (tmp_spot == null) { // tmp_spot = null means that spot is empty
                                    em.executeMove(iniX, iniY, finalX, finalY, this, false); // Execute the move of the player
                                } else {
                                    // Check if tmp_spot has the same color as player
                                    if (tmp_spot.getPiece().isColor().equals(currentSpot.getPiece().isColor())) {
                                        currentSpot = board.getSpot(finalX, finalY); //Change the current piece to another piece
                                        // Check if this piece is from the dice pieces
                                        if (dl.rightPiece(currentSpot.getPiece(), this))
                                            choicePiece(); // Assign the choice of the player
                                        else {
                                            currentSpot = null;
                                            bh.removeHighlightButtons(this); //Remove highlighted buttons
                                        }
                                        // This condition works if the piece is enemy
                                    } else {
                                        em.executeMove(iniX, iniY, finalX, finalY, this, false);
                                    }
                                }
                            }
                        }
                        //System.out.println("TEST " + iniX + " " + iniY + " " + finalX + " " + finalY);
                    });
                }
            }
        }

        //If the white is the AI player, when we run the AI algorithms
        //AI move
        if(!PLAYER_WHITE.isHuman()) {
            Move AImove = null;
            //System.out.println("White AI");
            switch (AI_WHITE) {
                case 0 : {
                    AImove = randomAgent.executeRandomMove(clone(), dicePiece, blackMove);
                    break;
                }
                case 1 : {
                    AImove = expectimax.calculateBestMoves(clone(), DEPTH_WHITE);
                    break;
                }
                case 2 : {
                    AImove = miniMax.calculateBestMoves(clone(), DEPTH_WHITE);
                    break;
                }
                case 4 : {
                    AImove = expectiMinimax.calculateBestMoves(clone(), DEPTH_WHITE);
                    break;
                }
            }

            if(AImove != null) {
                TOTAL_STEP_WHITE_IN_THE_GAME++;
                executeMovesAI.executeMovesAI(this, AImove);
            } else {
                dl.rollDice(this);
                cp.changePlayer(this);
            }
        }
    }

    /**
     * Assign the choice of the player
     */
    private void choicePiece() {
        if(allLegalMoves != null) {
            if(GUI) bh.removeHighlightButtons(this);
            allLegalMoves = null;
        }

        Piece piece = currentSpot.getPiece();
        //System.out.println(piece.getName());
        allLegalMoves = piece.checkPlayerMove(this, currentSpot, blackMove, 0);

        if (allLegalMoves == null) {
            currentSpot = null;
        } else {
            if(GUI) bh.highlightButtons(this);
        }
    }

    public GameScene getGameSc(){
        return gameSc;
    }

    public Stage getMainStage(){
        return mainStage;
    }

    public IntroScene getIntroSc(){
        return introSc;
    }

    public LogicGame clone(){
        Board newBoard = board.clone();

        LogicGame cloneLogicGame = new LogicGame(newBoard, false);

        cloneLogicGame.dicePiece = dicePiece;

        cloneLogicGame.blackMove = blackMove;

        return cloneLogicGame;
    }

    @Override
    public double[] toArray() {
        double[] boardArray = new double[65];

        int point = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Spot spot = board.getSpot(i, j);

                if(spot == null)
                    boardArray[point] = -1;
                else {
                    Piece piece = spot.getPiece();
                    if(piece.getColor())
                        boardArray[point] = piece.getNameInt() + 8;
                    else
                        boardArray[point] = piece.getNameInt() + 1;
                }
                point++;
            }
        }

        boardArray[point] = (dicePiece + 1) * 100;

        return boardArray;
    }

    @Override
    public boolean isSkipped() {
        System.out.println("isSKipped was called");
        ArrayList<Move> tmp = new ActionSpace().actionSpace(this, blackMove);
        return tmp.size() == 0;
    }

    @Override
    public INDArray getData() {
        return Nd4j.create(toArray());
    }

    @Override
    public Encodable dup() {
        System.out.println("Dup was called");
        return new LogicGame(new Board(), false);
    }
}
