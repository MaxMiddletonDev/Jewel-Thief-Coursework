package cs230.group29se.jewelthief.Persistence.Storage;

import java.util.List;

/**
 * Class that shows properties for each level.
 *  @author Iyaad
 * @version 1.0
 */

public class LevelDef {
    public int width;
    public int height;
    public List<String> tiles;
    public List<Object> npcStartStates;
    public List<Object> playerStartState;
    public int timeLimitSec;
    public List<Object> gates;
    public List<Object> items;
}