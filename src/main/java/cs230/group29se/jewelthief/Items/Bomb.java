package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The bomb class destroys certain items in its pathway after being triggered.
 * @author Charlie, Hamza
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


    // Add image references for each bomb stage (3,2,1,0)
    private final Image bombStage4 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/BOMB4.png").toString());
    private final Image bombStage3 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/BOMB3.png").toString());
    private final Image bombStage2 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/BOMB2.png").toString());
    private final Image bombStage1 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/BOMB1.png").toString());
    private final Image bombStage0 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/BOMB0.png").toString());

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

        // Set initial image to stage 3 when bomb is armed.
        this.image = bombStage3;
    }

    /**
     * Creates a dormant bomb with a position.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Bomb(final int x, final int y) {
        super(x, y);
        image = new Image(getClass().getResource(
                "/cs230/group29se/jewelthief/Images/BOMB0.png").toString());
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
                image = bombStage4;

                while (explodeLeft >= 0 || explodeRight < level.getWidth()
                        || explodeUp >= 0 || explodeDown < level.getHeight()) {
                    try {
                        // Left
                        if (explodeLeft >= 0) {
                            Tile tileLeft = level.getTile(explodeLeft, getY());
                            if (tileLeft.getOccupying() instanceof Destroyable destroyable) {
                                destroyable.remove(destroyable);
                                //bomb.draw(gc, explodeLeft, getY());
                            }
                        }

                        // Right
                        if (explodeRight < level.getWidth()) {
                            Tile tileRight = level.getTile(explodeRight, getY());
                            if (tileRight.getOccupying() instanceof Destroyable destroyable) {
                                destroyable.remove(destroyable);
                            }
                        }

                        // Up
                        if (explodeUp >= 0) {
                            Tile tileUp = level.getTile(getX(), explodeUp);
                            if (tileUp.getOccupying() instanceof Destroyable destroyable) {
                                destroyable.remove(destroyable);
                            }
                        }

                        // Down
                        if (explodeDown < level.getHeight()) {
                            Tile tileDown = level.getTile(getX(), explodeDown);
                            if (tileDown.getOccupying() instanceof Destroyable destroyable) {
                                destroyable.remove(destroyable);
                            }
                        }

                        explodeLeft--;
                        explodeRight++;
                        explodeUp--;
                        explodeDown++;
                        Thread.sleep(200);
                    } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                        // Optional: log e.getMessage() if needed
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
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
        if (!armed) {
            startTime = System.currentTimeMillis();
            timer.schedule(destroy(), timeRemaining);
            armed = true;
        }
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
        if (armed) {
            return startTime + timeRemaining - System.currentTimeMillis();
        } else {
            return timeRemaining;
        }
    }

    /**
     * Draw the bomb with the correct countdown image.
     * @param gc the class the bomb will be drawn with.
     */
    public void draw(final GraphicsContext gc) {


        // Dynamically change the bomb sprite based on time remaining.
        if (armed) {
            long remaining = getTimeRemaining();

            if (remaining <= 300) {
                image = bombStage4; // explosion image
            } else if (remaining <= 1300) {
                image = bombStage1; // "1" stage
            } else if (remaining <= 2300) {
                image = bombStage2; // "2" stage
            } else {
                image = bombStage3; // "3" stage
            }
        }

        gc.drawImage(image, getX() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                getY() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                Tile.HALF_TILE_SIZE, Tile.HALF_TILE_SIZE);
    }

    public void draw(final GraphicsContext gc, final int x, final int y) {
        gc.drawImage(image, x * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                y * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                Tile.HALF_TILE_SIZE, Tile.HALF_TILE_SIZE);
    }
}
