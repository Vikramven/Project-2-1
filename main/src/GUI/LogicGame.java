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
                            Piece piece = currentSpot.getPiece();
                            System.out.println(piece.getName());
                            allLegalMoves = piece.checkPlayerMove(board, currentSpot, player);

                            if (allLegalMoves == null) {
                                currentSpot = null;
                            } else {
                                System.out.println("NOT NULL");
                                highlightButtons(buttonBoard);
                            }
                        } else {
                            ; // Do nothing, just so we don't get the warning saying the spot we clicked in is null
                        }
                    } else if(currentSpot.getPiece().isColor().equals(player.isColorSide())) {
                        boolean flag = move.movePiece(board, currentSpot, allLegalMoves, finalX, finalY);
                        if(flag){
                            int oldX = currentSpot.getX();
                            int oldY = currentSpot.getY();
                            board.setSpot(null, oldX, oldY);

                            currentSpot.setX(finalX);
                            currentSpot.setX(finalY);
                            board.setSpot(currentSpot, finalX, finalY);

                            //Change image of piece

                            currentSpot = null;
                            allLegalMoves = null;
                        }
                    }
                });
            }
        }
    }

    private void highlightButtons(Button[][] buttonBoard) {
        for (int i = 0; i < allLegalMoves.size(); i++) {
            Spot spot = allLegalMoves.get(i);
            int x = spot.getX();
            int y = spot.getY();
            buttonBoard[x][y].setStyle("-fx-background-color: green;");
        }
    }
}
