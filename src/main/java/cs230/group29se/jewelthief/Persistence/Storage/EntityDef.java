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

    /** Kind of entity, e.g. PLAYER, FLYING, FOLLOWER, SMART, LOOT, BOMB, LEVER, GATE, DOOR. */
    public String type;

    /** X coordinate on the level grid (column). */
    public int x;

    /** Y coordinate on the level grid (row). */
    public int y;

    /**
     * First extra argument from the line, if present.
     * For example: direction ("LEFT", "RIGHT", "UP", "DOWN"),
     * colour ("R"), or loot type ("DOLLAR").
     */
    public String arg1;

    /**
     * Optional second extra argument from the line, if present.
     * Used when a level line has more than one extra value
     * (for example an extra mode, speed, or pattern ID).
     */
    public String arg2;

    public EntityDef(String type, int x, int y, String arg1, String arg2) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }
}