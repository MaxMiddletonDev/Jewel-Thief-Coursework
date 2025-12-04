package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Entities.Protectable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a Shield item in the Jewel Thief game.
 * The Shield is a destroyable item that can protect a Protectable entity when interacted with.
 *
 * @author Gustas Rove
 */
public class Shield extends Destroyable {

    /** The image representing the Shield item. */
    private final Image image = new Image(
            getClass().getResource("/cs230/group29se/jewelthief/Images/SHIELD.png").toString()
    );

    /**
     * Constructs a Shield object with a specified position in the level.
     *
     * @param x The x-coordinate (in tiles) where the Shield is located.
     * @param y The y-coordinate (in tiles) where the Shield is located.
     */
    public Shield(int x, int y) {
        super(x, y);
    }

    /**
     * Defines the interaction behavior of the Shield.
     * If the collector is an instance of Protectable, it sets the entity to protected
     * and removes the Shield from the game.
     */
    @Override
    public void interact() {
        if (collector instanceof Protectable protectable) {
            protectable.setProtected(true);
            remove(this);
        }
    }

    /**
     * Draws the Shield on the game canvas at its current position.
     *
     * @param gc The GraphicsContext used to draw the Shield.
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, getX() * 64, getY() * 64);
    }
}