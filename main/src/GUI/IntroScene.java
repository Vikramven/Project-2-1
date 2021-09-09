package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Stack;

public class IntroScene extends GUIMain {

    private static Scene introScene;
    private static Label introLabel;
    private static StackPane beginPane;
    private static VBox introBox;
    private static Button beginButton, exitButton;

    public IntroScene() {
        // Empty.
    }

    public static void setIntroScene() {

        beginPane = new StackPane();
        introScene = new Scene(beginPane, screenBounds.getWidth(), screenBounds.getHeight());
        introScene.getStylesheets().clear();
        introScene.getStylesheets().add("/res/Stylesheet.css");

        introBox = new VBox(screenBounds.getHeight()/17.3);
        introLabel = new Label("DICE CHESS 8");
        introLabel.getStyleClass().add("introLabel");
        beginButton = new Button("Begin!");
        beginButton.getStyleClass().add("beginButton");
        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("exitButton");

        introBox.getChildren().addAll(introLabel, beginButton, exitButton);

        System.out.println(screenBounds.getWidth() + " " + screenBounds.getHeight());
        beginButton.setPrefSize(screenBounds.getWidth()/6.1, screenBounds.getHeight()/11.5);
        exitButton.setPrefSize(screenBounds.getWidth()/6.1,screenBounds.getHeight()/11.5);

        setIntroButtonsActions();

        introBox.setAlignment(Pos.CENTER);
        beginPane.getChildren().add(introBox);
    }

    private static void setIntroButtonsActions() {

        beginButton.setOnAction(e -> {
            mainStage.setScene(GameScene.getGameScene());
            mainStage.setFullScreen(true);
            mainStage.setResizable(false);
            mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        });

        exitButton.setOnAction(e -> { System.exit(0);});
    }

    public static Scene getIntroScene() { return introScene; }
}

