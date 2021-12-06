package Logic.AI;

import Board.*;
import Logic.LogicGame;
import Logic.MoveLogic.Move;
import Pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;

public class Expectimax {


    public Expectimax(){

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
        Node bestMove = createTree(tree, l, l.blackMove, l.board, l.board.pieceHeap, l.dicePiece, 3,
                true);


////         Check the tree by prints
//        LinkedList<Node> children = tree.getChildren();
//
//        for (int i = 0; i < children.size(); i++) {
//            System.out.println("Children 1 " + children.get(i).getMove().getPiece().getName() + " COST = " + children.get(i).getCost());
//            LinkedList<Node> childrenOfChildren = children.get(i).getChildren();
//            for (int j = 0; j < childrenOfChildren.size(); j++) {
//                System.out.println("Children 2 " + childrenOfChildren.get(j).getChancePiece() + " COST = " + childrenOfChildren.get(j).getCost());
//                LinkedList<Node> childrenOfChildrenOF = childrenOfChildren.get(j).getChildren();
//                for (int k = 0; k < childrenOfChildrenOF.size(); k++) {
//                    System.out.println("Children 3 " + childrenOfChildrenOF.get(k).getMove().getPiece().getName() + " COST = " + childrenOfChildrenOF.get(k).getCost());
//                    LinkedList<Node> children4 = childrenOfChildrenOF.get(k).getChildren();
//                    for (int x = 0; x < children4.size(); x++) {
//                        System.out.println("Children 4 " + children4.get(x).getChancePiece() + " COST = " + children4.get(x).getCost());
//                        LinkedList<Node> children5 = children4.get(x).getChildren();
//                        for (int t = 0; t < children5.size(); t++) {
//                            System.out.println("Children 5 " + children5.get(t).getMove().getPiece().getName() + " COST = " + children5.get(t).getCost());
//
//                        }
//                    }
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
     */
    public Node createTree(Node node, LogicGame l, boolean player, Board board, PieceHeap pieceHeap, int dicePiece,
                           int depth, boolean max) {


        l.allLegalMoves = null;


        if (node.getDepth() == depth)
            return node;

        if(max) {

            // Create children for the node
            createChildren(l, player, node, dicePiece);

            LinkedList<Node> children = node.getChildren();

            Node maxValueOfNode = new Node();
            maxValueOfNode.setCost(Double.MIN_VALUE);

            // Simulation moves of children
            for (int i = 0; i < children.size(); i++) {

                Board cloneBoard = board.clone();

                PieceHeap clonePieceHeap = pieceHeap.clone();

                Node childNode = children.get(i);

                //Simulating the move
                Move move = childNode.getMove();

                l.board = cloneBoard;
                l.board.pieceHeap = clonePieceHeap;
                l.dicePiece = dicePiece;

                l.currentSpot = l.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

                l.allLegalMoves = new ArrayList<>();
                l.allLegalMoves.add(move);

                l.em.movePiece(move.getX(), move.getY(), l, false, true);

                l.currentSpot = null;

                Node evalNode = createTree(childNode, l, !player, l.board, l.board.pieceHeap, 0,
                            depth, false);

                if(evalNode.getCost() > maxValueOfNode.getCost() ) {
                            maxValueOfNode = evalNode;
                }
            }

            return maxValueOfNode;
        } else {
            node.addChanceNodes();

            LinkedList<Node> chancesNodes = node.getChildren();

            Node maxValueOfNode = new Node();
            maxValueOfNode.setCost(Double.MIN_VALUE);;

            for (int i = 0; i < chancesNodes.size(); i++) {
                Node currentChanceNode = chancesNodes.get(i);

                Board cloneBoard = board.clone();

                PieceHeap clonePieceHeap = pieceHeap.clone();


                l.board = cloneBoard;
                l.board.pieceHeap = clonePieceHeap;
                l.dicePiece = dicePiece;


                Node evalNode = createTree(currentChanceNode, l, !player, l.board, l.board.pieceHeap, currentChanceNode.getChancePiece(),
                        depth, true);

                double totalCost = 0;
                LinkedList<Node> childrenOfChance = currentChanceNode.getChildren();
                for (int j = 0; j < childrenOfChance.size(); j++) {
                    totalCost += childrenOfChance.get(j).getCost();
                }
                currentChanceNode.setCost(totalCost / 6.0);

                evalNode.setCost(evalNode.getCost() + currentChanceNode.getCost());

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
     */
    public void createChildren(LogicGame l, boolean player, Node node, int pieceNum){

        LinkedList<Coordinate> allPieces = l.board.pieceHeap.getAllPieces(pieceNum, player);

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
