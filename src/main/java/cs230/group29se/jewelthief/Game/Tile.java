package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Colour;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Comparator;

public class Tile {
    private final static int TILE_SIZE = 64;
    private final static int HALF_TILE_SIZE = TILE_SIZE / 2;
    private final static int BORDER_WIDTH = 2;
    private final int posX;
    private final int posY;
    private Colour[] colours = new Colour[4];

    public Tile(int posX,int posY, Colour[] colours) {
        this.posX = posX;
        this.posY = posY;
        this.colours = colours;
    }

    /**
     * Draws the tile on the given GraphicsContext.
     * @param gc
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
