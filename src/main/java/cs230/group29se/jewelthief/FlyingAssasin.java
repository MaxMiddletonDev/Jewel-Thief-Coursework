package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Game.Tile;

/**
 * An NPC that moves in a straight line, either horizontally or vertically, upon contact (when occupying the same
 * tile as a player/thief) it takes out the other moveable character, it doesn't take loot, just moves, and takes
 * out other movable characters
 *
 * @author Baba
 */
public class FlyingAssasin extends NonPlayableCharacter {

    /**
     * Constructor for creating new instance of FlyingAssasin.
     */
    public FlyingAssasin(Tile startingTile, Direction direction) {
        super(startingTile, direction);
    }

    /**
     * kept empty because a Flying Assasin does not collect items.
     *
     * @param item
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
            ((Player)other).setAliveTo(false);
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

    }
}
