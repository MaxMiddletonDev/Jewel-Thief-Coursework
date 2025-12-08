package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.Cosmetics.SkinId;
import cs230.group29se.jewelthief.Cosmetics.SkinRegistry;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Scenes.AchievementsScene.AchievementsScreen;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.EquipablesScene.EquipablesScreen;
import cs230.group29se.jewelthief.Scenes.ProfileScene.ProfileSelectScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
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

public class MainMenuController extends BaseController implements Initializable {

    public Button achievementsButton;
    public Button equipablesButton;
    public Button selectProfileButton;
    public ImageView equippedSkinImage;
    public Label profileNameLabel;
    @FXML
    public Button startButton;

    @FXML
    public Button highScoresButton;

    @FXML
    public Button creditsButton;

    @FXML
    public Button quitButton;

    @Override
    public Canvas getCanvas() {
        return null; // this screen has no Canvas
    }

    @FXML
    private void handleStartClicked() {
        Screen s = getScreen();          // comes from BaseController
        if (s instanceof MainMenuScreen menuScreen) {
            GameManager.clearCurrentLevel();
            menuScreen.onStartClicked();
        } else {
            System.out.println("Start clicked but screen is " + s);
        }
    }

    @FXML
    private void handleHighScoresClicked() {
        Screen s = getScreen();
        if (s instanceof MainMenuScreen menuScreen) {
            System.out.println("High Scores clicked"); // debug
            menuScreen.onHighScoresClicked();
        }
    }

    @FXML
    private void handleCreditsClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credits");
        alert.setHeaderText("Jewel Thief — Contributors");

        String content = """
                Developers:
                - Gustas Rove
                - Stuart
                - Dave
                - Kevin
                - Bob
                - Tim
                - Dr. Nefario
                - etc..

                Coursework 3 Student Project!!
                """;

        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleQuitClicked() {
        Platform.exit();
    }

    public void handleAchievementsClicked(ActionEvent actionEvent) {
        getScreen().setNextScreen(new AchievementsScreen());
        getScreen().setFinished(true);
    }

    public void handleEquipablesClicked(ActionEvent actionEvent) {
        getScreen().setNextScreen(new EquipablesScreen());
        getScreen().setFinished(true);
    }

    public void selectProfileClicked(ActionEvent actionEvent) {
        getScreen().setNextScreen(new ProfileSelectScreen());
        getScreen().setFinished(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    public void setProfileNameLabelText(String text) {
        profileNameLabel.setText(text);
    }

    public void setEquippedSkinImage(Image skinImage) {
        equippedSkinImage.setImage(skinImage);
    }

}