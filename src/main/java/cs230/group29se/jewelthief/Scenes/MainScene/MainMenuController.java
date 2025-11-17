package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        MainApplication.currentScreen.setFinished(true);
    }
}