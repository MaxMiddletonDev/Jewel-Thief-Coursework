package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Camper: selects a random important item and patrols a rectangular perimeter around it.
 *
 * - Patrols a rectangle around a selected item (Door, Lever preferred).
 * - If the item is removed or collected, selects a new item and builds a new patrol
 * - On collision with Player: inflicts hit if possible
 * - On collision with other NPCs (not Camper): eliminates them
 * - Does not collect items
 *
 * @author Gustas Rove
 */
public class Camper extends NonPlayableCharacter {

    private ArrayList<String> campableItemNames = new ArrayList<String>() {
        {
            add("Door");
            add("Lever");
        }
    };

    private final Level level;
    private Item targetItem;
    private final List<Tile> patrolPath = new ArrayList<>();
    private int pathIndex = 0;
    private final int rectHalfWidth;
    private final int rectHalfHeight;
    private final Random rnd = new Random();

    private final Image image = new Image(getClass().getResource("/cs230/group29se/jewelthief/Images/Entities/NPCs/CAMPER.png").toString());


    /**
     * @param startingTile   starting tile for the camper
     * @param direction      initial facing direction (unused for pathing but kept)
     * @param level          the level reference (used to find items and tiles)
     * @param id             identifier for saving/debugging
     * @param rectHalfWidth  half-width of patrol rectangle in tiles (>=1)
     * @param rectHalfHeight half-height of patrol rectangle in tiles (>=1)
     */
    public Camper(Tile startingTile, Direction direction, Level level, String id, int rectHalfWidth, int rectHalfHeight) {
        super(startingTile, direction);
        this.id = id;
        this.level = level;
        this.rectHalfWidth = Math.max(1, rectHalfWidth);
        this.rectHalfHeight = Math.max(1, rectHalfHeight);

        // tune movement / hit cooldowns as desired
        setMoveCooldownSeconds(0.4); // moves every 0.4s (adjust)
        setHitCooldownSeconds(1.5);

        selectRandomImportantItemAndBuildPath();
    }

    /**
     * Simplified constructor with default rectangle size 2x1.
     */
    public Camper(Tile startingTile, Direction direction, Level level, String id) {
        this(startingTile, direction, level, id, 2, 1);
    }

    /**
     * Camper does not collect items.
     */
    @Override
    public void collectItem(Item item) {
        // Camper does not pick up items
    }

    /**
     * On collision with another character:
     *  - If Player: inflict hit if possible
     *  - If NonPlayableCharacter (not Camper): eliminate it
     */
    @Override
    public void onCollisionWith(MoveableCharacter other) {
        if (other instanceof Player) {
            if (canHit()) {
                ((Player) other).getHit();
                resetHitCooldown();
            }
        } else if (other instanceof NonPlayableCharacter && !(other instanceof Camper)) {
            other.setAliveTo(false);
        }
    }

    /**
     * Moves along the patrol path around the target item.
     * If the target item is missing or collected, reselects a new item and builds a new path.
     */
    @Override
    public void move() {
        if (!isAlive) return;

        // ensure cooldown respects NonPlayableCharacter logic
        if (!canMove()) return;

        // If there's no valid patrol path (target missing or item removed), try to reselect
        if (patrolPath.isEmpty() || targetItem == null || targetItem.getCollector() != null) {
            selectRandomImportantItemAndBuildPath();
            if (patrolPath.isEmpty()) return;
        }

        // step to next tile on the patrol path
        Tile next = patrolPath.get(pathIndex);
        if (next != null) {
            currentTile = next;
        }

        pathIndex = (pathIndex + 1) % patrolPath.size();
    }

    /**
     * Attempts to select a random important item from the level then builds a rectangular perimeter path around it.
     * If no important items are found, picks any random item as fallback.
     */
    private void selectRandomImportantItemAndBuildPath() {
        patrolPath.clear();
        targetItem = null;

        List<Item> items = level.getItems();
        if (items == null || items.isEmpty()) return;

        // collect important items
        List<Item> important = new ArrayList<>();
        for (Item it : items) {
            if (it == null) continue;
            String name = it.getClass().getSimpleName();
            if (campableItemNames.contains(name)) {
                important.add(it);
            }
        }

        // pick random important item if any
        if (!important.isEmpty()) {
            targetItem = important.get(rnd.nextInt(important.size()));
            System.out.println("Camper " + id + " selected important item " + targetItem.getClass());
        } else {
            // fallback: pick any random item
            targetItem = items.get(rnd.nextInt(items.size()));
            System.out.println("Camper " + id + " found no important items, picked random item " + targetItem.getClass());
        }

        if (targetItem == null) return;

        int centerX = targetItem.getX();
        int centerY = targetItem.getY();

        buildPerimeterPath(centerX, centerY, rectHalfWidth, rectHalfHeight);

        // if built path is empty, try a smaller rectangle fallback
        if (patrolPath.isEmpty() && (rectHalfWidth > 1 || rectHalfHeight > 1)) {
            buildPerimeterPath(centerX, centerY, 1, 1);
        }

        // reset path index to start near current position (choose nearest path index)
        if (!patrolPath.isEmpty()) {
            pathIndex = findNearestPathIndex(currentTile);
        }
    }

    /**
     * Finds the index of the tile in the patrol path nearest to the given tile.
     * @param from tile to measure from
     * @return index of nearest tile in patrolPath
     */
    private int findNearestPathIndex(Tile from) {
        if (from == null || patrolPath.isEmpty()) return 0;
        int best = 0;
        int bestDist = Integer.MAX_VALUE;
        int[] pos = from.getPosition();
        for (int i = 0; i < patrolPath.size(); i++) {
            Tile t = patrolPath.get(i);
            if (t == null) continue;
            int[] p = t.getPosition();
            int d = Math.abs(p[0] - pos[0]) + Math.abs(p[1] - pos[1]);
            if (d < bestDist) {
                bestDist = d;
                best = i;
            }
        }
        return best;
    }

    /**
     * Builds the perimeter path around center tile. Adds tiles in clockwise order.
     */
    private void buildPerimeterPath(int centerX, int centerY, int halfW, int halfH) {
        patrolPath.clear();

        int minX = Math.max(0, centerX - halfW);
        int maxX = Math.min(level.getWidth() - 1, centerX + halfW);
        int minY = Math.max(0, centerY - halfH);
        int maxY = Math.min(level.getHeight() - 1, centerY + halfH);

        // Top edge left->right
        for (int x = minX; x <= maxX; x++) {
            addTileIfWalkable(x, minY);
        }
        // Right edge top->bottom (skip corner)
        for (int y = minY + 1; y <= maxY; y++) {
            addTileIfWalkable(maxX, y);
        }
        // Bottom edge right->left (skip corner)
        if (maxY != minY) {
            for (int x = maxX - 1; x >= minX; x--) {
                addTileIfWalkable(x, maxY);
            }
        }
        // Left edge bottom->top (skip corner)
        if (maxX != minX) {
            for (int y = maxY - 1; y > minY; y--) {
                addTileIfWalkable(minX, y);
            }
        }
    }

    /**
     * Adds the tile at (x,y) to the patrol path if it is walkable.
     * @param x tile x-coordinate
     * @param y tile y-coordinate
     */
    private void addTileIfWalkable(int x, int y) {
        if (x < 0 || y < 0 || x >= level.getWidth() || y >= level.getHeight()) return;
        Tile t = level.getTile(x, y);
        if(!t.isWalkable()) return;
        if (t == null) return;
        patrolPath.add(t);
    }

    /**
     * Checks if the camper is protected (cannot be hit).
     * @return true if protected, false otherwise
     */
    @Override
    public boolean isProtected() {
        return isProtected;
    }

    /**
     * Sets the protected status of the camper.
     * @param value true to set as protected, false otherwise
     */
    @Override
    public void setProtected(boolean value) {
        isProtected = value;
    }

    /**
     * Draw Function for Camper, shapes it to the tile size.
     */
    @Override
    public void draw(GraphicsContext gc) {
        int tileSize = Tile.TILE_SIZE;

        double x = currentTile.getX() * tileSize;
        double y = currentTile.getY() * tileSize;

        gc.drawImage(image, x, y, tileSize, tileSize);
    }
}
