package Logic.AI;

import Logic.MoveLogic.Move;

import java.util.ArrayList;
import java.util.LinkedList;

public class Node {

    //Parent of the node
    private Node parent;

    //Children of the node
    private LinkedList<Node> children = new LinkedList<>();

    //Move
    private Move move;

    //Cost of the move
    private int cost;

    //Depth of the node
    private int depth;

    private boolean root = false;

    /**
     * Constructor for children
     * @param move Move
     * @param parent Parent
     */
    public Node(Move move, Node parent){
        this.move = move;
        this.cost = move.getCost();
        this.parent = parent;
        this.depth = parent.getDepth() + 1;
    }

    /**
     * Constructor for the root
     */
    public Node(){
        this.root = true;
        this.cost = 0;
        this.parent = null;
        this.depth = 0;
    }

    /**
     * Gets all children
     * @return all children of the node
     */
    public LinkedList<Node> getChildren() {
        return children;
    }

    /**
     * Adds children to the node
     * @param move Arraylist of moves
     */
    public void addChildren(ArrayList<Move> move) {
        for (int i = 0; i < move.size(); i++) {
            children.add(new Node(move.get(i), this));
        }
    }


    /**
     * Gets move
     * @return Move
     */
    public Move getMove() {
        return move;
    }

    /**
     * Gets cost
     * @return Cost
     */
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Is root?
     * @return true = root || false = not root
     */
    public boolean isRoot() {
        return root;
    }

    /**
     * Depth of the node
     * @return Depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Gets parent
     * @return Parent of the node
     */
    public Node getParent() {
        return parent;
    }
}
