package dice_chess.Logic.Hybrid;

import com.sun.jna.platform.win32.OaIdl;

import java.util.LinkedList;

import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;

public class Tuple {

    double [] state;
    String action;
    double reward;


    public Tuple(double[] state, String action, double reward){
        this.state = state;
        this.action = action;
        this.reward = reward;
    }

    public Tuple(Move move , LogicGame l, boolean playerB ){
        this.state =l.toArray();
        char colour;
        if ( playerB) {
             colour = 'B';
        }
        else{
            colour= 'W';
        }
        this.action = createAction(move, colour);
        this.reward = move.getCost();

    }




    public static String createAction(Move move, char colour){
        //action representation
        Piece piece = move.getPiece();
        char name = piece.getName().charAt(0);
        int xCoor = move.getPieceSpotX();
        int yCoor = move.getPieceSpotY();
        int xFuture = move.getX();
        int yFuture = move.getY();
        String action = String.valueOf(colour + name);

        action = action + xCoor + yCoor + xFuture + yFuture;
        return action;
    }

    public double[] getState() {
        return state;
    }

    public double getReward() {
        return reward;
    }

    public String getAction() {
        return action;
    }
}
