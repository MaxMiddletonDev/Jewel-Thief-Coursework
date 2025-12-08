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

public class AchievementsController extends BaseController implements Initializable {

    @FXML private VBox achievementsContainer;

    @Override
    public Canvas getCanvas() {
        return null;
    }

    public void loadAchievements() {
        achievementsContainer.getChildren().clear();

        List<String> unlocked = PersistenceManager
                .getCurrentProfile().getUnlockedAchievements();

        for (Achievements a : Achievements.values()) {
            boolean isUnlocked = unlocked.contains(a.name());

            HBox row = new HBox(15);
            row.setAlignment(Pos.CENTER_LEFT);

            ImageView icon = new ImageView(new Image(getClass().getResource(a.getIconPath()).toString()));
            icon.setFitWidth(64);
            icon.setFitHeight(64);

            Label text = new Label(a.getName() + ": " + a.getDescription());
            text.setWrapText(true);

            row.getChildren().addAll(icon, text);

            if (!isUnlocked) {
                row.setOpacity(0.5);
            }

            achievementsContainer.getChildren().add(row);
        }
    }

    @FXML
    private void handleBackClicked() {
        if (getScreen() instanceof AchievementsScreen s) s.onBackClicked();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAchievements();
    }
}