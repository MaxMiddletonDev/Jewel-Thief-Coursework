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

    public EntityDef(String type, int x, int y, String arg1, String arg2) {
        this.setType(type);
        this.setX(x);
        this.setY(y);
        this.setArg1(arg1);
        this.setArg2(arg2);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }
}