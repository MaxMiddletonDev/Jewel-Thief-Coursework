package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;

/**
 * An NPC that moves following the left hand edge of their "assigned" colour, they collect items and upon coming in
 * contact with a player they cause the player to lose. If they come in contact with a flying assassin however (occupy)
 * the same tile, then they are removed from the game.
 *
 * @author Baba
 */
public class FloorThief extends NonPlayableCharacter {

    /**
     * Stores a FloorThief's assigned colour
     */
    private Colour assignedColour;

    /**
     * Stores a Floor Thief's level
     */
    private Level level;

    public FloorThief(Colour assignedColour, Tile startingTile, Direction direction, Level level) {
        super(startingTile, direction);
        this.assignedColour = assignedColour;
        this.level = level;
    }

    /**
     * Fetches a Floor Thief's assigned colour.
     * @return assignedColour
     */
    public Colour getAssignedColour() {
        return assignedColour;
    }

    /**
     * When a FloorThief collides/comes in contact with a player, the player is removed from the game, if it occupies
     * the same tile as a flying assassin, the Floor Thief is removed from the game
     *
     * @param other - character that collides with this one
     */
    @Override
    public void onCollisionWith(MoveableCharacter other) {
        if (!isAlive) {
            return;
        }

        if (other instanceof FlyingAssasin) {
            this.isAlive = false;
        } else if (other instanceof Player) {
            ((Player)other).setAliveTo(false);
        }
    }

    @Override
    public void collectItem(Item item) {}

    @Override
    public void move(){}

    /**
     * Checks if a floor thief is at an edge or not
     */
    public boolean isValidMove(int targetX, int targetY) {
        if (targetX >= 0 && targetY >= 0 && targetX < level.getWidth() && targetY < level.getHeight()) {
            return true;
        } else {
            return false;
        }
    }


}
