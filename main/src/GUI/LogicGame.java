package GUI;

import Board.*;
import Logic.Move;
import Pieces.Piece;
import Players.Human;
import Players.Player;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class LogicGame {

    private Spot currentSpot = null;
    private Player player = new Human(false, true);
    private ArrayList<Spot> allLegalMoves = null;

    public LogicGame() {
        // Empty.
    }

    public void setSpotAction(Board board, Button[][] buttonBoard) {

        Move move = new Move();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int finalX = x;
                int finalY = y;
                /*
                 */
                buttonBoard[x][y].setOnAction(e -> {
                    System.out.println("TEST " + finalX + " " + finalY);
//                    currentSpot = board.getSpot(finalX,finalY);
//                    System.out.println("TEST " + currentSpot.getX() + " " + currentSpot.getY());

                    if(currentSpot == null) {
                        currentSpot = board.getSpot(finalX, finalY);
                        if(currentSpot != null) {
                            choicePiece(board, buttonBoard);
                        }
                    } else {
                        Spot tmp_spot = board.getSpot(finalX, finalY);
                        if (currentSpot.getX() != finalX || currentSpot.getY() != finalY) {
                            if (tmp_spot == null) {
                                movePiece(move, board, finalX, finalY, buttonBoard);
                            } else {
                                if (tmp_spot.getPiece().isColor().equals(currentSpot.getPiece().isColor())) {
                                    currentSpot = board.getSpot(finalX, finalY);
                                    choicePiece(board, buttonBoard);
                                } else {
                                    movePiece(move, board, finalX, finalY, buttonBoard);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private void choicePiece(Board board, Button[][] buttonBoard){
        if(allLegalMoves != null) {
            removeHighLightButtons(buttonBoard);
            allLegalMoves = null;
        }

        Piece piece = currentSpot.getPiece();
        System.out.println(piece.getName());
        allLegalMoves = piece.checkPlayerMove(board, currentSpot, player);

        if (allLegalMoves == null) {
            currentSpot = null;
        } else {
            System.out.println("NOT NULL");
            highlightButtons(buttonBoard);
        }
    }

    private void movePiece(Move move, Board board, int finalX, int finalY, Button[][] buttonBoard){
        boolean flag = move.movePiece(board, currentSpot, allLegalMoves, finalX, finalY);
        if (flag) {
            //Change image of piece
            removeHighLightButtons(buttonBoard);
            currentSpot = null;
            allLegalMoves = null;
        }
    }

    private void highlightButtons(Button[][] buttonBoard) {
        for (Spot spot : allLegalMoves) {
            int x = spot.getX();
            int y = spot.getY();
            buttonBoard[x][y].setStyle("-fx-background-color: green;"); // To be changed later, just to see if it's working.
        }
    }

    private void removeHighLightButtons(Button[][] buttonBoard){
        String c;
        for (Spot spot : allLegalMoves) {
            int x = spot.getX();
            int y = spot.getY();
            if ((x + y) % 2 != 0) {
                c = "black";
            } else {
                c = "white";
            }
            buttonBoard[x][y].setStyle("-fx-background-color: " + c + ";");
        }
    }
}
