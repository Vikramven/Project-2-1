package Logic.AI;

import Board.*;
import Logic.LogicGame;
import Logic.MoveLogic.Move;
import Pieces.Piece;
import Players.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class MiniMax {

    public MiniMax(){

    }

    public ArrayList<Move> calculateBestMoves(LogicGame l){
        Board initialBoard = l.board.clone();
        PieceHeap initialPieceHeap = l.board.pieceHeap.clone();
        int[] initialDicePiece = new int[l.dicePiece.length];

        for (int i = 0; i < initialDicePiece.length; i++) {
            initialDicePiece[i] = l.dicePiece[i];
        }

        Node tree = new Node();

        createTree(tree, l, l.player);


        LinkedList<Node> children = tree.getChildren();

        for (int i = 0; i < children.size(); i++) {
            System.out.println("Children 1 " + children.get(i).getMove().getPiece().getName());
            LinkedList<Node> childrenOfChildren = children.get(i).getChildren();
            for (int j = 0; j < childrenOfChildren.size(); j++) {
                System.out.println("Children 2 " + childrenOfChildren.get(i).getMove().getPiece().getName());
            }
        }

        return null;
    }

    public void createTree(Node node, LogicGame l, Player player){
        if(node.getDepth() > 2)
            return;

        createChildren(l, player, node);

        LinkedList<Node> children = node.getChildren();

        for (int i = 0; i < children.size(); i++) {
            createTree(children.get(i), l, player);
        }
    }

    public void createChildren(LogicGame l, Player player, Node node){

        for (int i = 0; i < l.dicePiece.length; i++) {
            int pieceNum = l.dicePiece[i];

            LinkedList<Coordinate> allPieces = l.board.pieceHeap.getAllPieces(pieceNum, player.isBlackSide());

            for (int j = 0; j < allPieces.size(); j++) {
                Coordinate coordinate = allPieces.get(j);

                Spot spot = l.board.getSpot(coordinate.x, coordinate.y);

                Piece piece = spot.getPiece();

                ArrayList<Move> allMovesPiece = piece.checkPlayerMove(l.board, spot, player, l.board.pieceHeap);

                node.addChildren(allMovesPiece);
            }
        }
    }
}
