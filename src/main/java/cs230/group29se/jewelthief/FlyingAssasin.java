package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;

/**
 * An NPC that moves in a straight line, either horizontally or vertically, upon contact (when occupying the same
 * tile as a player/thief) it takes out the other moveable character, it doesn't take loot, just moves, and takes
 * out other moveable characters
 *
 * @author Baba
 */
public class FlyingAssasin extends NonPlayableCharacter {

    private Level level;

    /**
     * Constructor for creating new instance of FlyingAssasin.
     */
    public FlyingAssasin(Tile startingTile, Direction direction, Level level) {
        super(startingTile, direction);
        this.level = level;
    }

    /**
     * kept empty because a Flying Assasin does not collect items.
     *
     * @param item that would be collected.
     */
    @Override
    public void collectItem(Item item) {}

    /**
     * if a flying assasin occupies the same tile as another character, that character is taken off the game, if its
     * a player, the player loses and is no longer in game.
     *
     * @param other - character that collides with this one
     */
    @Override
    public void onCollisionWith(MoveableCharacter other) {
        if (other instanceof Player) {
            ((Player)other).getHit();
        } else if (other instanceof NonPlayableCharacter && !(other instanceof FlyingAssasin)) {
            ((NonPlayableCharacter)other).setAliveTo(false);
        }
    }

    /**
     * moves in a straight line, either horizontally or vertically, and when at the edge, takes a 180 degree turn and
     * moves in the opposite direction.
     */
    @Override
    public void move() {
        if (!isAlive) {
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

    @Override
    public boolean isProtected() {
        return isProtected;
    }

    @Override
    public void setProtected(boolean value) {
        isProtected = value;
    }
}
