package cs230.group29se.jewelthief.Scenes.LevelFailedScene;

import cs230.group29se.jewelthief.Game.GameManager;
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
 * Controller class for the Level Failed scene.
 * Manages the UI elements and handles user interactions such as retrying the level
 * or returning to the main menu.
 *
 * @author Gustas Rove
 */
public class LevelFailedController extends BaseController implements Initializable {

    /** Label for displaying the "Level Failed" message. */
    public Label levelFailedLabel;

    /** Label for displaying the player's score. */
    public Label scoreLabel;

    /** Button for navigating back to the main menu. */
    public Button backToMainMenuButton;

    /** Button for retrying the current level. */
    public Button retryLevelButton;

    /**
     * Initializes the Level Failed scene by setting the score label to the player's current score.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreLabel.setText(String.valueOf(GameManager.getCurrentLevel().getScore()));
    }

    /**
     * Restarts the current level by transitioning to the GameScreen.
     */
    public void restartLevel() {
        // Check how much time was left when we failed
        long timeLeftSec = GameManager.getCurrentLevel().getTimeRemainingTimeInSeconds();

        if (timeLeftSec <= 0) {
            // Time-out failure: remove the save so next load is fresh
            PersistenceManager pm = GameManager.getPersistenceManager();
            pm.deleteSaveForCurrentLevel();
        }
        // For non-timeout failures, we keep the save as-is

        // Transition to a new GameScreen; GameScreen.initialize()
        // will call GameManager.loadLevelForProfile, which will either
        // use the remaining save or reload fresh if we deleted it.
        getScreen().setNextScreen(new GameScreen());
        getScreen().setFinished(true);
    }


    /**
     * Returns to the main menu by transitioning to the MainMenuScreen.
     */
    public void backToMainMenu() {
        getScreen().setNextScreen(new MainMenuScreen());
        getScreen().setFinished(true);
    }

    /**
     * Retrieves the canvas for the scene. Since this scene does not use a canvas, it returns null.
     *
     * @return null, as no canvas is needed for this scene.
     */
    @Override
    public Canvas getCanvas() {
        return null;
    }
}