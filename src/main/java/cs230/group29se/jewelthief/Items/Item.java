package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.MoveableCharacter;
import javafx.scene.canvas.GraphicsContext;

/**
 * Item is an abstract class that allows it's children to be made with a location.
 * @version 1.2
 * @author Charlie
 */

public abstract class Item {
    /**
     * Where the item is positioned in a level.
     */
    private final int POS_X;
    private final int POS_Y;

    /**
     * The character that collected this item (Player or NPC).
     */
    protected MoveableCharacter collector;

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
     * Gets the items position.
     * @return the position of the item in the level.
     */
    public int getX() {
        return POS_X;
    }

    public int getY() {
        return POS_Y;
    }


    public abstract void draw(GraphicsContext gc);
}
