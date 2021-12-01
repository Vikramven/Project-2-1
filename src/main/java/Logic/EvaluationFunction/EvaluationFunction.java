package Logic.EvaluationFunction;

import Board.*;
import Board.PieceHeap;
import Logic.MoveLogic.Move;
import Pieces.Piece;
import Pieces.Queen;

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
    public int[][] evaluateTheBoard(PieceHeap enemyPieces, boolean player, Board board, Piece piece){
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


            //TODO decide which values for every piece
                switch (piece.getNameInt()) {
                    case 0: //Bishop
                        cost[goodX][goodY] = getPieceBishop(check); // put value for bishop
                    case 1: //Knight
                        cost[goodX][goodY] = 30;
                    case 2: //King
                        cost[goodX][goodY] = 1000;
                    case 3: //Pawn
                        cost[goodX][goodY] = 10;
                    case 4: //Queen
                        cost[goodX][goodY] = 50;
                    case 5: //Rook
                        cost[goodX][goodY] = 40;
                }


            //TODO decide which value
            cost[badX][badY] = -30;

        }

//        System.out.println();
//        for (int i = 0; i < cost.length; i++) {
//            for (int j = 0; j < cost.length; j++) {
//                System.out.print(cost[i][j] + " ");
//            }
//            System.out.println();
//        }
        return cost;
    }
    //TODO print this method for every piece
    public int getPieceBishop(int check){
        switch (check) {
            case 0: //Bishop
                return  30; // put value for bishop
            case 1: //Knight
                return  30;
            case 2: //King
                return  1000;
            case 3: //Pawn
                return 10;
            case 4: //Queen
                return  50;
            case 5: //Rook
                return  40;
        }
        return 0;
    }
}
