package cs230.group29se.jewelthief;

import javafx.scene.canvas.GraphicsContext;

/**
 * Allows for the creation of items that can be
 * collected and removed from the level.
 * Will implement the interface Remove when it is made.
 * @author Charlie, Hamza
 * @version 1.2 - ADDED: collector tracking (who picked up the item).
 */
public abstract class Collectable extends Item implements Remove {

    /**
     * The character that collected this item (Player or NPC).
     */
    protected MoveableCharacter collector;

    /**
     * Allows for a collectable to be made.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Collectable(final int x, final int y) {
        super(x, y);
    }

    /**
     * Sets the character that collected this item.
     * @param collector the MoveableCharacter that picked up this item
     */
    public void setCollector(MoveableCharacter collector) {
        this.collector = collector;
    }

    /**
     * Gets the character that collected this item.
     * @return the MoveableCharacter that picked up this item
     */
    public MoveableCharacter getCollector() {
        return collector;
    }

    /**
     * Interact is used when it is picked up and its behavior is
     * different based on the child class implementing it.
     * Will be removed at some point after collection.
     */
    public abstract void interact();

    public abstract void draw(GraphicsContext gc);
}
