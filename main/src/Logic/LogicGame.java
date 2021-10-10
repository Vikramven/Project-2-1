package Logic;

import Board.*;
import GUI.GUIMain;
import GUI.GameScene;
import Pieces.*;
import Players.Human;
import Players.Player;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class LogicGame extends GUIMain {

    //Current spot
    private Spot currentSpot = null;

    //All legal possible moves
    private ArrayList<Spot> allLegalMoves = null;

    //Player
    private final Player player = new Human(false, true);

    //Number of moves
    private int numberMoves = 3;

    //Variables from GUI

    //State of the game
    private final Board board;

    //Clickable spots on the board
    private final Button[][] buttonBoard;

    //Player label which shows who move
    private final Label playerPass;

    //Pieces from rolling dice
    private final int[] dicePiece;

    //Images of pieces
    private final ImageView[] diceImgViews;
    private final ArrayList<Image> images;

    //Pass button (Change the player move)
    private final Button passButton;

    /**
     * Constructor for the logic game
     * @param board State of the game
     * @param buttonBoard Clickable spots on the board
     * @param playerPass Player label which shows who move
     * @param dicePiece Pieces from rolling dice
     * @param diceImgViews Images of pieces
     * @param images Images of pieces
     * @param passButton Pass button (Change the player move)
     */
    public LogicGame(Board board, Button[][] buttonBoard, Label playerPass, int[] dicePiece,
                     ImageView[] diceImgViews, ArrayList<Image> images, Button passButton) {
        this.board = board;
        this.buttonBoard = buttonBoard;
        this.playerPass = playerPass;
        this.dicePiece = dicePiece;
        this.diceImgViews = diceImgViews;
        this.images = images;
        this.passButton = passButton;

        //Set Up the game = roll dice and set an action to the pass button
        setUpGame();

        //Assign action to every clickable spot on the board
        startLogicGameAction();
    }

    private void setUpGame() {
        //Rolling dice
        rollDice();

        //Action to the pass button
        passButton.setOnAction(e -> {
            changePlayer();
            rollDice();
        });
    }

    private void startLogicGameAction() {

        AtomicInteger iniX = new AtomicInteger();
        AtomicInteger iniY = new AtomicInteger();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int finalX = x;
                int finalY = y;

                buttonBoard[x][y].setOnAction(e -> {

                    if(currentSpot == null) { //currentSpot = null means that player did not choose the piece yet
                        currentSpot = board.getSpot(finalX, finalY); // Get the spot from the board
                        if(currentSpot != null) { // Check if it is not null
                            // Check if this piece is from the dice pieces
                            if(rightPiece(currentSpot.getPiece())) {
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
                                executeMove(iniX, iniY, finalX, finalY); // Execute the move of the player
                            } else {
                                // Check if tmp_spot has the same color as player
                                if (tmp_spot.getPiece().isColor().equals(currentSpot.getPiece().isColor())) {
                                    currentSpot = board.getSpot(finalX, finalY); //Change the current piece to another piece
                                    // Check if this piece is from the dice pieces
                                    if(rightPiece(currentSpot.getPiece()))
                                        choicePiece(); // Assign the choice of the player
                                    else {
                                        currentSpot = null;
                                        removeHighlightButtons(board, buttonBoard); //Remove highlighted buttons
                                    }
                                // This condition works if the piece is enemy
                                } else {
                                    executeMove(iniX, iniY, finalX, finalY);
                                }
                            }
                        }
                    }
                    //System.out.println("TEST " + iniX + " " + iniY + " " + finalX + " " + finalY);
                });
            }
        }
    }

    /**
     * Change the player in the logic and in the GUI
     */
    private void changePlayer() {
        numberMoves = 3; // Number of moves the player is allowed per turn.
        currentSpot = null;
        allLegalMoves = null;

        // Change the player in the logic and in the GUI
        if(player.isColorSide().equals("White")) {
            playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 65px 65px to 100px 100px, #ff8000, #32cd32);");
            player.setColorSide(true);
        } else {
            playerPass.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
            player.setColorSide(false);
        }

        removeHighlightButtons(board, buttonBoard); // Remove Highlight buttons
    }

    /**
     * Randomly roll the dice
     */
    private void rollDice() {
        int point = 0;
        for (ImageView diceImgView : diceImgViews) {
            int random = (int) (Math.random() * 6);
            diceImgView.setImage(images.get(random));
            dicePiece[point] = random;
            point++;
        }
    }

    /**
     * Remove one piece from dice pieces
     * @param piece Piece of the player
     */
    private void removeOneMove(Piece piece) {
        for (int i = 0; i < dicePiece.length; i++) {
            if(dicePiece[i] == piece.getNameInt()) {
                dicePiece[i] = 6;
                return;
            }
        }
    }

    /**
     * Check if player takes a piece from dice pieces
     * @param piece Player choice
     * @return true = this piece is in the dice pieces / false = not in the dice pieces
     */
    private boolean rightPiece(Piece piece) {
        for (int j : dicePiece) {
            if (j == piece.getNameInt())
                return true;
        }
        return false;
    }

    /**
     * Assign the choice of the player
     */
    private void choicePiece() {
        if(allLegalMoves != null) {
            removeHighlightButtons(board, buttonBoard);
            allLegalMoves = null;
        }

        Piece piece = currentSpot.getPiece();
        //System.out.println(piece.getName());
        allLegalMoves = piece.checkPlayerMove(board, currentSpot, player);

        if (allLegalMoves == null) {
            currentSpot = null;
        } else {
            highlightButtons();
        }
    }

    /**
     * Highlight buttons (Hints)
     */
    private void highlightButtons() {
        for (Spot spot : allLegalMoves) {
            int x = spot.getX();
            int y = spot.getY();
            buttonBoard[x][y].setStyle("-fx-background-color: rgb(204, 204, 255)");
        }
    }

    /**
     * Remove all hints from the board
     * @param board The state of the board
     * @param buttonBoard buttons on the board
     */
    private void removeHighlightButtons(Board board, Button[][] buttonBoard) {
        Spot spot;
        String c;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                spot = board.getSpot(x, y);
                if ((x + y) % 2 != 0) { c = "green"; }
                else { c = "white"; }
                if(spot == null) {
                    buttonBoard[x][y].setStyle("-fx-background-color: " + c + ";");
                } else {
                    buttonBoard[x][y].setStyle(
                            "-fx-background-color: " + c + ";" +
                                    "-fx-background-image: url('" + spot.getPiece().getImageURL() + "');" +
                                    "-fx-background-size: 70px;" +
                                    "-fx-background-repeat: no-repeat;" +
                                    "-fx-background-position: 50%;");
                }
            }
        }
    }

    /**
     * Execution the move
     * @param iniX GUI x
     * @param iniY GUI y
     * @param finalX where player moves x coordinate
     * @param finalY where player moves y coordinate
     */
    private void executeMove(AtomicInteger iniX, AtomicInteger iniY, int finalX, int finalY) {
        boolean flag = movePiece(finalX, finalY);
        if(flag) {
            movePieceGUI(iniX, iniY, finalX, finalY);
            numberMoves--;
            if (numberMoves == 0) {
                changePlayer();
                numberMoves = 3;
                rollDice();
            }
        }
    }

    /**
     * Repaint the board to the current state of the game
     * @param iniX GUI x
     * @param iniY GUI y
     * @param finalX where player moves x coordinate
     * @param finalY where player moves y coordinate
     */
    private void movePieceGUI(AtomicInteger iniX, AtomicInteger iniY, int finalX, int finalY) {
        Spot finalSpot = board.getSpot(finalX,finalY);
        String c;

        if ((finalX + finalY) % 2 != 0) {
            c = "green";
        } else {
            c = "white";
        }

        if(finalSpot != null) {
            buttonBoard[iniX.intValue()][iniY.intValue()].setStyle("-fx-background-color: " + c + ";");
            buttonBoard[finalX][finalY].setStyle(
                        "-fx-background-color: " + c + ";" +
                        "-fx-background-image: url('" + finalSpot.getPiece().getImageURL() + "');" +
                        "-fx-background-size: 70px;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-position: 50%;");
            }
        removeHighlightButtons(board, buttonBoard);
        currentSpot = null;
        allLegalMoves = null;
    }

    /**
     * Move the piece
     * @param x X coordinate which choose the player
     * @param y Y coordinate which choose the player
     * @return true = player chose (clicked on) a legal move / false = player did not choose a legal move
     */
    private boolean movePiece(int x, int y) {

        for (int i = 0; i < allLegalMoves.size(); i++) {
            if(x == allLegalMoves.get(i).getX() && y == allLegalMoves.get(i).getY()){

                removeOneMove(currentSpot.getPiece()); // Remove piece from dice pieces

                int oldX = currentSpot.getX();
                int oldY = currentSpot.getY();
                board.setSpot(null, oldX, oldY);

                Spot win = board.getSpot(x, y);
                if(win != null){
                    Piece winPiece = win.getPiece();
                    if(winPiece.getName().equals("King")){
                        String colorWin = currentSpot.getPiece().isColor();

                        Alert winAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        winAlert.setTitle("We have a winner!");
                        winAlert.setHeaderText(colorWin + " won the game! " +
                                "Would to like to play again or go back to the main menu?");
                        winAlert.initOwner(mainStage);

                        ButtonType playAgain = new ButtonType("Play Again!");
                        ButtonType goBack = new ButtonType("Go back.");

                        winAlert.getButtonTypes().setAll(playAgain,goBack);

                        Optional<ButtonType> result = winAlert.showAndWait();
                        if (result.get() == playAgain){
                            gameSc.setGameScene();
                            mainStage.setScene(gameSc.getGameScene());
                            mainStage.setFullScreen(true);
                            mainStage.setResizable(false);
                        } else if(result.get() == goBack) {
                            gameSc.setGameScene();
                            mainStage.setScene(introSc.getIntroScene());
                            mainStage.setFullScreen(true);
                            mainStage.setResizable(false);
                        }
                    }
                }

                currentSpot.setX(x);
                currentSpot.setY(y);
                board.setSpot(currentSpot, x, y);

                if(currentSpot.getPiece().getName().equals("Pawn")){
                    checkEnPassant(currentSpot);
                }

                if(currentSpot.getPiece().getName().equals("King")){
                    King piece = (King) currentSpot.getPiece();
                    int kingX = currentSpot.getX();
                    int kingY = currentSpot.getY();
                    boolean black = (kingX == 7);

                    if(piece.isCastling()) {
                        int longOrShort = kingY - oldY;
                        if (longOrShort == 2) { // Long Castling
                            changeRook(7, 4, kingX, black);
                        } else if(longOrShort == -2){ // Short Castling
                            changeRook(0, 2, kingX, black);
                        }
                    }
                    piece.setCastling(false);
                }

                if(currentSpot.getPiece().getName().equals("Rook")){
                    Rook rook = (Rook) currentSpot.getPiece();
                    rook.setCastling(false);
                }

                return true;
            }
        }
        return false;
    }

    /**
     * Change the rook
     * @param oldRookY old rook position Y
     * @param newRookY new rook position Y
     * @param kingX king position X
     * @param black color of the piece
     */
    private void changeRook(int oldRookY, int newRookY, int kingX, boolean black) {
        board.setSpot(null, kingX, oldRookY);
        Spot newRookSpot = new Spot(kingX, newRookY, new Rook(black));
        board.setSpot(newRookSpot, kingX, newRookY);
    }

    /**
     * Check on EnPassant
     * @param spot Spot of the pawn
     */
    private void checkEnPassant(Spot spot) {
        Piece pawn = spot.getPiece();
        if(pawn.isColor().equals("White")){
            if(spot.getX() == 7)
                enPassant(spot,false);
        } else {
            if(spot.getX() == 0)
                enPassant(spot,true);
        }
    }

    /**
     * Perform EnPassant
     * @param spot The spot of the pawn
     * @param black Color of the piece
     */
    private void enPassant(Spot spot, boolean black) {

        int random = (int) (Math.random() * 4);

        // Shows an alert informing which piece the player got from the random dice roll.
        if(random != 0) {
            String result = promRoll(random);
            Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
            resAlert.setTitle("Random Roll Result");
            resAlert.setHeaderText("Dice Chess 8 rolled the dice... Your pawn got promoted to a " + result + ".");
            resAlert.initOwner(mainStage);
            resAlert.showAndWait();

            switch (result) {
                case "Knight" -> {
                    Knight knight = new Knight(black);
                    spot.setPiece(knight);
                }
                case "Bishop" -> {
                    Bishop bishop = new Bishop(black);
                    spot.setPiece(bishop);
                }
                case "Queen" -> {
                    Queen queen = new Queen(black);
                    spot.setPiece(queen);
                }
                case "Rook" -> {
                    Rook rook = new Rook(black);
                    spot.setPiece(rook);
                }
            }
        } else {
            Alert selAlert = new Alert(Alert.AlertType.CONFIRMATION);
            selAlert.setTitle("Selecting a new piece...");
            selAlert.setHeaderText("LUCKY ROLL! Choose the piece which your pawn will be transformed to.");
            selAlert.initOwner(mainStage);

            ButtonType kChoice = new ButtonType(promRoll(1));
            ButtonType bChoice = new ButtonType(promRoll(2));
            ButtonType qChoice = new ButtonType(promRoll(3));
            ButtonType rChoice = new ButtonType(promRoll(4));

            selAlert.getButtonTypes().setAll(kChoice, bChoice, qChoice, rChoice);

            Optional<ButtonType> results = selAlert.showAndWait();
            if (results.get() == kChoice) {
                Knight knight = new Knight(black);
                spot.setPiece(knight);
            } else if (results.get() == bChoice) {
                Bishop bishop = new Bishop(black);
                spot.setPiece(bishop);
            } else if (results.get() == qChoice) {
                Queen queen = new Queen(black);
                spot.setPiece(queen);
            } else if (results.get() == rChoice) {
                Rook rook = new Rook(black);
                spot.setPiece(rook);
            }
        }
    }

    /**
     * Determine a valid promotion roll result
     * @param iterator The value gotten from a single dice roll
     * @return The name of the piece associated with the inputted iterator
     */
    private String promRoll(int iterator) {
        return switch (iterator) {
            case 0 -> "Pawn: FREE CHOICE";
            case 1 -> "Knight";
            case 2 -> "Bishop";
            case 3 -> "Queen";
            case 4 -> "Rook";
            default -> null;
        };
    }
}
