package Logic.AdditionalPieceLogic;

import Board.Spot;
import Logic.LogicGame;
import Pieces.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class PawnPromotion {

    /**
     * Check on pawn promotion
     * @param spot Spot of the pawn
     */
    public void checkPawnPromotion(Spot spot, LogicGame l) {
        Piece pawn = spot.getPiece();
        if(pawn.isColor().equals("White")){
            if(spot.getX() == 7 && !l.winFlag) {
                l.getGameSc().addMoveToHist("WHITE PROMOTION:");
                pawnPromotion(spot, false, l);
            }
        } else {
            if(spot.getX() == 0 && !l.winFlag) {
                l.getGameSc().addMoveToHist("BLACK PROMOTION:");
                pawnPromotion(spot, true, l);
            }
        }
    }

    /**
     * Perform EnPassant
     * @param spot The spot of the pawn
     * @param black Color of the piece
     */
    private void pawnPromotion(Spot spot, boolean black, LogicGame l) {
        int random = (int)Math.floor(Math.random()*(4-0+1)+0);

        if(black) {
            if (l.playerBlack.isHuman())
                random = (int) (Math.random() * 4);
        } else {
            if (l.playerWhite.isHuman())
                random = (int) (Math.random() * 4);
        }

        if(!l.winFlag) {
            Stage mainStage = l.getMainStage();
            // Shows an alert informing which piece the player got from the random dice roll.
            if (random != 0) {
                String result = promRoll(random);

                if(black) {
                    if (l.playerBlack.isHuman()) {
                        Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
                        resAlert.setTitle("Random Roll Result");
                        resAlert.setHeaderText("Dice Chess 8 rolled the dice... Your pawn got promoted to a " + result + ".");
                        resAlert.initOwner(mainStage);
                        resAlert.showAndWait();
                    }
                } else {
                    if (l.playerWhite.isHuman()) {
                        Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
                        resAlert.setTitle("Random Roll Result");
                        resAlert.setHeaderText("Dice Chess 8 rolled the dice... Your pawn got promoted to a " + result + ".");
                        resAlert.initOwner(mainStage);
                        resAlert.showAndWait();
                    }
                }

                switch (result) {
                    case "Knight" -> {
                        Knight knight = new Knight(black);
                        l.board.pieceHeap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 1);
                        spot.setPiece(knight);
                    }
                    case "Bishop" -> {
                        Bishop bishop = new Bishop(black);
                        l.board.pieceHeap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 0);
                        spot.setPiece(bishop);
                    }
                    case "Queen" -> {
                        Queen queen = new Queen(black);
                        l.board.pieceHeap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 4);
                        spot.setPiece(queen);
                    }
                    case "Rook" -> {
                        Rook rook = new Rook(black);
                        l.board.pieceHeap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 5);
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
                if (results.isPresent()) {
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
