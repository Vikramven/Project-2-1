package dice_chess.GUI;

import dice_chess.Players.AI;
import dice_chess.Players.Human;
import dice_chess.Players.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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
//            Alert modeSel = new Alert(Alert.AlertType.CONFIRMATION);
//            modeSel.setTitle("Dice Chess 8 - Game Mode Selection");
//            modeSel.setHeaderText("Select which game mode you'd like to play.");
//            modeSel.initOwner(mainStage);
//
//            ButtonType twoPlButton = new ButtonType("Multiplayer");
//            ButtonType vsAIButton = new ButtonType("Against the AI");
//            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
//
//            modeSel.getButtonTypes().setAll(twoPlButton, vsAIButton, closeButton);
//
//            Optional<ButtonType> result = modeSel.showAndWait();
              // Human vs Human
//            if (result.get() == twoPlButton) {
//
//                Player whitePl = new Human(false);
//                Player blackPl = new Human(true);
//                Player[] plToAdd = {whitePl, blackPl};
//                gameSc.setGameScene(plToAdd);
//                mainStage.setScene(gameSc.getGameScene());
              // AI vs Human
//            } else if (result.get() == vsAIButton) {
//                Alert colSel = new Alert(Alert.AlertType.CONFIRMATION);
//                colSel.setTitle("Dice Chess 8 - Side Color Selection");
//                colSel.setHeaderText("Select which side color you'd like to take.");
//                colSel.initOwner(mainStage);
//
//                ButtonType blackSide = new ButtonType("Black Side");
//                ButtonType whiteSide = new ButtonType("White Side");
//
//                colSel.getButtonTypes().setAll(blackSide, whiteSide);
//
//                Optional<ButtonType> newResult = colSel.showAndWait();
//                if(newResult.get() == blackSide) {
//                    Player humanPl = new Human(true);
//                    Player aiPl = new AI(false);
//                    Player[] plToAdd = {humanPl, aiPl};
//                    gameSc.setGameScene(plToAdd);
//                } else if(newResult.get() == whiteSide) {
//                    Player humanPl = new Human(false);
//                    Player aiPl = new AI(true);
//                    Player[] plToAdd = {aiPl, humanPl};
//                    gameSc.setGameScene(plToAdd);
//                }
//                mainStage.setScene(gameSc.getGameScene());
//            }
            // Create Popup Stage
            Stage settingsStage = new Stage(); settingsStage.setTitle("Game Settings");
            settingsStage.setWidth(640);
            settingsStage.setHeight(480);
            settingsStage.centerOnScreen();
            settingsStage.setResizable(false); settingsStage.setFullScreen(false);
            settingsStage.initOwner(mainStage);
            settingsStage.show();

            setPlayerSelScene(settingsStage);

            mainStage.setFullScreen(true);
            mainStage.setResizable(false);
            mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        });

        exitButton.setOnAction(e -> { System.exit(0); });
    }

    public void setPlayerSelScene(Stage s) {

        // First Scene - Player Selection
        BorderPane settingsPane = new BorderPane();

        VBox centerBox = new VBox(50);
        centerBox.setPrefSize(360,280);

        Label playerSelLabel = new Label("Player Selection");
        playerSelLabel.getStyleClass().add("settingsLabel1");

        HBox playerSelBox = new HBox(100);
        ToggleGroup whiteGroup = new ToggleGroup();
        ToggleGroup blackGroup = new ToggleGroup();
        RadioButton[] rButton = new RadioButton[4];

        // Creation: 0 WHITE AI - 1 WHITE HUMAN - 2 BLACK AI - 3 BLACK HUMAN
        for(int i = 0; i < rButton.length; i++) {
            rButton[i] = new RadioButton();
            if(i == 0 || i == 1) { rButton[i].setToggleGroup(whiteGroup);}
            else { rButton[i].setToggleGroup(blackGroup); }

            if(i == 0 || i == 2) { rButton[i].setText("A.I."); }
            else { rButton[i].setText("Human"); }
        }

        VBox whiteBox = new VBox();
        Label whitePlLabel = new Label("White Player");
        whitePlLabel.getStyleClass().add("settingsLabel2");
        whiteBox.getChildren().addAll(whitePlLabel, rButton[0], rButton[1]);

        VBox blackBox = new VBox();
        Label blackPlLabel = new Label("Black Player");
        blackPlLabel.getStyleClass().add("settingsLabel2");
        blackBox.getChildren().addAll(blackPlLabel, rButton[2], rButton[3]);

        playerSelBox.getChildren().addAll(whiteBox, blackBox);

        HBox depthBox = new HBox(20);
        Label depthLabel = new Label("Depth:");
        depthLabel.getStyleClass().add("settingsLabel3");
        TextField depthField = new TextField();
        Button nextButton = new Button("NEXT");
        nextButton.getStyleClass().add("nextButton");
        depthBox.getChildren().addAll(depthLabel, depthField, nextButton);

        centerBox.getChildren().addAll(playerSelLabel, playerSelBox, depthBox);

        // Assign positions
        playerSelBox.setAlignment(Pos.CENTER);
        depthBox.setAlignment(Pos.CENTER);
        centerBox.setAlignment(Pos.CENTER);

        // Create empty buttons to allow centering
        Button rightEmpty = new Button();
        rightEmpty.setPrefSize(140,480);
        Button bottomEmpty = new Button();
        bottomEmpty.setPrefSize(640,100);
        settingsPane.setRight(rightEmpty);
        settingsPane.setBottom(bottomEmpty);
        settingsPane.setCenter(centerBox);

        // Finish up preparing the scene
        Scene settingsScene = new Scene(settingsPane, s.getWidth(), s.getHeight());
        settingsScene.getStylesheets().add("/SettingsSS.css");
        s.setScene(settingsScene);

        // Button Actions
        nextButton.setOnAction(e -> {
            // Access Selected Radio Buttons
            RadioButton selWhite = (RadioButton) whiteGroup.getSelectedToggle();
            RadioButton selBlack = (RadioButton) blackGroup.getSelectedToggle();
            String whiteGroupValue = selWhite.getText();
            String blackGroupValue = selBlack.getText();

            // White A.I. vs Human
            if(whiteGroupValue.equals("A.I.") && blackGroupValue.equals("Human")) {
                setAISettingsScene("White",s,false,true);
            // Black A.I. vs Human
            } else if(whiteGroupValue.equals("Human") && blackGroupValue.equals("A.I.")) {
                setAISettingsScene("Black",s,false,true);
            // Both A.I.
            } else if(whiteGroupValue.equals("A.I.") && blackGroupValue.equals("A.I.")) {
                setAISettingsScene("White",s,true,true);
            // Both Human Choices (Multiplayer)
            } else {
                Player whitePl = new Human(false);
                Player blackPl = new Human(true);
                Player[] plToAdd = {whitePl, blackPl};
                gameSc.setGameScene(plToAdd);
                s.close();
                mainStage.setScene(gameSc.getGameScene());
                System.out.println("WHITE HUMAN VS BLACK HUMAN");
            }
        });
    }

    public void setAISettingsScene(String c, Stage s, boolean b, boolean firstCall) {

        // Second (and possibly Third) Scene - AI Settings
        BorderPane settingsPane = new BorderPane();

        Label setAILabel = new Label(c + " AI Settings");
        setAILabel.getStyleClass().add("settingsLabel1");

        VBox centerBox = new VBox(50);
        centerBox.setPrefSize(360,280);

        // Prepare Radio Buttons
        ToggleGroup aiSetGroup = new ToggleGroup();
        RadioButton[] rButton = new RadioButton[4];
        for(int i = 0; i < 4; i++) {
            rButton[i] = new RadioButton();
            rButton[i].setToggleGroup(aiSetGroup);
        }
        rButton[0].setText("Minimax / α-β Pruning");
        rButton[1].setText("Expected Minimax");
        rButton[2].setText("Expected Max");
        rButton[3].setText("Baseline (Random) Agent");

        VBox leftOptions = new VBox(50);
        leftOptions.getChildren().addAll(rButton[0], rButton[1]);
        VBox rightOptions = new VBox(50);
        rightOptions.getChildren().addAll(rButton[2], rButton[3]);

        HBox settingsBox = new HBox(50);
        settingsBox.getChildren().addAll(leftOptions, rightOptions);

        Button nextButton = new Button("NEXT");
        nextButton.getStyleClass().add("nextButton");

        centerBox.getChildren().addAll(setAILabel, settingsBox, nextButton);

        // Assign positions
        settingsBox.setAlignment(Pos.CENTER);
        nextButton.setAlignment(Pos.CENTER);
        centerBox.setAlignment(Pos.CENTER);

        // Create empty buttons to allow centering
        Button rightEmpty = new Button();
        rightEmpty.setPrefSize(140,480);
        Button bottomEmpty = new Button();
        bottomEmpty.setPrefSize(640,100);
        settingsPane.setRight(rightEmpty);
        settingsPane.setBottom(bottomEmpty);
        settingsPane.setCenter(centerBox);

        // Finish up preparing the scene
        Scene settingsScene = new Scene(settingsPane, s.getWidth(), s.getHeight());
        settingsScene.getStylesheets().add("/SettingsSS.css");
        s.setScene(settingsScene);

        // Button Actions
        nextButton.setOnAction(e -> {

            // Second Call - which means both A.I. were configured
            if(!firstCall) {
                Player whiteAIPl = new AI(true);
                Player blackAIPl = new AI(false);
                Player[] plToAdd = {whiteAIPl, blackAIPl};
                //gameSc.setGameScene(plToAdd); // Uncomment when double AI is ready
                s.close();
                mainStage.setScene(gameSc.getGameScene());
                System.out.println("WHITE AI VS BLACK AI");
            } else {
                // Need to select Black A.I.'s settings as well
                if (b) {
                    setAISettingsScene("Black", s, false, false);
                    System.out.println("Creating Black AI");
                // No need for extra A.I. settings, start the game
                } else {
                    if (c.equals("White")) {
                        Player blackHPl = new Human(true);
                        Player whiteAIPl = new AI(false);
                        Player[] plToAdd = {blackHPl, whiteAIPl};
                        gameSc.setGameScene(plToAdd);
                        System.out.println("WHITE AI VS BLACK HUMAN");
                    } else {
                        Player whiteHPl = new Human(false);
                        Player blackAIPl = new AI(true);
                        Player[] plToAdd = {whiteHPl, blackAIPl};
                        gameSc.setGameScene(plToAdd);
                        System.out.println("WHITE HUMAN VS BLACK AI");
                    }
                    s.close();
                    mainStage.setScene(gameSc.getGameScene());
                }
            }
        });
    }

    public Scene getIntroScene() { return introScene; }
}
