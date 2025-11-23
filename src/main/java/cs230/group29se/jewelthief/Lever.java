package cs230.group29se.jewelthief;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 * Levers can be collected to open gates of the same colour.
 * @author Charlie
 * @version 1.0
 */
public class Lever extends Destroyable {
    /**
     * The colour of the lever.
     */
    private final Colour colour;

    /**
     * A list of gates that are the same colour as the lever.
     */
    private final ArrayList<Gate> gates = new ArrayList<Gate>();

    /**
     * Width of all levels.
     */
    private static final double WIDTH = 50.0;

    /**
     * Height of all levers.
     */
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
        image = new Image("Images/LEVER"+colour.name()+".png");
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
            gate.remove();
        }
        this.remove();
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), 40, 40);
    }
}
