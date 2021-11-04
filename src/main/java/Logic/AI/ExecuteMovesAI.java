package Logic.AI;

import Logic.LogicGame;
import Logic.MoveLogic.Move;

import java.util.ArrayList;

public class ExecuteMovesAI {

    public void executeMovesAI(LogicGame l, ArrayList<Move> moves){

        int length = moves.size();

        for (int i = length-1;i >= 0; i--) {

            Move move = moves.get(i);

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
        }

        l.allLegalMoves = null;
    }
}
