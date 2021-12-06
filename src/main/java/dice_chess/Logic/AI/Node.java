package dice_chess.Logic.AI;

import dice_chess.Logic.MoveLogic.Move;

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
    private double cost;

    //Depth of the node
    private int depth;

    //Determine if it is the Chance Node
    private boolean chanceNode = false;

    //Determine the name int of the Chance Node
    private int chancePiece;

    //Determine if the node is the root or not
    private boolean root = false;

    /**
     * Constructor for children
     * @param move Move
     * @param parent Parent
     */
    public Node(Move move, Node parent){
        this.move = move;
        if(!parent.chanceNode) {
            this.cost = move.getCost() + parent.cost;
        }else{
            this.cost = move.getCost() + parent.parent.cost;
        }
        this.parent = parent;
        this.depth = parent.getDepth() + 1;

    }

    /**
     * Constructor for chance node
     * @param parent Parent
     */
    public Node(Node parent, int chancePiece){
        this.chancePiece = chancePiece;
        this.parent = parent;
        this.depth = parent.getDepth();
        this.chanceNode = true;
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
     * Adding the chance nodes to the node
     */
    public void addChanceNodes(){
        for (int i = 0; i < 6; i++) {
            children.add(new Node(this, i));
        }
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
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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

    /**
     * Gets the name int of the Chance Node
     * @return the name int of the piece
     */
    public int getChancePiece() {
        return chancePiece;
    }
}
