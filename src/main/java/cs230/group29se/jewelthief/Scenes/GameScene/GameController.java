package cs230.group29se.jewelthief.Scenes.GameScene;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    public Label timerLabel;
    @FXML
    public Label scoreLabel;
    @FXML
    Canvas gameCanvas;

    private GameScreen screen;

    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
