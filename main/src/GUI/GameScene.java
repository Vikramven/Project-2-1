package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class GameScene extends GUIMain {

    private static Scene gameScene;
    private static Label menuLabel;
    private static BorderPane gamePane;
    private static StackPane menuPane;
    private static VBox menuBox;
    private static Button passButton, rollButton, backButton;

    public GameScene() {
        // Empty.
    }

    public static void setGameScene() {

        gamePane = new BorderPane();
        gameScene = new Scene(gamePane, screenBounds.getWidth(), screenBounds.getHeight());
        gameScene.getStylesheets().clear();
        gameScene.getStylesheets().add("/res/Stylesheet.css");

        menuPane = new StackPane();
        menuBox = new VBox(screenBounds.getHeight()/28);
        menuLabel = new Label("MENU");
        menuLabel.getStyleClass().add("menuLabel");
        passButton = new Button("Pass");
        passButton.getStyleClass().add("passButton");
        passButton.setPrefWidth(screenBounds.getWidth()/12.5);
        rollButton = new Button("Roll!");
        rollButton.getStyleClass().add("rollButton");
        rollButton.setPrefWidth(screenBounds.getWidth()/12.5);
        backButton = new Button("Back");
        backButton.getStyleClass().add("backButton");
        backButton.setPrefWidth(screenBounds.getWidth()/12.5);

        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(menuLabel, passButton, rollButton, backButton);

        setGameButtonsActions();

        menuPane.getChildren().add(menuBox);
        gamePane.setRight(menuPane);
    }

    private static void setGameButtonsActions() {

        passButton.setOnAction(e -> {

        });

        rollButton.setOnAction(e -> {

        });

        backButton.setOnAction(e -> {
            mainStage.setScene(IntroScene.getIntroScene());
            mainStage.setFullScreen(true);
            mainStage.setResizable(false);
            mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        });
    }

    public static Scene getGameScene() { return gameScene; }
}
