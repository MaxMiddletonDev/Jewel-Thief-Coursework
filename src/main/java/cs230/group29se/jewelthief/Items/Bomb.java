package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.MainApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Timer;

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

    //timing for countdown
    private final double countDownSeconds = 1;
    private final double countDownTicks = countDownSeconds * MainApplication.TPS;
    private double countdownTickProgress = countDownTicks;





    private int countDownLeft = 3;

    //timing for explosions
    private final double nextBoomSeconds = 0.06125;
    private final double nextBoomTicks = nextBoomSeconds * MainApplication.TPS;
    private double nextBoomCountdown = nextBoomTicks;
    private int explosions = 0;





    private ArrayList<Destroyable> destroyed = new ArrayList<>();
    private boolean exploding = false;




    // Add image references for each bomb stage (3,2,1,0)
    private final Image bombCentre = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMBSTART.png").toString());
    private final Image bombVertical = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMBVERTICAL.png").toString());
    private final Image bombHorizontal = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMBHORIZONTAL.png").toString());
    private final Image bombStage3 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB3.png").toString());
    private final Image bombStage2 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB2.png").toString());
    private final Image bombStage1 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB1.png").toString());
    private final Image bombStage0 = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB0.png").toString());

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
    public Bomb(int countDownLeft, double countdownTickProgress, double nextBoomCountdown, int explosions, boolean armed, boolean exploding, final int x, final int y) {
        super(x, y);
        this.countDownLeft = countDownLeft;
        this.countdownTickProgress = countdownTickProgress;
        this.nextBoomCountdown = nextBoomCountdown;
        this.explosions = explosions;
        this.armed = armed;
        this.exploding = exploding;
    }


    /**
     * Creates a dormant bomb with a position.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Bomb(final int x, final int y) {
        super(x, y);
        image = new Image(getClass().getResource(
                "/cs230/group29se/jewelthief/Images/Items/Bomb/BOMB0.png").toString());
    }

    /**
     * Destroys all destroyable items in the bombs horizontal and vertical path.
     */
    public void destroy() {
        destroyed.clear();
        Level level = GameManager.getCurrentLevel();

        explosions++;
        boolean expanded = false;

        expanded |= explodeDirection(level, getX()-explosions, getY()); // left
        expanded |= explodeDirection(level, getX()+explosions, getY()); // right
        expanded |= explodeDirection(level, getX(), getY()-explosions); // up
        expanded |= explodeDirection(level, getX(), getY()+explosions); // down

        if (!expanded) {
            destroyed.add(this);
        }
    }


    private boolean explodeDirection(Level level, int x, int y) {
                    if (x < 0 || y < 0 || x >= level.getWidth() || y >= level.getHeight()) {
                        return false;
                    }

                    Tile tile = level.getTile(x, y);

                    // If tile contains a destroyable → destroy it
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
            startTime = System.currentTimeMillis();
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
        Level level = GameManager.getCurrentLevel();

        if (exploding) {


            for (int i = 1; i <= explosions; i++) {
                //draw left explosion
                if (getX() - i >= 0) {
                    gc.drawImage(bombHorizontal, (getX()-i) * Tile.TILE_SIZE,
                            getY() * Tile.TILE_SIZE,
                            Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
                //draw right explosion
                if (getX() + i < level.getWidth()) {
                    gc.drawImage(bombHorizontal, (getX()+ i) * Tile.TILE_SIZE,
                            getY() * Tile.TILE_SIZE,
                            Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
                //draw up explosion
                if (getY() - i >= 0 ) {
                    gc.drawImage(bombVertical, getX() * Tile.TILE_SIZE,
                            (getY()- i) * Tile.TILE_SIZE,
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
            gc.drawImage(image, getX() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                    getY() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                    Tile.HALF_TILE_SIZE, Tile.HALF_TILE_SIZE);
        }
    }


    public boolean getExploding() {
        return exploding;
    }



    public void updateNextBoom() {
        nextBoomCountdown--;
        if (nextBoomCountdown <= 0) {
            nextBoomCountdown = nextBoomTicks;
            destroy();
        }
    }


    public void updateCountDown() {
        countdownTickProgress--;
        if (countdownTickProgress <= 0 ) {
            countdownTickProgress = countDownTicks;
            countDownLeft--;

            //explosion starts
            if (countDownLeft == 0) {
                exploding = true;
            }
        }
    }

    public ArrayList<Destroyable> toDestroy() {
        return destroyed;
    }
    public int getCountDownLeft() {
        return countDownLeft;
    }
    public int getExplosions() {
        return explosions;
    }
    public double getNextBoomCountdown() {
        return nextBoomCountdown;
    }
    public double getCountdownTickProgress() {
        return countdownTickProgress;
    }
}
