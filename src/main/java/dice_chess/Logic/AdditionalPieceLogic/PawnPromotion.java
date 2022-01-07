package dice_chess.Logic.AdditionalPieceLogic;

import dice_chess.Board.Spot;
import dice_chess.Logic.LogicGame;
import dice_chess.Pieces.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class PawnPromotion {

    /**
     * Check on pawn promotion
     * @param l LogicGame object
     * @param spot Spot of the pawn
     * @param AI boolean variable which determine if it is AI simulation or not
     */
    public void checkPawnPromotion(Spot spot, LogicGame l, boolean AI) {
        Piece pawn = spot.getPiece();
        if(pawn.isColor().equals("White")){
            if(spot.getX() == 7 && !l.winFlag) {
                if(!AI) l.getGameSc().addMoveToHist("WHITE PROMOTION:");
                pawnPromotion(spot, false, l, AI);
            }
        } else {
            if(spot.getX() == 0 && !l.winFlag) {
                if(!AI) l.getGameSc().addMoveToHist("BLACK PROMOTION:");
                pawnPromotion(spot, true, l, AI);
            }
        }
    }

    /**
     * Perform pawn promotion
     * @param spot The spot of the pawn
     * @param black Color of the piece
     * @param l LogicGame object
     * @param noGuiForAi boolean variable which determine if it is AI simulation or not
     */
    private void pawnPromotion(Spot spot, boolean black, LogicGame l, boolean noGuiForAi) {
        int random = (int)Math.floor(Math.random()*(4-1+1)+1);


        if(!noGuiForAi) random = (int) (Math.random() * 4);


        if(!l.winFlag) {
            Stage mainStage = l.getMainStage();
            // Shows an alert informing which piece the player got from the random dice roll.
            if (random != 0) {
                String result = promRoll(random);

                if(!noGuiForAi) {
                    Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
                    resAlert.setTitle("Random Roll Result");
                    resAlert.setHeaderText("Dice Chess 8 rolled the dice... Your pawn got promoted to a " + result + ".");
                    resAlert.initOwner(mainStage);
                    resAlert.showAndWait();
                }

                switch (result) {
                    case "Knight" : {
                        Knight knight = new Knight(black);
                        l.board.pieceMap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 1);
                        spot.setPiece(knight);
                        break;
                    }
                    case "Bishop" : {
                        Bishop bishop = new Bishop(black);
                        l.board.pieceMap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 0);
                        spot.setPiece(bishop);
                        break;
                    }
                    case "Queen" : {
                        Queen queen = new Queen(black);
                        l.board.pieceMap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 4);
                        spot.setPiece(queen);
                        break;
                    }
                    case "Rook" : {
                        Rook rook = new Rook(black);
                        l.board.pieceMap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 5);
                        spot.setPiece(rook);
                        break;
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
                        l.board.pieceMap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 1);
                        spot.setPiece(knight);
                    } else if (results.get() == bChoice) {
                        Bishop bishop = new Bishop(black);
                        l.board.pieceMap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 0);
                        spot.setPiece(bishop);
                    } else if (results.get() == qChoice) {
                        Queen queen = new Queen(black);
                        l.board.pieceMap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 4);
                        spot.setPiece(queen);
                    } else if (results.get() == rChoice) {
                        Rook rook = new Rook(black);
                        l.board.pieceMap.pawn(spot.getPiece().getNameInt(), spot.getPiece().getColor(), spot.getX(), spot.getY(), 5);
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
        switch (iterator) {
            case 0 : {
                return "Pawn: FREE CHOICE";
            }
            case 1 : {
                return "Knight";
            }
            case 2 : {
                return "Bishop";
            }
            case 3 : {
                return "Queen";
            }
            case 4 : {
                return "Rook";
            }
            default : {
                return null;
            }
        }
    }

}
