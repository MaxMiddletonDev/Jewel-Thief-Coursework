package cs230.group29se.jewelthief.Scenes;

import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import javafx.scene.canvas.Canvas;

public abstract class BaseController {
    private Screen screen;

    /** Get the canvas associated with this controller.
     *  Note, some controllers may not have a canvas, in which case this can return null.
     * @return the Canvas object
     */
    public abstract Canvas getCanvas();

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }
}
