package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Colour;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Represents a tile in the game, which is divided into four quadrants,
 * each with its own colour.
 * @author Gustas Rove
 */
public class Tile {
    private final static int TILE_SIZE = 64;
    private final static int HALF_TILE_SIZE = TILE_SIZE / 2;
    private final static int BORDER_WIDTH = 2;
    private final int posX;
    private final int posY;
    private Colour[] colours = new Colour[4];

    /**
     * Constructs a Tile with the specified position and colours.
     * @param posX the x position of the tile in the grid
     * @param posY the y position of the tile in the grid
     * @param colours an array of four colours for the quadrants
     */
    public Tile(int posX,int posY, Colour[] colours) {
        this.posX = posX;
        this.posY = posY;
        this.colours = colours;
    }

    /**
     * Determines if a move from this tile to the specified target tile is valid.
     * @param target the destination tile to check against.
     * @return True if the tiles share a color, false if otherwise.
     */
    public boolean isValidMove(Tile target) {
        if (target == null) {
            return false;
        }
        for (Colour playerColour : this.colours) {
            for (Colour targetColour : target.getColours()) {
                if (playerColour == targetColour) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the array of colours assigned with this tile.
     * Made to compare colours between tiles.
     * @return an array of Colour objects assigned to said tile.
     */
    private Colour[] getColours() {
        return this.colours;
    }

    /**
     * Retrieves the x-coordinate of the tile's position on the grid.
     * @return the x-coordinate.
     */
    public int getX() {
        return posX;
    }

    /**
     * Retrieves the y-coordinate of the tile's position on the grid.
     * @return the y-coordinate.
     */
    public int getY() {
        return posY;
    }

    public int[] getPosition() {
        return new int[]{posX, posY};
    }

    /**
     * Draws the tile on the given GraphicsContext.
     * @param gc the GraphicsContext to draw on
     */
    public void draw(GraphicsContext gc) {
        Arrays.sort(colours, Comparator.comparing(Colour::toString));

        Color tlColour = colours[0].toFxColor();
        Color trColour = colours[1].toFxColor();
        Color blColour = colours[2].toFxColor();
        Color brColour = colours[3].toFxColor();

        // Draw the four quadrants
        gc.setFill(tlColour);
        int pixelX = posX * TILE_SIZE + BORDER_WIDTH;
        int pixelY = posY * TILE_SIZE + BORDER_WIDTH;
        gc.fillRect(pixelX, pixelY, HALF_TILE_SIZE, HALF_TILE_SIZE);

        gc.setFill(trColour);
        gc.fillRect(pixelX + HALF_TILE_SIZE, pixelY, HALF_TILE_SIZE, HALF_TILE_SIZE);

        gc.setFill(blColour);
        gc.fillRect(pixelX, pixelY + HALF_TILE_SIZE, HALF_TILE_SIZE, HALF_TILE_SIZE);

        gc.setFill(brColour);
        gc.fillRect(pixelX + HALF_TILE_SIZE, pixelY + HALF_TILE_SIZE, HALF_TILE_SIZE, HALF_TILE_SIZE);

        // Draw border
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(BORDER_WIDTH);
        gc.strokeRect(pixelX, pixelY, TILE_SIZE, TILE_SIZE);

        /*
    int px = gridX * TILE_SIZE;
    int py = gridY * TILE_SIZE;

    gc.setFill(tile.getColor());
    gc.fillRect(px, py, TILE_SIZE, TILE_SIZE);
         */
    }
}
