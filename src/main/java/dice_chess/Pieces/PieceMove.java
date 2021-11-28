package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;

public interface PieceMove {

    public ArrayList<Move> allLegalMoves(Board board, Spot spot, int[][] cost);
}
