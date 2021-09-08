package Pieces;

import Board.Board;
import Board.Spot;

import java.util.ArrayList;

public class King extends Piece {

    /**
     * Constructor
     * @param black Define the color for the piece
     */
    public King(boolean black) {
        super(black);
        name = "King";
    }

    //TODO HAVE TO IMPLEMENT      RULES?????
    public ArrayList<Spot> allLegalMoves(Board board, Spot spot) {
        ArrayList<Spot> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();

        return legalMoves;
    }
}
