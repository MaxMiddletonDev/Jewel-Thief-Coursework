package cs230.group29se.jewelthief.Scenes.LevelSelectScene;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelSelectController implements Initializable {

    @FXML
    public Canvas levelSelectCanvas;
    @FXML
    public Button level1Button;
    @FXML
    private Label levelSelectText;

    private LevelSelectScreen screen;

    public void setScreen(LevelSelectScreen screen) {
        this.screen = screen;
    }

    @FXML
    protected void selectLevel1() {
        levelSelectText.setText("Welcome to the level select screen!");
        screen.setFinished(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Check safe file and enable/disable buttons accordingly
    }
}