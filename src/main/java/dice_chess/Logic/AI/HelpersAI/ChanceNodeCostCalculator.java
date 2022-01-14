package dice_chess.Logic.AI.HelpersAI;

import java.util.LinkedList;

public class ChanceNodeCostCalculator {

    public void calculateCostForChance(Node currentChanceNode, Node evalNode){
        //Calculating the cost of the chance node and add to their children
        double totalCost = 0;
        LinkedList<Node> childrenOfChance = currentChanceNode.getChildren();
        for (int j = 0; j < childrenOfChance.size(); j++) {
            totalCost += childrenOfChance.get(j).getCost();
        }
        currentChanceNode.setCost(totalCost / currentChanceNode.getChildren().size() / 6.0);

        evalNode.setCost(evalNode.getCost() + currentChanceNode.getCost());
    }
}
