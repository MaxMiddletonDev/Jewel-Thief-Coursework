package cs230.group29se.jewelthief.Scenes.GameScene;

import cs230.group29se.jewelthief.Scenes.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends BaseController implements Initializable {

    @FXML
    public Label timerLabel;
    @FXML
    public Label scoreLabel;
    @FXML
    Canvas gameCanvas;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public Canvas getCanvas() {
        return gameCanvas;
    }
}
