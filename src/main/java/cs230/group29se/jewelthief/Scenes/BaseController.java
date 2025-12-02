package cs230.group29se.jewelthief.Scenes;

import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import javafx.scene.canvas.Canvas;

/**
 * Abstract base class for all controllers in the game.
 * Provides common functionality for managing the associated screen and retrieving the canvas.
 *
 * @author Gustas Rove
 */
public abstract class BaseController {
    private Screen screen;

    /**
     * Retrieves the canvas associated with this controller.
     * Note: Some controllers may not have a canvas, in which case this method can return null.
     *
     * @return the Canvas object, or null if no canvas is associated with this controller.
     */
    public abstract Canvas getCanvas();

    /**
     * Sets the screen associated with this controller.
     *
     * @param screen the Screen object to associate with this controller.
     */
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    /**
     * Retrieves the screen associated with this controller.
     *
     * @return the Screen object associated with this controller.
     */
    public Screen getScreen() {
        return screen;
    }
}