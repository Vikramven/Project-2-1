package Pieces;

import Board.Board;
import Board.Spot;
import Logic.MoveLogic.Move;
import Players.Player;
import Board.*;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Piece implements PieceMove {

    protected boolean black;

    protected String name, imageURL;

    protected int nameInt;

    public Piece(boolean black) {
        this.black = black;
    }

    public String isColor() {
        if(black)
            return "Black";
        return "White";
    }

    public boolean getColor(){
        return black;
    }

    public void setWhite() {
        black = false;
    }

    public void setBlack() {
        black = true;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() { return imageURL; }

    public int getNameInt() {
        return nameInt;
    }

    /**
     * Define if piece have obstacles on their moves
     * @param spot The spot
     * @param legalMoves Arraylist with all possible legal move
     * @return true = obstacle: false = no obstacle
     */
    protected boolean isObstacle(Spot spot, ArrayList<Move> legalMoves, int costPiece, int x, int y){

        if(spot != null) {
            if (!spot.getPiece().isColor().equals(isColor())) {
                legalMoves.add(new Move(spot.getX(), spot.getY(), spot.getPiece(), costPiece, x, y));
            }
            return true;
        }

        return false;
    }


    /**
     * Check if the piece goes out of the board
     * @param x coordinate
     * @return true = goes out: false = stay on the board
     */
    protected boolean isBoardBounds(int x){
        return x < 0 || x > 7;
    }

    /**
     * Check if player move with the correct piece
     */
    public ArrayList<Move> checkPlayerMove(Board board, Spot spot, Player player, PieceHeap enemyPieces){
        if(spot.getPiece().isColor().equals(player.isColorSide())){
            int[][] cost = calculateBoard(enemyPieces, player, board);
            return allLegalMoves(board, spot, cost);
        }
        return null;
    }

    private int[][] calculateBoard(PieceHeap enemyPieces, Player player, Board board){
        ArrayList<Move> movesEnemy = new ArrayList<>();
        int[][] emptyCost = new int[8][8];
        for (int i = 0; i < 6; i++) {
            LinkedList<Coordinate> pieces = enemyPieces.getAllPieces(i, !player.isBlackSide());
            for (int j = 0; j < pieces.size(); j++) {
                Coordinate coordinate = pieces.get(j);

                Spot spot = board.getSpot(coordinate.x, coordinate.y);

                Piece piece = spot.getPiece();

                movesEnemy.addAll(piece.allLegalMoves(board,spot, emptyCost));
            }
        }

        int[][] cost = new int[8][8];

        for (int i = 0; i < movesEnemy.size(); i++) {
            Move move = movesEnemy.get(i);

            int badX = move.getX();
            int badY = move.getY();

            int goodX = move.getPieceSpotX();
            int goodY = move.getPieceSpotY();

            int check=move.getPiece().nameInt;

            switch(check){
                case 0:
                    cost[goodX][goodY] = 10;
                case 2:
                    cost[goodX][goodY]=50;
                case 1:
                    cost[goodX][goodY]=10;
                case 3:
                    cost[goodX][goodY]=5;
            }

            cost[badX][badY] = -10;

        }
        return cost;
    }
}
