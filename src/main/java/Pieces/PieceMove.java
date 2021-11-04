package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public interface PieceMove {

    public ArrayList<Move> allLegalMoves(Board board, Spot spot, int[][] cost);
}
