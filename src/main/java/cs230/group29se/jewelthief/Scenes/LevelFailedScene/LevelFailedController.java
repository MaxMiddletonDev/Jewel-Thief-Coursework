package cs230.group29se.jewelthief.Scenes.LevelFailedScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import javafx.fxml.FXML;
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

    @FXML
    private Label levelFailedLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private Button retryLevelButton;

    /**
     * Initializes the Level Failed scene by setting the score label to the player's current score.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        scoreLabel.setText(String.valueOf(GameManager.getCurrentLevel().getScore()));
    }

    /**
     * Restarts the current level by transitioning to the GameScreen.
     */
    public void restartLevel() {
        //  Reset level: remove save for current profile+level
        PersistenceManager.deleteSaveForCurrentLevel();

        // New GameScreen, load from txt
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