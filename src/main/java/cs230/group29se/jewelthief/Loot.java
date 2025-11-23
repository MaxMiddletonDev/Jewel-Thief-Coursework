package cs230.group29se.jewelthief;

import javafx.scene.canvas.GraphicsContext;


/**
 * Loot can be one of various types that increase the score of the
 * level when picked up by the player.
 * @author Charlie
 * @version 0.1 - interact needs to be implemented - needs level to be implemented.
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
     * Interact increases the score based on the value of the loot.
     * Needs level to be implemented.
     */
    public void interact() {

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
        gc.drawImage(type.getImage(),getX()*40,getY()*40,40,40);
    }
}
