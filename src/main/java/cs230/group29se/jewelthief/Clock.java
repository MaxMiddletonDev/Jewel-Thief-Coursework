package cs230.group29se.jewelthief;

/**
 * Allows for a clock to be created which change the time left in a level.
 * @author Charlie
 * @version 0.1 - Interact is not implemented - Needs level to be implemented.
 */
public class Clock extends Destroyable {

    /**
     * The amount time will change by when picked up by a player,
     * floor thief, or smart thief.
     */
    private static final int TIME_CHANGE = 5;

    /**
     * Allows for a clock to be created at a position in the level.
     * @param position position the clock will have.
     */
    public Clock(final int[] position) {
        super(position);
    }

    /**
     * Changes the time left in the level.
     * Needs timer to be implemented before it can be created.
     */
    public void interact() {

    }

    @Override
    public void remove() {

    }
}
