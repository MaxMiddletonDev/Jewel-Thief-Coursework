package cs230.group29se.jewelthief.Scenes.MainScene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {

    @FXML
    private Button startButton;

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
}