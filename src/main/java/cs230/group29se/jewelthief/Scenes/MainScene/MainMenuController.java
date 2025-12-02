package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.MainApplication;
import cs230.group29se.jewelthief.Scenes.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Main Menu scene in the game.
 * Manages the main menu UI and handles user interactions.
 * Implements the Initializable interface to handle initialization logic.
 *
 * @author Gustas Rove
 */
public class MainMenuController extends BaseController implements Initializable {

    /** Label for displaying the welcome text on the main menu. */
    @FXML
    private Label welcomeText;

    /**
     * Handles the event when the "Hello" button is clicked. Test method, will be removed.
     * Updates the welcome text and marks the current screen as finished.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        MainApplication.currentScreen.setFinished(true);
    }

    /**
     * Initializes the Main Menu scene.
     * This method is called when the scene is first loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // No specific initialization logic for this scene.
    }

    /**
     * Retrieves the canvas for the scene. Since this scene does not use a canvas, it returns null.
     *
     * @return null, as no canvas is needed for this scene.
     */
    @Override
    public Canvas getCanvas() {
        return null;
    }
}