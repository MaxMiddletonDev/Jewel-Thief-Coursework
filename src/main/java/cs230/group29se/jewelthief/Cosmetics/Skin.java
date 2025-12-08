package cs230.group29se.jewelthief.Cosmetics;

import javafx.scene.image.Image;

public class Skin {
    private final SkinId id;       // "default", "ninja", "ghost"
    private final String displayName;
    private final Image image;

    public Skin(SkinId id, String displayName, String imagePath) {
        this.id = id;
        this.displayName = displayName;
        this.image = new Image(getClass().getResourceAsStream(imagePath));
    }

    public SkinId getId() { return id; }
    public String getDisplayName() { return displayName; }
    public Image getImage() { return image; }
}
