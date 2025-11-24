package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Game.Tile;

import java.util.List;

/**
 * This class provides functionality for a non-playable character as well as provides data for the same.
 *
 * @author Baba & Max
 */
public abstract class NonPlayableCharacter implements MoveableCharacter {

    /**
     * Gives us an NPCs current tile on which they're on.
     */
    protected Tile currentTile;

    /**
     * tells us if an NPC is alive or not
     */
    protected boolean isAlive;

    /**
     * tells us which direction an NPC is facing
     */
    protected Direction direction;

    /**
     * Tells us what items an NPC has collected
     */
    protected List<Item> collectedItems;

    /**
     * Constructor for creating a new instance of an NPC
     */
    public NonPlayableCharacter(Tile startingTile, Direction direction) {
        this.currentTile = startingTile;
        this.direction = direction;
        this.isAlive = true;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int[] getPosition() {
        return currentTile.getPosition();
    }

    @Override
    public void collectItem(Item item) {

    }

    @Override
    public abstract void onCollisionWith(MoveableCharacter other);

    @Override
    public abstract void move();
}
