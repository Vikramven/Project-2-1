package GUI;

import Board.*;
import Logic.Move;
import Pieces.Piece;
import Players.Human;
import Players.Player;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class LogicGame {

    Spot currentSpot = null;
    Player player = new Human(false, true);
    ArrayList<Spot> allLegalMoves = null;
    Button buttonStateBoard[][] = new Button[8][8];

    public LogicGame(){

    }

    public void setButtons(){
        Board board = new Board();
        Move move = new Move();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                buttonStateBoard[x][y] = new Button();
                int finalX = x;
                int finalY = y;
                /*



                 */
                buttonStateBoard[x][y].setOnAction(e -> {
                    if(currentSpot == null){
                        currentSpot = board.getSpot(finalX, finalY);
                        Piece piece = currentSpot.getPiece();
                        allLegalMoves = piece.checkPlayerMove(board, currentSpot, player);

                        if (allLegalMoves == null) {
                            currentSpot = null;
                        } else {
                            lightButton();
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

    private void lightButton(){
        for (int i = 0; i < allLegalMoves.size(); i++) {
            Spot spot = allLegalMoves.get(i);
            int x = spot.getX();
            int y = spot.getY();
            //buttonStateBoard[x][y] = qqqq
        }
    }
}
