package Board;

import java.util.LinkedList;

public class PieceHeap {

    LinkedList<Coordinate>[] whitePieces = new LinkedList[6];

    LinkedList<Coordinate>[] blackPieces = new LinkedList[6];

    public PieceHeap(){
        createWhitePieceHeap();
        createBlackPieceHeap();
    }

    public PieceHeap(LinkedList<Coordinate>[] w, LinkedList<Coordinate>[] b){
        this.whitePieces = w;
        this.blackPieces = b;
    }

    private void createWhitePieceHeap(){
        for (int i = 0; i < whitePieces.length; i++) {
            whitePieces[i] = new LinkedList<>();
        }

        whitePieces[3].add(new Coordinate(1, 0));
        whitePieces[3].add(new Coordinate(1, 1));
        whitePieces[3].add(new Coordinate(1, 2));
        whitePieces[3].add(new Coordinate(1, 3));
        whitePieces[3].add(new Coordinate(1, 4));
        whitePieces[3].add(new Coordinate(1, 5));
        whitePieces[3].add(new Coordinate(1, 6));
        whitePieces[3].add(new Coordinate(1, 7));

        whitePieces[5].add(new Coordinate(0, 0));
        whitePieces[1].add(new Coordinate(0, 1));
        whitePieces[0].add(new Coordinate(0, 2));
        whitePieces[2].add(new Coordinate(0, 3));
        whitePieces[4].add(new Coordinate(0, 4));
        whitePieces[0].add(new Coordinate(0, 5));
        whitePieces[1].add(new Coordinate(0, 6));
        whitePieces[5].add(new Coordinate(0, 7));

    }

    private void createBlackPieceHeap(){
        for (int i = 0; i < blackPieces.length; i++) {
            blackPieces[i] = new LinkedList<>();
        }

        blackPieces[3].add(new Coordinate(6, 0));
        blackPieces[3].add(new Coordinate(6, 1));
        blackPieces[3].add(new Coordinate(6, 2));
        blackPieces[3].add(new Coordinate(6, 3));
        blackPieces[3].add(new Coordinate(6, 4));
        blackPieces[3].add(new Coordinate(6, 5));
        blackPieces[3].add(new Coordinate(6, 6));
        blackPieces[3].add(new Coordinate(6, 7));

        blackPieces[5].add(new Coordinate(7, 0));
        blackPieces[1].add(new Coordinate(7, 1));
        blackPieces[0].add(new Coordinate(7, 2));
        blackPieces[2].add(new Coordinate(7, 3));
        blackPieces[4].add(new Coordinate(7, 4));
        blackPieces[0].add(new Coordinate(7, 5));
        blackPieces[1].add(new Coordinate(7, 6));
        blackPieces[5].add(new Coordinate(7, 7));
    }

    public LinkedList<Coordinate> getAllPieces(int x, boolean black){
           if(!black)
               return whitePieces[x];

           return blackPieces[x];
    }

    public void popPiece(int Piece, boolean black, int x, int y){
        LinkedList<Coordinate> allPiece;
        if(!black)
            allPiece = whitePieces[Piece];
        else
            allPiece = blackPieces[Piece];

        for (int i = 0; i < allPiece.size(); i++) {
            int tmp_x = allPiece.get(i).x;
            int tmp_y = allPiece.get(i).y;
            if(tmp_x == x && tmp_y == y) {
                if(!black)
                    whitePieces[Piece].remove(i);
                else
                    blackPieces[Piece].remove(i);
                return;
            }
        }
    }

    public void pawn(int Piece, boolean black, int x, int y, int changePiece){
        popPiece(Piece, black, x, y);

        if(!black)
            whitePieces[changePiece].add(new Coordinate(x, y));
        else
            blackPieces[changePiece].add(new Coordinate(x, y));
    }

    public void changeCoordinate(int Piece, boolean black, int x, int y, int moveX, int moveY){
        LinkedList<Coordinate> allPiece;
        if(!black)
            allPiece = whitePieces[Piece];
        else
            allPiece = blackPieces[Piece];

        for (int i = 0; i < allPiece.size(); i++) {
            Coordinate coordinate = allPiece.get(i);
            int tmp_x = coordinate.x;
            int tmp_y = coordinate.y;
            if(tmp_x == x && tmp_y == y) {
                coordinate.x = moveX;
                coordinate.y = moveY;
                return;
            }
        }
    }

    public PieceHeap clone(){
        LinkedList<Coordinate>[] newWhitePieces = new LinkedList[6];

        LinkedList<Coordinate>[] newBlackPieces = new LinkedList[6];

        for (int i = 0; i < newWhitePieces.length; i++) {
            newWhitePieces[i] = new LinkedList<>();
        }

        for (int i = 0; i < newBlackPieces.length; i++) {
            newBlackPieces[i] = new LinkedList<>();
        }

        for (int i = 0; i < blackPieces.length; i++) {
            for (int j = 0; j < blackPieces[i].size(); j++) {
                Coordinate coordinate = blackPieces[i].get(j);
                newBlackPieces[i].add(new Coordinate(coordinate.x, coordinate.y));
            }
        }

        for (int i = 0; i < whitePieces.length; i++) {
            for (int j = 0; j < whitePieces[i].size(); j++) {
                Coordinate coordinate = whitePieces[i].get(j);
                newWhitePieces[i].add(new Coordinate(coordinate.x, coordinate.y));
            }
        }
        return new PieceHeap(newWhitePieces, newBlackPieces);
    }
}
