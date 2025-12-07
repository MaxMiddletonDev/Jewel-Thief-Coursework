package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Game.Colour;
import cs230.group29se.jewelthief.Game.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * An obstacle that blocks the player that can be opened.
 * Will implement Remove when remove() can be made.
 * @author Charlie
 * @version 1.1
 */
public final class Gate implements Remove {
    /**
     * The colour the gate is.
     */
    private final Colour colour;
    /**
     * The width of all gates.
     */
    private static final double WIDTH = 50.0;
    /**
     * The height of all gates.
     */
    private static final double HEIGHT = 50.0;

    /**
     * How far across the gate is from the left.
     */
    private final int posX;
    /**
     * How far down the gate is from the top.
     */
    private final int posY;
    /**
     * Image of the lever, based on the colour.
     */
    private final Image image;
    /**
     * Allows for gates to be made in the level.
     * @param colour The colour of the gate.
     * @param x the row the gate is in.
     * @param y the column the gate is in.
     */
    public Gate(final Colour colour, final int x, final int y) {
        this.colour = colour;
        posX = x;
        posY = y;
        image = new Image(getClass().getResource(
                "/cs230/group29se/jewelthief/Images/GATE"
                        + colour.name() + ".png").toString());
    }

    /**
     * Gets the colour of the gate.
     * @return the colour of the gate.
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Draws this gate onto the screen at its x,y.
     * @param gc the class the gate will be drawn with.
     */
    public void draw(final GraphicsContext gc) {
        gc.drawImage(image, getX() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                getY() * Tile.TILE_SIZE + Tile.HALF_TILE_SIZE / 2,
                Tile.HALF_TILE_SIZE, Tile.HALF_TILE_SIZE);
    }

    /**
     * Get how far from the left the gate is.
     * @return the x position of the gate.
     */
    public int getX() {
        return posX;
    }

    /**
     * Get how far from the top the gate is.
     * @return the y position of the gate.
     */
    public int getY() {
        return posY;
    }
}
