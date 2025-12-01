package cs230.group29se.jewelthief.Scenes.LevelSelectScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelSelectController extends BaseController implements Initializable {

    @FXML
    public Button level1Button;
    public Button level2Button;
    public Button level4Button;
    public Button level5Button;
    public Button level3Button;
    public Button level6Button;
    public Button level7Button;
    public Button level8Button;
    public Button level9Button;
    public Button level10Button;


    @FXML
    protected void selectLevel(ActionEvent e) {
        Button b = (Button) e.getSource();
        int level = Integer.parseInt(b.getUserData().toString());
        GameManager.setCurrentLevelNumber(level);
        getScreen().setFinished(true);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Check safe file and enable/disable buttons accordingly
        level1Button.setDisable(!isLevelUnlocked(1));
        level2Button.setDisable(!isLevelUnlocked(2));
        level3Button.setDisable(!isLevelUnlocked(3));
        level4Button.setDisable(!isLevelUnlocked(4));
        level5Button.setDisable(!isLevelUnlocked(5));
        level6Button.setDisable(!isLevelUnlocked(6));
        level7Button.setDisable(!isLevelUnlocked(7));
        level8Button.setDisable(!isLevelUnlocked(8));
        level9Button.setDisable(!isLevelUnlocked(9));
        level10Button.setDisable(!isLevelUnlocked(10));
    }

    /**
     * PLACEHOLDER METHOD. SHOULD BE A DATA PERSISTENCE METHOD
     * Should take in a level number and return whether that level is unlocked in the save file
     */
    private boolean isLevelUnlocked(int levelNumber) {
        if(levelNumber == 1) {
            return true;
        }{
            return false;
        }
    }

    @Override
    public Canvas getCanvas() {
        return null; // No canvas needed for this scene
    }
}