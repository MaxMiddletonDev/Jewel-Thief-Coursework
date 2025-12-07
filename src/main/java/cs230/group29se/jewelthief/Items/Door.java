package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Entities.FloorThief;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Entities.Player;
import cs230.group29se.jewelthief.Game.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * Door allows a player or thief to leave the level when open.
 * @author Charlie, Hamza, Ben.
 * @version 1.0 Door can be used as intended.
 */
public class Door extends Item {

    /**
     * Door starts closed till all loot is gone.
     */
    private boolean closed = true;

    /**
     * Image of the door.
     */
    private final Image image = new Image(getClass().getResource(
            "/cs230/group29se/jewelthief/Images/Items/DOOR.png").toString());

    /**
     * Allows for a door to be added to a level.
     * @param x Where in tiles the item is located.
     * @param y Where in tiles the item is located.
     */
    public Door(final int x, final int y) {
        super(x, y);
    }

    /**
     * Ends the level if open and the player wins or loses
     * depending on what entered the door, player is a win,
     * floor or smart thief is a loss.
     * needs thief, player, and game to be implemented.
     */
    public void interact() {
        Level level = GameManager.getCurrentLevel();
        if (level.containsNoLootAndLevers()) {
            this.closed = false;
            //if the player uses the door win
            //if a thief uses the door
            System.out.println(this.collector);
            if (collector instanceof Player player) {
                // win
                level.finishLevel();
            } else if (collector instanceof FloorThief) {
                level.failLevel("Enemy used the door!");
            }
        } else {
            this.setCollector(null); // the collection has failed
        }
    }

    /**
     * Gets if the door is closed (true) or open (false).
     * @return the state of the door.
     */
    public boolean getClosed() {
        return closed;
    }

    /**
     * Sets the state of the door to closed (true) or open (false).
     * @param closed closed gets set to true (closed) or false (open).
     */
    public void setClosed(final boolean closed) {
        this.closed = closed;
    }


    /**
     * Draws a door at its position.
     * @param gc the class the door will be drawn with.
     */
    public void draw(final GraphicsContext gc) {
        gc.drawImage(image, getX() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                getY() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                Tile.HALF_TILE_SIZE, Tile.HALF_TILE_SIZE);
    }
}
