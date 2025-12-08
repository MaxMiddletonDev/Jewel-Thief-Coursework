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
        setNextScreen(new LevelSelectScreen());
    }

    /**
     * Initializes the Main Menu screen.
     * This method is called when the screen is first loaded.
     * It retrieves the selected profile name from the GameManager.
     */
    @Override
    public void onInitialize() {

//        PersistenceManager.setActiveProfileName(GameManager.getSelectedProfileName());
        PersistenceManager.loadProfile(GameManager.getSelectedProfileName());

        String selectedProfile = GameManager.getSelectedProfileName();
        ((MainMenuController)getController()).setProfileNameLabelText(selectedProfile);

        SkinId skinId = SkinId.fromString(GameManager.getSelectedPlayerSkinId());
        Image skinImage = SkinRegistry.getById(skinId).getImage();
        ((MainMenuController)getController()).setEquippedSkinImage(skinImage);
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


    public enum NextAction {START_GAME, SHOW_HIGHSCORES, SHOW_ACHIEVEMENTS}


    public void onStartClicked() {
        setNextScreen(new LevelSelectScreen());
        setFinished(true);
    }
    public void onHighScoresClicked() {
        setNextScreen(new HighScoresScreen());
        setFinished(true);
    }

    public void onAchievementsClicked() {
        setNextScreen(new AchievementsScreen());
        setFinished(true);
    }

//    @Override
//    public Scene createScene() {
//        try {
//            FXMLLoader loader = new FXMLLoader(
//                    getClass().getResource("/cs230/group29se/jewelthief/game-view.fxml")
//            );
//            root = loader.load();
//            controller = loader.getController();
//            // Binds controller to this screen
//            controller.setScreen(this);
//
//            // Use the controllers canvas as this Screen's canvas
//            Canvas gameCanvas = controller.getCanvas();
//            setCanvas(gameCanvas);
//            if (gameCanvas != null) {
//                setGraphicsContext(gameCanvas.getGraphicsContext2D());
//            }
//
//            scene = new Scene(root, 1028, 900);
//            return scene;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


}