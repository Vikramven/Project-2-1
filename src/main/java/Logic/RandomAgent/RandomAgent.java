package Logic.RandomAgent;

import Board.Coordinate;
import Board.Spot;
import Logic.LogicGame;
import Logic.MoveLogic.Move;
import Pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;

public class RandomAgent {

    public Move executeRandomMove(LogicGame l, int pieceNum, boolean player){

        LinkedList<Coordinate> allPieces = l.board.pieceHeap.getAllPieces(pieceNum, player);

        ArrayList<Move> allMovesPiece = new ArrayList<>();

        for (int j = 0; j < allPieces.size(); j++) {
            Coordinate coordinate = allPieces.get(j);

//                    System.out.println(coordinate.x + "  " + coordinate.y);

            Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

            Piece piece = spot.getPiece();

            ArrayList<Move> allMovesPieceOfCurrentPiece = piece.checkPlayerMove(l.board, spot, player, l.board.pieceHeap, true);

            allMovesPiece.addAll(allMovesPieceOfCurrentPiece);
        }

        int max = allMovesPiece.size() - 1;
        int min = 0;
        int randomNumber = (int)Math.floor(Math.random()*(max-min+1)+min);

        return allMovesPiece.get(randomNumber);
    }
}
