package cs230.group29se.jewelthief.Persistence.Profile;
import java.util.Map;

/**
 * Current save state of the game.
 * @author Iyaad
 * @version 1.0
 */
public class SaveData {
    private String levelId;                 // or levelNumber if you prefer int
    private String profileName;             // who owns this save
    private Object[] playerState;           // heterogeneous snapshot pieces e.g. (x,y,hp,inventory, etc.)
    private int elapsedSeconds;       // cumulative play time
    private Map<String, Object> npcStates;  // keyed by npc identifier
    private Map<String, Object> gates;      // keyed by gate identifier
    private Map<String, Object> items;      // keyed by item identifier

    public SaveData() {}

    public String getLevelId() { return levelId; }
    public void setLevelId(String levelId) { this.levelId = levelId; }

    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }

    public Object[] getPlayerState() { return playerState; }
    public void setPlayerState(Object[] playerState) { this.playerState = playerState; }

    public int getElapsedSeconds() { return elapsedSeconds; }
    public void setElapsedSeconds(int elapsedSeconds) { this.elapsedSeconds = elapsedSeconds; }

    public Map<String, Object> getNpcStates() { return npcStates; }
    public void setNpcStates(Map<String, Object> npcStates) { this.npcStates = npcStates; }

    public Map<String, Object> getGates() { return gates; }
    public void setGates(Map<String, Object> gates) { this.gates = gates; }

    public Map<String, Object> getItems() { return items; }
    public void setItems(Map<String, Object> items) { this.items = items; }
}
