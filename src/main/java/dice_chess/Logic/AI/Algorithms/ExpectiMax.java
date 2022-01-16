package dice_chess.Logic.AI.Algorithms;

import dice_chess.Board.*;
import dice_chess.Logic.AI.HelpersAI.ChanceNodeCostCalculator;
import dice_chess.Logic.AI.HelpersAI.Node;
import dice_chess.Logic.AI.HelpersAI.SimulateMove;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;
import dice_chess.Logic.Hybrid.Tuple;
import dice_chess.Logic.Hybrid.QLearner;
import static dice_chess.Constant.Constant.*;



import java.util.ArrayList;
import java.util.LinkedList;

public class ExpectiMax{

    private final SimulateMove sm = new SimulateMove();
    private final ChanceNodeCostCalculator cn = new ChanceNodeCostCalculator();

    /**
     *
     * @param l State of the game
     * @return The best Move
     */
    public Move calculateBestMoves(LogicGame l, int depth){

        // Create a root of the tree
        Node tree = new Node();

        // Creating a tree
        Node bestMove = createTree(tree, l, l.blackMove, l.board, l.dicePiece, depth,
                true);


        // Find the best move
        if(bestMove.getParent() == null)
            return null;



        while(!bestMove.getParent().isRoot()) {
            //sequence of moves create tuples fo rthem
            if (Q_LEARNER) {
                if(! bestMove.isChanceNode()){
                    if(bestMove.getMove() == null ){
                        System.out.println("best move is null ");
                    }else {
                        Tuple t = new Tuple(bestMove.getMove(), l, bestMove.getMove().getPiece().getColor());//I NEED THE PLAYER!!!!
                        new QLearner(GAMMA).storeTuple(t);
                    }

                }
            }
            bestMove = bestMove.getParent();
        }


        return bestMove.getMove();
    }


    /**
     * Create a tree recursively, and apply ExpectiMax algorithm for this tree
     * @param node Node
     * @param l State of the game (Simulation)
     * @param player player side
     * @param board State of the board (Simulation)
     * @param dicePiece Piece on the dice (Simulations)
     * @param depth Maximum Depth of the tree
     * @param max determine which action we do, find the max node or find the max chance node
     */
    public Node createTree(Node node, LogicGame l, boolean player, Board board, int dicePiece,
                           int depth, boolean max) {

        //Base case of the recursion
        if (node.getDepth() == depth)
            return node;

        if(max) {

            // Create children for the node
            createChildren(l.clone(), player, node, dicePiece);

            LinkedList<Node> children = node.getChildren();

            Node maxValueOfNode = new Node();
            maxValueOfNode.setCost(Double.MIN_VALUE);

            // Simulation moves of children
            for (int i = 0; i < children.size(); i++) {

                Node childNode = children.get(i);

                //If on the first depth it find the win move, if execute it, without continue creating the tree
                if(childNode.getDepth() == 1){
                    if(childNode.getCost() > 100){
                        return childNode;
                    }
                }

                sm.executeMove(childNode, l, board, dicePiece);

                Node evalNode = createTree(childNode, l, !player, l.board, 0,
                            depth, false);


                if(evalNode.getCost() > maxValueOfNode.getCost() ) {
                            maxValueOfNode = evalNode;
                }
            }

            return maxValueOfNode;
        } else {

            //Add chance nodes
            node.addChanceNodes();

            //Get children of the node
            LinkedList<Node> chancesNodes = node.getChildren();

            Node maxValueOfNode = new Node();
            maxValueOfNode.setCost(Double.MIN_VALUE);;

            for (int i = 0; i < chancesNodes.size(); i++) {

                //Cloning the state of the board
                Node currentChanceNode = chancesNodes.get(i);

                //Reset the state of the board
                l.board = board.clone();;
                l.dicePiece = dicePiece;

                Node evalNode = createTree(currentChanceNode, l, player, l.board, currentChanceNode.getChancePiece(),
                        depth, true);


                //Calculating the cost of the chance node and add to their children
                cn.calculateCostForChance(currentChanceNode, evalNode);


                if(evalNode.getCost() > maxValueOfNode.getCost() ) {
                    maxValueOfNode = evalNode;
                }
            }

            return maxValueOfNode;
        }
    }


    /**
     * Create children for children
     * @param l State of the game (Simulation)
     * @param player player side
     * @param node Node
     * @param pieceNum the name int of the piece
     */
    private void createChildren(LogicGame l, boolean player, Node node, int pieceNum){

        LinkedList<Coordinate> allPieces = l.board.pieceMap.getAllPieces(pieceNum, player);

        for (int j = 0; j < allPieces.size(); j++) {
            Coordinate coordinate = allPieces.get(j);

//          System.out.println(coordinate.x + "  " + coordinate.y);

            Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

            Piece piece = spot.getPiece();

            ArrayList<Move> allMovesPiece = piece.checkPlayerMove(l, spot, player, EVALUATION_FUNCTION_EXPECTI_MAX);

            if(allMovesPiece != null)
                node.addChildren(allMovesPiece);

        }
    }
}
