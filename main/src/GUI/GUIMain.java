package GUI;

import Board.Board;
import Players.Human;
import Players.Player;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUIMain extends Application {

    protected static Rectangle2D screenBounds;
    protected static Stage mainStage;
    protected static LogicGame logicGame;
    protected static IntroScene introSc;
    protected static GameScene gameSc;
    protected static Board board;
    protected Player player = new Human(false, true);

    /**
     * Constructor 1
     * @param b Loads the same board from the Game class into the GUI package
     * @param args Arguments passed directly from Game.main()
     */
    public GUIMain(Board b, String[] args) {
        this.board = b;
        Application.launch(args);
    }

    /**
     * Constructor 2
     * Empty, so that we can create the objects introSc and gameSc - used to set the scenes -
     * - without having to use static variables OR having to reload the board.
     */
    public GUIMain() {
        //Empty
    }

    @Override
    public void start(Stage primaryStage) {

        mainStage = primaryStage;
        board = new Board();
        logicGame = new LogicGame();

        screenBounds = Screen.getPrimary().getBounds();

        introSc = new IntroScene();
        gameSc = new GameScene();
        introSc.setIntroScene();
        gameSc.setGameScene();

        mainStage.setScene(introSc.getIntroScene());

        mainStage.setTitle("Dice Chess 8");
        mainStage.getIcons().add(new Image(GUIMain.class.getResourceAsStream("/res/logo.jpg")));
        mainStage.setFullScreen(true);
        mainStage.setResizable(false);
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainStage.show();
    }

}
