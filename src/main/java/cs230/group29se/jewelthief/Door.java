package cs230.group29se.jewelthief;

/**
 * Door allows a player or thief to leave the level when open.
 * @author Charlie
 * @version 0.1 - interact is not implemented - needs game before implementation.
 */
public class Door extends Item {

    /**
     * Door starts closed till all loot is gone.
     */
    private boolean closed = true;

    /**
     * Allows for a door to be added to a level.
     * @param position Where the door is located in the level.
     */
    public Door(final int[] position) {
        super(position);
    }

    /**
     * Ends the level if open and the player wins or loses
     * depending on what entered the door, player is a win,
     * floor or smart thief is a loss.
     * needs thief, player, and game to be implemented.
     */
    public void interact() {

    }

    /**
     * Gets if the door is closed (true) or open (false).
     * @return the state of the door.
     */
    public boolean getClosed() {
        return closed;
    }

    /**
     * Sets the state of the door to closed (true) or open (false).
     * @param closed closed gets set to true (closed) or false (open).
     */
    public void setClosed(final boolean closed) {
        this.closed = closed;
    }
}
