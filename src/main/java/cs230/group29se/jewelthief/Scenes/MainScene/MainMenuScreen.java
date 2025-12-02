package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.MainApplication;
import cs230.group29se.jewelthief.Scenes.LevelSelectScene.LevelSelectScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Represents the Main Menu screen in the game.
 * This screen is displayed as the starting point of the game, allowing the player
 * to navigate to other parts of the application, such as the Level Select screen.
 * It extends the Screen class and provides methods for initialization, updates, and drawing.
 *
 * @author Gustas Rove
 */
public class MainMenuScreen extends Screen {

    /**
     * Constructs a MainMenuScreen and sets its title, FXML path, and the next screen.
     * The screen title is set to "Main Menu", and the FXML file path is specified.
     * The next screen is set to the LevelSelectScreen.
     */
    public MainMenuScreen() {
        setScreenTitle("Main Menu");
        setScreenFXMLPath("/cs230/group29se/jewelthief/main-view.fxml");
        setNextScreen(new LevelSelectScreen());
    }

    /**
     * Initializes the Main Menu screen.
     * This method is called when the screen is first loaded.
     * Currently, no specific initialization logic is implemented.
     */
    @Override
    public void initialize() {
        // No initialization logic for this screen.
    }

    /**
     * Updates the Main Menu screen.
     * This method is called periodically to update the screen's state.
     * Currently, no specific update logic is implemented.
     */
    @Override
    public void update() {
        // No update logic for this screen.
    }

    /**
     * Draws the Main Menu screen.
     * This method is called to render the screen's visuals.
     * Currently, no specific drawing logic is implemented.
     */
    @Override
    public void draw() {
        // No drawing logic for this screen.
    }
}