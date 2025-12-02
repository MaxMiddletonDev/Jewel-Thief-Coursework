package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import javafx.scene.canvas.GraphicsContext;


/**
 * Loot can be one of various types that increase the score of the
 * level when picked up by the player.
 * @author Charlie
 * @version 1.0 - interact added.
 */
public class Loot extends Destroyable {

    /**
     * The type of loot that the instance will be based on.
     */
    private final LootEnum type;

    /**
     * Allows for loot to be created with a position
     *  and set value from existing LootEnum.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     * @param type the type of loot from a set of types.
     */
    public Loot(final LootEnum type, final int x, final int y) {
        super(x, y);
        this.type = type;
    }


    /**
     * Changes the score of the level:
     *  - If Player collects  → increase score.
     * Then removes the loot from play.
     */
    @Override
    public void interact() {
        // Get current level from the game manager
        Level currentLevel = GameManager.getCurrentLevel();
        if (currentLevel != null) {
                currentLevel.addScore(type.getValue());
        }

        // Remove the loot from the level so it can't be reused
        remove(this);
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

    /**
     * Draws this loot onto the screen at its x,y.
     * @param gc the class the loot will be drawn with.
     */
    public void draw(GraphicsContext gc) {
        gc.drawImage(type.getImage(),getX()*64,getY()*64);
    }
}
