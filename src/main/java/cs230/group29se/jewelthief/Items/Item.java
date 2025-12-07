package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Entities.MoveableCharacter;
import javafx.scene.canvas.GraphicsContext;

/**
 * Item is an abstract class that allows it's children
 * to be made with a location and a collector.
 * @version 1.2
 * @author Charlie
 */

public abstract class Item {
    /**
     * How far across the gate is from the left.
     */
    private final int POS_X;
    /**
     * How far down the gate is from the top.
     */
    private final int POS_Y;
    /**
     * The character that collected this item (Player or NPC).
     */
    protected MoveableCharacter collector;
    /**
     * What moveable character interacted with the item.
     * @param collector the instance which interacted with the item.
     */
    public void setCollector(final MoveableCharacter collector) {
        this.collector = collector;
    }

    /**
     * Gets the character that collected this item.
     * @return the MoveableCharacter that picked up this item.
     */
    public MoveableCharacter getCollector() {
        return collector;
    }

    /**
     * Allows an item to be created and its location in the level.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Item(final int x, final int y) {
        POS_X = x;
        POS_Y = y;
    }
    /**
     * Interact is used when it is picked up and its behavior is
     * different based on the child class implementing it.
     */
    public abstract void interact();

    /**
     * Gets the items x position, how far from the left side it is.
     * @return the x position of the item in the level.
     */
    public int getX() {
        return POS_X;
    }
    /**
     * Gets the items y position, how far down the top item is.
     * @return the y position of the item in the level.
     */
    public int getY() {
        return POS_Y;
    }

    /**
     * Will draw the child at its position.
     * @param gc the class the item will be drawn with.
     */
    public abstract void draw(GraphicsContext gc);
}
