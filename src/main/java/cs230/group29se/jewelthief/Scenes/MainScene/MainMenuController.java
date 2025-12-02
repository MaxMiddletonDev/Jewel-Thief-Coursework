package cs230.group29se.jewelthief.Scenes.MainScene;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MainMenuController {

    @FXML
    private Button startButton;

    @FXML
    private Button creditsButton;

    @FXML
    private Button quitButton;

    private MainMenuScreen screen;

    public void setScreen(MainMenuScreen screen) {
        this.screen = screen;
    }

    @FXML
    private void handleStartClicked() {
        if (screen != null) {
            screen.onStartClicked();
        }
    }

    @FXML
    private Button highScoresButton;

    @FXML
    private void handleHighScoresClicked() {
        if (screen != null) {
            screen.onHighScoresClicked();
        }
    }

    @FXML
    private void handleCreditsClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credits");
        alert.setHeaderText("Jewel Thief — Contributors");

        //TODO get everyone to put their name here!!
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
        // Cleanly exit the application
        Platform.exit();
    }
}