package cs230.group29se.jewelthief;

import javafx.scene.canvas.GraphicsContext;

/**
 * Allows for the creation of items that can be
 * collected and removed from the level.
 * Will implement the interface Remove when it is made.
 * @author Charlie
 * @version 1.1
 */
public abstract class Collectable extends Item implements Remove {

    /**
     * Allows for a collectable to be made.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Collectable(final int x, final int y) {
        super(x, y);
    }

    /**
     * Interact is used when it is picked up and its behavior is
     * different based on the child class implementing it.
     * will be removed at some point after collection.
     */
    public abstract void interact();

    public abstract void draw(GraphicsContext gc);
}
