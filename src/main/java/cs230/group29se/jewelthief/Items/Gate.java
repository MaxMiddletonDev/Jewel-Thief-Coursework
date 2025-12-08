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

    public static final int XPOS_TILE_ADJUSTMENT = 2;
    public static final int YPOS_TILE_ADJUSTMENT = 1;
    private final Colour colour;
    private final int posX;
    private final int posY;
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
                "/cs230/group29se/jewelthief/Images/Items/Gates & Levers/GATE"
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
        gc.drawImage(image, getX() * Tile.TILE_SIZE + XPOS_TILE_ADJUSTMENT,
                getY() * Tile.TILE_SIZE + YPOS_TILE_ADJUSTMENT,
                Tile.TILE_SIZE, Tile.TILE_SIZE);
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
