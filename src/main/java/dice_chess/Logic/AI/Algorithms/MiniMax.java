package dice_chess.Logic.AI.Algorithms;

import dice_chess.Board.*;

import dice_chess.Logic.AI.HelpersAI.Node;
import dice_chess.Logic.AI.HelpersAI.SimulateMove;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Logic.*;
import dice_chess.Pieces.Piece;
import static dice_chess.Constant.Constant.EVALUATION_FUNCTION_MINIMAX;

import java.util.ArrayList;
import java.util.LinkedList;

public class MiniMax {

    private final SimulateMove sm = new SimulateMove();

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
                true, true, Double.MIN_VALUE, Double.MAX_VALUE);


        // Find the best move
        if(bestMove.getParent() == null)
            return null;


        while(!bestMove.getParent().isRoot()) {
            bestMove = bestMove.getParent();
        }


        return bestMove.getMove();
    }


    /**
     * Create a tree recursively
     * @param node Node
     * @param l State of the game (Simulation)
     * @param player player side
     * @param board State of the board (Simulation)
     * @param dicePiece Piece on the dice (Simulations)
     * @param depth Maximum Depth of the tree
     * @param firstDepth boolean for the first depth
     */
    public Node createTree(Node node, LogicGame l, boolean player, Board board, int dicePiece,
                           int depth, boolean firstDepth, boolean max, double alpha, double beta) {

        if (node.getDepth() == depth || node.getCost() > 190.0)
            return node;

        // Create children for the node
        createChildren(l, player, node, firstDepth);

        LinkedList<Node> children = node.getChildren();

        if(max) {

            Node maxValue = new Node();
            maxValue.setCost(Double.MIN_VALUE);

            // Simulation moves of children
            for (int i = 0; i < children.size(); i++) {
                Node childNode = children.get(i);

                sm.executeMove(childNode, l, board, dicePiece);

                // l.board.print();
                Node evalNode = createTree(childNode, l, !player, l.board, l.dicePiece,
                        depth, false, false, alpha, beta);

                // If evaluating value is larger than our maxValue up until this point
                if(evalNode.getCost() >= maxValue.getCost() ) {
                    maxValue = evalNode;
                }

                // Pruning conditions
                if(evalNode.getCost() >= alpha) {
                    alpha = evalNode.getCost();
                }
                if(beta <= alpha) { break;}
            }
            return maxValue;

        } else {

            Node minValue = new Node();
            minValue.setCost(Double.MAX_VALUE);

            // Simulation moves of children
            for (int i = 0; i < children.size(); i++) {
                Node childNode = children.get(i);

                sm.executeMove(childNode, l, board, dicePiece);

                // l.board.print();
                Node evalNode = createTree(childNode, l, !player, l.board, l.dicePiece,
                        depth, false, true, alpha, beta);

                // If the evaluating node is less than our minValue up until this point
                if(evalNode.getCost() <= minValue.getCost() ) {
                    minValue = evalNode;
                }

                // Pruning conditions
                if(evalNode.getCost() <= beta) {
                    beta = evalNode.getCost();
                }
                if(beta <= alpha) { break;}
            }
            return minValue;
        }
    }


    /**
     * Create children for children
     * @param l State of the game (Simulation)
     * @param player player side
     * @param node Node
     * @param firstChildren boolean for the first depth
     */
    private void createChildren(LogicGame l, boolean player, Node node, boolean firstChildren){

        int[] pieceNum;

        // First depth = create children for one piece
        // Next depths = create children for all pieces
        if(firstChildren)
            pieceNum = new int[]{l.dicePiece};
        else
            pieceNum = new int[]{0, 1, 2, 3, 4, 5};

        // Create children with possible legal move
        for (int i = 0; i < pieceNum.length; i++) {
            LinkedList<Coordinate> allPieces = l.board.pieceMap.getAllPieces(pieceNum[i], player);

            for (int j = 0; j < allPieces.size(); j++) {
                Coordinate coordinate = allPieces.get(j);

                Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

                Piece piece = spot.getPiece();

                ArrayList<Move> allMovesPiece = piece.checkPlayerMove(l, spot, player, EVALUATION_FUNCTION_MINIMAX);

                if(allMovesPiece != null)
                    node.addChildren(allMovesPiece);

            }
        }
    }
}
