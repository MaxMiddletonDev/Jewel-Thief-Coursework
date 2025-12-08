package cs230.group29se.jewelthief.Scenes.AchievementsScene;

import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;

/**
 * Represents the Achievements screen in the game.
 * This screen displays the player's achievements and allows navigation back to the main menu.
 *
 * @author Max Middleton
 */
public class AchievementsScreen extends Screen {

    public static final String ACHIEVEMENTS = "Achievements";
    public static final String ACHIEVEMENTS_XML_PATH = "/cs230/group29se/jewelthief/achievements-view.fxml";

    /**
     * Constructs a new AchievementsScreen instance.
     * Sets the screen title and the FXML path for the achievements view.
     */
    public AchievementsScreen() {
        setScreenTitle(ACHIEVEMENTS); // Sets the title of the screen
        setScreenFXMLPath(ACHIEVEMENTS_XML_PATH); // Path to the FXML file
    }

    /**
     * Called when the screen is initialized.
     * Loads the achievements data using the controller.
     */
    @Override
    public void onInitialize() {
        if (getController() instanceof AchievementsController c) {
            c.loadAchievements(); // Loads the achievements data
        }
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

    /**
     * Handles the back button click event.
     * Marks the screen as finished, allowing navigation back to the previous screen.
     */
    public void onBackClicked() {
        finished = true; // Marks the screen as finished
    }

    /**
     * Returns the next screen to navigate to.
     * In this case, it navigates back to the Main Menu screen.
     *
     * @return the next screen, which is the Main Menu screen
     */
    @Override
    public Screen getNextScreen() {
        return new MainMenuScreen(); // Returns the Main Menu screen
    }
}
