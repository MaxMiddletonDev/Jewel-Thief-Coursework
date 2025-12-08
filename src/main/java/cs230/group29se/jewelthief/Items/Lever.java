package cs230.group29se.jewelthief.Items;

import java.util.ArrayList;

import cs230.group29se.jewelthief.Game.Colour;
import cs230.group29se.jewelthief.Game.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Levers can be collected to open gates of the same colour.
 * @author Charlie
 * @version 1.0
 */
public class Lever extends Destroyable {

    private final Colour colour;
    private final ArrayList<Gate> gates = new ArrayList<Gate>();
    private static final double WIDTH = 50.0;
    private static final double HEIGHT = 50.0;
    private final Image image;

    /**
     * Allows for a lever to be made with a position and colour.
     * @param colour Colour of the lever.
     * @param x Where in tiles the item is located
     * @param y Where in tiles the item is located
     */
    public Lever(final Colour colour, final int x, final int y) {

        super(x, y);
        this.colour = colour;

        image = new Image(getClass().getResource(
                "/cs230/group29se/jewelthief/Images/Items/Gates & Levers/LEVER"
                        + colour.name() + ".png").toString());
    }

    /**
     * Gets the colour of the lever.
     * @return The colour of the lever.
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Links lever to a gate.
     * @param gate gate to be added to gates.
     */
    public void addGate(final Gate gate) {
        gates.add(gate);
    }

    /**
     * Removes all gates of the same colour then itself.
     */
    public void interact() {
        for (Gate gate : gates) {
            gate.remove(gate);
        }
        this.remove(this);
    }

    /**
     * Draws a lever at its position.
     * @param gc the class the lever will be drawn with.
     */
    public void draw(final GraphicsContext gc) {
        gc.drawImage(image, getX() * Tile.TILE_SIZE + 2,
                getY() * Tile.TILE_SIZE + 1,
                Tile.TILE_SIZE, Tile.TILE_SIZE);
    }
}
