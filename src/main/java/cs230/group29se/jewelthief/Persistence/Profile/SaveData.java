package cs230.group29se.jewelthief.Persistence.Profile;
import java.util.Map;

/**
 * SaveData — 当前游戏的“存档快照”数据模型。<br>
 * SaveData — Data model representing a snapshot of the current game save.
 *
 * <p>这是一个可变的 DTO（仅承载数据），供持久化层读取/写入，常被序列化为 JSON。<br>
 * This is a mutable DTO (data holder only) used by the persistence layer to read/write,
 * typically serialized to JSON.</p>
 *
 * <p>本类不包含业务逻辑；具体的读写由 {@code FileStore}/{@code JsonSerializer} 实现。<br>
 * No business logic lives here; reading/writing is handled by {@code FileStore}/{@code JsonSerializer}.</p>
 *
 * <p><b>JSON 约束：</b> {@code Object[]} 与 {@code Map<String,Object>} 的 value 必须是可 JSON 序列化的类型。<br>
 * <b>JSON constraint:</b> Values inside {@code Object[]} and {@code Map<String,Object>} must be JSON-serializable.</p>
 *
 * @author You
 * @since 1.0
 * @see cs230.group29se.jewelthief.Persistence.Storage.FileStore
 * @see cs230.group29se.jewelthief.Persistence.Storage.JsonSerializer
 */
public class SaveData {
    /**
     * 存档对应的关卡/场景标识（例如 "level-03"），非空。<br>
     * The level/scene identifier for this save (e.g., "level-03"), non-null.
     */
    private String levelId;                 // or levelNumber if you prefer int
    /**
     * 拥有此存档的玩家档案名（用于与 Profile/排行榜关联），非空。<br>
     * The owner profile name associated with this save, non-null.
     */
    private String profileName;             // who owns this save
    /**
     * 存档时刻的玩家“异构快照”（如位置、生命、背包等）。元素必须可 JSON 序列化。<br>
     * Heterogeneous snapshot of the player at save time (position, HP, inventory, etc.). Elements must be JSON-serializable.
     */
    private Object[] playerState;           // heterogeneous snapshot pieces e.g. (x,y,hp,inventory, etc.)
    /**
     * 自开始到存档的累计时长（秒），非负。<br>
     * Cumulative play time in seconds, non-negative.
     */
    private int elapsedSeconds;       // cumulative play time
    /**
     * NPC 状态表（以 NPC 标识为 key）。value 必须可 JSON 序列化。<br>
     * NPC states keyed by NPC identifier. Values must be JSON-serializable.
     */
    private Map<String, Object> npcStates;  // keyed by npc identifier
    /**
     * 闸门/门状态表（以 gate 标识为 key）。value 必须可 JSON 序列化。<br>
     * Gate/door states keyed by gate identifier. Values must be JSON-serializable.
     */
    private Map<String, Object> gates;      // keyed by gate identifier
    /**
     * 物品状态表（以 item 标识为 key，如数量、耐久等）。value 必须可 JSON 序列化。<br>
     * Item states keyed by item identifier (e.g., count, durability). Values must be JSON-serializable.
     */
    private Map<String, Object> items;      // keyed by item identifier
    /**
     * 无参构造器：供 JSON 反序列化框架使用。<br>
     * No-arg constructor required by JSON deserializers.
     */

    public SaveData() {}
    /**
     * 获取关卡标识。<br>
     * Gets the level identifier.
     * @return 关卡/场景 id（非空） / non-null level/scene id
     */
    public String getLevelId() { return levelId; }
    /**
     * 设置关卡标识。<br>
     * Sets the level identifier.
     * @param levelId 合法的关卡/场景 id / valid level/scene id
     */
    public void setLevelId(String levelId) { this.levelId = levelId; }
    /**
     * 获取玩家档案名。<br>
     * Gets the owner profile name.
     * @return 玩家档案名（非空） / non-null profile name
     */
    public String getProfileName() { return profileName; }
    /**
     * 设置玩家档案名。<br>
     * Sets the owner profile name.
     * @param profileName 非空玩家档案名 / non-null profile name
     */
    public void setProfileName(String profileName) { this.profileName = profileName; }
    /**
     * 获取玩家异构快照。元素需可 JSON 序列化。<br>
     * Gets the heterogeneous player snapshot. Elements must be JSON-serializable.
     * @return 快照数组 / snapshot array
     */
    public Object[] getPlayerState() { return playerState; }
    /**
     * 设置玩家异构快照。元素需可 JSON 序列化。<br>
     * Sets the heterogeneous player snapshot. Elements must be JSON-serializable.
     * @param playerState 快照数组 / snapshot array
     */
    public void setPlayerState(Object[] playerState) { this.playerState = playerState; }
    /**
     * 获取累计时长（秒）。<br>
     * Gets cumulative elapsed time in seconds.
     * @return 非负秒数 / non-negative seconds
     */
    public int getElapsedSeconds() { return elapsedSeconds; }
    /**
     * 设置累计时长（秒）。<br>
     * Sets cumulative elapsed time in seconds.
     * @param elapsedSeconds 非负秒数 / non-negative seconds
     */
    public void setElapsedSeconds(int elapsedSeconds) { this.elapsedSeconds = elapsedSeconds; }
    /**
     * 获取 NPC 状态表（npcId → state）。<br>
     * Gets NPC states (npcId → state).
     * @return 映射 / map
     */
    public Map<String, Object> getNpcStates() { return npcStates; }
    /**
     * 设置 NPC 状态表（npcId → state），value 需可 JSON 序列化。<br>
     * Sets NPC states (npcId → state). Values must be JSON-serializable.
     * @param npcStates 映射 / map
     */
    public void setNpcStates(Map<String, Object> npcStates) { this.npcStates = npcStates; }
    /**
     * 获取闸门/门状态表（gateId → state）。<br>
     * Gets gate/door states (gateId → state).
     * @return 映射 / map
     */
    public Map<String, Object> getGates() { return gates; }
    /**
     * 设置闸门/门状态表（gateId → state），value 需可 JSON 序列化。<br>
     * Sets gate/door states (gateId → state). Values must be JSON-serializable.
     * @param gates 映射 / map
     */
    public void setGates(Map<String, Object> gates) { this.gates = gates; }
    /**
     * 获取物品状态表（itemId → state/count）。<br>
     * Gets item states (itemId → state/count).
     * @return 映射 / map
     */
    public Map<String, Object> getItems() { return items; }
    /**
     * 设置物品状态表（itemId → state/count），value 需可 JSON 序列化。<br>
     * Sets item states (itemId → state/count). Values must be JSON-serializable.
     * @param items 映射 / map
     */
    public void setItems(Map<String, Object> items) { this.items = items; }
}
