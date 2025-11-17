package cs230.group29se.jewelthief;

/**
 * Allows for the creation of items that can be
 * collected and removed from the level.
 * Will implement the interface Remove when it is made.
 * @author Charlie
 * @version 1.0
 */
public abstract class Collectable extends Item implements Remove {

    /**
     * Allows for a collectable to be made.
     * @param position Position of the collectable item.
     */
    public Collectable(final int[] position) {
        super(position);
    }

    /**
     * Interact is used when it is picked up and its behavior is
     * different based on the child class implementing it.
     * will be removed at some point after collection.
     */
    public abstract void interact();
}
