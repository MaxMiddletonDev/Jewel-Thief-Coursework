package cs230.group29se.jewelthief;

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
    BLUE;

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
        };
    }
}
