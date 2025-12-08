package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Item;
import javafx.scene.image.Image;


/**
 * An NPC that moves in a straight line, either horizontally or vertically,
 * upon contact (when occupying the same.
 * tile as a player/thief) it takes out the other moveable character,
 * it doesn't take loot, just moves, and takes.
 * out other moveable characters
 *
 * @author Baba
 */
public class FlyingAssassin extends NonPlayableCharacter {

    public static final float MOVE_COOLDOWN_SECONDS = 0.3F;
    public static final double HIT_COOLDOWN_SECONDS = 1.5;
    public static final String IMAGE_PATH =
            "/cs230/group29se/jewelthief/Images/"
                    + "Entities/NPCs/FLYINGASSASSIN.png";
    public static final String PLAYER_HIT_LOG =
            "Flying Assasin hit the Player!";
    private static final String COOLDOWN_FORMAT_LOG =
            "Flying Assassin is on hit cooldown: %s ticks remaining.";

    private Level level;
    private final Image image
            = new Image(getClass().getResource(IMAGE_PATH).toString());

    /**
     * Constructor for creating new instance of FlyingAssassin.
     * Creates a new Flying Assassin instance.
     *
     * @param startingTile The tile the Assassin starts on
     * @param direction The direction the Assassin starts
     * @param level The level the Assassin will be instantiated on
     * @param id Assassin's ID
     */
    public FlyingAssassin(final Tile startingTile,
                          final Direction direction,
                          final Level level,
                          final String id) {
        super(startingTile, direction);
        this.id = id;
        this.level = level;
        setMoveCooldownSeconds(MOVE_COOLDOWN_SECONDS);
        setHitCooldownSeconds(HIT_COOLDOWN_SECONDS);
    }

    /**
     * Kept empty because a Flying Assassin does not collect items.
     *
     * @param item that would be collected.
     */
    @Override
    public void collectItem(final Item item) { }
    /**
     * if a flying assassin occupies the same tile as another character,
     * that character is taken off the game,
     * if it's a player, the player loses and is no longer in game.
     *
     * @param other - character that collides with this one
     */
    @Override
    public void onCollisionWith(final MoveableCharacter other) {
        if (other instanceof Player) {
            if (canHit()) {
                ((Player) other).getHit();
                resetHitCooldown();
                System.out.println(PLAYER_HIT_LOG);
            } else {
                System.out.println(String.format(
                        COOLDOWN_FORMAT_LOG, hitCooldown));
            }
        } else if (other instanceof NonPlayableCharacter
                && !(other instanceof FlyingAssassin)) {
            other.setAliveTo(false);
        }
    }

    /**
     * Moves in a straight line, either horizontally or vertically,
     * and when at the edge, takes a 180-degree turn and
     * Moves in the opposite direction.
     */
    @Override
    public void move() {
        if (!isAlive()) {
            return;
        }

        //Only move once every X seconds
        if (!canMove()) {
            return;
        }

        int[] currentPosition = getPosition();
        int targetX = currentPosition[0];
        int targetY = currentPosition[1];

        if (getDirection() == Direction.UP) {
            targetY--;
        } else if (getDirection() == Direction.DOWN) {
            targetY++;
        } else if (getDirection() == Direction.LEFT) {
            targetX--;
        } else if (getDirection() == Direction.RIGHT) {
            targetX++;
        }

        if (isValidMove(targetX, targetY)) {
            Tile targetTile = level.getTile(targetX, targetY);
            if (targetTile != null) {
                setCurrentTile(targetTile);
            }
        } else {
            reverseDirection();
        }


    }

    /**
     * Checks if a flying assassin is at an edge or not.
     * @param targetX states the edge x
     * @param targetY states the edge y
     * @return true or false based on if it's a valid move.
     */
    public boolean isValidMove(final int targetX, final int targetY) {
        if (targetX >= 0 && targetY >= 0
                && targetX < level.getWidth() && targetY < level.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reverses direction of Flying assassin.
     */
    public void reverseDirection() {
        if (getDirection() == Direction.UP) {
            setDirection(Direction.DOWN);
        } else if (getDirection() == Direction.DOWN) {
            setDirection(Direction.UP);
        } else if (getDirection() == Direction.LEFT) {
            setDirection(Direction.RIGHT);
        } else if (getDirection() == Direction.RIGHT) {
            setDirection(Direction.LEFT);
        }
    }

    /**
     * Gets specific enemies sprite.
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * Is a check to find if protected.
     * @return isProtected
     */
    @Override
    public boolean isProtected() {
        return isProtected;
    }

    /**
     * This sets if Protected.
     * @param value true to protect the entity, false to remove protection.
     */
    @Override
    public void setProtected(final boolean value) {
        isProtected = value;
    }
}
