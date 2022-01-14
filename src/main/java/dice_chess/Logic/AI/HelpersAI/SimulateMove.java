package dice_chess.Logic.AI.HelpersAI;

import dice_chess.Board.Board;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;

public class SimulateMove {

    public void executeMove(Node childNode, LogicGame l, Board board, int dicePiece){
        //Simulating the move
        Move move = childNode.getMove();

        //Reset the state of the board
        l.board = board.clone();
        l.dicePiece = dicePiece;

        //Get the piece from the simulated board
        l.currentSpot = l.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

        //Add the move to the logic game
        l.allLegalMoves = new ArrayList<>();
        l.allLegalMoves.add(move);

        //Simulate the move
        l.em.movePiece(move.getX(), move.getY(), l, false, true, true);

        //Reset our states
        l.currentSpot = null;
        l.allLegalMoves = null;
    }

}
