package dice_chess.Logic;

import dice_chess.Board.*;
import dice_chess.GUI.GUIMain;
import dice_chess.GUI.GameScene;
import dice_chess.GUI.IntroScene;
import dice_chess.Logic.AI.ExecuteMovesAI;
import dice_chess.Logic.AI.MiniMax;
import dice_chess.Logic.GameLogic.ChangePlayer;
import dice_chess.Logic.GameLogic.DiceLogic;
import dice_chess.Logic.HintLogic.ButtonHighlight;
import dice_chess.Logic.MoveLogic.ExecuteMove;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.*;
import dice_chess.Players.Player;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LogicGame extends GUIMain {

    // Current spot
    public Spot currentSpot = null;

    // All legal possible moves
    public ArrayList<Move> allLegalMoves = null;

    // Player
    public Player playerWhite;
    public Player playerBlack;

    public boolean blackMove = false;


    // Variables from the _dice_chess.GUI
    // State of the game
    public Board board;

    // Clickable spots on the board
    public final Button[][] buttonBoard;

    // Player label which shows who move
    public final Label playerPass;

    // _dice_chess.Pieces from rolling dice
    public int dicePiece;

    // Images of pieces
    public final ImageView[] diceImgViews;
    public final ArrayList<Image> images;

    // Pass button (Change the player move)
    public final Button passButton;

    // Boolean that allows other methods to know whether we have a winner or not
    public boolean winFlag = false;


    //Parts of logic
    public ChangePlayer cp = new ChangePlayer();
    public ButtonHighlight bh = new ButtonHighlight();
    public DiceLogic dl = new DiceLogic();
    public ExecuteMove em = new ExecuteMove();
    private final MiniMax miniMax = new MiniMax();
    private final ExecuteMovesAI executeMovesAI = new ExecuteMovesAI();

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
                     ImageView[] diceImgViews, ArrayList<Image> images, Button passButton, Player playerWhite, Player playerBlack) {
        this.board = board;
        this.buttonBoard = buttonBoard;
        this.playerPass = playerPass;
        this.diceImgViews = diceImgViews;
        this.images = images;
        this.passButton = passButton;
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;

        //Set Up the game = roll dice and set an action to the pass button
        setUpGame();

        //Assign action to every clickable spot on the board
        startLogicGameAction();
    }

    private void setUpGame() {
        //Rolling dice
        dl.rollDice(this);

        //Action to the pass button
        passButton.setOnAction(e -> {
            dl.rollDice(this);
            cp.changePlayer(this);
        });
    }

    public AtomicInteger iniX = new AtomicInteger();
    public AtomicInteger iniY = new AtomicInteger();

    private void startLogicGameAction() {

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int finalX = x;
                int finalY = y;

                buttonBoard[x][y].setOnAction(e -> {

                    if(currentSpot == null) { //currentSpot = null means that player did not choose the piece yet
                        currentSpot = board.getSpot(finalX, finalY); // Get the spot from the board
                        if(currentSpot != null) { // Check if it is not null
                            // Check if this piece is from the dice pieces
                            if(dl.rightPiece(currentSpot.getPiece(), this)) {
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
                                em.executeMove(iniX, iniY, finalX, finalY, this); // Execute the move of the player
                            } else {
                                // Check if tmp_spot has the same color as player
                                if (tmp_spot.getPiece().isColor().equals(currentSpot.getPiece().isColor())) {
                                    currentSpot = board.getSpot(finalX, finalY); //Change the current piece to another piece
                                    // Check if this piece is from the dice pieces
                                    if(dl.rightPiece(currentSpot.getPiece(), this))
                                        choicePiece(); // Assign the choice of the player
                                    else {
                                        currentSpot = null;
                                        bh.removeHighlightButtons(this); //Remove highlighted buttons
                                    }
                                // This condition works if the piece is enemy
                                } else {
                                    em.executeMove(iniX, iniY, finalX, finalY, this);
                                }
                            }
                        }
                    }
                    //System.out.println("TEST " + iniX + " " + iniY + " " + finalX + " " + finalY);
                });
            }
        }

        //AI move
        if(!playerWhite.isHuman()) {
            System.out.println("White AI");
            miniMax.calculateBestMoves(this);
            dl.rollDice(this);
            cp.changePlayer(this);
        }
            //executeMovesAI.executeMovesAI(this, miniMax.calculateBestMoves(this));
    }

    /**
     * Assign the choice of the player
     */
    private void choicePiece() {
        if(allLegalMoves != null) {
            bh.removeHighlightButtons(this);
            allLegalMoves = null;
        }

        Piece piece = currentSpot.getPiece();
        //System.out.println(piece.getName());
        allLegalMoves = piece.checkPlayerMove(board, currentSpot, blackMove, board.pieceHeap, false);

        if (allLegalMoves == null) {
            currentSpot = null;
        } else {
            bh.highlightButtons(this);
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

}
