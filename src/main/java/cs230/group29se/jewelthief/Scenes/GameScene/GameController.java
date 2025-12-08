package cs230.group29se.jewelthief.Scenes.GameScene;

import cs230.group29se.jewelthief.Scenes.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the game scene in the game.
 * Manages the game canvas and UI elements such as the timer and score labels.
 * Implements the Initializable interface to handle initialization logic.
 *
 * @author Gustas Rove
 */
public class GameController extends BaseController implements Initializable {

    @FXML
    private Label timerLabel;
    @FXML
    private Label scoreLabel;

    /** Canvas for rendering the game graphics. */
    @FXML
    private Canvas gameCanvas;

    /**
     * Initializes the game controller. This method is called automatically
     * after the FXML file has been loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // Initialization logic can be added here if needed.
    }

    /**
     * Retrieves the game canvas used for rendering the game graphics.
     *
     * @return The game canvas.
     */
    @Override
    public Canvas getCanvas() {
        return gameCanvas;
    }
    /**
     * Updates the timer label with the given time string.
     *
     * @param time The time string to display on the timer label.
     */
    public void updateTimerLabel(final String time) {
        timerLabel.setText(time);
    }
    /**
     * Updates the score label with the given score.
     *
     * @param score The score to display on the score label.
     */
    public void updateScoreLabel(final String score) {
        scoreLabel.setText(score);
    }


}
