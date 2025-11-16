/**
 * LootEnum is a template for the Loot class to set the loot as.
 * @author Charlie
 * @version 1.0
 */
public enum LootEnum {
    /**
     * Lowest value loot called CENT.
     */
    CENT(10),

    /**
     * Second-lowest value loot called DOLLAR.
     */
    DOLLAR(20),

    /**
     * Second-highest value loot called RUBY.
     */
     RUBY(40),

    /**
     * Highest value loot called DIAMOND.
     */
    DIAMOND(60);

    /**
     * VALUE is the amount the loot will be worth.
     */
    private final int VALUE;

    /**
     * Constructs one of the enum constants.
     * @param value is the value of the loot that the score may change by.
     */
    LootEnum(final int value) {
        this.VALUE = value;
    }

    /**
     * Returns the value of the loot.
     * @return VALUE is sent to loot to change the score.
     */
    public int getValue() {
        return VALUE;
    }
}
