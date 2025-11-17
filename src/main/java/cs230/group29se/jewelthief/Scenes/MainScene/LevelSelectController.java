package cs230.group29se.jewelthief.Scenes.MainScene;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LevelSelectController {
    @FXML
    private Label levelSelectText;

    @FXML
    protected void onLevelSelectionButtonClick() {
        levelSelectText.setText("Welcome to the level select screen!");
    }
}