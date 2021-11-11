package Logic.AI;

import Board.*;
import Logic.LogicGame;
import Logic.MoveLogic.Move;
import Pieces.Piece;
import Players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MiniMax {

    private Node maxCostNode;

    public MiniMax(){

    }

    public ArrayList<Move> calculateBestMoves(LogicGame l){
        Board initialBoard = l.board.clone();
        PieceHeap initialPieceHeap = l.board.pieceHeap.clone();
        int initialDicePiece = l.dicePiece;

        Node tree = new Node();

        maxCostNode = tree;

        createTree(tree, l, l.blackMove, l.board, l.board.pieceHeap, l.dicePiece, null, 3);


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

        l.board = initialBoard;
        l.board.pieceHeap = initialPieceHeap;
        l.dicePiece = initialDicePiece;

        ArrayList<Move> moves = new ArrayList<>();
        while(!maxCostNode.isRoot()){

            moves.add(maxCostNode.getMove());

            maxCostNode = maxCostNode.getParent();
        }

        return moves;
    }

    public void createTree(Node node, LogicGame l, boolean player, Board board, PieceHeap pieceHeap, int dicePiece, Piece piece, int depth){
        l.allLegalMoves = null;


        if(node.getDepth() > depth)
            return;

        createChildren(l, player, node);

        LinkedList<Node> children = node.getChildren();

        for (int i = 0; i < children.size(); i++) {
            Board cloneBoard = board.clone();
            PieceHeap clonePieceHeap = pieceHeap.clone();
            int cloneDicePiece = dicePiece;



            Node childNode = children.get(i);

            if(childNode.getCost() >= maxCostNode.getCost())
                maxCostNode = childNode;

            Move move = childNode.getMove();

            l.board = cloneBoard;
            l.board.pieceHeap = clonePieceHeap;
            l.dicePiece = cloneDicePiece;

            l.currentSpot = l.board.getSpot(move.getPieceSpotX(), move.getPieceSpotY());

            l.allLegalMoves = new ArrayList<>();
            l.allLegalMoves.add(move);


            l.em.movePiece(move.getX(), move.getY(), l, false);


            l.currentSpot = null;

//            l.board.print();

            createTree(children.get(i), l, player, l.board, l.board.pieceHeap, l.dicePiece, move.getPiece(), depth);
        }
    }

    public void createChildren(LogicGame l, boolean player, Node node){

//        System.out.println(Arrays.toString(l.dicePiece));
        int length = l.dicePiece;
        int[] temp = new int[length];
        int k = 0;

//        for (int i = 0; i < length-1; i++){
//            if (l.dicePiece != l.dicePiece){
//                temp[k++] = l.dicePiece[i];
//            }
//        }
        temp[k++] = l.dicePiece;
        // Changing original array
        for (int i = 0; i < k; i++){
            l.dicePiece = temp[i];
        }
//
//        System.out.println(Arrays.toString(l.dicePiece));
//        System.out.println(k);

        for (int i = 0; i < k; i++) {
            int pieceNum = l.dicePiece;
            if(pieceNum < 6){
            LinkedList<Coordinate> allPieces = l.board.pieceHeap.getAllPieces(pieceNum, player);

                for (int j = 0; j < allPieces.size(); j++) {
                    Coordinate coordinate = allPieces.get(j);

//                    System.out.println(coordinate.x + "  " + coordinate.y);

                    Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

                    Piece piece = spot.getPiece();

                    ArrayList<Move> allMovesPiece = piece.checkPlayerMove(l.board, spot, player, l.board.pieceHeap);

                    node.addChildren(allMovesPiece);
                }
            }
        }
    }
}
