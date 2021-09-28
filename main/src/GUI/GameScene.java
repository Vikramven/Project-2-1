package GUI;

import Board.Board;
import Board.Spot;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class GameScene extends GUIMain {

    private Scene gameScene;
    private Label menuLabel, histTitleLabel, histLabel, playerLabel;
    private BorderPane gamePane;
    private StackPane menuPane, histPane, dicePane, playerPane;
    private GridPane boardPane;
    private ScrollPane scrollPane;
    private VBox menuBox, histBox, scrollBox;
    private HBox diceBox, playerBox;
    private Image diceImgs[];
    private ImageView diceImgViews[];
    private Button passButton, rollButton, backButton;
    private boolean p1Turn;

    public GameScene() {
        // Empty.
    }

    public void setGameScene() {

        gamePane = new BorderPane();
        gameScene = new Scene(gamePane, screenBounds.getWidth(), screenBounds.getHeight());
        gameScene.getStylesheets().clear();
        gameScene.getStylesheets().add("/res/Stylesheet.css");

        setMenuPane();
        setHistPane();
        setBoardPane();
        setDicePane();
        setPlayerPane();
        setGameButtonsActions();

        gamePane.setLeft(histPane);
        gamePane.setRight(menuPane);
        gamePane.setCenter(boardPane);
        gamePane.setBottom(dicePane);
        gamePane.setTop(playerPane);
    }

    private void setHistPane() {

        histPane = new StackPane();
        histBox = new VBox(screenBounds.getHeight()/28);
        histTitleLabel = new Label("MOVES");
        histTitleLabel.getStyleClass().add("histTitleLabel");

        /*
        histLabel = new Label("No moves yet\na\na\na\na\na\ng");
        histLabel.setMinHeight(screenBounds.getHeight()/4);
        histLabel.setMaxHeight(screenBounds.getHeight()/4);
        histLabel.getStyleClass().add("histLabel");
        histLabel.setTextAlignment(TextAlignment.CENTER);
        */

        scrollBox = new VBox();
        scrollBox.getStyleClass().add("scrollBox");

        scrollPane = new ScrollPane();
        scrollPane.setContent(scrollBox);
        scrollPane.getStyleClass().add("scrollPane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setMinHeight(screenBounds.getHeight()/4);
        scrollPane.setMaxHeight(screenBounds.getHeight()/4);
        scrollPane.setFitToHeight(true);

        // Use this to add each move - remove button afterwards, used just for testing
        /*
        Button b = new Button("add");
        b.setOnAction(ev -> {
            Label newLabel =  new Label("WHATEVER THE MOVE WAS");
            scrollBox.getChildren().add(newLabel);
            newLabel.setStyle(
                    "-fx-font: 15px SansSerifBold;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            newLabel.setAlignment(Pos.CENTER);
            newLabel.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                    scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
        });
        */
        histBox.setAlignment(Pos.CENTER);
        histBox.getChildren().addAll(histTitleLabel, scrollPane /*, b*/); // remove button afterwards, used for testing

        histPane.getChildren().add(histBox);
    }

    private void setBoardPane() {

        boardPane = new GridPane();
        boardPane.setAlignment(Pos.CENTER);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col ++) {
                StackPane square = new StackPane();
                String c;
                if ((row + col) % 2 != 0) {
                    c = "black";
                } else {
                    c = "white";
                }
                square.setStyle("-fx-background-color: " + c + ";");
                boardPane.add(square, col, row);
            }
        }
        for (int i = 0; i < 8; i++) {
            boardPane.getColumnConstraints().add(new ColumnConstraints(screenBounds.getWidth()/17.5));
            boardPane.getRowConstraints().add(new RowConstraints(screenBounds.getHeight()/10));
        }
    }

    private void setMenuPane() {

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

        menuPane.getChildren().add(menuBox);
    }

    private void setDicePane() {

        dicePane = new StackPane();
        diceBox = new HBox(screenBounds.getWidth()/20.5);
        diceImgViews = new ImageView[3];
        diceImgs = new Image[3];
        for(int i = 0; i < diceImgs.length; i++) {
            diceImgs[i] = new Image(GameScene.class.getResourceAsStream("/res/white_pawn.png"));
            diceImgViews[i] = new ImageView(diceImgs[i]);
            diceImgViews[i].setFitHeight(screenBounds.getHeight()/11.5);
            diceImgViews[i].setFitWidth(screenBounds.getWidth()/20.5);
        }

        diceBox.setAlignment(Pos.TOP_CENTER);
        diceBox.getChildren().addAll(diceImgViews);

        dicePane.getChildren().add(diceBox);
    }

    private void setPlayerPane() {

        playerPane = new StackPane();
        playerBox = new HBox();
        String playerString = "Player1" + " vs " + "Player2";
        p1Turn = true;
        playerLabel = new Label(playerString);
        playerLabel.setStyle(
                "-fx-font: 42px SansSerifBold;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");

        playerBox.setAlignment(Pos.CENTER);
        playerBox.getChildren().add(playerLabel);

        playerPane.getChildren().add(playerBox);
    }

    private void setGameButtonsActions() {

        // This works (visually) for PvP pre-set settings (names, String, etc) - need to update when we introduce AI
        passButton.setOnAction(e -> {
            if(p1Turn) {
                playerLabel.setStyle(
                        "-fx-font: 42px SansSerifBold;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: linear-gradient(from 65px 65px to 100px 100px, #ff8000, #32cd32);");
                p1Turn = false;
            } else {
                playerLabel.setStyle(
                        "-fx-font: 42px SansSerifBold;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
                p1Turn = true;
            }
        });

        // Just to show it's working, need to implement randomness and individual swapping
        rollButton.setOnAction(e -> {
            for(int i = 0; i < diceImgs.length; i++) {
                diceImgs[i] = new Image(GameScene.class.getResourceAsStream("/res/white_rook.png"));
                diceImgViews[i].setImage(diceImgs[i]);
            }
        });

        backButton.setOnAction(e -> {
            mainStage.setScene(introSc.getIntroScene());
            mainStage.setFullScreen(true);
            mainStage.setResizable(false);
            mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        });
    }

    public Scene getGameScene() { return gameScene; }
}
