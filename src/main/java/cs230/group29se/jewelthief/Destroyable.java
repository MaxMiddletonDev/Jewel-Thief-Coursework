package cs230.group29se.jewelthief;

/**
 * Destroyable allows its children to be destroyed.
 * Destroyable implements Remove for collected items,
 * and those being destroyed.
 * @author Charlie
 * @version 1.0
 */
public abstract class Destroyable extends Item {

    /**
     * Allows for a destroyable item to be created with a position in level.
     * @param position the position of the destroyable item.
     */
    public Destroyable(final int[] position) {
        super(position);
    }

    /**
     * Interact is used when it is picked up and its behavior is
     *  different based on the child class implementing it.
     */
    public abstract void interact();
}
