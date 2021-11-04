package Logic.GameLogic;

import GUI.GameScene;
import GUI.IntroScene;
import Logic.LogicGame;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class WinGui {

    public void winGui(LogicGame l, int oldY, int oldX, int y, int x){
        l.winFlag = true;
        String colorWin = l.currentSpot.getPiece().isColor();

        GameScene gameSc = l.getGameSc();
        Stage mainStage = l.getMainStage();
        IntroScene introSc = l.getIntroSc();

        gameSc.addMoveToHist(colorWin + " " + l.currentSpot.getPiece().getName() + " (WM) -> "
                + (oldY+1) + " " + (oldX+1) + " to " + (y+1) + " " + (x+1));

        Alert winAlert = new Alert(Alert.AlertType.CONFIRMATION);
        winAlert.setTitle("We have a winner!");
        winAlert.setHeaderText(colorWin + " won the game! " +
                "Would to like to play again or go back to the main menu?");
        winAlert.initOwner(mainStage);

        ButtonType playAgain = new ButtonType("Play Again!");
        ButtonType goBack = new ButtonType("Go back.");

        winAlert.getButtonTypes().setAll(playAgain,goBack);

        Optional<ButtonType> results = winAlert.showAndWait();
        if(results.isPresent()) {
            if (results.get() == playAgain) {
                gameSc.setGameScene();
                mainStage.setScene(gameSc.getGameScene());
                mainStage.setFullScreen(true);
                mainStage.setResizable(false);
            } else if (results.get() == goBack) {
                gameSc.setGameScene();
                mainStage.setScene(introSc.getIntroScene());
                mainStage.setFullScreen(true);
                mainStage.setResizable(false);
            }
        }
    }
}
