package cs230.group29se.jewelthief.Scenes.AchievementsScene;

import cs230.group29se.jewelthief.Game.Achievements;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Achievements screen.
 * Handles the display of achievements and user interactions.
 *
 * @author Max Middleton
 */
public class AchievementsController extends BaseController implements Initializable {

    // FXML-injected container for displaying achievements
    @FXML private VBox achievementsContainer;

    /**
     * Returns the canvas associated with this screen.
     * This screen does not use a canvas, so it returns null.
     *
     * @return null
     */
    @Override
    public Canvas getCanvas() {
        return null;
    }

    /**
     * Loads and displays the achievements in the achievements container.
     * Achievements are displayed with their icons, names, and descriptions.
     * Locked achievements are displayed with reduced opacity.
     */
    public void loadAchievements() {
        // Clear the container before loading achievements
        achievementsContainer.getChildren().clear();

        // Retrieve the list of unlocked achievements for the current profile
        List<String> unlocked = PersistenceManager
                .getCurrentProfile().getUnlockedAchievements();

        // Iterate through all achievements and display them
        for (Achievements a : Achievements.values()) {
            boolean isUnlocked = unlocked.contains(a.name());

            // Create a row for each achievement
            HBox row = new HBox(15);
            row.setAlignment(Pos.CENTER_LEFT);

            // Create an icon for the achievement
            ImageView icon = new ImageView(new Image(getClass().getResource(a.getIconPath()).toString()));
            icon.setFitWidth(64);
            icon.setFitHeight(64);

            // Create a label for the achievement name and description
            Label text = new Label(a.getName() + ": " + a.getDescription());
            text.setWrapText(true);

            // Add the icon and text to the row
            row.getChildren().addAll(icon, text);

            // Set reduced opacity for locked achievements
            if (!isUnlocked) {
                row.setOpacity(0.5);
            }

            // Add the row to the achievements container
            achievementsContainer.getChildren().add(row);
        }
    }

    /**
     * Handles the "Back" button click event.
     * Navigates back to the previous screen.
     */
    @FXML
    private void handleBackClicked() {
        if (getScreen() instanceof AchievementsScreen s) s.onBackClicked();
    }

    /**
     * Initializes the controller after the FXML file has been loaded.
     * Loads the achievements into the achievements container.
     *
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAchievements();
    }
}
