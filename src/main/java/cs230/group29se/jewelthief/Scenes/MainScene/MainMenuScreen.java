package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.Scenes.AchievementsScene.AchievementsScreen;
import cs230.group29se.jewelthief.Cosmetics.SkinId;
import cs230.group29se.jewelthief.Cosmetics.SkinRegistry;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.HighScoresScene.HighScoresScreen;
import cs230.group29se.jewelthief.Scenes.LevelSelectScene.LevelSelectScreen;
import cs230.group29se.jewelthief.Scenes.ProfileScene.ProfileSelectScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import static cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager.loadProfile;

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
    }

    /**
     * Initializes the Main Menu screen.
     * This method is called when the screen is first created.
     * It loads the selected profile data and updates the profile name label
     * and equipped skin image in the Main Menu controller.
     */
    @Override
    public void onInitialize() {

        //Load selected profile data into PersistenceManager
        PersistenceManager.loadProfile(GameManager.getSelectedProfileName());

        //Update profile name label
        String selectedProfile = GameManager.getSelectedProfileName();
        ((MainMenuController) getController()).setProfileNameLabelText(selectedProfile);

        //Update equipped skin image
        SkinId skinId = SkinId.fromString(GameManager.getSelectedPlayerSkinId());
        Image skinImage = SkinRegistry.getById(skinId).getImage();
        ((MainMenuController) getController()).setEquippedSkinImage(skinImage);
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