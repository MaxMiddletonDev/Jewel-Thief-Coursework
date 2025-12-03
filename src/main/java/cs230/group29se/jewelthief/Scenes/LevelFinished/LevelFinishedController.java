package cs230.group29se.jewelthief.Scenes.LevelFinished;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelFinishedController extends BaseController implements Initializable {


    public Button NextLevelButton;
    public Button MainMenuButton;
    public Label levelFinishedLabel;
    public Label levelScoreLabel;
    private LevelFinishedScreen screen;

    /**
     * Initializes the Level Complete scene by setting the score label's value to the player's score for the level
     *
     * @param url The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelScoreLabel.setText(String.valueOf(GameManager.getCurrentLevel().getScore()));
    }

    public void selectNextLevel(ActionEvent actionEvent) {
        int currentLevelNumber = GameManager.getCurrentLevel().getLevelNumber();
        GameManager.setCurrentLevelNumber(currentLevelNumber + 1);
        getScreen().setNextScreen(new GameScreen());
        getScreen().setFinished(true);
    }

    public void MainMenu(ActionEvent actionEvent) {
        getScreen().setNextScreen(new MainMenuScreen());
        getScreen().setFinished(true);
    }

    @Override
    public Canvas getCanvas() {return null;}
}
