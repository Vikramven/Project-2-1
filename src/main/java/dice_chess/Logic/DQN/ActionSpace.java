package dice_chess.Logic.DQN;

import dice_chess.Board.Coordinate;
import dice_chess.Board.Spot;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;

public class ActionSpace {

    public ArrayList<Move> actionSpace(LogicGame logicGame, boolean blackSide){
        ArrayList<Move> actionSpace = new ArrayList<>();

        LinkedList<Coordinate> coordinatesOfPieces = logicGame.board.pieceMap.getAllPieces(logicGame.dicePiece, blackSide);
        for (int j = 0; j < coordinatesOfPieces.size(); j++) {
            Coordinate coordinate = coordinatesOfPieces.get(j);

            Spot spot = logicGame.board.getSpot(coordinate.x, coordinate.y);

            Piece piece = spot.getPiece();

            ArrayList<Move> allMovesPiece = piece.checkPlayerMove(logicGame.board, spot, blackSide,
                    logicGame.board.pieceMap, true);

            actionSpace.addAll(allMovesPiece);
        }
        return actionSpace;
    }
}
