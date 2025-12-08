package cs230.group29se.jewelthief.Scenes.ProfileScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import static cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager.loadProfile;

/**
 * Represents the profile selection screen in the game.
 * Allows the user to select a profile or go back to the main menu.
 *
 * @author Iyaad
 */
public class ProfileSelectScreen extends Screen {
    /**
     * Constructs a ProfileSelectScreen and sets its title, FXML path, and next screen.
     */
    public ProfileSelectScreen() {
        setScreenTitle("Select Profile");
        setScreenFXMLPath("/cs230/group29se/jewelthief/profile-select-view.fxml");
        setNextScreen(new MainMenuScreen());
    }

    /**
     * Initializes the profile selection screen.
     * Populates the list of profiles if the controller is an instance of ProfileSelectController.
     */
    @Override
    public void onInitialize() {
        if (getController() instanceof ProfileSelectController c) {
            c.populateProfiles();
        }
    }

    /**
     * Updates the profile selection screen.
     * Currently, no update logic is implemented.
     */
    @Override
    public void update() {
    }

    /**
     * Draws the profile selection screen.
     * Currently, no drawing logic is implemented.
     */
    @Override
    public void draw() {
    }

    /**
     * Handles the event when a profile is chosen.
     * Sets the selected profile name in the GameManager, loads the profile, and marks the screen as finished.
     *
     * @param profileName The name of the selected profile.
     */
    public void onProfileChosen(String profileName) {
        GameManager.setSelectedProfileName(profileName);
        loadProfile(profileName);
        setFinished(true);
    }

    /**
     * Handles the event when the back button is clicked.
     * Marks the screen as finished to transition to the previous screen.
     */
    public void onBackClicked() {
        setFinished(true);
    }

}
