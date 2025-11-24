package cs230.group29se.jewelthief;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Allows for a clock to be created which change the time left in a level.
 * @author Charlie
 * @version 0.2 - Interact is not implemented - Needs level to be implemented.
 */
public class Clock extends Destroyable {

    /**
     * The amount time will change by when picked up by a player,
     * floor thief, or smart thief.
     */
    private static final int TIME_CHANGE = 5;

    private final Image image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/CLOCK.png").toString());;
    /**
     * Allows for a clock to be created at a position in the level.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Clock(final int x, final int y) {
        super(x, y);
    }

    /**
     * Changes the time left in the level.
     * Needs timer to be implemented before it can be created.
     */
    public void interact() {

    }


    /**
     * Draws this clock onto the screen at its x,y.
     * @param gc the class the clock will be drawn with.
     */
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, getX()*64, getY()*64);
    }
}
