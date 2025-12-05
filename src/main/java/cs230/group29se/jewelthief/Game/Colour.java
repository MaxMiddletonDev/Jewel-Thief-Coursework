package cs230.group29se.jewelthief.Game;

import javafx.scene.paint.Color;

/**
 * An enum of the different possible colours for levers, gates and tiles.
 * @author Charlie, Gustas
 * @version 1.1
 */
public enum Colour {
    RED,
    YELLOW,
    GREEN,
    BLUE,
    CYAN,
    MAGENTA;

    /**
     * Converts the Colour enum to a JavaFX Color.
     * @return the corresponding JavaFX Color.
     */
    public Color toFxColor() {
        return switch (this) {
            case RED -> Color.RED;
            case YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
            case BLUE -> Color.BLUE;
            case CYAN -> Color.CYAN;
            case MAGENTA -> Color.MAGENTA;
        };
    }
}
