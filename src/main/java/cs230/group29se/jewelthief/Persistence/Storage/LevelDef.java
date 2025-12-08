package cs230.group29se.jewelthief.Persistence.Storage;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that shows properties for each level (static definition from levelX.txt).
 * @author Iyaad
 * @version 1.0
 */
public class LevelDef {
    private String levelId;                 // e.g. "1" for level8.txt
    private List<String> tiles;            // tile rows
    private ArrayList<EntityDef> entities; // all entities (PLAYER, FLYING, LOOT, GATE, etc.)
    private int width;                     // SIZE w h -> w
    private int height;                    // SIZE w h -> h
    private List<EntityDef> npcStartStates; // FLYING, FOLLOWER, SMART, ...
    private List<EntityDef> playerStartState; // optional list if you want, usually size 0 or 1
    private int timeLimitSec;              // TIME value
    private List<EntityDef> gates;         // GATE entities
    private List<EntityDef> items;         // LOOT, BOMB, LEVER, ...
    private EntityDef playerStart;         // for convenience; direct reference to the PLAYER entity

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public List<String> getTiles() {
        return tiles;
    }

    public void setTiles(List<String> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<EntityDef> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<EntityDef> entities) {
        this.entities = entities;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<EntityDef> getNpcStartStates() {
        return npcStartStates;
    }

    public void setNpcStartStates(List<EntityDef> npcStartStates) {
        this.npcStartStates = npcStartStates;
    }

    public List<EntityDef> getPlayerStartState() {
        return playerStartState;
    }

    public void setPlayerStartState(List<EntityDef> playerStartState) {
        this.playerStartState = playerStartState;
    }

    public int getTimeLimitSec() {
        return timeLimitSec;
    }

    public void setTimeLimitSec(int timeLimitSec) {
        this.timeLimitSec = timeLimitSec;
    }

    public List<EntityDef> getGates() {
        return gates;
    }

    public void setGates(List<EntityDef> gates) {
        this.gates = gates;
    }

    public List<EntityDef> getItems() {
        return items;
    }

    public void setItems(List<EntityDef> items) {
        this.items = items;
    }

    public EntityDef getPlayerStart() {
        return playerStart;
    }

    public void setPlayerStart(EntityDef playerStart) {
        this.playerStart = playerStart;
    }
}