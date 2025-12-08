package cs230.group29se.jewelthief.Persistence.Storage;

/**
 * One starting entity defined in a level file.
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
 * @author Iyaad
 * @version 1.0
 */
public class EntityDef {

    private String type;
    private int x;
    private int y;

    private String arg1;
    private String arg2;

    /**
     * Creates an entity definition with type, position and optional arguments.
     *
     * @param type the entity type identifier
     * @param x    the x-coordinate of the entity
     * @param y    the y-coordinate of the entity
     * @param arg1 optional first argument
     * @param arg2 optional second argument
     */
    public EntityDef(final String type, final int x, final int y,
                     final String arg1, final String arg2) {
        this.setType(type);
        this.setX(x);
        this.setY(y);
        this.setArg1(arg1);
        this.setArg2(arg2);
    }

    /**
     * Returns the entity type identifier.
     * @return the entity type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the entity type identifier.
     * @param type the new entity type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Returns the x-coordinate of the entity.
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the entity.
     * @param x the new x-coordinate
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the entity.
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the entity.
     * @param y the new y-coordinate
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Returns the first optional argument.
     * @return the first argument, or null if not set
     */
    public String getArg1() {
        return arg1;
    }

    /**
     * Sets the first optional argument.
     * @param arg1 the new first argument
     */
    public void setArg1(final String arg1) {
        this.arg1 = arg1;
    }

    /**
     * Returns the second optional argument.
     * @return the second argument, or null if not set
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * Sets the second optional argument.
     * @param arg2 the new second argument
     */
    public void setArg2(final String arg2) {
        this.arg2 = arg2;
    }
}
