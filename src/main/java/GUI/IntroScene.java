package GUI;

import Players.AI;
import Players.Human;
import Players.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class IntroScene extends GUIMain {

    private Scene introScene;
    private Label introLabel;
    private StackPane beginPane;
    private VBox introBox;
    private Button beginButton, exitButton;

    public IntroScene() {
        // Empty.
    }

    public void setIntroScene() {

        beginPane = new StackPane();
        introScene = new Scene(beginPane, screenBounds.getWidth(), screenBounds.getHeight());
        introScene.getStylesheets().clear();
        introScene.getStylesheets().add("/Stylesheet.css");

        introBox = new VBox(screenBounds.getHeight()/17.3);
        introLabel = new Label("DICE CHESS 8");
        introLabel.getStyleClass().add("introLabel");
        beginButton = new Button("Begin!");
        beginButton.getStyleClass().add("beginButton");
        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("exitButton");

        introBox.getChildren().addAll(introLabel, beginButton, exitButton);

        beginButton.setPrefSize(screenBounds.getWidth()/6.1, screenBounds.getHeight()/11.5);
        exitButton.setPrefSize(screenBounds.getWidth()/6.1,screenBounds.getHeight()/11.5);

        setIntroButtonsActions();

        introBox.setAlignment(Pos.CENTER);
        beginPane.getChildren().add(introBox);
    }

    private void setIntroButtonsActions() {

        beginButton.setOnAction(e -> {
            Alert modeSel = new Alert(Alert.AlertType.CONFIRMATION);
            modeSel.setTitle("Dice Chess 8 - Game Mode Selection");
            modeSel.setHeaderText("Select which game mode you'd like to play.");
            modeSel.initOwner(mainStage);

            ButtonType twoPlButton = new ButtonType("Multiplayer");
            ButtonType vsAIButton = new ButtonType("Against the AI");
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

            modeSel.getButtonTypes().setAll(twoPlButton, vsAIButton, closeButton);

            Optional<ButtonType> result = modeSel.showAndWait();
            if (result.get() == twoPlButton) {
                // TODO Implement Human vs Human
                Player whitePl = new Human(false);
                Player blackPl = new Human(true);
                Player[] plToAdd = {whitePl, blackPl};
                gameSc.setGameScene(plToAdd);
                mainStage.setScene(gameSc.getGameScene());
            } else if (result.get() == vsAIButton) {
                // TODO Implement Human vs AI
                Alert colSel = new Alert(Alert.AlertType.CONFIRMATION);
                colSel.setTitle("Dice Chess 8 - Side Color Selection");
                colSel.setHeaderText("Select which side color you'd like to take.");
                colSel.initOwner(mainStage);

                ButtonType blackSide = new ButtonType("Black Side");
                ButtonType whiteSide = new ButtonType("White Side");

                colSel.getButtonTypes().setAll(blackSide, whiteSide);

                Optional<ButtonType> newResult = colSel.showAndWait();
                if(newResult.get() == blackSide) {
                    Player humanPl = new Human(true);
                    Player aiPl = new AI(false);
                    Player[] plToAdd = {humanPl, aiPl};
                    gameSc.setGameScene(plToAdd);
                } else if(newResult.get() == whiteSide) {
                    Player humanPl = new Human(false);
                    Player aiPl = new AI(true);
                    Player[] plToAdd = {humanPl, aiPl};
                    gameSc.setGameScene(plToAdd);
                }
                mainStage.setScene(gameSc.getGameScene());
            }
            mainStage.setFullScreen(true);
            mainStage.setResizable(false);
            mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        });

        exitButton.setOnAction(e -> { System.exit(0);});
    }

    public Scene getIntroScene() { return introScene; }
}
