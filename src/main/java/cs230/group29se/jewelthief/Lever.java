package cs230.group29se.jewelthief;

import java.util.ArrayList;

/**
 * Levers can be collected to open gates of the same colour.
 * @author Charlie
 * @version 1.0
 */
public class Lever extends Destroyable {
    /**
     * The colour of the lever.
     */
    private Colour colour;
    /**
     * A list of gates that are the same colour as the lever.
     */
    private final ArrayList<Gate> gates = new ArrayList<Gate>();

    /**
     * Allows for a lever to be made with a position and colour.
     * @param colour Colour of the lever.
     * @param position Position of the lever.
     */
    public Lever(final Colour colour, final int[] position) {
        super(position);
        this.colour = colour;
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
}
