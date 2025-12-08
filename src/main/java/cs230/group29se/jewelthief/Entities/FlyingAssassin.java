package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Item;
import javafx.scene.image.Image;


/**
 * An NPC that moves in a straight line, either horizontally or vertically, upon contact (when occupying the same
 * tile as a player/thief) it takes out the other moveable character, it doesn't take loot, just moves, and takes
 * out other moveable characters
 *
 * @author Baba
 */
public class FlyingAssassin extends NonPlayableCharacter {

    private Level level;
    private final Image image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/Entities/NPCs/FLYINGASSASSIN.png").toString());

    /**
     * Constructor for creating new instance of FlyingAssassin.
     * Creates a new Flying Assassin instance.
     *
     * @param startingTile The tile the Assassin starts on
     * @param direction The direction the Assassin starts
     * @param level The level the Assassin will be instantiated on
     * @param id Assassin's ID
     */
    public FlyingAssassin(Tile startingTile, Direction direction, Level level, String id) {
        super(startingTile, direction);
        this.id = id;
        this.level = level;
        setMoveCooldownSeconds(0.3F); // Flying Assassin moves every 2 seconds
        setHitCooldownSeconds(1.5); // Flying Assassin can hit every 2 seconds
    }

    /**
     * Kept empty because a Flying Assassin does not collect items.
     *
     * @param item that would be collected.
     */
    @Override
    public void collectItem(Item item) {}

    /**
     * if a flying assassin occupies the same tile as another character, that character is taken off the game, if it's
     * a player, the player loses and is no longer in game.
     *
     * @param other - character that collides with this one
     */
    @Override
    public void onCollisionWith(MoveableCharacter other) {
        if (other instanceof Player) {
            if(canHit()){
                ((Player) other).getHit();
                resetHitCooldown();
                System.out.println("Flying Assasin hit the Player!");
            }else{
                System.out.println("Flying Assasin is on hit cooldown: " + hitCooldown + " ticks remaining.");
            }
        }
        else if (other instanceof NonPlayableCharacter && !(other instanceof FlyingAssassin)) {
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
        if (!isAlive) {
            return;
        }

        //Only move once every X seconds
        if(!canMove()){
            return;
        }

        int[] currentPosition = getPosition();
        int targetX = currentPosition[0];
        int targetY = currentPosition[1];

        if (direction == Direction.UP) {
            targetY--;
        } else if (direction == Direction.DOWN) {
            targetY++;
        } else if (direction == Direction.LEFT) {
            targetX--;
        } else if (direction == Direction.RIGHT) {
            targetX++;
        }

        if (isValidMove(targetX, targetY)) {
            Tile targetTile = level.getTile(targetX, targetY);
            if (targetTile != null) {
                currentTile = targetTile;
            }
        } else {
            reverseDirection();
        }


    }

    /**
     * Checks if a flying assassin is at an edge or not
     */
    public boolean isValidMove(int targetX, int targetY) {
        if (targetX >= 0 && targetY >= 0 && targetX < level.getWidth() && targetY < level.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reverses direction of Flying assassin
     */
    public void reverseDirection() {
        if (direction == Direction.UP) {
            direction = Direction.DOWN;
        } else if (direction == Direction.DOWN) {
            direction = Direction.UP;
        } else if (direction == Direction.LEFT) {
            direction = Direction.RIGHT;
        } else if (direction == Direction.RIGHT) {
            direction = Direction.LEFT;
        }
    }

    /**
     * Gets specific enemies sprite
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
    public void setProtected(boolean value) {
        isProtected = value;
    }
}
