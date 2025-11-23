package cs230.group29se.jewelthief.Scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

abstract public class Screen {
    protected Pane root;
    protected Scene scene;
    protected boolean finished = false;
    // Initialize
    public abstract void initialize();

    // Update, Logic happens here
    public abstract void update();

    // Draw, Rendering happens here
    public abstract void draw();

    // IsFinished bool
    public boolean isFinished(){
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    public abstract Scene createScene();
}
