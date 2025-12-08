package cs230.group29se.jewelthief.Entities;

import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Game.Tile;
import cs230.group29se.jewelthief.Items.Item;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This selects a random important item and patrols,
 * a rectangular perimeter around it.
 * - Patrols a rectangle around a selected item (Door, Lever preferred).
 * - If the item is removed or collected,
 *   selects a new item and builds a new patrol
 * - On collision with Player: inflicts hit if possible
 * - On collision with other NPCs (not Camper): eliminates them
 * - Does not collect items
 *
 * @author Gustas Rove
 */
public class Camper extends NonPlayableCharacter {

    public static final double MOVE_COOLDOWN_SECONDS = 0.4;
    public static final double HIT_COOLDOWN_SECONDS = 1.5;
    public static final int RECT_HALF_WIDTH = 2;
    public static final int RECT_HALF_HEIGHT = 1;
    public static final int NEXT_TILE_STEP_ADDER = 1;
    public static final int HALF_WIDTH = 1;
    public static final int HALF_HEIGHT = 1;

    private static final String ITEM_SELECTED_FORMAT =
            "Camper %s selected important item %s at (%d, %d)";
    private static final String ITEM_FALLBACK_FORMAT =
            "Camper %s found no important items, "
                    + "picked random item %s at (%d, %d)";
    public static final int MIN_DIMENSION = 1;
    public static final String IMAGE_PATH =
            "/cs230/group29se/jewelthief/Images/Entities/NPCs/CAMPER.png";
    private ArrayList<String> campableItemNames = new ArrayList<String>() {
        public static final String DOOR = "Door";
        public static final String LEVER = "Lever";

        {
            add(DOOR);
            add(LEVER);
        }
    };

    private final Level level;
    private Item targetItem;
    private final List<Tile> patrolPath = new ArrayList<>();
    private int pathIndex = 0;
    private final int rectHalfWidth;
    private final int rectHalfHeight;
    private final Random rnd = new Random();

    private final Image image =
            new Image(getClass().getResource(IMAGE_PATH).toString());


    /**
     * @param startingTile   starting tile for the camper
     * @param direction      initial facing direction
     * @param level          the level reference (used to find items and tiles)
     * @param id             identifier for saving/debugging
     * @param rectHalfWidth  half-width of patrol rectangle in tiles (>=1)
     * @param rectHalfHeight half-height of patrol rectangle in tiles (>=1)
     */
    public Camper(final Tile startingTile,
                  final Direction direction,
                  final Level level,
                  final String id,
                  final int rectHalfWidth,
                  final int rectHalfHeight) {
        super(startingTile, direction);
        this.id = id;
        this.level = level;
        this.rectHalfWidth = Math.max(1, rectHalfWidth);
        this.rectHalfHeight = Math.max(1, rectHalfHeight);

        // tune movement / hit cooldowns as desired
        setMoveCooldownSeconds(MOVE_COOLDOWN_SECONDS);
        setHitCooldownSeconds(HIT_COOLDOWN_SECONDS);

        selectRandomImportantItemAndBuildPath();
    }

    /**
     * Simplified constructor with default rectangle size 2x1.
     * @param startingTile is the campers initial tile.
     * @param direction is the direction they will face.
     * @param level is the current level they are on
     * @param id is the id for Camper
     */
    public Camper(final Tile startingTile,
                  final Direction direction,
                  final Level level,
                  final String id) {
        this(startingTile,
                direction,
                level,
                id,
                RECT_HALF_WIDTH,
                RECT_HALF_HEIGHT);
    }

    /**
     * Camper does not collect items.
     */
    @Override
    public void collectItem(final Item item) {
        // Camper does not pick up items
    }

    /**
     * On collision with another character:
     *  - If Player: inflict hit if possible.
     *  - If NonPlayableCharacter (not Camper): eliminate it
     */
    @Override
    public void onCollisionWith(final MoveableCharacter other) {
        if (other instanceof Player) {
            if (canHit()) {
                ((Player) other).getHit();
                resetHitCooldown();
            }
        } else if (other instanceof NonPlayableCharacter
                && !(other instanceof Camper)) {
            other.setAliveTo(false);
        }
    }

    /**
     * Moves along the patrol path around the target item.
     * If the target item is missing or collected,
     * reselects a new item and builds a new path.
     */
    @Override
    public void move() {
        if (!isAlive()) {
            return;
        }

        // ensure cooldown respects NonPlayableCharacter logic
        if (!canMove()) {
            return;
        }

        // If there's no valid patrol path, try to reselect
        if (patrolPath.isEmpty() || targetItem == null
                || targetItem.getCollector() != null) {
            selectRandomImportantItemAndBuildPath();
            if (patrolPath.isEmpty()) {
                return;
            }
        }

        // step to next tile on the patrol path
        Tile next = patrolPath.get(pathIndex);
        if (next != null) {
            setCurrentTile(next);
        }

        pathIndex = (pathIndex + NEXT_TILE_STEP_ADDER) % patrolPath.size();
    }

    /**
     * Attempts to select a random important item from the level
     * then builds a rectangular perimeter path around it.
     * If no important items are found, picks any random item as fallback.
     */
    private void selectRandomImportantItemAndBuildPath() {
        patrolPath.clear();
        targetItem = null;

        List<Item> items = level.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }

        // collect important items
        List<Item> important = new ArrayList<>();
        for (Item it : items) {
            if (it == null) {
                continue;
            }
            String name = it.getClass().getSimpleName();
            if (campableItemNames.contains(name)) {
                important.add(it);
            }
        }

        String logTemplate;

        if (!important.isEmpty()) {
            targetItem = important.get(rnd.nextInt(important.size()));
            logTemplate = ITEM_SELECTED_FORMAT;
        } else {
            targetItem = items.get(rnd.nextInt(items.size()));
            logTemplate = ITEM_FALLBACK_FORMAT;
        }

        // Apply the format string with: ID, Item Name, X, Y
        System.out.println(String.format(
                logTemplate,
                this.id,
                targetItem.getClass().getSimpleName(),
                targetItem.getX(),
                targetItem.getY()
        ));

        int centerX = targetItem.getX();
        int centerY = targetItem.getY();

        buildPerimeterPath(centerX, centerY, rectHalfWidth, rectHalfHeight);

        // if built path is empty, try a smaller rectangle fallback
        if (patrolPath.isEmpty() && (rectHalfWidth > MIN_DIMENSION
                || rectHalfHeight > MIN_DIMENSION)) {
            buildPerimeterPath(centerX, centerY, HALF_WIDTH, HALF_HEIGHT);
        }

        // reset path index to start near current position
        if (!patrolPath.isEmpty()) {
            pathIndex = findNearestPathIndex(getCurrentTile());
        }
    }

    /**
     * Finds the index of the tile in the patrol path nearest to the given tile.
     * @param from tile to measure from
     * @return index of nearest tile in patrolPath
     */
    private int findNearestPathIndex(final Tile from) {
        if (from == null || patrolPath.isEmpty()) {
            return 0;
        }
        int best = 0;
        int bestDist = Integer.MAX_VALUE;
        int[] pos = from.getPosition();
        for (int i = 0; i < patrolPath.size(); i++) {
            Tile t = patrolPath.get(i);
            if (t == null) {
                continue;
            }
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
     * Builds the perimeter path around center tile.
     * Adds tiles in clockwise order.
     * @param centerX Used for the item it's going to surround with X
     * @param centerY Used for the item it's going to surround with Y
     * @param halfW This will calculate the area of the item with Width
     * @param halfH This will calculate the area of the item with Height
     */
    private void buildPerimeterPath(final int centerX,
                                    final int centerY,
                                    final int halfW,
                                    final int halfH) {
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
    private void addTileIfWalkable(final int x, final int y) {
        if (x < 0 || y < 0 || x >= level.getWidth() || y >= level.getHeight()) {
            return;
        }
        Tile t = level.getTile(x, y);
        if (!t.isWalkable()) {
            return;
        }
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
    public void setProtected(final boolean value) {
        isProtected = value;
    }

    /**
     * Gets specific enemies sprite.
     */
    @Override
    public Image getImage() {
        return this.image;
    }
}
