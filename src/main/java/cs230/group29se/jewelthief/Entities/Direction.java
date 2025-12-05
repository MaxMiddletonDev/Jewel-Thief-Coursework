package cs230.group29se.jewelthief.Entities;

/**
 * Enum to show the possible movements for Player and NPCs.
 * Stores X and Y modifiers for movement.
 * @author Max Middleton
 */

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int x;
    private final int y;

    /**
     * Constructs new direction in an array called directionFinder
     * @param x
     * @param y
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get Method to get the X Direction
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Get Method to get the Y Direction
     * @return y
     */
    public int getY() {
        return y;
    }


}
