package cs230.group29se.jewelthief.Scenes;

import javafx.scene.Scene;

import java.io.IOException;

abstract public class Screen {
    protected boolean finished = false;
    // Initialize
    abstract public void initialize();

    // Update, Logic happens here
    abstract public void update();

    // Draw, Rendering happens here
    abstract public void draw();

    // IsFinished bool
    public boolean isFinished(){
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
