package Logic.GameLogic;

import Logic.LogicGame;
import Pieces.Piece;
import javafx.scene.image.ImageView;

public class DiceLogic {

    /**
     * Randomly roll the dice
     */
    public void rollDice(LogicGame l) {
        for (ImageView diceImgView : l.diceImgViews) {
            int random = (int) (Math.random() * 6);
            diceImgView.setImage(l.images.get(random));
            l.dicePiece = random;
        }
    }


    /**
     * Check if player takes a piece from dice pieces
     * @param piece Player choice
     * @return true = this piece is in the dice pieces / false = not in the dice pieces
     */
    public boolean rightPiece(Piece piece, LogicGame l) {
        int j = l.dicePiece;
        return j == piece.getNameInt();
    }
}
