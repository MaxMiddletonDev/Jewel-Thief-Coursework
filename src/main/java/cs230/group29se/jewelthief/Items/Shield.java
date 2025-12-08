package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Entities.Protectable;
import cs230.group29se.jewelthief.Game.Achievements;
import cs230.group29se.jewelthief.Game.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager.writeUnlockedAchievement;

/**
 * Represents a Shield item in the Jewel Thief game.
 * The Shield is a destroyable item that can protect
 * a Protectable entity when interacted with.
 *
 * @author Gustas Rove
 */
public class Shield extends Destroyable {

    /** The image representing the Shield item. */
    private final Image image = new Image(
            getClass().getResource(
                    "/cs230/group29se/jewelthief/Images/Items/SHIELD.png")
                    .toString()
    );

    /**
     * Constructs a Shield object with a specified position in the level.
     *
     * @param x The x-coordinate (in tiles) where the Shield is located.
     * @param y The y-coordinate (in tiles) where the Shield is located.
     */
    public Shield(final int x, final int y) {
        super(x, y);
    }

    /**
     * Defines the interaction behavior of the Shield.
     * If the collector is an instance of Protectable,
     * it sets the entity to protected and removes the Shield from the game.
     */
    @Override
    public void interact() {
        if (collector instanceof Protectable protectable) {
            protectable.setProtected(true);
            writeUnlockedAchievement(Achievements.TANK);
            remove(this);
        }
    }

    /**
     * Draws the Shield on the game canvas at its current position.
     *
     * @param gc The GraphicsContext used to draw the Shield.
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(image, getX() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                getY() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                Tile.HALF_TILE_SIZE, Tile.HALF_TILE_SIZE);
    }
}
