package dice_chess.Logic.EvaluationFunction;

import dice_chess.Board.*;
import dice_chess.Board.PieceMap;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;

public class EvaluationFunction {

    /**
     *
     Problem:cost is static, we need to determine what the best possible moves are, but how do we do that? cannot be random?
     can we assign fixed possible 'best costs' for every move?

     Co-ordinate class only contains location of x and y

     seems to be correct
     */
    public int[][] evaluateTheBoard(PieceMap enemyPieces, boolean player, Board board, Piece piece){
        ArrayList<Move> movesEnemy = new ArrayList<>();
        int[][] emptyCost = new int[8][8];
        for (int i = 0; i < 6; i++) {//6 pieces
            LinkedList<Coordinate> pieces = enemyPieces.getAllPieces(i, !player);
            for (int j = 0; j < pieces.size(); j++) {
                Coordinate coordinate = pieces.get(j);

                Spot spot = board.getSpot(coordinate.x, coordinate.y);

                piece = spot.getPiece();

                movesEnemy.addAll(piece.allLegalMoves(board,spot, emptyCost));
            }
        }

        int[][] cost = new int[8][8];


        for (int i = 0; i < movesEnemy.size(); i++) {
            Move move = movesEnemy.get(i);

            int badX = move.getX();
            int badY = move.getY();

            int goodX = move.getPieceSpotX();
            int goodY = move.getPieceSpotY();

            int check=move.getPiece().getNameInt();



            cost[goodX][goodY] = getEvaluateAttack(check);


            cost[badX][badY] = -10;

        }

        return cost;
    }


    public int getEvaluateAttack(int check){
        switch (check) {
            case 0: //Bishop
                return  10;
            case 1: //Knight
                return  10;
            case 2: //King
                return  1000;
            case 3: //Pawn
                return 5;
            case 4: //Queen
                return  20;
            case 5: //Rook
                return  15;
        }
        return 0;
    }
}
