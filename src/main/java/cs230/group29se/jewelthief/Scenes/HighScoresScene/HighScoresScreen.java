package cs230.group29se.jewelthief.Scenes.HighScoresScene;

import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;

/**
 * Represents the High Scores screen in the game.
 * This screen displays the high scores and allows navigation back to the main menu.
 *
 * @author Iyaad, Gustas Rove
 */
public class HighScoresScreen extends Screen {

    /**
     * Constructs a new HighScoresScreen instance.
     * Sets the screen title, FXML path, and the next screen to navigate to.
     */
    public HighScoresScreen() {
        setScreenTitle("High Scores"); // Sets the title of the screen
        setScreenFXMLPath("/cs230/group29se/jewelthief/highscores-view.fxml"); // Path to the FXML file
        setNextScreen(new MainMenuScreen()); // Sets the next screen to the main menu
    }

    /**
     * Called when the screen is initialized.
     * Loads the high scores data using the controller.
     */
    @Override
    public void onInitialize() {
        ((HighScoresController)getController()).loadData(); // Loads high scores data
    }

    /**
     * Updates the screen. Currently, no update logic is implemented.
     */
    @Override
    public void update() {
        // No update logic required for this screen
    }

    /**
     * Draws the screen. Currently, no drawing logic is implemented.
     */
    @Override
    public void draw() {
        // No drawing logic required for this screen
    }

}
