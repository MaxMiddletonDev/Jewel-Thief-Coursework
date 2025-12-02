package cs230.group29se.jewelthief.Persistence.Storage;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that shows properties for each level (static definition from levelX.txt).
 * @author Iyaad
 * @version 1.0
 */
public class LevelDef {
    public String levelId;                 // e.g. "1" for level1.txt
    public List<String> tiles;            // tile rows
    public ArrayList<EntityDef> entities; // all entities (PLAYER, FLYING, LOOT, GATE, etc.)
    public int width;                     // SIZE w h -> w
    public int height;                    // SIZE w h -> h
    public List<EntityDef> npcStartStates; // FLYING, FOLLOWER, SMART, ...
    public List<EntityDef> playerStartState; // optional list if you want, usually size 0 or 1
    public int timeLimitSec;              // TIME value
    public List<EntityDef> gates;         // GATE entities
    public List<EntityDef> items;         // LOOT, BOMB, LEVER, ...
    public EntityDef playerStart;         // for convenience; direct reference to the PLAYER entity
}