package GUI;

import Board.Board;
import Board.Spot;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Optional;

public class GameScene extends GUIMain {

    private Scene gameScene;
    private Label menuLabel, histTitleLabel, histLabel;
    private BorderPane gamePane;
    private StackPane menuPane, histPane, dicePane, playerPane;
    private GridPane boardPane;
    private ScrollPane scrollPane;
    private VBox menuBox, histBox, scrollBox;
    private HBox diceBox, playerBox;
    private ArrayList<Image> images;
    private ImageView diceImgViews[];
    private Button passButton, rollButton, backButton, buttonStateBoard[][];
    protected Label playerLabel = new Label("White" + " vs " + "Black");
    private int[] dicePiece = new int[3];

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

        gamePane.setLeft(histPane);
    }

    private void setBoardPane() {

        boardPane = new GridPane();
        buttonStateBoard = new Button[8][8];
        boardPane.setAlignment(Pos.CENTER);

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

        logicGame.setSpotAction(board, buttonStateBoard, playerLabel, dicePiece, diceImgViews, images);

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
        rollButton = new Button("Roll!");
        rollButton.getStyleClass().add("rollButton");
        rollButton.setPrefWidth(screenBounds.getWidth()/12.5);
        backButton = new Button("Back");
        backButton.getStyleClass().add("backButton");
        backButton.setPrefWidth(screenBounds.getWidth()/12.5);

        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(menuLabel, passButton, rollButton, backButton);

        menuPane.getChildren().add(menuBox);

        gamePane.setRight(menuPane);
    }

    private void setDicePane() {

        dicePane = new StackPane();
        diceBox = new HBox(screenBounds.getWidth()/20.5);
        diceImgViews = new ImageView[3];
        images = new ArrayList<>();
        images.add(new Image(GameScene.class.getResourceAsStream("/res/white_bishop.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/res/white_knight.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/res/white_king.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/res/white_pawn.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/res/white_queen.png")));
        images.add(new Image(GameScene.class.getResourceAsStream("/res/white_rook.png")));
        for(int i = 0; i < diceImgViews.length; i++) {
            diceImgViews[i] = new ImageView(images.get(i));
            diceImgViews[i].setFitHeight(screenBounds.getHeight()/11.5);
            diceImgViews[i].setFitWidth(screenBounds.getWidth()/20.5);
        }
        rollDice(diceImgViews, images, dicePiece);

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

    private void setGameButtonsActions() {

        // This works (visually) for PvP pre-set settings (names, String, etc.) - need to update when we introduce AI
        passButton.setOnAction(e -> {
            changePlayer(buttonStateBoard, playerLabel);
            rollDice(diceImgViews, images, dicePiece);
        });

        //TODO We do not need the button roll
//        // Just to show it's working, need to implement randomness and individual swapping
//        rollButton.setOnAction(e -> {
//            rollDice(diceImgViews, images, dicePiece);
//        });

        backButton.setOnAction(e -> {
            Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confAlert.setTitle("Restarting the game...");
            confAlert.setHeaderText("Are you sure? Selecting OK will restart the game!");
            confAlert.initOwner(mainStage);

            Optional<ButtonType> result = confAlert.showAndWait();
            if (result.get() == ButtonType.OK){
                board.restartGame();
                setGameScene();
                mainStage.setScene(introSc.getIntroScene());
                mainStage.setFullScreen(true);
                mainStage.setResizable(false);
            }
        });
    }

    /**
     * Remove all hints from the board
     * @param board The state of the board
     * @param buttonBoard buttons on the board
     */
    protected void removeHighlightButtons(Board board, Button[][] buttonBoard) {
        Spot spot;
        String c;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                spot = board.getSpot(x, y);
                if ((x + y) % 2 != 0) { c = "green"; }
                else { c = "white"; }
                if(spot == null) {
                    buttonBoard[x][y].setStyle("-fx-background-color: " + c + ";");
                } else {
                    buttonBoard[x][y].setStyle(
                            "-fx-background-color: " + c + ";" +
                                    "-fx-background-image: url('" + spot.getPiece().getImageURL() + "');" +
                                    "-fx-background-size: 70px;" +
                                    "-fx-background-repeat: no-repeat;" +
                                    "-fx-background-position: 50%;");
                }
            }
        }
    }


    /**
     * Change the player move
     * @param buttonBoard buttons on the board
     * @param playerLabel Label which show who move
     */
    protected void changePlayer(Button[][] buttonBoard, Label playerLabel){
        if(player.isColorSide().equals("White")) {
            playerLabel.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 65px 65px to 100px 100px, #ff8000, #32cd32);");
            player.setColorSide(true);
        } else {
            playerLabel.setStyle(
                    "-fx-font: 42px SansSerifBold;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: linear-gradient(from 60px 60px to 100px 100px, #32cd32, #ff8000);");
            player.setColorSide(false);
        }
        removeHighlightButtons(board, buttonBoard);
    }

    protected void rollDice(ImageView diceImgViews[], ArrayList<Image> images, int[] dicePiece){
        int point = 0;
        for (ImageView diceImgView : diceImgViews) {
            int random = (int) (Math.random() * 6);
            diceImgView.setImage(images.get(random));
            dicePiece[point] = random;
            point++;
        }
    }

    public Scene getGameScene() { return gameScene; }
}
