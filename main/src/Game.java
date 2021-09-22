import Board.Board;

public class Game {

    public static void main(String[] args) {
        Game game = new Game();
        //Application.launch(GUIMain.class, args);
    }

    public Game(){
        Board board = new Board();
        board.print();
    }
}
