package cs230.group29se.jewelthief.Scenes.LevelFinished;

import cs230.group29se.jewelthief.Scenes.Screen;

/**
 * Represents the "Level Finished" screen in the game.
 * This screen is displayed when the player completes a level.
 *
 * @author Ben Poole
 */
public class LevelFinishedScreen extends Screen {

    /**
     * Constructs a LevelFinishedScreen and sets its title and FXML path.
     * The screen title is set to "Level Finished" and the FXML path is specified.
     */
    public LevelFinishedScreen() {
        setScreenTitle("Level Finished");
        setScreenFXMLPath("/cs230/group29se/jewelthief/level-finished.fxml");
    }

    /**
     * Initializes the "Level Finished" screen.
     * This method is called when the screen is first loaded.
     * Currently, no specific initialization logic is implemented.
     */
    @Override
    public void onInitialize() {
        // No initialization logic for this screen.
    }

    /**
     * Updates the "Level Finished" screen.
     * This method is called periodically to update the screen's state.
     * Currently, no specific update logic is implemented.
     */
    @Override
    public void update() {
        //no update logic for LevelFinishedScreen
    }

    /**
     * Draws the "Level Finished" screen.
     * This method is called to render the screen's visuals.
     * Currently, no specific drawing logic is implemented.
     */
    @Override
    public void draw() {
        //no drawing logic for LevelFinishedScreen
    }
}
