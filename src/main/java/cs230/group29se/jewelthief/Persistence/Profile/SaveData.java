package cs230.group29se.jewelthief.Persistence.Profile;
import java.util.Map;

/**
 * SaveData — Data model representing a snapshot of the current game save.
 * This is a mutable DTO (data holder only) used by the persistence layer to read/write,
 * typically serialized to JSON.</p>
 *
 * No business logic lives here; reading/writing is handled by {@code FileStore}/{@code JsonSerializer}.</p>
 *
 * <b>JSON constraint:</b> Values inside {@code Object[]} and {@code Map<String,Object>} must be JSON-serializable.</p>
 *
 * @author You
 * @since 1.0
 * @see cs230.group29se.jewelthief.Persistence.Storage.FileStore
 * @see cs230.group29se.jewelthief.Persistence.Storage.JsonSerializer
 */
public class SaveData {
    /**
     * The level/scene identifier for this save (e.g., "level-03"), non-null.
     */
    private String levelId;                 // or levelNumber if you prefer int
    /**
     * The owner profile name associated with this save, non-null.
     */
    private String profileName;             // who owns this save
    /**
     * Heterogeneous snapshot of the player at save time (position, HP, inventory, etc.). Elements must be JSON-serializable.
     */
    private Object[] playerState;           // heterogeneous snapshot pieces e.g. (x,y,hp,inventory, etc.)
    /**
     * Cumulative play time in seconds, non-negative.
     */
    private int elapsedSeconds;       // cumulative play time
    /**
     * NPC states keyed by NPC identifier. Values must be JSON-serializable.
     */
    private Map<String, Object> npcStates;  // keyed by npc identifier
    /**
     * Gate/door states keyed by gate identifier. Values must be JSON-serializable.
     */
    private Map<String, Object> gates;      // keyed by gate identifier
    /**
     * Item states keyed by item identifier (e.g., count, durability). Values must be JSON-serializable.
     */
    private Map<String, Object> items;      // keyed by item identifier
    /**
     * No-arg constructor required by JSON deserializers.
     */

    public SaveData() {}
    /**
     * Gets the level identifier.
     * @return  non-null level/scene id
     */
    public String getLevelId() { return levelId; }
    /**
     * Sets the level identifier.
     * @param levelId  valid level/scene id
     */
    public void setLevelId(String levelId) { this.levelId = levelId; }
    /**
     * Gets the owner profile name.
     * @return non-null profile name
     */
    public String getProfileName() { return profileName; }
    /**
     * Sets the owner profile name.
     * @param profileName  non-null profile name
     */
    public void setProfileName(String profileName) { this.profileName = profileName; }
    /**
     * Gets the heterogeneous player snapshot. Elements must be JSON-serializable.
     * @return  snapshot array
     */
    public Object[] getPlayerState() { return playerState; }
    /**
     * Sets the heterogeneous player snapshot. Elements must be JSON-serializable.
     * @param playerState snapshot array
     */
    public void setPlayerState(Object[] playerState) { this.playerState = playerState; }
    /**
     * Gets cumulative elapsed time in seconds.
     * @return non-negative seconds
     */
    public int getElapsedSeconds() { return elapsedSeconds; }
    /**
     * Sets cumulative elapsed time in seconds.
     * @param elapsedSeconds  non-negative seconds
     */
    public void setElapsedSeconds(int elapsedSeconds) { this.elapsedSeconds = elapsedSeconds; }
    /**
     * Gets NPC states (npcId → state).
     * @return map
     */
    public Map<String, Object> getNpcStates() { return npcStates; }
    /**
     * Sets NPC states (npcId → state). Values must be JSON-serializable.
     * @param npcStates  map
     */
    public void setNpcStates(Map<String, Object> npcStates) { this.npcStates = npcStates; }
    /**
     * Gets gate/door states (gateId → state).
     * @return  map
     */
    public Map<String, Object> getGates() { return gates; }
    /**
     * Sets gate/door states (gateId → state). Values must be JSON-serializable.
     * @param gates  map
     */
    public void setGates(Map<String, Object> gates) { this.gates = gates; }
    /**
     * Gets item states (itemId → state/count).
     * @return map
     */
    public Map<String, Object> getItems() { return items; }
    /**
     * Sets item states (itemId → state/count). Values must be JSON-serializable.
     * @param items  map
     */
    public void setItems(Map<String, Object> items) { this.items = items; }
}
