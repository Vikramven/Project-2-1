package GUI;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;


public class GUIMain extends Application {

    protected static Stage mainStage;
    protected static Rectangle2D screenBounds;

    public GUIMain() {
        // Empty.
    }

    @Override
    public void start(Stage primaryStage) {

        mainStage = primaryStage;

        screenBounds = Screen.getPrimary().getBounds();

        IntroScene.setIntroScene();
        GameScene.setGameScene();

        mainStage.setScene(IntroScene.getIntroScene());

        mainStage.setTitle("Dice Chess 8");
        mainStage.getIcons().add(new Image(GUIMain.class.getResourceAsStream("/res/logo.jpg")));
        mainStage.setFullScreen(true);
        mainStage.setResizable(false);
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainStage.show();
    }

}
