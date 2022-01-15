package dice_chess.Logic.AI.Algorithms;

import dice_chess.Board.Board;
import dice_chess.Board.Coordinate;
import dice_chess.Board.Spot;
import dice_chess.Logic.AI.HelpersAI.ChanceNodeCostCalculator;
import dice_chess.Logic.AI.HelpersAI.Node;
import dice_chess.Logic.AI.HelpersAI.SimulateMove;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Pieces.Piece;
import static dice_chess.Constant.Constant.EVALUATION_FUNCTION_EXPECTI_MINIMAX;

import java.util.ArrayList;
import java.util.LinkedList;

public class ExpectiMinimax{

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
                true, 1);


        // Find the best move
        if(bestMove.getParent() == null)
            return null;


        while(!bestMove.getParent().isRoot()) {
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
                           int depth, boolean max, int minimaxOrRandomNode) {

        //Base case of the recursion
        if (node.getDepth() == depth)
            return node;

        if(max && minimaxOrRandomNode == 1) {
            // Create children for the node
            createChildren(l.clone(), player, node, dicePiece);

            LinkedList<Node> children = node.getChildren();

            Node maxValueOfNode = new Node();
            maxValueOfNode.setCost(Double.MIN_VALUE);

            // Simulation moves of children
            for (int i = 0; i < children.size(); i++) {

                //Cloning the state of the board
                Node childNode = children.get(i);

                //If on the first depth it find the win move, if execute it, without continue creating the tree
                if(childNode.getDepth() == 1){
                    if(childNode.getCost() > 100){
                        return childNode;
                    }
                }

                sm.executeMove(childNode, l, board, dicePiece);

                Node evalNode = createTree(childNode, l, !player, l.board, 0,
                        depth, max, 3);


                if(evalNode.getCost() > maxValueOfNode.getCost() ) {
                    maxValueOfNode = evalNode;
                }
            }

            return maxValueOfNode;
        }else if(!max && minimaxOrRandomNode == 1){

            // Create children for the node
            createChildren(l.clone(), player, node, dicePiece);

            LinkedList<Node> children = node.getChildren();

            Node minValueOfNode = new Node();
            minValueOfNode.setCost(Double.MAX_VALUE);

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
                        depth, max, 3);


                if(evalNode.getCost() < minValueOfNode.getCost()) {
                    minValueOfNode = evalNode;
                }
            }

            return minValueOfNode;


        }else if(minimaxOrRandomNode == 3){

            //Add chance nodes
            node.addChanceNodes();

            //Get children of the node
            LinkedList<Node> chancesNodes = node.getChildren();

            Node randomValueOfNode = new Node();
            if(max)
                randomValueOfNode.setCost(Double.MIN_VALUE);
            else
                randomValueOfNode.setCost(Double.MAX_VALUE);

            for (int i = 0; i < chancesNodes.size(); i++) {

                Node currentChanceNode = chancesNodes.get(i);

                //Reset the state of the board
                l.board = board.clone();
                l.dicePiece = dicePiece;;

                Node evalNode = createTree(currentChanceNode, l, player, l.board, currentChanceNode.getChancePiece(),
                        depth, !max, 1);

                //Calculating the cost of the chance node and add to their children
                cn.calculateCostForChance(currentChanceNode, evalNode);

                if(max) {
                    if (evalNode.getCost() > randomValueOfNode.getCost()) {
                        randomValueOfNode = evalNode;
                    }
                } else {
                    if (evalNode.getCost() < randomValueOfNode.getCost()) {
                        randomValueOfNode = evalNode;
                    }
                }
            }
            return randomValueOfNode;
        }

        return null;
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

            Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

            Piece piece = spot.getPiece();

            ArrayList<Move> allMovesPiece = piece.checkPlayerMove(l, spot, player, EVALUATION_FUNCTION_EXPECTI_MINIMAX);

            if(allMovesPiece != null)
                node.addChildren(allMovesPiece);

        }
    }
}
