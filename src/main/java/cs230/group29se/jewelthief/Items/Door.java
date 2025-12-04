package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * Door allows a player or thief to leave the level when open.
 * @author Charlie
 * @version 0.2 - interact is not implemented - needs game before implementation.
 */
public class Door extends Item {

    /**
     * Door starts closed till all loot is gone.
     */
    private boolean closed = true;

    private final Image image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/DOOR.png").toString());

    /**
     * Allows for a door to be added to a level.
     * @param x Where in tiles the item is located.
     * @param y Where in tiles the item is located.
     */
    public Door(final int x , final int y) {
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



    public void draw(GraphicsContext gc) {
        gc.drawImage(image, getX()*64, getY()*64);
    }
}
