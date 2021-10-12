package Pieces;

import Board.Board;
import Board.Spot;

import java.util.ArrayList;

public interface PieceMove {

    public ArrayList<Spot> allLegalMoves(Board board, Spot spot);
}
