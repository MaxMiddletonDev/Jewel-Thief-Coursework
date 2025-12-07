package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MainMenuController extends BaseController {

    public Button achievementsButton;
    public Button equipablesButton;
    public Button selectProfileButton;
    public ImageView equippedSkinImage;
    public Label profileNameLabel;
    @FXML
    private Button startButton;

    @FXML
    private Button highScoresButton;

    @FXML
    private Button creditsButton;

    @FXML
    private Button quitButton;

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
                - Iyaad
                - Christina
                - Meng
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
    }

    public void handleEquipablesClicked(ActionEvent actionEvent) {

    }

    public void selectProfileClicked(ActionEvent actionEvent) {
    }
}