package dice_chess.Logic.GameLogic;

import dice_chess.GUI.GameScene;
import dice_chess.GUI.IntroScene;
import dice_chess.Logic.LogicGame;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class WinGui {

    /**
     * Win interface appears when someone wins the game
     * @param l LogicGame object
     */
    public void winGui(LogicGame l, String colorWin){

        GameScene gameSc = l.getGameSc();
        Stage mainStage = l.getMainStage();
        IntroScene introSc = l.getIntroSc();

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
                gameSc.setGameScene(gameSc.getPlayers());
                mainStage.setScene(gameSc.getGameScene());
                mainStage.setFullScreen(true);
                mainStage.setResizable(false);
            } else if (results.get() == goBack) {
                gameSc.setGameScene(gameSc.getPlayers());
                mainStage.setScene(introSc.getIntroScene());
                mainStage.setFullScreen(true);
                mainStage.setResizable(false);
            }
        }
    }
}
