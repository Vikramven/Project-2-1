package GUI;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUIMain extends Application {

    protected static Rectangle2D screenBounds;
    protected static Stage mainStage;
    protected static IntroScene introSc;
    protected static GameScene gameSc;

    /**
     * Constructor
     * Empty, so that we can create the objects introSc and gameSc - used to set the scenes -
     * - without having to use static variables OR having to reload the board.
     */
    public GUIMain(String[] args) {
        Application.launch(args);
    }

    public GUIMain(){

    }

    @Override
    public void start(Stage primaryStage) {

        mainStage = primaryStage;

        screenBounds = Screen.getPrimary().getBounds();

        introSc = new IntroScene();
        gameSc = new GameScene();
        introSc.setIntroScene();
        gameSc.setGameScene();

        mainStage.setScene(introSc.getIntroScene());

        mainStage.setTitle("Dice Chess 8");
        mainStage.getIcons().add(new Image(GUIMain.class.getResourceAsStream("/logo.jpg")));
        mainStage.setFullScreen(false);
        mainStage.setResizable(false);
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainStage.show();
    }

}
