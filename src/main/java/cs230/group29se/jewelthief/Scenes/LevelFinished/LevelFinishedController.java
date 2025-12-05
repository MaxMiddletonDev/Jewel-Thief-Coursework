package cs230.group29se.jewelthief.Scenes.LevelFinished;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.GameProfileHelper;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Level Finished scene.
 * Manages the UI elements and handles user interactions such as retrying the level or returning to the main menu.
 *
 * @author Ben Poole
 */
public class LevelFinishedController extends BaseController implements Initializable {


    /** Button to go to the next level */
    public Button NextLevelButton;
    /** Button to go back to main screen */
    public Button MainMenuButton;
    /** Label to display the "Level Finished" message */
    public Label levelFinishedLabel;
    /** Label for displaying the score earned during the level */
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
        // Show the score
        int score = GameManager.getCurrentLevel().getScore();
        levelScoreLabel.setText(String.valueOf(score));

        // Submit highscore for this level
        String profileName = GameProfileHelper.getActiveProfileName();
        int levelNum = GameManager.getCurrentLevel().getLevelNumber();
        String levelId = String.valueOf(levelNum);
        String timestamp = java.time.Instant.now().toString();

        PersistenceManager pm = GameManager.getPersistenceManager();
        pm.submitScore(profileName, levelId, score, timestamp);

        // Reset the level by deleting the save so next run is fresh
        pm.deleteSaveForCurrentLevel();
    }

    /**
     * Starts the next level in numerical order
     */
    public void selectNextLevel() {
        PersistenceManager pm = GameManager.getPersistenceManager();
        pm.deleteSaveForCurrentLevel();
        int currentLevelNumber = GameManager.getCurrentLevel().getLevelNumber();
        GameManager.setCurrentLevelNumber(currentLevelNumber + 1);
        getScreen().setNextScreen(new GameScreen());
        getScreen().setFinished(true);
    }

    /**
     * Returns to the main menu
     */
    public void MainMenu() {
        PersistenceManager pm = GameManager.getPersistenceManager();
        pm.deleteSaveForCurrentLevel();
        getScreen().setNextScreen(new MainMenuScreen());
        getScreen().setFinished(true);
    }

    /**
     * Retrieves the canvas for the scene. Since this scene does not use a canvas, it returns null.
     *
     * @return null, as no canvas is needed for this scene.
     */
    @Override
    public Canvas getCanvas() {return null;}
}
