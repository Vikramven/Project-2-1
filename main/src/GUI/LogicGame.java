package GUI;

import Board.*;
import Pieces.King;
import Pieces.Piece;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LogicGame extends GameScene{

    private Spot currentSpot = null;
    private ArrayList<Spot> allLegalMoves = null;

    public LogicGame() {
        // Empty.
    }

    public void setSpotAction(Board board, Button[][] buttonBoard, Label playerPass) {
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
                                movePieceGUI(board, iniX, iniY, finalX, finalY, buttonBoard);

                                changePlayer(buttonBoard, playerPass);
                            } else {
                                if (tmp_spot.getPiece().isColor().equals(currentSpot.getPiece().isColor())) {
                                    currentSpot = board.getSpot(finalX, finalY);
                                    choicePiece(board, buttonBoard);
                                } else {
                                    movePieceGUI(board, iniX, iniY, finalX, finalY, buttonBoard);

                                    changePlayer(buttonBoard, playerPass);
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

    private void movePieceGUI(Board board, AtomicInteger iniX, AtomicInteger iniY, int finalX, int finalY, Button[][] buttonBoard) {
        boolean flag = movePiece(board, currentSpot, allLegalMoves, finalX, finalY);
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

    /**
     * Move the piece
     * @param board Board
     * @param spot Spot
     * @param legalMoves all legal possible moves for the piece
     * @param x X coordinate which choose the player
     * @param y Y coordinate which choose the player
     */
    public boolean movePiece(Board board, Spot spot, ArrayList<Spot> legalMoves, int x, int y){

        for (int i = 0; i < legalMoves.size(); i++) {
            if(x == legalMoves.get(i).getX() && y == legalMoves.get(i).getY()){
                int oldX = spot.getX();
                int oldY = spot.getY();
                board.setSpot(null, oldX, oldY);

                spot.setX(x);
                spot.setY(y);
                board.setSpot(spot, x, y);

                if(spot.getPiece().getName().equals("Pawn")){
                    checkEnPassant(spot);
                }

                if(spot.getPiece().getName().equals("King")){
                    King piece = (King) spot.getPiece();
                    if(piece.isCastling()) {
                        int longOrShort = spot.getY() - y;
                        if (longOrShort == 2) {
                            //TODO
                        } else if(longOrShort == -2){
                            //TODO
                        }
                    }
                    piece.setCastling(false);
                }
                return true;
            }
        }
        return false;
    }

    private boolean wasCastling(int longOrShort){
        return longOrShort == -2 || longOrShort == 2;
    }

    /**
     * Check on EnPassant
     * @param spot Spot of the pawn
     */
    private void checkEnPassant(Spot spot){
        Piece pawn = spot.getPiece();
        if(pawn.isColor().equals("White")){
            if(spot.getX() == 7)
                enPassant(spot);
        } else {
            if(spot.getX() == 0)
                enPassant(spot);
        }
    }

    /**
     * Do EnPassant
     * @param spot The spot of the pawn
     */
    private void enPassant(Spot spot){
        //TODO: CALL THE GUI and player choose which piece he/she wants
    }
}
