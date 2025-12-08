package cs230.group29se.jewelthief.Cosmetics;

import javafx.scene.image.Image;

/**
 * Skin: represents a cosmetic skin for the player character.
 *
 * @author Gustas Rove
 * @version 1.0
 */
public class Skin {
    private final SkinId id;       // "default", "ninja", "ghost"
    private final String displayName;
    private final Image image;

    /**
     * Constructor for Skin.
     *
     * @param id          The skin's enum identifier.
     * @param displayName The skin's display name.
     * @param imagePath   The path to the skin's image resource.
     */
    public Skin(final SkinId id,
                final String displayName,
                final String imagePath) {
        this.id = id;
        this.displayName = displayName;
        this.image = new Image(getClass().getResourceAsStream(imagePath));
    }

    /**
     * Gets the display name of the skin.
     *
     * @return The skin's display name.
     */
    public SkinId getId() {
        return id;
    }

    /**
     * Gets the display name of the skin.
     *
     * @return The skin's display name.
     */
    public Image getImage() {
        return image;
    }
}
