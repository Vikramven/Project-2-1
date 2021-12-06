package dice_chess.Logic.AI;

import dice_chess.Board.*;

import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Logic.*;
import dice_chess.Pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;

public class MiniMax {


    public MiniMax(){

    }

    /**
     *
     * @param l State of the game
     * @return The best Move
     */
    public Move calculateBestMoves(LogicGame l){

        // Clone objects to avoid side effects
        Board initialBoard = l.board.clone();
        PieceHeap initialPieceHeap = l.board.pieceHeap.clone();
        int initialDicePiece = l.dicePiece;


        // Create a root of the tree
        Node tree = new Node();

        // Creating a tree
        Node bestMove = createTree(tree, l, l.blackMove, l.board, l.board.pieceHeap, l.dicePiece, 4,
                true, true, Double.MIN_VALUE, Double.MAX_VALUE);


        // Check the tree by prints
//        LinkedList<Node> children = tree.getChildren();
//
//        for (int i = 0; i < children.size(); i++) {
//            System.out.println("Children 1 " + children.get(i).getMove().getPiece().getName() + " COST = " + children.get(i).getCost());
//            LinkedList<Node> childrenOfChildren = children.get(i).getChildren();
//            for (int j = 0; j < childrenOfChildren.size(); j++) {
//                System.out.println("Children 2 " + childrenOfChildren.get(j).getMove().getPiece().getName() + " COST = " + childrenOfChildren.get(j).getCost());
//                LinkedList<Node> childrenOfChildrenOF = childrenOfChildren.get(j).getChildren();
//                for (int k = 0; k < childrenOfChildrenOF.size(); k++) {
//                    System.out.println("Children 3 " + childrenOfChildrenOF.get(k).getMove().getPiece().getName() + " COST = " + childrenOfChildrenOF.get(k).getCost());
//                }
//            }
//        }


        // Return to the original state
        l.board = initialBoard;
        l.board.pieceHeap = initialPieceHeap;
        l.dicePiece = initialDicePiece;


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
     * @param pieceHeap Where pieces are located (Simulation)
     * @param dicePiece Piece on the dice (Simulations)
     * @param depth Maximum Depth of the tree
     * @param firstDepth boolean for the first depth
     */
    public Node createTree(Node node, LogicGame l, boolean player, Board board, PieceHeap pieceHeap, int dicePiece,
                           int depth, boolean firstDepth, boolean max, double alpha, double beta) {

        if (node.getDepth() == depth)
            return node;

        // Create children for the node
        createChildren(l, player, node, firstDepth);

        LinkedList<Node> children = node.getChildren();

        if(max) {

            Node maxValue = new Node();
            maxValue.setCost(Double.MIN_VALUE);

            // Simulation moves of children
            for (int i = 0; i < children.size(); i++) {

                //Cloning the state of the board
                Board cloneBoard = board.clone();
                PieceHeap clonePieceHeap = pieceHeap.clone();
                Node childNode = children.get(i);

                //Simulating the move
                Move move = childNode.getMove();

                //Reset the state of the board
                l.board = cloneBoard;
                l.board.pieceHeap = clonePieceHeap;
                l.dicePiece = dicePiece;

                //Get the piece from the simulated board
                l.currentSpot = l.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

                //Add the move to the logic game
                l.allLegalMoves = new ArrayList<>();
                l.allLegalMoves.add(move);

                //Simulate the move
                l.em.movePiece(move.getX(), move.getY(), l, false, true, true);

                //Reset our states
                l.currentSpot = null;
                l.allLegalMoves = null;

                // l.board.print();
                Node evalNode = createTree(children.get(i), l, !player, l.board, l.board.pieceHeap, l.dicePiece,
                        depth, false, false, alpha, beta);

                //If on the first depth it find the win move, if execute it, without continue creating the tree
                if(firstDepth){
                    if(childNode.getCost() > 950.0){
                        return childNode;
                    }
                }

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

                //Cloning the state of the board
                Board cloneBoard = board.clone();
                PieceHeap clonePieceHeap = pieceHeap.clone();
                Node childNode = children.get(i);

                //Simulating the move
                Move move = childNode.getMove();

                //Reset the state of the board
                l.board = cloneBoard;
                l.board.pieceHeap = clonePieceHeap;
                l.dicePiece = dicePiece;

                //Get the piece from the simulated board
                l.currentSpot = l.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

                //Add the move to the logic game
                l.allLegalMoves = new ArrayList<>();
                l.allLegalMoves.add(move);

                //Simulate the move
                l.em.movePiece(move.getX(), move.getY(), l, false, true, true);

                //Reset our states
                l.currentSpot = null;
                l.allLegalMoves = null;

                // l.board.print();
                Node evalNode = createTree(children.get(i), l, !player, l.board, l.board.pieceHeap, l.dicePiece,
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
    public void createChildren(LogicGame l, boolean player, Node node, boolean firstChildren){

        int[] pieceNum;

        // First depth = create children for one piece
        // Next depths = create children for all pieces
        if(firstChildren)
            pieceNum = new int[]{l.dicePiece};
        else
            pieceNum = new int[]{0, 1, 2, 3, 4, 5};

//        System.out.println(Arrays.toString(pieceNum));

        // Create children with possible legal move
        for (int i = 0; i < pieceNum.length; i++) {
            LinkedList<Coordinate> allPieces = l.board.pieceHeap.getAllPieces(pieceNum[i], player);

            for (int j = 0; j < allPieces.size(); j++) {
                Coordinate coordinate = allPieces.get(j);

//                    System.out.println(coordinate.x + "  " + coordinate.y);

                Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

                Piece piece = spot.getPiece();

                ArrayList<Move> allMovesPiece = piece.checkPlayerMove(l.board, spot, player, l.board.pieceHeap, true);

                if(allMovesPiece != null)
                    node.addChildren(allMovesPiece);

            }
        }
    }
}
