package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;

/**
 * This class represents a Player Character while implementing MoveableCharacter.
 * This Player will serve as the Main Character in which the User will use.
 * @author Max Middleton
 */

public class Player implements MoveableCharacter {
    private Tile currentTile;
    private boolean isAlive;
    private Direction direction;

    private Level level;

    /**
     * Constructs a new Player instance.
     * @param startTile the tile where the player spawns at the start of the level.
     * @param level the level instance the player belongs to, used for checking boundaries and tile data.
     */
    public Player(Tile startTile, Level level) {
        this.currentTile = startTile;
        this.level = level;
        this.direction = Direction.UP;
        this.isAlive = true;
    }
    /**
     * Interactions with a collectable item.
     * @param item the item to interact with.
     */
    public void collectItem(Item item) {
        // If the item is collectable, record that the player collected it
        if (item instanceof Collectable collectable) {
            collectable.setCollector(this);
        }

        // Then trigger the item's behaviour
        item.interact();
    }

    /**
     * Updates the facing direction of the player.
     * @param direction the new direction for the player to face.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Retrieves the player's current facing direction.
     * @return direction
     */
    @Override
    public Direction getDirection() {
        return direction;
    }

    /**
     * Retrieves the coordinate position of the player.
     * @return an int array containing the x and y coordinates of the player's current tile.
     */
    @Override
    public int[] getPosition() {
        return currentTile.getPosition();
    }

    /**
     * Checks if the player is currently alive.
     * @return True if the player is alive, false otherwise.
     */
    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    /**
     * When a character has collided with another character capable of removing them from the game, it set isAlive to
     * false, indicating their removal from the game
     *
     * @return set to true if character is still alive, false otherwise.
     */
    public void setAliveTo(boolean alive) {isAlive = alive; }


    /**
     * Attempts to move the player in the current facing direction.
     * The method scans the grid along the vector of the current direction (dx, dy).
     * Then if its valid it will change co-ordinates
     */
    public void move() {
        int x = currentTile.getX();
        int y = currentTile.getY();

        int dx = direction.getX();
        int dy = direction.getY();

        x += dx;
        y += dy;

        while (x >= 0 && x < level.getWidth() && y >= 0 && y < level.getHeight()) {
            Tile target = level.getTile(x, y);
            if (currentTile.isValidMove(target)) {
                this.currentTile = target;
                return;
            }
            x += dx;
            y += dy;
        }
    }

    /**
     * This method is to handle player collision with enemy
     * which will end the game as player will die.
     * @param other - character that collides with this one
     */
    @Override
    public void onCollisionWith(MoveableCharacter other) {
        // Will create when enemies are created
    }
}
