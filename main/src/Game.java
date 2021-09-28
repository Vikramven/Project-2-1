import Board.Board;
import GUI.GUIMain;

public class Game {

    private Board board;

    public static void main(String[] args) {
        Game game = new Game();
        GUIMain gui = new GUIMain(game.getBoard(),args);
    }

    public Game() {
        board = new Board();
        board.print();
    }

    public Board getBoard() { return this.board; }

}
