package cs230.group29se.jewelthief.Cosmetics;

import javafx.scene.image.Image;

/**
 * Alternative sprites for the player
 * @author Gustas Rove
 */
public class Skin {
    private final SkinId id;       // "default", "ninja", "ghost"
    private final String displayName;
    private final Image image;

    /**
     * sets the unique identification enum, reference name and the filepath to the matching image
     * @param id the unique enum for the cosmetic
     * @param displayName the name to be displayed to the player
     * @param imagePath the location to find the image to be representing
     */
    public Skin(SkinId id, String displayName, String imagePath) {
        this.id = id;
        this.displayName = displayName;
        this.image = new Image(getClass().getResourceAsStream(imagePath));
    }

    /**
     *  gets the associated enum
     * @return the associated enum
     */
    public SkinId getId() { return id; }

    /**
     * gets the associated image
     * @return the associated image
     */
    public Image getImage() { return image; }
}
