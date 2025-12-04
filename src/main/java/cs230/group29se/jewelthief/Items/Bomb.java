package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The bomb class destroys certain items in its pathway after being triggered.
 * @author Charlie
 * @version 0.2 - //TODO can be implemented.
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
     * starting image of the bomb it is not final since
     * the bombs image changes as it counts down.
     */
    private Image image;


    /**
     * Creates an active bomb with a location and time left before detonation.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     * @param timeRemaining how long left till the bomb detonates.
     */
    public Bomb(final Long timeRemaining, final int x, final int y) {
        super(x, y);
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
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Bomb(final int x, final int y) {
        super(x, y);
        image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/BOMB0.png").toString());
    }


    /**
     * Destroys all destroyable items in the bombs horizontal and vertical path.
     * @return the task the timer will execute when finished.
     */
    public TimerTask destroy() {
        Bomb bomb = this;
        Level level = GameManager.getCurrentLevel();
        return new TimerTask() {
            @Override
            public void run() {
                int explodeLeft = getX() - 1;
                int explodeRight = getX() + 1;
                int explodeUp = getY() - 1;
                int explodeDown = getY() + 1;

                bomb.remove(bomb);

                while (explodeLeft >= 0 || explodeRight < level.getWidth() || explodeUp >= 0 || explodeDown < level.getHeight()) {
                    try {
                        // Left
                        if (explodeLeft >= 0) {
                            var tileLeft = level.getTile(explodeLeft, getY());
                            if (tileLeft.getOccupying() instanceof Destroyable destroyable) {
                                destroyable.remove(destroyable);
                            }
                        }

                        // Right
                        if (explodeRight < level.getWidth()) {
                            var tileRight = level.getTile(explodeRight, getY());
                            if (tileRight.getOccupying() instanceof Destroyable destroyable) {
                                destroyable.remove(destroyable);
                            }
                        }

                        // Up
                        if (explodeUp >= 0) {
                            var tileUp = level.getTile(getX(), explodeUp);
                            if (tileUp.getOccupying() instanceof Destroyable destroyable) {
                                destroyable.remove(destroyable);
                            }
                        }

                        // Down
                        if (explodeDown < level.getHeight()) {
                            var tileDown = level.getTile(getX(), explodeDown);
                            if (tileDown.getOccupying() instanceof Destroyable destroyable) {
                                destroyable.remove(destroyable);
                            }
                        }

                        explodeLeft--;
                        explodeRight++;
                        explodeUp--;
                        explodeDown++;
                    } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                        // Optional: log e.getMessage() if needed
                    }
                }
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

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, getX()*64, getY()*64);
    }
}
