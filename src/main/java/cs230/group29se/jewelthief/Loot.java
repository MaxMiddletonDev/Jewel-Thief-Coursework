package cs230.group29se.jewelthief;

/**
 * Loot can be one of various types that increase the score of the
 * level when picked up by the player.
 * @author Charlie
 * @version 0.1 - interact needs to be implemented - needs level to be implemented.
 */
public class Loot extends Destroyable {

    /**
     * The type of loot that the instance will be based on.
     */
    private final LootEnum type;

    /**
     * Allows for loot to be created with a position
     *  and set value from existing LootEnum.
     * @param position the position of the loot.
     * @param type the type of loot from a set of types.
     */
    public Loot(final int[] position, final LootEnum type) {
        super(position);
        this.type = type;
    }

    /**
     * Interact increases the score based on the value of the loot.
     * Needs level to be implemented.
     */
    public void interact() {

    }

    /**
     * Gets the value of the loot.
     * @return the value of the loot.
     */
    public int getValue() {
        return type.getValue();
    }

    /**
     * Gets the type of loot.
     * @return the type of loot.
     */
    public LootEnum getType() {
        return type;
    }
}
