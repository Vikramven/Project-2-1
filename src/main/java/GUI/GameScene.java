package GUI;

import Board.Board;
import Board.Spot;
import Logic.LogicGame;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Optional;

public class GameScene extends GUIMain {

    private Scene gameScene;
    private Label menuLabel, histTitleLabel;
    private BorderPane gamePane;
    private StackPane menuPane, histPane, dicePane, playerPane;
    private GridPane boardPane;
    private ScrollPane scrollPane;
    private VBox menuBox, histBox, scrollBox;
    private HBox diceBox, playerBox;
    private ArrayList<Image> images;
    private ImageView diceImgViews[];
    private Button passButton, backButton, rulesButton;
    private Label playerLabel = new Label("White" + " vs " + "Black");
    private int[] dicePiece = new int[3];

    public GameScene() {
        // Empty.
    }

    public void setGameScene() {

        gamePane = new BorderPane();
        gameScene = new Scene(gamePane, screenBounds.getWidth(), screenBounds.getHeight());
        gameScene.getStylesheets().clear();
        gameScene.getStylesheets().add("/Stylesheet.css");

        setMenuPane();
        setHistPane();
        setDicePane();
        setPlayerPane();
        setGameButtonsActions();
        setBoardPane();
    }

    private void setHistPane() {

        histPane = new StackPane();
        histBox = new VBox(screenBounds.getHeight()/28);
        histTitleLabel = new Label("MOVES");
        histTitleLabel.getStyleClass().add("histTitleLabel");

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

        histBox.setAlignment(Pos.CENTER);
        histBox.getChildren().addAll(histTitleLabel, scrollPane);

        histPane.getChildren().add(histBox);

        gamePane.setLeft(histPane);
    }

    private void setBoardPane() {

        boardPane = new GridPane();
        Button[][] buttonStateBoard = new Button[8][8];
        boardPane.setAlignment(Pos.CENTER);

        Board board = new Board();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                StackPane square = new StackPane();
                Spot spot = board.getSpot(x,y);
                String c;

                buttonStateBoard[x][y] = new Button();
                square.getChildren().add(buttonStateBoard[x][y]);
                if ((x + y) % 2 != 0) { c = "green"; }
                else { c = "white"; }
                buttonStateBoard[x][y].setStyle("-fx-background-color: " + c + ";");
                if(spot != null) {
                    buttonStateBoard[x][y].setStyle(
                            "-fx-background-color: " + c + ";" +
                            "-fx-background-image: url('" + spot.getPiece().getImageURL()+ "');" +
                            "-fx-background-size: 70px;" +
                            "-fx-background-repeat: no-repeat;" +
                            "-fx-background-position: 50%;");
                }
                buttonStateBoard[x][y].setMaxWidth(Double.MAX_VALUE);
                buttonStateBoard[x][y].setMaxHeight(Double.MAX_VALUE);
                boardPane.setFillWidth(buttonStateBoard[x][y], true);
                boardPane.setFillHeight(buttonStateBoard[x][y], true);

                boardPane.add(square, y, x);
            }
        }

        for (int i = 0; i < 8; i++) {
            boardPane.getColumnConstraints().add(new ColumnConstraints(screenBounds.getWidth()/17.5));
            boardPane.getRowConstraints().add(new RowConstraints(screenBounds.getHeight()/10));
        }

        new LogicGame(board, buttonStateBoard, playerLabel, dicePiece, diceImgViews, images, passButton);

        gamePane.setCenter(boardPane);
    }

    private void setMenuPane() {

        menuPane = new StackPane();
        menuBox = new VBox(screenBounds.getHeight()/28);
        menuLabel = new Label("MENU");
        menuLabel.getStyleClass().add("menuLabel");
        passButton = new Button("Pass");
        passButton.getStyleClass().add("passButton");
        passButton.setPrefWidth(screenBounds.getWidth()/12.5);
        rulesButton = new Button("Rules");
        rulesButton.getStyleClass().add("rulesButton");
        rulesButton.setPrefWidth(screenBounds.getWidth()/12.5);
        backButton = new Button("Back");
        backButton.getStyleClass().add("backButton");
        backButton.setPrefWidth(screenBounds.getWidth()/12.5);

        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(menuLabel, rulesButton, passButton, backButton);

        menuPane.getChildren().add(menuBox);

        gamePane.setRight(menuPane);
    }

    private void setDicePane() {

        dicePane = new StackPane();
        diceBox = new HBox(screenBounds.getWidth()/20.5);
        diceImgViews = new ImageView[3];
        images = new ArrayList<>();
        images.add(new Image(GameScene.class.getResourceAsStream("/white_bishop.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/white_knight.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/white_king.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/white_pawn.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/white_queen.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/white_rook.png")));
        for(int i = 0; i < diceImgViews.length; i++) {
            diceImgViews[i] = new ImageView(images.get(i));
            diceImgViews[i].setFitHeight(screenBounds.getHeight()/11.5);
            diceImgViews[i].setFitWidth(screenBounds.getWidth()/20.5);
        }

        diceBox.setAlignment(Pos.TOP_CENTER);
        diceBox.getChildren().addAll(diceImgViews);

        dicePane.getChildren().add(diceBox);

        gamePane.setBottom(dicePane);
    }

    private void setPlayerPane() {

        playerPane = new StackPane();
        playerBox = new HBox();
        playerLabel.setStyle(
                "-fx-font: 42px SansSerifBold;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");

        playerBox.setAlignment(Pos.CENTER);
        playerBox.getChildren().add(playerLabel);

        playerPane.getChildren().add(playerBox);

        gamePane.setTop(playerPane);
    }

    @SuppressWarnings("unchecked")
    private void setGameButtonsActions() {

        rulesButton.setOnAction(e -> {
            Alert[] ruleAlerts = new Alert[5];
            Optional<ButtonType>[] results = new Optional[5];
            for(int i = 0; i < 5; i++) {
                ruleAlerts[i] = new Alert(Alert.AlertType.CONFIRMATION);
                ruleAlerts[i].setTitle("Dice Chess 8 - Rules");
                ruleAlerts[i].getDialogPane().setPrefSize(screenBounds.getWidth()/2.5,
                        screenBounds.getHeight()/4);
                ruleAlerts[i].initOwner(mainStage);
            }

            ruleAlerts[0].setHeaderText("Let's take a look at the rules of this variation of dice chess. \n" +
                    "1. The goal of the game remains the same: To capture the enemy's king.");
            ruleAlerts[1].setHeaderText("2. At every turn, the player rolls three dice (shown at the bottom). \n" +
                    "The outcome determines which pieces can be used for that move. \nNOTE: The rolled pieces will " +
                    "always be white, regardless of whose turn it is. To know whose turn it is, check the green " +
                    "highlighted colour name at the top. \nEach player can PASS if they have no moves or are done " +
                    "with which moves they'd like to make.");
            ruleAlerts[2].setHeaderText("3. In this version, there is no check or checkmate. \nIt is allowed to " +
                    "move the King to a square attacked by an opponent's piece.");
            ruleAlerts[3].setHeaderText("4. In the case that a pawn is bound to advance to the last row, the player " +
                    "has to wait until they roll pawn again, contrary to some versions of the game. Thus, the player " +
                    "promotes the pawn to one of the pieces chosen by a separate (occult) dice roll. \nIf 'pawn' is " +
                    "the roll result, the player has the freedom to choose whichever piece the pawn gets promoted to.");
            ruleAlerts[4].setHeaderText("5. Castling is allowed and issued to solely a King's move (not Rook).");

            ButtonType previousButton = new ButtonType("Previous");
            ButtonType nextButton = new ButtonType("Next");
            ButtonType closeButton = new ButtonType("Close");

            ruleAlerts[0].getButtonTypes().setAll(nextButton,closeButton);
            ruleAlerts[1].getButtonTypes().setAll(previousButton,nextButton,closeButton);
            ruleAlerts[2].getButtonTypes().setAll(previousButton,nextButton,closeButton);
            ruleAlerts[3].getButtonTypes().setAll(previousButton,nextButton,closeButton);
            ruleAlerts[4].getButtonTypes().setAll(previousButton,closeButton);

            int iterator = 0;
            while(iterator >= 0) {
                results[iterator] = ruleAlerts[iterator].showAndWait();
                if(results[iterator].get() == previousButton) {
                    iterator--;
                } else if (results[iterator].get() == nextButton) {
                    iterator++;
                } else if (results[iterator].get() == closeButton) {
                    ruleAlerts[iterator].close();
                    iterator = -1;
                }
            }
        });

        // This works (visually) for PvP pre-set settings (names, String, etc.) - need to update when we introduce AI
        backButton.setOnAction(e -> {
            Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confAlert.setTitle("Restarting the game...");
            confAlert.setHeaderText("Are you sure? Selecting OK will finish this game!");
            confAlert.initOwner(mainStage);

            Optional<ButtonType> result = confAlert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK){
                    setGameScene();
                    mainStage.setScene(introSc.getIntroScene());
                    mainStage.setFullScreen(true);
                    mainStage.setResizable(false);
                }
            }
        });
    }

    public void addMoveToHist(String move) {

        Label newLabel =  new Label(move);
        scrollBox.getChildren().add(newLabel);
        newLabel.setStyle(
                "-fx-font: 15px SansSerifBold;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;");
        newLabel.setAlignment(Pos.CENTER);
        newLabel.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
    }

    public Scene getGameScene() { return gameScene; }
}
