package dice_chess.Logic.Hybrid;

import dice_chess.Logic.AI.HelpersAI.Node;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;

import java.util.LinkedList;

public class QLearner{
    /*
    A MAX Q Learner learns by looking up teh probabilities that the has hardcored in DB
    and looks them up
    if (s,a ) is new, ten looking up will return null. W
    When so we need to store the new tuple, and the uniform prob of that action.
    When we save tuples, wesave the unseen reward, the probability dist it had when saw the (a,s)
     */

    /*
    QLearner has the following properties
    Loooking/storing orob
    Updating them
     */
    static HashMapDB hm = new HashMapDB();
    //gamma param; learning discounted rate
    public static double gamma;

    public static LinkedList<Tuple> sequence = new LinkedList<Tuple>();

    public QLearner( double gamma ){

        this.gamma = gamma;
    }

    public static void storeTuple(Tuple tuple){
        sequence.add(tuple);
    }


// when backpropagating the error,
    //get the probs of all moves done
    public double[] lookUpProbs(LinkedList<Tuple> futureNodes){
        double [] probs = new double[futureNodes.size()];
        for (int i = 0; i <futureNodes.size(); i++ ){
            probs[i] = hm.getprobability(futureNodes.get(i).sToString(), futureNodes.get(i).action);
            //for every futureNodes.get(i) look up in DB
            //probs[i]
        }
        return probs;
    }


    // look up prob of a unique (s,a)
    public double lookUpProb(Tuple t, int f){
        double prob ;

        if ( !hm.checkForSAPair(t.sToString(), t.action)){
            //apply uniform dist with #legal moves
            prob = 1/f;
            hm.updateMap(t.sToString(), t.action, prob);
        } else {
            prob = hm.getprobability(t.sToString(), t.action);
        }

        return prob;
    }

    public void updateProbs (){
        //get sequence of tuples
        //one by one check them in DB and get reward and prob
        double [] loss = new double[sequence.size()];
        double [] probs = lookUpProbs(sequence);
        for (int i = sequence.size() -1; i > 0; i--){
            //its a weighted sum of discount rewards
            //start at the end of sequence
            //TODO TEsting if good results to the power of only with int??
            loss[i] = - java.lang.Math.log(probs[i]) * ((int)gamma ^i ) * sequence.get(i).reward;
            applyUpdate(loss[i], sequence.get(i));
        }

    }

    public static void applyUpdate(double loss, Tuple t){
        //retrive the old prob + loss and store new prob in DB
            double prob = hm.getprobability(t.sToString(), t.action);
            double newP = prob + loss;
            // this is the new prob. Change it to DB.

        //is it updtaeMap and updateTExt?
            hm.updateMap(t.sToString(), t.action, newP);

    }



}
