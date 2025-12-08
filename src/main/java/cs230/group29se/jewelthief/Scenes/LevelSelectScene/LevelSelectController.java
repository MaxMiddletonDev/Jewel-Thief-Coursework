package cs230.group29se.jewelthief.Scenes.LevelSelectScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.SoundManager;
import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Level Select scene in the game.
 * Manages the level selection buttons and handles user interactions to select a level.
 * Implements the Initializable interface to handle initialization logic.
 *
 * @author Gustas Rove
 */
public class LevelSelectController extends BaseController implements Initializable {

    @FXML
    private Button level1Button;
    @FXML
    private Button level2Button;
    @FXML
    private Button level4Button;
    @FXML
    private Button level5Button;
    @FXML
    private Button level3Button;
    @FXML
    private Button level6Button;
    @FXML
    private Button level7Button;
    @FXML
    private Button level8Button;
    @FXML
    private Button level9Button;
    @FXML
    private Button level10Button;
    @FXML
    private Button backButton;

    /**
     * Handles the event when a level button is clicked.
     * Sets the current level number in the GameManager and marks the screen as finished.
     *
     * @param e The ActionEvent triggered by the button click.
     */
    @FXML
    protected void selectLevel(ActionEvent e) {
        SoundManager.playStart();
        Button b = (Button) e.getSource();
        int level = Integer.parseInt(b.getUserData().toString());
        GameManager.setCurrentLevelNumber(level);
        getScreen().setFinished(true);
    }

    /**
     * Initializes the Level Select scene by enabling or disabling level buttons
     * based on whether the levels are unlocked.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Check save file and enable/disable buttons accordingly
        level1Button.setDisable(!isLevelUnlocked(1));
        level2Button.setDisable(!isLevelUnlocked(2));
        level3Button.setDisable(!isLevelUnlocked(3));
        level4Button.setDisable(!isLevelUnlocked(4));
        level5Button.setDisable(!isLevelUnlocked(5));
        level6Button.setDisable(!isLevelUnlocked(6));
        level7Button.setDisable(!isLevelUnlocked(7));
        level8Button.setDisable(!isLevelUnlocked(8));
        level9Button.setDisable(!isLevelUnlocked(9));
        level10Button.setDisable(!isLevelUnlocked(10));
    }

    /**
     * Placeholder method to determine if a level is unlocked.
     * This method should be replaced with a data persistence method to check the save file.
     *
     * @param levelNumber The level number to check.
     * @return true if the level is unlocked, false otherwise.
     */
    private boolean isLevelUnlocked(int levelNumber) {
        ProfileData data = PersistenceManager.getCurrentProfile();
        int maxLevel = data.getMaxUnlockedLvl();
        return levelNumber <= maxLevel;
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

    /**
     * Handles the event when the back button is clicked.
     * Transitions back to the Main Menu screen.
     */
    @FXML
    public void onBackClicked() {
        getScreen().setNextScreen(new MainMenuScreen());
        getScreen().setFinished(true);
    }
}