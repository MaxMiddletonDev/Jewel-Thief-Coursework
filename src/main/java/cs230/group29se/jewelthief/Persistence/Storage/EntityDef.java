package cs230.group29se.jewelthief.Persistence.Storage;

/**
 * Represents one starting entity defined in a level file.
 * <p>
 * Examples:
 * <ul>
 *   <li>PLAYER 0 0
 *       → type = "PLAYER", x = 0, y = 0, arg1 = null, arg2 = null</li>
 *   <li>FLYING 1 1 RIGHT
 *       → type = "FLYING", x = 1, y = 1, arg1 = "RIGHT"</li>
 *   <li>FOLLOWER 4 6 DOWN B
 *       → type = "FOLLOWER", x = 4, y = 6, arg1 = "DOWN", arg2 = "B"</li>
 *   <li>LOOT 2 3 DOLLAR
 *       → type = "LOOT", x = 2, y = 3, arg1 = "DOLLAR"</li>
 * </ul>
 * </p>
 *
 * @author Iyaad
 * @version 1.0
 */
public class EntityDef {
    // The type of the entity (e.g., PLAYER, FLYING, FOLLOWER, LOOT)
    private String type;
    private int x;       // The x-coordinate of the entity
    private int y;       // The y-coordinate of the entity
    private String arg1; // Optional argument 1 for the entity
    private String arg2; // Optional argument 2 for the entity

    /**
     * Constructs an EntityDef object with the specified parameters.
     *
     * @param type The type of the entity
     * @param x The x-coordinate of the entity
     * @param y The y-coordinate of the entity
     * @param arg1 Optional argument 1 for the entity
     * @param arg2 Optional argument 2 for the entity
     */
    public EntityDef(final String type, final int x,
                     final int y, final String arg1,
                     final String arg2) {
        this.setType(type);
        this.setX(x);
        this.setY(y);
        this.setArg1(arg1);
        this.setArg2(arg2);
    }

    /**
     * Gets the type of the entity.
     *
     * @return The type of the entity
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the entity.
     *
     * @param type The type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets the x-coordinate of the entity.
     *
     * @return The x-coordinate of the entity
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the entity.
     *
     * @param x The x-coordinate to set
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the entity.
     *
     * @return The y-coordinate of the entity
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the entity.
     *
     * @param y The y-coordinate to set
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Gets the optional argument 1 for the entity.
     *
     * @return The optional argument 1
     */
    public String getArg1() {
        return arg1;
    }

    /**
     * Sets the optional argument 1 for the entity.
     *
     * @param arg1 The optional argument 1 to set
     */
    public void setArg1(final String arg1) {
        this.arg1 = arg1;
    }

    /**
     * Gets the optional argument 2 for the entity.
     *
     * @return The optional argument 2
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * Sets the optional argument 2 for the entity.
     *
     * @param arg2 The optional argument 2 to set
     */
    public void setArg2(final String arg2) {
        this.arg2 = arg2;
    }
}
