package dice_chess.GUI;

import dice_chess.Players.AI;
import dice_chess.Players.Human;
import dice_chess.Players.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

            // Create Popup Stage
            Stage settingsStage = new Stage(); settingsStage.setTitle("Game Settings");
//            settingsStage.setWidth(640);
//            settingsStage.setHeight(480);
            settingsStage.centerOnScreen();
            settingsStage.setResizable(false); settingsStage.setFullScreen(false);
            settingsStage.initOwner(mainStage);
            settingsStage.show();

            setPlayerSelScene(settingsStage);

            mainStage.setFullScreen(false);
            mainStage.setResizable(true);
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

        // Default selected Radio Buttons -> Human vs Human
        rButton[1].setSelected(true);
        rButton[3].setSelected(true);

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
        playerSelLabel.setAlignment(Pos.CENTER);
        playerSelBox.setAlignment(Pos.CENTER);
        depthBox.setAlignment(Pos.CENTER);
        centerBox.setAlignment(Pos.CENTER);

        // Create empty buttons to allow centering
//        Button rightEmpty = new Button();
//        rightEmpty.setPrefSize(140,480);
//        Button bottomEmpty = new Button();
//        bottomEmpty.setPrefSize(640,100);
//        settingsPane.setRight(rightEmpty);
//        settingsPane.setBottom(bottomEmpty);
        settingsPane.setCenter(centerBox);

        // Finish up preparing the scene
        Scene settingsScene = new Scene(settingsPane, s.getMaxWidth(), s.getHeight());
        settingsScene.getStylesheets().add("/SettingsSS.css");
        s.setScene(settingsScene);

        // Button Actions
        nextButton.setOnAction(e -> {

            boolean showGameFlag;

            try {
                int depthValue = Integer.parseInt(depthField.getText());
                showGameFlag = true;
                gameSc.setDepth(depthValue);
            } catch(NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Please insert an integer value for the depth.");
                showGameFlag = false;
                alert.initOwner(s);
                alert.show();
            }

            if(showGameFlag) {

                // Access Selected Radio Buttons
                RadioButton selWhite = (RadioButton) whiteGroup.getSelectedToggle();
                RadioButton selBlack = (RadioButton) blackGroup.getSelectedToggle();
                String whiteGroupValue = selWhite.getText();
                String blackGroupValue = selBlack.getText();

                // White A.I. vs Human
                if (whiteGroupValue.equals("A.I.") && blackGroupValue.equals("Human")) {
                    setAISettingsScene("White", s, false, true);
                    // Black A.I. vs Human
                } else if (whiteGroupValue.equals("Human") && blackGroupValue.equals("A.I.")) {
                    setAISettingsScene("Black", s, false, true);
                    // Both A.I.
                } else if (whiteGroupValue.equals("A.I.") && blackGroupValue.equals("A.I.")) {
                    setAISettingsScene("White", s, true, true);
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
        RadioButton[] rButton = new RadioButton[3];
        for(int i = 0; i < 3; i++) {
            rButton[i] = new RadioButton();
            rButton[i].setToggleGroup(aiSetGroup);
        }

        rButton[0].setText("Baseline (Random) Agent");
        rButton[1].setText("ExpectiMax");
        rButton[2].setText("Minimax / α-β Pruning");
        //rButton[3].setText("Expected Minimax"); // Possible implementation next phase

        Button nextButton = new Button("NEXT");
        nextButton.getStyleClass().add("nextButton");

        VBox leftOptions = new VBox(50);
        leftOptions.getChildren().addAll(rButton[0], rButton[1]);
        VBox rightOptions = new VBox(50);
        rightOptions.getChildren().addAll(rButton[2], nextButton);

        HBox settingsBox = new HBox(50);
        settingsBox.getChildren().addAll(leftOptions, rightOptions);

//        Button nextButton = new Button("NEXT");
//        nextButton.getStyleClass().add("nextButton");

        centerBox.getChildren().addAll(setAILabel, settingsBox);

        // Assign positions
        settingsBox.setAlignment(Pos.CENTER);
        nextButton.setAlignment(Pos.CENTER);
        centerBox.setAlignment(Pos.CENTER);

        // Create empty buttons to allow centering
//        Button rightEmpty = new Button();
//        rightEmpty.setPrefSize(140,480);
//        Button bottomEmpty = new Button();
//        bottomEmpty.setPrefSize(640,100);
//        settingsPane.setRight(rightEmpty);
//        settingsPane.setBottom(bottomEmpty);
        settingsPane.setCenter(centerBox);

        // Finish up preparing the scene
        Scene settingsScene = new Scene(settingsPane, s.getWidth(), s.getHeight());
        settingsScene.getStylesheets().add("/SettingsSS.css");
        s.setScene(settingsScene);

        // Button Actions
        nextButton.setOnAction(e -> {

            RadioButton setButton = (RadioButton) aiSetGroup.getSelectedToggle();
            String aiSetValue = setButton.getText();

            // Second Call - which means both A.I. were configured
            if(!firstCall) {
                Player whiteAIPl = new AI(true);
                Player blackAIPl = new AI(false);
                Player[] plToAdd = {whiteAIPl, blackAIPl};
                gameSc.setAIBlack(getAIEquivInt(aiSetValue));
                s.close();
                gameSc.setGameScene(plToAdd);
                mainStage.setScene(gameSc.getGameScene());
                System.out.println("WHITE AI SET VALUE:" + aiSetValue);
                System.out.println("WHITE AI VS BLACK AI");
            } else {
                // Need to select Black A.I.'s settings as well
                if (b) {
                    setAISettingsScene("Black", s, false, false);
                    gameSc.setAIWhite(getAIEquivInt(aiSetValue));
                    System.out.println("WHITE AI SET VALUE:" + aiSetValue);
                    System.out.println("Creating Black AI");
                // No need for extra A.I. settings, start the game
                } else {
                    if (c.equals("White")) {
                        Player blackHPl = new Human(true);
                        Player whiteAIPl = new AI(false);
                        Player[] plToAdd = {whiteAIPl, blackHPl};
                        gameSc.setAIWhite(getAIEquivInt(aiSetValue));
                        gameSc.setGameScene(plToAdd);
                        System.out.println("WHITE AI SET VALUE:" + aiSetValue);
                        System.out.println("WHITE AI VS BLACK HUMAN");
                    } else {
                        Player whiteHPl = new Human(false);
                        Player blackAIPl = new AI(true);
                        Player[] plToAdd = {whiteHPl, blackAIPl};
                        gameSc.setAIBlack(getAIEquivInt(aiSetValue));
                        gameSc.setGameScene(plToAdd);
                        System.out.println("BLACK AI SET VALUE:" + aiSetValue);
                        System.out.println("WHITE HUMAN VS BLACK AI");
                    }
                    s.close();
                    mainStage.setScene(gameSc.getGameScene());
                }
            }
        });
    }

    private int getAIEquivInt(String radioValue) {
        int equivInt;
        if(radioValue.equals("Baseline (Random) Agent")) {
            equivInt = 0;
        } else if(radioValue.equals("ExpectiMax")) {
            equivInt = 1;
        } else {
            equivInt = 2;
        }
        return equivInt;
    }

    public Scene getIntroScene() { return introScene; }
}
