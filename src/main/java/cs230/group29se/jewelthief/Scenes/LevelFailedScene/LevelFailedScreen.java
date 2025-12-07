package cs230.group29se.jewelthief.Scenes.LevelFailedScene;

import cs230.group29se.jewelthief.MainApplication;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents the "Level Failed" screen in the game.
 * This screen is displayed when the player fails a level.
 * It extends the Screen class and provides methods for initialization, updates, and drawing.
 *
 * @author Gustas Rove
 */
public class LevelFailedScreen extends Screen {

    /**
     * Constructs a LevelFailedScreen and sets its title and FXML path.
     * The screen title is set to "Level Failed" and the FXML file path is specified.
     */
    public LevelFailedScreen() {
        setScreenTitle("Level Failed");
        setScreenFXMLPath("/cs230/group29se/jewelthief/level-failed-view.fxml");
    }

    /**
     * Initializes the "Level Failed" screen.
     * This method is called when the screen is first loaded.
     * Currently, no specific initialization logic is implemented.
     */
    @Override
    public void onInitialize() {
        // No initialization logic for this screen.
    }

    /**
     * Updates the "Level Failed" screen.
     * This method is called periodically to update the screen's state.
     * Currently, no specific update logic is implemented.
     */
    @Override
    public void update() {
        // No update logic for this screen.
    }

    /**
     * Draws the "Level Failed" screen.
     * This method is called to render the screen's visuals.
     * Currently, no specific drawing logic is implemented.
     */
    @Override
    public void draw() {
        // No drawing logic for this screen.
    }
}