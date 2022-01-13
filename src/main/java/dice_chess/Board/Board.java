package dice_chess.Board;

import dice_chess.Pieces.*;

public class Board {

    private Spot[][] board;

    /**
     * Piece heap helps to get exact coordinates of pieces on the board
     */
    public PieceMap pieceMap = new PieceMap();

    /**
     * Constructor which start the game
     */
    public Board(){
        this.restartGame();
    }

    /**
     * Constructor which clone the game
     */
    public Board(Spot[][] board){
        this.board = board;
    }

    /**
     * Gives the spot, if coordinates are wrong, it gives an error
     * @param x X coordinate
     * @param y Y coordinate
     * @return The Spot
     */
    public Spot getSpot(int x, int y){

        if(x < 0 || x > 7 || y < 0 || y > 7){
            throw new RuntimeException("Wrong coordinates of spot");
        }

        return board[x][y];
    }

    /**
     * Set the spot, if coordinates are wrong, it gives an error
     * @param spot The spot
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setSpot(Spot spot, int x, int y){

        if(x < 0 || x > 7 || y < 0 || y > 7){
            throw new RuntimeException("Wrong coordinates of spot");
        }
        board[x][y] = spot;
    }


    /**
     * Start the game
     */
    private void restartGame() {

        board = new Spot[8][8];

        //White Side
        //First Line
        board[0][0] = new Spot(0, 0, new Rook(false));
        board[0][1] = new Spot(0, 1, new Knight(false));
        board[0][2] = new Spot(0, 2, new Bishop(false));
        board[0][3] = new Spot(0, 3, new King(false));
        board[0][4] = new Spot(0, 4, new Queen(false));
        board[0][5] = new Spot(0, 5, new Bishop(false));
        board[0][6] = new Spot(0, 6, new Knight(false));
        board[0][7] = new Spot(0, 7, new Rook(false));

        //Second Line: Pawn line
        board[1][0] = new Spot(1, 0, new Pawn(false));
        board[1][1] = new Spot(1, 1, new Pawn(false));
        board[1][2] = new Spot(1, 2, new Pawn(false));
        board[1][3] = new Spot(1, 3, new Pawn(false));
        board[1][4] = new Spot(1, 4, new Pawn(false));
        board[1][5] = new Spot(1, 5, new Pawn(false));
        board[1][6] = new Spot(1, 6, new Pawn(false));
        board[1][7] = new Spot(1, 7, new Pawn(false));


        //Black Side
        //First Line
        board[7][0] = new Spot(7, 0, new Rook(true));
        board[7][1] = new Spot(7, 1, new Knight(true));
        board[7][2] = new Spot(7, 2, new Bishop(true));
        board[7][3] = new Spot(7, 3, new King(true));
        board[7][4] = new Spot(7, 4, new Queen(true));
        board[7][5] = new Spot(7, 5, new Bishop(true));
        board[7][6] = new Spot(7, 6, new Knight(true));
        board[7][7] = new Spot(7, 7, new Rook(true));

        //Second Line: Pawn line
        board[6][0] = new Spot(6, 0, new Pawn(true));
        board[6][1] = new Spot(6, 1, new Pawn(true));
        board[6][2] = new Spot(6, 2, new Pawn(true));
        board[6][3] = new Spot(6, 3, new Pawn(true));
        board[6][4] = new Spot(6, 4, new Pawn(true));
        board[6][5] = new Spot(6, 5, new Pawn(true));
        board[6][6] = new Spot(6, 6, new Pawn(true));
        board[6][7] = new Spot(6, 7, new Pawn(true));
    }

    /**
     * Print the board of the game
     */
    public void print(){
        System.out.println("\n\n\n");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Spot spot = board[i][j];
                if(spot == null)
                    System.out.print(" " + i + " " + j);
                else {
                    Piece piece = spot.getPiece();
                    String namePiece = piece.getName();
                    String colorPiece = piece.isColor();

                    System.out.print(" " + namePiece.charAt(0) + namePiece.charAt(1) + colorPiece.charAt(0));
                }
            }
            System.out.println();
        }
    }

    /**
     * Clone the object Board to avoid mutations
     * @return the clone of the object Board
     */
    @Override
    public Board clone(){
        Spot[][] newBoard = new Spot[8][8];

        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard.length; j++) {
                if(board[i][j] != null)
                    newBoard[i][j] = board[i][j].clone();
                else
                    newBoard[i][j] = null;
            }
        }
        return new Board(newBoard);
    }
}
