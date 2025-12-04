package cs230.group29se.jewelthief.Items;

import cs230.group29se.jewelthief.Colour;
import cs230.group29se.jewelthief.Remove;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * An obstacle that blocks the player that can be opened.
 * Will implement Remove when remove() can be made.
 * @author Charlie
 * @version 1.1
 */
public class Gate implements Remove {
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
        image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/GATE" + colour.name() + ".png").toString());
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
        gc.drawImage(image, posX*64, posY*64, 32 ,32);
    }

    public int getX() {
        return posX;
    }

    public int getY (){
        return posY;
    }
}
