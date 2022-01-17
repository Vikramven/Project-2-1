package dice_chess.Logic.EvaluationFunction;
import dice_chess.Logic.AI.HelpersAI.Node;

import static dice_chess.Constant.Constant.*;
import dice_chess.Board.*;
import dice_chess.Board.PieceMap;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.*;
import dice_chess.Logic.Hybrid.Tuple;


import java.lang.management.MonitorInfo;
import java.lang.reflect.Array;
import java.util.LinkedList;

import java.util.ArrayList;
public class EvaluationFunction {

    private double[][] costDynamic;

    private final int evalFun;

    /**
     * 0 - no evaluation function
     * 1 - position + (position+threats)
     * 2 - Shannon evaluation function
     * @param evalFun evaluation function, which we will use
     */

    public EvaluationFunction(int evalFun, boolean player, LogicGame l){
        this.evalFun = evalFun;

        if(evalFun == 1)
            evaluateTheBoard(player, l);
    }

    public double evaluateMove(int x, int y, Piece piece, boolean black, int moveX, int moveY, LogicGame l){

        double cost = 0;
        if(evalFun == 1) {
            cost = firstEvaluationApproach(x, y, piece.getNameInt(), black);
        } else if (evalFun == 2){
            cost = secondEvaluationApproach(l.clone(), piece, x, y, moveX, moveY);
        } else if( evalFun == 3) {
            cost = assignProb(l, black, x, y, moveX, moveY, piece);
        }

        return cost;
    }


    private double firstEvaluationApproach(int x, int y, int pieceInt, boolean black){
        switch (pieceInt){
            case 0 : {
                return costDynamic[x][y] + new Bishop(black).getPositionCost()[x][y];
            }
            case 1 : {
                return costDynamic[x][y] + new Knight(black).getPositionCost()[x][y];
            }
            case 2 : {
                return costDynamic[x][y] + new King(black).getPositionCost()[x][y];
            }
            case 3 : {
                return costDynamic[x][y] + new Pawn(black).getPositionCost()[x][y];
            }
            case 5 : {
                return costDynamic[x][y] + new Rook(black).getPositionCost()[x][y];
            }
        }

        return 0;
    }

    private double secondEvaluationApproach(LogicGame l, Piece piece, int x, int y, int moveX, int moveY){
        //Simulating the move
        Move move = new Move(moveX, moveY, piece, 0, x, y);

        //Get the piece from the simulated board
        l.currentSpot = l.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

        //Add the move to the logic game
        l.allLegalMoves = new ArrayList<>();
        l.allLegalMoves.add(move);

        //Simulate the move
        l.em.movePiece(move.getX(), move.getY(), l, false, true, true);

        //Reset our states
        l.allLegalMoves = null;
        l.currentSpot = null;

        return getShannonEvalScore(l.board.pieceMap, piece.getColor(), l);
    }

    /**
     *
     Problem:cost is static, we need to determine what the best possible moves are, but how do we do that? cannot be random?
     can we assign fixed possible 'best costs' for every move?

     Co-ordinate class only contains location of x and y

     seems to be correct
     */
    private void evaluateTheBoard(boolean player, LogicGame l) {
        ArrayList<Move> movesEnemy = new ArrayList<>();
        for (int i = 0; i < 6; i++) { //6 pieces
            LinkedList<Coordinate> pieces = l.board.pieceMap.getAllPieces(i, !player);
            for (int j = 0; j < pieces.size(); j++) {
                Coordinate coordinate = pieces.get(j);

                Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

                Piece piece = spot.getPiece();

                movesEnemy.addAll(piece.allLegalMoves(l, spot, 0));
            }
        }

        costDynamic = new double[8][8];

        for (int i = 0; i < movesEnemy.size(); i++) {
            Move move = movesEnemy.get(i);

            int badX = move.getX();
            int badY = move.getY();

            int goodX = move.getPieceSpotX();
            int goodY = move.getPieceSpotY();

            int check = move.getPiece().getNameInt();

            costDynamic[goodX][goodY] = getEvaluateAttack(check);

            costDynamic[badX][badY] = -10;
        }
    }

    private int getEvaluateAttack(int check) {
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


    /**
     * Do we have to assign values to a board matrix w/ this evaluation like we did w/ the prev one OR
     * is it a single value evaluation, bcs it seems so in the link
     * @param player current player's (getting evaluated) color
     * @return evaluation of the board (score) in relation to the side to move
     */
    private double getShannonEvalScore(PieceMap pieceMap, boolean player, LogicGame l) {

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
        int crtMob = countMobility(pieceMap, player, l);
        int oppMob = countMobility(pieceMap, !player, l);

        double mobScore = 0.1 * compSideDiff(crtMob, oppMob);

        return (matScore + mobScore);
    }


    private int pieceCount(PieceMap pieceMap, int pieceInt, boolean player) {

        return pieceMap.getAllPieces(pieceInt, player).size();
    }

    private int countMobility(PieceMap enemyPieces, boolean black, LogicGame l) {

        ArrayList<Move> movesEnemy = new ArrayList<>();

        for (int i = 0; i < 6; i++) { // 6 pieces
            LinkedList<Coordinate> pieces = enemyPieces.getAllPieces(i, black);
            for (int j = 0; j < pieces.size(); j++) {
                Coordinate coordinate = pieces.get(j);

                Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

                if(spot == null)
                    System.out.println("GGGG");
                Piece piece = spot.getPiece();

                movesEnemy.addAll(piece.allLegalMoves(l, spot, 0));
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


//COST OF ONE INDIVIDUAL MOVE
     public double assignProb(LogicGame l, boolean playerB, int x, int y , int moveX, int moveY, Piece piece) {
        double cost = secondEvaluationApproach(l.clone(), piece, x, y, moveX, moveY);
        Move m = new Move(x, y, piece, cost, moveX, moveY);
        char colour;
         if ( playerB) {
             colour = 'B';
         }
         else{
             colour= 'W';
         }
        Tuple t = new Tuple(l.toArray(),Tuple.createAction(m, colour), cost  );

         ArrayList<Move> moves = new ArrayList<Move>();
         LinkedList<Coordinate> listCoordinate = l.board.pieceMap.getAllPieces(piece.getNameInt(), piece.getColor());
         for (int i = 0; i < listCoordinate.size(); i++) {
             Coordinate coorPiece = listCoordinate.get(i);
             moves.addAll(piece.allLegalMoves(l, new Spot(coorPiece.x, coorPiece.y, piece), 0));
         }
         double probability = ql.lookUpProb(t, moves.size());

        return probability;
    }
}