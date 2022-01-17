package dice_chess.Logic.Hybrid;

import com.sun.jna.platform.win32.OaIdl;
import dice_chess.Constant.Constant;
import java.util.LinkedList;

import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;
import org.nd4j.shade.protobuf.StringValue;

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

    public String sToString(){
        //String string= "";
        StringBuilder s = new StringBuilder(this.state.length);
        boolean firstIter = true;
        for( int i = 0; i< this.state.length; i ++ ){
            if (firstIter){
                s.append("[");
                firstIter= false;
            } else {
                s.append(",");
            }
            s.append(this.state[i]);
        }
        s.append("]");
        return s.toString();
    }




    public static String createAction(Move move, char colour){
        //action representation
        Piece piece = move.getPiece();
        String name = piece.getName().substring(0, 2);

       /*
       if (Constant.QDEBUG){
            System.out.println("name of piece :" + name );
        }

        */
        int xCoor = move.getPieceSpotX();
        int yCoor = move.getPieceSpotY();
        int xFuture = move.getX();
        int yFuture = move.getY();
        String action = colour + name;

        action = action + xCoor + yCoor + xFuture + yFuture;

        /*
        if (Constant.QDEBUG){
            System.out.println("name of action :" + action);
        }

         */
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
