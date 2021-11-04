package Logic.GameLogic;

import Logic.LogicGame;
import Pieces.Piece;
import javafx.scene.image.ImageView;

public class DiceLogic {

    /**
     * Randomly roll the dice
     */
    public void rollDice(LogicGame l) {
        int point = 0;
        for (ImageView diceImgView : l.diceImgViews) {
            int random = (int) (Math.random() * 6);
            diceImgView.setImage(l.images.get(random));
            l.dicePiece[point] = random;
            point++;
        }
    }

    /**
     * Remove one piece from dice pieces
     * @param piece Piece of the player
     */
    public void removeOneMove(Piece piece, LogicGame l) {
        for (int i = 0; i < l.dicePiece.length; i++) {
            if(l.dicePiece[i] == piece.getNameInt()) {
                l.dicePiece[i] = 6;
                return;
            }
        }
    }

    /**
     * Check if player takes a piece from dice pieces
     * @param piece Player choice
     * @return true = this piece is in the dice pieces / false = not in the dice pieces
     */
    public boolean rightPiece(Piece piece, LogicGame l) {
        for (int j : l.dicePiece) {
            if (j == piece.getNameInt())
                return true;
        }
        return false;
    }
}
