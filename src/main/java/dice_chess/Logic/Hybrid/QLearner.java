package dice_chess.Logic.Hybrid;

import dice_chess.Logic.AI.HelpersAI.Node;

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

    //gamma param; learning discounted rate
    public static int gamma;

    public static LinkedList<Tuple> sequence;

    public QLearner( int gamma ){

        this.gamma = gamma;
    }

    public static void storeTuple(Tuple tuple){
        sequence.add(tuple);
    }

// when backpropagating the error,
    //get the probs of all moves done
    public static double[] lookUpProbs(LinkedList<Tuple> futureNodes){
        double [] probs = new double[futureNodes.size()];
        for (int i = 0; i <futureNodes.size(); i++ ){
            //Create tuples for each future Node

            //for every futureNodes.get(i) look up in DB
            //probs[i]
        }
        return probs;
    }
    // look up prob of a unique (s,a)
    public static double lookUpProb(Tuple t, int f){
        //Look up in DB if prob is in DB
        double prob = 0.0;
       /* if (prob == null){
            prob = 1/f;
            //save the tuple in DB
        }

        */
        return prob;
    }

    public static void updateProbs (){
        //get sequence of tuples
        //one by one check them in DB and get reward and prob
        double [] loss = new double[sequence.size()];
        double [] probs = lookUpProbs(sequence);
        for (int i = sequence.size(); i > 0; i--){
            //its a weighted sum of discount rewards
            //start at the end of sequence
            loss[i] = - java.lang.Math.log(probs[i]) * (gamma^i ) * sequence.get(i).reward;
            applyUpdate(loss[i]);
        }

    }

    public static void applyUpdate(double loss){
        //retrive the old prob + loss and store new prob in DB
            // this is the new prob. Change it to DB.

    }



}
