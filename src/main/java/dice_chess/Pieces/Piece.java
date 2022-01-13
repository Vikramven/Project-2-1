package dice_chess.Pieces;

import dice_chess.Board.Board;
import dice_chess.Board.Spot;
import dice_chess.Logic.EvaluationFunction.EvaluationFunction;
import dice_chess.Logic.LogicGame;
import dice_chess.Logic.MoveLogic.Move;
import dice_chess.Board.*;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Piece implements PieceMove {

    //The color of the piece
    protected boolean black;

    //The name of the piece
    protected String name, imageURL;

    //The name int of the piece
    protected int nameInt;


    public Piece(boolean black) {
        this.black = black;
    }

    /**
     * Get the String color of the piece
     * @return the String color of the piece
     */
    public String isColor() {
        if(black)
            return "Black";
        return "White";
    }

    /**
     * Get the color of the piece
     * @return the color of the piece
     */
    public boolean getColor(){
        return black;
    }

    /**
     * Assign the white color for the piece
     */
    public void setWhite() {
        black = false;
    }

    /**
     * Assign the black color for the piece
     */
    public void setBlack() {
        black = true;
    }

    /**
     * Get the String name of the piece
     * @return the String name of the piece
     */
    public String getName() {
        return name;
    }

    /**
     * Get the String image URL of the piece
     * @return the String image URL of the piece
     */
    public String getImageURL() { return imageURL; }

    /**
     * Get the name int of the piece
     * @return the name int of the piece
     */
    public int getNameInt() {
        return nameInt;
    }

    /**
     * Define if piece have obstacles on their moves
     * @param spot The spot
     * @param legalMoves Arraylist with all possible legal move
     * @return true = obstacle: false = no obstacle
     */
    protected boolean isObstacle(Spot spot, ArrayList<Move> legalMoves, int x, int y, Piece piece, EvaluationFunction ef, LogicGame l){

        if(spot != null) {
            if (!spot.getPiece().isColor().equals(isColor())) {
                double costMove = ef.evaluateMove(x, y, this, black, spot.getX(), spot.getY(), l);
                legalMoves.add(new Move(spot.getX(), spot.getY(), piece, costMove, x, y));
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
    public ArrayList<Move> checkPlayerMove(LogicGame l, Spot spot, boolean player, int evalFunction){
        if(spot.getPiece().getColor() == player){

            return allLegalMoves(l, spot, evalFunction);
        }
        return null;
    }


    public double pythagorasKingEvaluation(Board board, boolean color, int x, int y){
        LinkedList<Coordinate> listCoordinateKing = board.pieceMap.getAllPieces(2, !color);

        if(listCoordinateKing.size() != 0) {
            Coordinate coordinateKing = listCoordinateKing.get(0);

            int a = Math.abs(x - coordinateKing.x);
            int b = Math.abs(y - coordinateKing.y);
            double c = Math.pow(a, 2) + Math.pow(b, 2);
            c = Math.sqrt(c) * 2;

            return 15 - c;
        }
        return 0;
    }
}
