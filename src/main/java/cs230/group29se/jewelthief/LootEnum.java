package cs230.group29se.jewelthief;

import javafx.scene.image.Image;

/**
 * LootEnum is a template for the Loot class to set the loot as.
 * @author Charlie
 * @version 1.1
 */
public enum LootEnum {
    /**
     * Lowest value loot called CENT with an image of the cent.
     */
    CENT(10, new Image("Images/CENT.png")),

    /**
     * Second-lowest value loot called DOLLAR with an image of the dollar.
     */
    DOLLAR(20, new Image("Images/DOLLAR.png")),

    /**
     * Second-highest value loot called RUBY with an image of the ruby.
     */
     RUBY(40, new Image("Images/RUBY.png")),

    /**
     * Highest value loot called DIAMOND with an image of the diamond.
     */
    DIAMOND(60, new Image("Images/DIAMOND.png"));

    /**
     * VALUE is the amount the loot will be worth.
     */
    private final int VALUE;

    /**
     * The width of the image.
     * All images of Loot should be the same size unless discussed otherwise.
     */
    private final Image image;
    /**
     * Constructs one of the enum constants.
     * @param value is the value of the loot that the score may change by.
     * @param image the image of loot to be shown.
     */

    LootEnum(final int value, final Image image) {
        this.VALUE = value;
        this.image = image;
    }

    /**
     * Returns the value of the loot.
     * @return VALUE is sent to loot to change the score.
     */
    public int getValue() {
        return VALUE;
    }

    /**
     * Gets the image of the loot.
     * @return the image of the loot.
     */
    public Image getImage() {
        return image;
    }
}
