package dice_chess.Logic.Hybrid;

import dice_chess.Logic.AI.HelpersAI.Node;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import static dice_chess.Constant.Constant.*;

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
    public HashMapDB hm = new HashMapDB();
    //gamma param; learning discounted rate
    public double gamma;

    public LinkedList<Tuple> sequence = new LinkedList<Tuple>();

    public QLearner( double gamma ){

        this.gamma = gamma;
    }

    public void storeTuple(Tuple tuple){
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
        double prob= 0.2 ;

        if ( !hm.checkForSAPair(t.sToString(), t.action)){
            if ( f> 0){
                prob = 1/f;
            }
            //apply uniform dist with #legal moves

            hm.updateMap(t.sToString(), t.action, prob);
            if(QDEBUG){
                System.out.println("uniform prob " + t.sToString() + "-" + t.action +" ;" + prob);
            }
        } else {
            prob = hm.getprobability(t.sToString(), t.action);
            if(QDEBUG){
                System.out.println("Looked up prob  " + t.sToString() + "-" + t.action +" ;" + prob);
            }
        }

        return prob;
    }

    public void updateProbs (){
        //get sequence of tuples
        //one by one check them in DB and get reward and prob
        double [] loss = new double[sequence.size()];
        double [] accuR = new double[sequence.size()];
        double [] probs = lookUpProbs(sequence);
        //its a weighted sum of discount rewards
        //start at the end of sequence
        for (int i = sequence.size() -1; i >= 0; i--){
            //get the current discounted reward
            accuR[i] = sequence.get(i).reward * Math.pow(GAMMA, i);
            for (int j = sequence.size() -1; j< i; j--) {
                //sum all previous rewards
                accuR[i] =+ accuR[j];
                if (QDEBUG) {
                    System.out.println("loss calculated for " + i + " : " + loss[i]);
                    System.out.println("new prob ");
                }
                loss[i] = -java.lang.Math.log(probs[i]) * accuR[i];
            }
            applyUpdate(loss[i], sequence.get(i));
        }
    }

    public void applyUpdate(double loss, Tuple t){
        //retrive the old prob + loss and store new prob in DB
            double prob = hm.getprobability(t.sToString(), t.action);
            double newP = prob + loss;
            // this is the new prob. Change it to DB.
            if(QDEBUG){
                System.out.println("updated prb " + newP + "-------" + t.sToString() +"-" + t.action);
            }
        //is it updtaeMap and updateTExt?
            hm.updateMap(t.sToString(), t.action, newP);

    }



}

