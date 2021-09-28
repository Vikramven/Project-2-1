import Board.Board;
import GUI.GUIMain;
import javafx.application.Application;

import java.awt.desktop.AppForegroundListener;

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
