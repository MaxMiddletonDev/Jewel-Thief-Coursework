package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Items.Gate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a tile in the game, which is divided into four quadrants,
 * each with its own colour.
 * @author Gustas Rove
 */
public class Tile {
    public final static int TILE_SIZE = 64;
    public final static int HALF_TILE_SIZE = TILE_SIZE / 2;
    private final static int BORDER_WIDTH = 2;
    private final int posX;
    private final int posY;
    private Colour[] colours = new Colour[4];
    //The item or gate or nothing occupying a tile
    private Object occupying = null;

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

    public static int getTileSize() {
        return TILE_SIZE;
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
     * Checks if a tile has a Floor Thief's assigned colour
     */
    public boolean containsColour(Colour assignedColour) {
        for (Colour colour : this.colours) {
            if (colour == assignedColour) {
                return true;
            }
        }
        return false;
    }

    /**
     * Draws the tile on the given GraphicsContext.
     * @param gc the GraphicsContext to draw on
     */
    public void draw(GraphicsContext gc) {
//        Arrays.sort(colours, Comparator.comparing(Colour::toString));

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
    }


    /**
     * Changes what loot or gate is on a tile. Can be null.
     * @param occupying the object that will occupy the tile.
     */
    public void setOccupying(Object occupying) {
        this.occupying = occupying;
    }

    /**
     * Get what item or gate may be occupying the tile.
     * @return the item or gate occupying the tile,
     * if null then the tile is not occupied.
     */
    public Object getOccupying(){
        return occupying;
    }

    /**
     * Checks if a tile is walkable
     * @return true if the tile is walkable
     */
    public boolean isWalkable() {
        return !(occupying instanceof Gate);
    }

    /**
     * Checks if a tile has a bomb
     * @return true if there is a bomb on a tile
     */
    public boolean hasBomb() {
        return occupying instanceof cs230.group29se.jewelthief.Items.Bomb;
    }

    /**
     * checks if a tile has a gate
     * @return true if there is a gate on a tile
     */
    public boolean hasGate() {
        return occupying instanceof cs230.group29se.jewelthief.Items.Gate;
    }

    /**
     * Checks a gates colour
     * @return gate colour
     */
    public Colour getGateColour() {
        if (occupying instanceof cs230.group29se.jewelthief.Items.Gate) {
            return ((cs230.group29se.jewelthief.Items.Gate)occupying).getColour();
        }
        return null;
    }

}
