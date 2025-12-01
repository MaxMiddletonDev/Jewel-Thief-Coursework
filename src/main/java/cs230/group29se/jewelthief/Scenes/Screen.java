package cs230.group29se.jewelthief.Scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Abstract class representing a Screen in the application.
 * Each screen must implement methods for initialization, updating, and drawing.
 *
 * @author Gustas Rove
 * @version 1.0
 */
abstract public class Screen {
    protected Screen nextScreen;

    protected Pane root;
    protected Scene scene;
    protected boolean finished = false;

    /**
     * Initialize, Scene Setup happens here
     */
    public abstract void initialize();

    /**
     * Update, Game Logic happens here
     */
    public abstract void update();

    /**
     * Draw, Rendering happens here
     */
    public abstract void draw();

    /**
     * Check if the screen is finished.
     * Used to transition to the next screen.
     * @return true if finished, false otherwise
     */
    public boolean isFinished(){
        return finished;
    }

    /**
     * Set the finished state of the screen.
     * @param finished true if finished, false otherwise
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Screen getNextScreen() {
        return nextScreen;
    }

    /**
     * Create and return the Scene for this screen.
     * @return the Scene object
     */
    public abstract Scene createScene();
}
