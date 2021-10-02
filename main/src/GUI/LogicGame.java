package GUI;

import Board.*;
import Logic.Move;
import Pieces.Piece;
import Players.Human;
import Players.Player;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LogicGame {

    private Spot currentSpot = null;
    private Player player = new Human(false, true);
    private ArrayList<Spot> allLegalMoves = null;

    public LogicGame() {
        // Empty.
    }

    public void setSpotAction(Board board, Button[][] buttonBoard) {
        Move move = new Move();
        AtomicInteger iniX = new AtomicInteger();
        AtomicInteger iniY = new AtomicInteger();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int finalX = x;
                int finalY = y;

                buttonBoard[x][y].setOnAction(e -> {
                    if(currentSpot == null) {
                        currentSpot = board.getSpot(finalX, finalY);
                        if(currentSpot != null) {
                            iniX.set(currentSpot.getX());
                            iniY.set(currentSpot.getY());
                            choicePiece(board, buttonBoard);
                        }
                    } else {
                        Spot tmp_spot = board.getSpot(finalX, finalY);
                        if (currentSpot.getX() != finalX || currentSpot.getY() != finalY) {
                            if (tmp_spot == null) {
                                movePiece(move, board, iniX, iniY, finalX, finalY, buttonBoard);
                            } else {
                                if (tmp_spot.getPiece().isColor().equals(currentSpot.getPiece().isColor())) {
                                    currentSpot = board.getSpot(finalX, finalY);
                                    choicePiece(board, buttonBoard);
                                } else {
                                    movePiece(move, board, iniX, iniY, finalX, finalY, buttonBoard);
                                }
                            }
                        }
                    }
                    //System.out.println("TEST " + iniX + " " + iniY + " " + finalX + " " + finalY);
                });
            }
        }
    }

    private void choicePiece(Board board, Button[][] buttonBoard){
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
            highlightButtons(buttonBoard);
        }
    }

    private void movePiece(Move move, Board board, AtomicInteger iniX, AtomicInteger iniY, int finalX, int finalY, Button[][] buttonBoard) {
        boolean flag = move.movePiece(board, currentSpot, allLegalMoves, finalX, finalY);
        if (flag) {
            Spot iniSpot = board.getSpot(iniX.intValue(),iniY.intValue());
            Spot finalSpot = board.getSpot(finalX,finalY);
            String c;

            if ((finalX + finalY) % 2 != 0) { c = "green"; }
            else { c = "white"; }

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
    }

    private void highlightButtons(Button[][] buttonBoard) {
        for (Spot spot : allLegalMoves) {
            int x = spot.getX();
            int y = spot.getY();
            buttonBoard[x][y].setStyle("-fx-background-color: rgb(204, 204, 255)");
        }
    }

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
}
