package cs230.group29se.jewelthief.Scenes.LevelSelectScene;

import cs230.group29se.jewelthief.MainApplication;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents the Level Select screen in the game.
 * This screen allows the player to select a level to play.
 * It extends the Screen class and provides methods for initialization, updates, and drawing.
 *
 * @author Gustas Rove
 */
public class LevelSelectScreen extends Screen {

    /**
     * Constructs a LevelSelectScreen and sets its title, FXML path, and the next screen.
     * The screen title is set to "Level Select", and the FXML file path is specified.
     * The next screen is set to the GameScreen.
     */
    public LevelSelectScreen() {
        setScreenTitle("Level Select");
        setScreenFXMLPath("/cs230/group29se/jewelthief/level-select-view.fxml");
        setNextScreen(new GameScreen());
    }

    /**
     * Initializes the Level Select screen.
     * This method is called when the screen is first loaded.
     * Currently, no specific initialization logic is implemented.
     */
    @Override
    public void initialize() {
        // No initialization logic for this screen.
    }

    /**
     * Updates the Level Select screen.
     * This method is called periodically to update the screen's state.
     * Currently, no specific update logic is implemented.
     */
    @Override
    public void update() {
        // No update logic for this screen.
    }

    /**
     * Draws the Level Select screen.
     * This method is called to render the screen's visuals.
     * Currently, no specific drawing logic is implemented.
     */
    @Override
    public void draw() {
        // No drawing logic for this screen.
    }
}