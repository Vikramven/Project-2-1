package dice_chess.Logic.GameLogic;

import dice_chess.Logic.LogicGame;
import dice_chess.Pieces.Piece;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DiceLogic {

    /**
     * Randomly roll the dice
     */
    public void rollDice(LogicGame l) {
        int random = (int) (Math.random() * 6);
        l.diceImgViews.setImage(l.images.get(random));
        l.dicePiece = random;
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
