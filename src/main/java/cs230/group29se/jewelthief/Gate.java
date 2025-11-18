package cs230.group29se.jewelthief;

/**
 * An obstacle that blocks the player that can be opened.
 * Will implement Remove when remove() can be made.
 * @author Charlie
 * @version 1.0
 */
public class Gate implements Remove {
    /**
     * The colour the gate is
     */
    private Colour colour;

    /**
     *  It is not in the design but gate should
     *  have a position? i cant think what it would do maybe data
     *  preservation but would that data not be gotten by the tile itself
     *  {@code private int[] position;}
     */

    /**
     * Allows for gates to be made in the l
     * @param colour The colour of the gate
     */
    public Gate(Colour colour) {
        this.colour = colour;
    }

    /**
     * Gets the colour of the gate.
     * @return the colour of the gate.
     */
    public Colour getColour() {
        return colour;
    }

}
