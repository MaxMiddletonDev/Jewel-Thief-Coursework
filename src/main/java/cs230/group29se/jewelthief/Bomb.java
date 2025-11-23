package cs230.group29se.jewelthief;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The bomb class destroys certain items in its pathway after being triggered.
 * @author Charlie
 * @version 0.1 - run isn't implemented - needs level and tiles.
 */
public class Bomb extends Destroyable {

    /**
     * The bombs timer used for exploding.
     */
    private final Timer timer = new Timer();

    /**
     * Used to find out how long is left before the bomb explodes. Default is 0.
     */
    private long startTime;

    /**
     * The time left on the bomb, by default is 3000 since all bombs have 3
     * seconds before they explode after being interacted with.
     */
    private long timeRemaining = 3000;

    /**
     * By default, all bombs are not set to explode if you wanted to create
     * a bomb at the start of exploding you'd pass in time remaining as 3000.
     */
    private boolean armed = false;


    /**
     * Creates an active bomb with a location and time left before detonation.
     * @param position where the bomb is located.
     * @param timeRemaining how long left till the bomb detonates.
     */
    public Bomb(final int[] position, final Long timeRemaining) {
        super(position);
        // ensures bomb timer is 3 seconds or smaller.
        if (this.timeRemaining >= timeRemaining) {
            this.timeRemaining = timeRemaining;
        }
        startTime = System.currentTimeMillis();
        timer.schedule(destroy(), this.timeRemaining);
        armed = true;
    }

    /**
     * Creates a dormant bomb with a position.
     * @param position where the bomb is located.
     */
    public Bomb(final int[] position) {
        super(position);
    }


    /**
     * Destroys all destroyable items in the bombs horizontal and vertical path.
     * @return the task the timer will execute when finished.
     */
    public TimerTask destroy() {
        return new TimerTask() {
            /**
             * TODO -when level exists and the array of tiles are made
             * TODO -then run can be implemented.
             */
            @Override
            public void run() {
                System.out.println("testing");
                //this.remove(); // uncomment when remove is defined.
            }
        };
    }


    /**
     * sets a timer to destroy the bomb.
     * can be set to a loop to change sprites.
     */
    public void interact() {
        startTime = System.currentTimeMillis();
        timer.schedule(destroy(), timeRemaining);
        armed = true;
    }

    /**
     * Get if the bomb is armed.
     * @return the state of the bomb.
     */
    public boolean getArmed() {
        return armed;
    }


    /**
     * Get the time left (in milliseconds) before the bomb explodes.
     * @return the time left before the bomb explodes.
     */
    public long getTimeRemaining() {
        // if the bomb is armed startTime must be defined
        if (armed) {
            // Timer doesn't have a time remaining so system time is used.
            return startTime + timeRemaining - System.currentTimeMillis();

        } else {
            // full-timer of the bomb
            return timeRemaining;
        }
    }

    @Override
    public void remove() {

    }
}
