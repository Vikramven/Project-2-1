package Logic.AI;

import Logic.MoveLogic.Move;

import java.util.ArrayList;
import java.util.LinkedList;

public class Node {

    private Node parent;

    private LinkedList<Node> children = new LinkedList<>();

    private Move move;

    private int cost;

    private int depth;

    private boolean root = false;

    public Node(Move move, Node parent){
        this.move = move;
        this.cost = move.getCost() + parent.getCost();
        this.parent = parent;
        this.depth = parent.getDepth() + 1;
    }

    public Node(){
        this.root = true;
        this.cost = 0;
        this.parent = null;
        this.depth = 0;
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

    public void addChildren(ArrayList<Move> move) {
        for (int i = 0; i < move.size(); i++) {
            children.add(new Node(move.get(i), this));
        }
    }

    public Move getMove() {
        return move;
    }

    public int getCost() {
        return cost;
    }

    public boolean isRoot() {
        return root;
    }

    public int getDepth() {
        return depth;
    }

    public Node getParent() {
        return parent;
    }
}
