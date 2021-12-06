package dice_chess.Logic.RandomAgent;

import dice_chess.Board.Coordinate;
import dice_chess.Board.Spot;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;

public class RandomAgent {

    /**
     * Random Agent, we take all legal possible move and randomly pick the move
     * @param l LogicGame object
     * @param pieceNum the name int of the piece
     * @param player the color of the player
     * @return the random move of the exact piece
     */
    public Move executeRandomMove(LogicGame l, int pieceNum, boolean player){

        LinkedList<Coordinate> allPieces = l.board.pieceHeap.getAllPieces(pieceNum, player);

        ArrayList<Move> allMovesPiece = new ArrayList<>();

        //Find all pieces and get all legal possible moves of these pieces
        for (int j = 0; j < allPieces.size(); j++) {
            Coordinate coordinate = allPieces.get(j);

//                    System.out.println(coordinate.x + "  " + coordinate.y);

            Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

            Piece piece = spot.getPiece();

            ArrayList<Move> allMovesPieceOfCurrentPiece = piece.checkPlayerMove(l.board, spot, player, l.board.pieceHeap, true);

            allMovesPiece.addAll(allMovesPieceOfCurrentPiece);
        }

        //Randomly pick the move from the list
        int max = allMovesPiece.size() - 1;
        int min = 0;
        int randomNumber = (int)Math.floor(Math.random()*(max-min+1)+min);

        if(allMovesPiece.size() == 0)
            return null;


        return allMovesPiece.get(randomNumber);
    }
}
