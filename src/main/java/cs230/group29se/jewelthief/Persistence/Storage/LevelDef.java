package cs230.group29se.jewelthief.Persistence.Storage;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the static definition of a level,
 * including its properties and entities,
 * as defined in a level file (e.g., levelX.txt).
 * <p>
 * This class contains information about the
 * level's dimensions, tiles, entities,
 * time limit, and other relevant properties.
 * </p>
 *
 * @author Iyaad
 * @version 1.0
 */
public class LevelDef {
    // The logical ID of the level (e.g., "1" for level8.txt)
    private String levelId;
    private List<String> tiles;
    private ArrayList<EntityDef> entities; // All entities in the level
    private int width;
    private int height;
    private List<EntityDef> npcStartStates;
    // Optional list for player start states, usually size 0 or 1
    private List<EntityDef> playerStartState;
    private int timeLimitSec;
    private List<EntityDef> gates;
    private List<EntityDef> items;
    private EntityDef playerStart;

    /**
     * Gets the logical ID of the level.
     *
     * @return The level ID.
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * Sets the logical ID of the level.
     *
     * @param levelId The level ID to set.
     */
    public void setLevelId(final String levelId) {
        this.levelId = levelId;
    }

    /**
     * Gets the tile rows representing the level layout.
     *
     * @return A list of tile rows.
     */
    public List<String> getTiles() {
        return tiles;
    }

    /**
     * Sets the tile rows representing the level layout.
     *
     * @param tiles The list of tile rows to set.
     */
    public void setTiles(final List<String> tiles) {
        this.tiles = tiles;
    }

    /**
     * Gets all entities in the level.
     *
     * @return A list of entities.
     */
    public ArrayList<EntityDef> getEntities() {
        return entities;
    }

    /**
     * Sets all entities in the level.
     *
     * @param entities The list of entities to set.
     */
    public void setEntities(final ArrayList<EntityDef> entities) {
        this.entities = entities;
    }

    /**
     * Gets the width of the level.
     *
     * @return The width of the level.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the level.
     *
     * @param width The width to set.
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * Gets the height of the level.
     *
     * @return The height of the level.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the level.
     *
     * @param height The height to set.
     */
    public void setHeight(final int height) {
        this.height = height;
    }

    /**
     * Gets the starting states of NPCs in the level.
     *
     * @return A list of NPC starting states.
     */
    public List<EntityDef> getNpcStartStates() {
        return npcStartStates;
    }

    /**
     * Sets the starting states of NPCs in the level.
     *
     * @param npcStartStates The list of NPC starting states to set.
     */
    public void setNpcStartStates(
            final List<EntityDef> npcStartStates) {
        this.npcStartStates = npcStartStates;
    }

    /**
     * Gets the optional list of player start states.
     *
     * @return A list of player start states.
     */
    public List<EntityDef> getPlayerStartState() {
        return playerStartState;
    }

    /**
     * Sets the optional list of player start states.
     *
     * @param playerStartState The list of player start states to set.
     */
    public void setPlayerStartState(
            final List<EntityDef> playerStartState) {
        this.playerStartState = playerStartState;
    }

    /**
     * Gets the time limit for the level in seconds.
     *
     * @return The time limit in seconds.
     */
    public int getTimeLimitSec() {
        return timeLimitSec;
    }

    /**
     * Sets the time limit for the level in seconds.
     *
     * @param timeLimitSec The time limit to set.
     */
    public void setTimeLimitSec(final int timeLimitSec) {
        this.timeLimitSec = timeLimitSec;
    }

    /**
     * Gets the GATE entities in the level.
     *
     * @return A list of GATE entities.
     */
    public List<EntityDef> getGates() {
        return gates;
    }

    /**
     * Sets the GATE entities in the level.
     *
     * @param gates The list of GATE entities to set.
     */
    public void setGates(final List<EntityDef> gates) {
        this.gates = gates;
    }

    /**
     * Gets the items in the level.
     *
     * @return A list of items.
     */
    public List<EntityDef> getItems() {
        return items;
    }

    /**
     * Sets the items in the level.
     *
     * @param items The list of items to set.
     */
    public void setItems(final List<EntityDef> items) {
        this.items = items;
    }

    /**
     * Gets the direct reference to the PLAYER entity.
     *
     * @return The PLAYER entity.
     */
    public EntityDef getPlayerStart() {
        return playerStart;
    }

    /**
     * Sets the direct reference to the PLAYER entity.
     *
     * @param playerStart The PLAYER entity to set.
     */
    public void setPlayerStart(final EntityDef playerStart) {
        this.playerStart = playerStart;
    }
}
