/**
 * Item is an abstract class that allows it's children to be made with a location.
 * @version 1.0
 * @author Charlie
 */

public abstract class Item {
    /**
     * Where the item is positioned in a level.
     */
    private final int[] POSITION;
    
    /**
     * Allows an item to be created and its location in the level.
     * @param position where the item is located.
     */
    public Item(int[] position) {
        POSITION = position;
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
    public int[] getPosition() {
        return POSITION;
    }
}
