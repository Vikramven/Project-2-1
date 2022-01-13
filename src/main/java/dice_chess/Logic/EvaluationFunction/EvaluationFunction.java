package dice_chess.Logic.EvaluationFunction;

import dice_chess.Board.*;
import dice_chess.Board.PieceMap;
import dice_chess.Logic.DQN.DQN;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;
import dice_chess.Players.AI;
import dice_chess.Players.Human;
import javafx.application.Platform;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class EvaluationFunction {

    public EvaluationFunction()  {
        Board board = new Board();
        LogicGame logicGame = new LogicGame(board, new AI(false), new Human(true), 3, 3, 3, 0, 0);

        System.out.println(getShannonEvalScore(logicGame.board, logicGame.board.pieceMap, !logicGame.blackMove));

    }

    /**
     *
     Problem:cost is static, we need to determine what the best possible moves are, but how do we do that? cannot be random?
     can we assign fixed possible 'best costs' for every move?

     Co-ordinate class only contains location of x and y

     seems to be correct
     */
    public int[][] evaluateTheBoard(PieceMap enemyPieces, boolean player, Board board, Piece piece) {
        ArrayList<Move> movesEnemy = new ArrayList<>();
        int[][] emptyCost = new int[8][8];
        for (int i = 0; i < 6; i++) { //6 pieces
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

            int check = move.getPiece().getNameInt();

            cost[goodX][goodY] = getEvaluateAttack(check);

            cost[badX][badY] = -10;

        }

        return cost;
    }

    public int getEvaluateAttack(int check) {
        switch (check) {
            case 0: // Bishop
                return  10;
            case 1: // Knight
                return  10;
            case 2: // King
                return  1000;
            case 3: // Pawn
                return 5;
            case 4: // Queen
                return  20;
            case 5: // Rook
                return  15;
        }
        return 0;
    }


    public static void main(String[] args) {
        Platform.startup(() ->
        {
            new EvaluationFunction();
        });
    }

    /**
     * Do we have to assign values to a board matrix w/ this evaluation like we did w/ the prev one OR
     * is it a single value evaluation, bcs it seems so in the link
     * @param player current player's (getting evaluated) color
     * @return evaluation of the board (score) in relation to the side to move
     */
    public double getShannonEvalScore(Board board, PieceMap pieceMap, boolean player) {

        // Testing, should output 8, 2 and 1 initially
        System.out.println("Pawn " + pieceCount(pieceMap,3, player));
        System.out.println("Rook " + pieceCount(pieceMap,5, player));
        System.out.println("King " + pieceCount(pieceMap,2, player));

        // PieceInt Sequence
        // 0 Bishop | 1 Knight | 2 King | 3 Pawn | 4 Queen | 5 Rook
        double matScore = 200 * compSideDiff(pieceCount(pieceMap,2, player), pieceCount(pieceMap,2, !player))
                + 9 * compSideDiff(pieceCount(pieceMap,4, player), pieceCount(pieceMap, 4, !player))
                + 5 * compSideDiff(pieceCount(pieceMap,5, player), pieceCount(pieceMap, 5, !player))
                + 3 * (compSideDiff(pieceCount(pieceMap,0, player), pieceCount(pieceMap, 0, !player))
                    + compSideDiff(pieceCount(pieceMap,1, player), pieceCount(pieceMap, 1, !player)))
                + 1 * compSideDiff(pieceCount(pieceMap,3, player), pieceCount(pieceMap, 3, player));

        // Compute Mobility (i.e. nrLegalMoves) Score
        // TODO: Adjust to crtMob, the countMob() method only counts for opposing players as it is
        int crtMob = countMobility(pieceMap, player, board);
        int oppMob = countMobility(pieceMap, !player, board);
        System.out.println("CrtPlayer Moves " + crtMob);
        System.out.println("OppPlayer Moves " + oppMob);

        double mobScore = 0.1 * compSideDiff(crtMob, oppMob);

        return (matScore + mobScore);
    }


    private int pieceCount(PieceMap pieceMap, int pieceInt, boolean player) {

        return pieceMap.getAllPieces(pieceInt, player).size();
    }

    private int countMobility(PieceMap enemyPieces, boolean black, Board board) {

        ArrayList<Move> movesEnemy = new ArrayList<>();
        int[][] emptyCost = new int[8][8];

        for (int i = 0; i < 6; i++) { // 6 pieces
            LinkedList<Coordinate> pieces = enemyPieces.getAllPieces(i, black);
            for (int j = 0; j < pieces.size(); j++) {
                Coordinate coordinate = pieces.get(j);

                Spot spot = board.getSpot(coordinate.x, coordinate.y);

                Piece piece = spot.getPiece();

                movesEnemy.addAll(piece.allLegalMoves(board,spot, emptyCost));
            }
        }
        return movesEnemy.size();
    }

    /**
     *
     * @param iniSideScore as per Shannon (1949), it behaves like the currentSideScore; in the MatMob eval, white side
     * @param finSideScore as per Shannon (1949), it behaves like the opposingSideScore; in the MatMob eval, black side
     * @return the difference (value) between the sides
     */
    private int compSideDiff(int iniSideScore, int finSideScore) {
        return (iniSideScore - finSideScore);
    }
}
