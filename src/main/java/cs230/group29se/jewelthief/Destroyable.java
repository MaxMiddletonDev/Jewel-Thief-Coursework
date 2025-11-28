package cs230.group29se.jewelthief;

import javafx.scene.canvas.GraphicsContext;

/**
 * Destroyable allows its children to be destroyed.
 * Destroyable inherits collectable for Remove for collected items,
 * and those being destroyed.
 * @author Charlie
 * @version 1.1
 */
public abstract class Destroyable extends Collectable {

    /**
     * Allows for a destroyable item to be created with a position in level.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Destroyable(final int x, final int y) {
        super(x, y);
    }

    /**
     * Interact is used when it is picked up and its behavior is
     *  different based on the child class implementing it.
     */
    public abstract void interact();

    public abstract void draw(GraphicsContext gc);
}
