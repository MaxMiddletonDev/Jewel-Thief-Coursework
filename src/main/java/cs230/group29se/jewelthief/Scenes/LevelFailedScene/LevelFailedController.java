package cs230.group29se.jewelthief.Scenes.LevelFailedScene;

import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelFailedController extends BaseController implements Initializable {
    public Label levelFailedLabel;
    public Label scoreLabel;
    public Button backToMainMenuButton;
    public Button retryLevelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void restartLevel() {
        getScreen().setNextScreen(new GameScreen());
        getScreen().setFinished(true);
    }

    public void backToMainMenu() {
        getScreen().setNextScreen(new MainMenuScreen());
        getScreen().setFinished(true);
    }



    @Override
    public Canvas getCanvas() {
        return null;// No canvas needed for this scene
    }
}
