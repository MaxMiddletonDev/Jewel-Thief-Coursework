package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.MainApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * The bomb class destroys certain items in its pathway after being triggered.
 * @author Charlie, Hamza
 * @version 0.2 - //TODO can be implemented.
 */
public class Bomb extends Destroyable {
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
     * The time between countdowns in seconds.
     */
    private final double countDownSeconds = 1;
    /**
     * The time between countdowns in ticks.
     */
    private final double countDownTicks = countDownSeconds
            * MainApplication.TPS;
    /**
     * How far the ticks have progressed to one countDownSeconds.
     */
    private double countdownTickProgress = countDownTicks;
    /**
     * How many countdowns remaining.
     */
    private int countDownLeft = 3;


    /**
     * How many seconds between explosions.
     */
    private final double nextBoomSeconds = 0.06125;
    /**
     * How many ticks between explosions.
     */
    private final double nextBoomTicks = nextBoomSeconds * MainApplication.TPS;
    /**
     * How far has the explosion countdown progressed.
     */
    private double nextBoomCountdown = nextBoomTicks;
    /**
     * How many explosions have happened.
     */
    private int explosions = 0;
    /**
     * Items to be destroyed.
     */
    private ArrayList<Destroyable> destroyed = new ArrayList<>();
    /**
     * Is the bomb exploding false for no.
     */
    private boolean exploding = false;


    /**
     * Centre explosion image.
     */
    private final Image bombCentre = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMBSTART.png")
            .toString());
    /**
     * Vertical explosion image.
     */
    private final Image bombVertical = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMBVERTICAL.png")
            .toString());
    /**
     * Horizontal explosion image.
     */
    private final Image bombHorizontal = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMBHORIZONTAL.png")
            .toString());
    /**
     * 3 seconds left bomb image.
     */
    private final Image bombStage3 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB3.png")
            .toString());
    /**
     * 2 seconds left bomb image.
     */
    private final Image bombStage2 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB2.png")
            .toString());
    /**
     * 1 second remaining bomb image.
     */
    private final Image bombStage1 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB1.png")
            .toString());
    /**
     * Un-triggered bomb.
     */
    private final Image bombStage0 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB0.png")
            .toString());

    /**
     * Allows for the creation of an active bomb.
     * @param countDownLeft how many countdowns left before the bomb explodes.
     * @param countdownTickProgress how far into the countdown the bomb is.
     * @param nextBoomCountdown how long till next explosion.
     * @param explosions how many explosions have occurred.
     * @param armed is the bomb armed.
     * @param exploding is the bomb exploding.
     * @param x the x position.
     * @param y the y position.
     */
    public Bomb(final int countDownLeft, final double countdownTickProgress,
                final double nextBoomCountdown, final int explosions,
                final boolean armed, final boolean exploding, final int x,
                final int y) {
        super(x, y);
        this.countDownLeft = countDownLeft;
        this.countdownTickProgress = countdownTickProgress;
        this.nextBoomCountdown = nextBoomCountdown;
        this.explosions = explosions;
        this.armed = armed;
        this.exploding = exploding;

        //TODO HAVE IT BE THE RIGHT IMAGE
        image = new Image(getClass().getResource(
                "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB0.png")
                .toString());
    }


    /**
     * Creates a dormant bomb with a position.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Bomb(final int x, final int y) {
        super(x, y);
        image = new Image(getClass().getResource(
                "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB0.png")
                .toString());
    }

    /**
     * Destroys all destroyable items in the bombs horizontal and vertical path.
     */
    public void destroy() {
        destroyed.clear();
        Level level = GameManager.getCurrentLevel();

        explosions++;
        boolean expanded = false;

        // left
        expanded |= explodeDirection(level, getX() - explosions, getY());
        // right
        expanded |= explodeDirection(level, getX() + explosions, getY());
        // up
        expanded |= explodeDirection(level, getX(), getY() - explosions);
        // down
        expanded |= explodeDirection(level, getX(), getY() + explosions);

        if (!expanded) {
            destroyed.add(this);
        }
    }


    /**
     * Checks if the explode direction is valid.
     * @param level  the current level.
     * @param x the x position of the explosion.
     * @param y the y position of the explosion.
     * @return true if the explosion was valid.
     */
    private boolean explodeDirection(final Level level,
                                     final int x, final int y) {
                    if (x < 0 || y < 0 || x >= level.getWidth() || y >= level.getHeight()) {
                        return false;
                    }

                    Tile tile = level.getTile(x, y);

                    //add it to destroyed to be destroyed
                    if (tile.getOccupying() instanceof Destroyable d) {
                        destroyed.add(d);
                    }
                    return true;  // we expanded
                }

    /**
     * sets a timer to destroy the bomb.
     * can be set to a loop to change sprites.
     */
    public void interact() {
        if (!armed) {
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
     * Draw the bomb with the correct countdown image.
     * If the bomb is exploding it draws where the explosions are.
     * @param gc the class the bomb will be drawn with.
     */
    public void draw(final GraphicsContext gc) {
        Level level = GameManager.getCurrentLevel();

        if (exploding) {


            for (int i = 1; i <= explosions; i++) {
                //draw left explosion
                if (getX() - i >= 0) {
                    gc.drawImage(bombHorizontal, (getX() - i) * Tile.TILE_SIZE,
                            getY() * Tile.TILE_SIZE,
                            Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
                //draw right explosion
                if (getX() + i < level.getWidth()) {
                    gc.drawImage(bombHorizontal, (getX() + i) * Tile.TILE_SIZE,
                            getY() * Tile.TILE_SIZE,
                            Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
                //draw up explosion
                if (getY() - i >= 0) {
                    gc.drawImage(bombVertical, getX() * Tile.TILE_SIZE,
                            (getY() - i) * Tile.TILE_SIZE,
                            Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
                //draw down explosion
                if (getY() + i < level.getHeight()) {
                    gc.drawImage(bombVertical, getX() * Tile.TILE_SIZE,
                            (getY() + i) * Tile.TILE_SIZE,
                            Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
                gc.drawImage(bombCentre, getX() * Tile.TILE_SIZE,
                        getY() * Tile.TILE_SIZE,
                        Tile.TILE_SIZE, Tile.TILE_SIZE);
            }


        } else if (armed) {
            if (countDownLeft == 0) {
                image = bombStage0; // explosion image
            } else if (countDownLeft == 1) {
                image = bombStage1; // "1" stage
            } else if (countDownLeft == 2) {
                image = bombStage2; // "2" stage
            } else {
                image = bombStage3; // "3" stage
            }

        gc.drawImage(image, getX() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                getY() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                Tile.HALF_TILE_SIZE, Tile.HALF_TILE_SIZE);
        } else {
            gc.drawImage(image, getX() * Tile.TILE_SIZE
                            +
                            Tile.HALF_TILE_SIZE / 2, getY() * Tile.TILE_SIZE
                            +
                            Tile.HALF_TILE_SIZE / 2, Tile.HALF_TILE_SIZE,
                            Tile.HALF_TILE_SIZE);
        }
    }

    /**
     * Gets true if the bomb is exploding.
     * @return true if the bomb is exploding.
     */
    public boolean getExploding() {
        return exploding;
    }


    /**
     * counts down a tick towards the next explosion.
     */
    public void updateNextBoom() {
        nextBoomCountdown--;
        if (nextBoomCountdown <= 0) {
            nextBoomCountdown = nextBoomTicks;
            destroy();
        }
    }

    /**
     * Counts down a tick. If the tick counter hits 0 and countdownLeft is 0
     * start exploding.
     */
    public void updateCountDown() {
        countdownTickProgress--;
        if (countdownTickProgress <= 0) {
            countdownTickProgress = countDownTicks;
            countDownLeft--;

            //explosion starts
            if (countDownLeft == 0) {
                exploding = true;
            }
        }
    }

    /**
     * The list of items that can now be destroyed when the loop ends.
     * @return the list of items to be destroyed.
     */
    public ArrayList<Destroyable> toDestroy() {
        return destroyed;
    }

    /**
     * How many ticks until the bomb explodes.
     * @return how many ticks until the bomb explodes.
     */
    public int getCountDownLeft() {
        return countDownLeft;
    }

    /**
     * How many explosions have happened from the bomb so far.
     * @return the amount of explosions that have passed.
     */
    public int getExplosions() {
        return explosions;
    }

    /**
     * How many ticks left until the next explosion.
     * @return how many ticks till next explosion.
     */
    public double getNextBoomCountdown() {
        return nextBoomCountdown;
    }

    /**
     * How many ticks left until countdown left changes.
     * @return how far into ticks.
     */
    public double getCountdownTickProgress() {
        return countdownTickProgress;
    }
}
