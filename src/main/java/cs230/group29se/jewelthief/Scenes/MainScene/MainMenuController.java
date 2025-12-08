package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.Scenes.AchievementsScene.AchievementsScreen;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.EquipablesScene.EquipablesScreen;
import cs230.group29se.jewelthief.Scenes.HighScoresScene.HighScoresScreen;
import cs230.group29se.jewelthief.Scenes.LevelSelectScene.LevelSelectScreen;
import cs230.group29se.jewelthief.Scenes.ProfileScene.ProfileSelectScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Main Menu screen.
 * Handles user interactions and navigation to other screens.\
 *
 * @author Gustas Rove, Iyaad
 */
public class MainMenuController extends BaseController implements Initializable {

    public static final String CREDITS = "Credits";
    public static final String CREDITS_HEADER = "Jewel Thief — Contributors";
    public static final String CREDITS_CONTENTS = """
            Developed by
            - Rove Gustas
            - Poole Ben
            - Middleton Max
            - Hilborne Charlie;;
            - Syahrizal Amsyar
            - Traore Mouhamadou Baba
            - Al Assem Hamza
            - Oritsetsewundede Christiana
            - Meng Shaohua
            """;
    @FXML
    private Button achievementsButton;
    @FXML
    private Button equipablesButton;
    @FXML
    private Button selectProfileButton;
    @FXML
    private ImageView equippedSkinImage;
    @FXML
    private Label profileNameLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button highScoresButton;
    @FXML
    private Button creditsButton;
    @FXML
    private Button quitButton;

    /**
     * Returns the canvas associated with this screen.
     * This screen does not use a canvas, so it returns null.
     *
     * @return null
     */
    @Override
    public Canvas getCanvas() {
        return null; // this screen has no Canvas
    }

    /**
     * Handles the "Start" button click event.
     * Transitions to the Level Select screen.
     */
    @FXML
    private void handleStartClicked() {
        getScreen().setNextScreen(new LevelSelectScreen());
        getScreen().setFinished(true);
    }

    /**
     * Handles the "High Scores" button click event.
     * Transitions to the High Scores screen.
     */
    public void handleHighScoresClicked() {
        getScreen().setNextScreen(new HighScoresScreen());
        getScreen().setFinished(true);
    }

    /**
     * Handles the "Credits" button click event.
     * Displays an alert with the credits' information.
     */
    @FXML
    private void handleCreditsClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(CREDITS);
        alert.setHeaderText(CREDITS_HEADER);

        String content = CREDITS_CONTENTS;

        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Handles the "Quit" button click event.
     * Exits the application.
     */
    @FXML
    private void handleQuitClicked() {
        Platform.exit();
    }

    /**
     * Handles the "Achievements" button click event.
     * Transitions to the Achievements screen.
     *
     * @param actionEvent the event triggered by the button click
     */
    public void handleAchievementsClicked(ActionEvent actionEvent) {
        getScreen().setNextScreen(new AchievementsScreen());
        getScreen().setFinished(true);
    }

    /**
     * Handles the "Equipables" button click event.
     * Transitions to the Equipables screen.
     *
     * @param actionEvent the event triggered by the button click
     */
    public void handleEquipablesClicked(ActionEvent actionEvent) {
        getScreen().setNextScreen(new EquipablesScreen());
        getScreen().setFinished(true);
    }

    /**
     * Handles the "Select Profile" button click event.
     * Transitions to the Profile Select screen.
     *
     * @param actionEvent the event triggered by the button click
     */
    public void selectProfileClicked(ActionEvent actionEvent) {
        getScreen().setNextScreen(new ProfileSelectScreen());
        getScreen().setFinished(true);
    }

    /**
     * Initializes the controller after the FXML file has been loaded.
     *
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic if needed
    }

    /**
     * Sets the text of the profile name label.
     *
     * @param text the text to set
     */
    public void setProfileNameLabelText(String text) {
        profileNameLabel.setText(text);
    }

    /**
     * Sets the image of the equipped skin.
     *
     * @param skinImage the image to set
     */
    public void setEquippedSkinImage(Image skinImage) {
        equippedSkinImage.setImage(skinImage);
    }
}
