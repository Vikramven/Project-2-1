package dice_chess.Logic.AI;

import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

import java.util.ArrayList;

public class ExecuteMovesAI {

    public void executeMovesAI(LogicGame l, Move move){

        int currentX = move.getPieceSpotX();
        int currentY = move.getPieceSpotY();

        l.iniX.set(currentX);
        l.iniY.set(currentY);

        int moveX = move.getX();
        int moveY = move.getY();

        l.currentSpot = l.board.getSpot(currentX, currentY);

        l.allLegalMoves = new ArrayList<>();
        l.allLegalMoves.add(move);

        l.em.executeMove(l.iniX, l.iniY, moveX, moveY, l);

        l.allLegalMoves = new ArrayList<>();
        l.currentSpot = null;
        l.allLegalMoves = null;
    }
}
