package cs230.group29se.jewelthief.Persistence.Storage;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that shows properties for each level.
 * @author Iyaad
 * @version 1.0
 */
public class LevelDef {
    private String levelId;
    private List<String> tiles;
    private ArrayList<EntityDef> entities;
    private int width;
    private int height;
    private List<EntityDef> npcStartStates;
    private List<EntityDef> playerStartState;
    private int timeLimitSec;
    private List<EntityDef> gates;
    private List<EntityDef> items;
    private EntityDef playerStart;

    /**
     * Returns the identifier of this level.
     * @return the level id
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * Sets the identifier of this level.
     * @param levelId the new level id
     */
    public void setLevelId(final String levelId) {
        this.levelId = levelId;
    }

    /**
     * Returns the raw tile data for this level.
     * @return list of tile strings
     */
    public List<String> getTiles() {
        return tiles;
    }

    /**
     * Sets the raw tile data for this level.
     * @param tiles list of tile strings
     */
    public void setTiles(final List<String> tiles) {
        this.tiles = tiles;
    }

    /**
     * Returns the list of entity definitions in this level.
     * @return list of entities
     */
    public ArrayList<EntityDef> getEntities() {
        return entities;
    }

    /**
     * Sets the list of entity definitions in this level.
     * @param entities new list of entities
     */
    public void setEntities(final ArrayList<EntityDef> entities) {
        this.entities = entities;
    }

    /**
     * Returns the width of the level in tiles.
     * @return level width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the level in tiles.
     * @param width new level width
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * Returns the height of the level in tiles.
     * @return level height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the level in tiles.
     * @param height new level height
     */
    public void setHeight(final int height) {
        this.height = height;
    }

    /**
     * Returns the initial states for NPCs in this level.
     * @return list of NPC start state definitions
     */
    public List<EntityDef> getNpcStartStates() {
        return npcStartStates;
    }

    /**
     * Sets the initial states for NPCs in this level.
     * @param npcStartStates list of NPC start state definitions
     */
    public void setNpcStartStates(final List<EntityDef> npcStartStates) {
        this.npcStartStates = npcStartStates;
    }

    /**
     * Returns the initial state(s) for the player in this level.
     * @return list containing player start state definitions
     */
    public List<EntityDef> getPlayerStartState() {
        return playerStartState;
    }

    /**
     * Sets the initial state(s) for the player in this level.
     * @param playerStartState list containing player start state definitions
     */
    public void setPlayerStartState(final List<EntityDef> playerStartState) {
        this.playerStartState = playerStartState;
    }

    /**
     * Returns the time limit for this level in seconds.
     * @return time limit in seconds
     */
    public int getTimeLimitSec() {
        return timeLimitSec;
    }

    /**
     * Sets the time limit for this level in seconds.
     * @param timeLimitSec new time limit in seconds
     */
    public void setTimeLimitSec(final int timeLimitSec) {
        this.timeLimitSec = timeLimitSec;
    }

    /**
     * Returns the gate entity definitions in this level.
     * @return list of gate definitions
     */
    public List<EntityDef> getGates() {
        return gates;
    }

    /**
     * Sets the gate entity definitions in this level.
     * @param gates list of gate definitions
     */
    public void setGates(final List<EntityDef> gates) {
        this.gates = gates;
    }

    /**
     * Returns the item entity definitions in this level.
     * @return list of item definitions
     */
    public List<EntityDef> getItems() {
        return items;
    }

    /**
     * Sets the item entity definitions in this level.
     * @param items list of item definitions
     */
    public void setItems(final List<EntityDef> items) {
        this.items = items;
    }

    /**
     * Returns the starting position definition for the player.
     * @return player start definition
     */
    public EntityDef getPlayerStart() {
        return playerStart;
    }

    /**
     * Sets the starting position definition for the player.
     * @param playerStart new player start definition
     */
    public void setPlayerStart(final EntityDef playerStart) {
        this.playerStart = playerStart;
    }
}
