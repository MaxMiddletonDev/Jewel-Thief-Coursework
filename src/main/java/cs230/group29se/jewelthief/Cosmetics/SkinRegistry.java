package cs230.group29se.jewelthief.Cosmetics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry for managing and storing all available skins in the game.
 * Skins are statically initialized and can be retrieved by their unique ID.
 * This class provides methods to register, retrieve, and list all skins.
 *
 * <p>
 * All file paths and skin names are extracted into constants to avoid
 * magic literals and improve maintainability.
 *
 * @author Gustas Rove
 */
public class SkinRegistry {

    private static final String BASE_PATH =
            "/cs230/group29se/jewelthief/Images/Entities/Player/";

    private static final String DEFAULT_GUY_NAME       = "Default Guy";
    private static final String SPEEDSTER_NAME         = "Speedster";
    private static final String SURVIVOR_NAME          = "Survivor";
    private static final String DEMO_MAN_NAME          = "Demolition Expert";
    private static final String MONEY_MAN_NAME         = "Money Man";
    private static final String PRO_NAME               = "Professional";
    private static final String TANK_NAME              = "Tank";

    private static final String DEFAULT_GUY_FILE = BASE_PATH + "PLAYER.png";
    private static final String SPEEDSTER_FILE   =
            BASE_PATH + "PLAYER_SPEEDSTER.png";
    private static final String SURVIVOR_FILE    =
            BASE_PATH + "PLAYER_SURVIVOR.png";
    private static final String DEMO_MAN_FILE    =
            BASE_PATH + "PLAYER_DEMOMAN.png";
    private static final String MONEY_MAN_FILE   =
            BASE_PATH + "PLAYER_MONEYMAN.png";
    private static final String PRO_FILE         =
            BASE_PATH + "PLAYER_PRO.png";
    private static final String TANK_FILE        =
            BASE_PATH + "PLAYER_TANK.png";

    // A map storing all registered skins, keyed by their unique SkinId.
    private static final Map<SkinId, Skin> SKINS = new HashMap<>();

    // Static block to initialize and register all predefined skins.
    static {
        register(new Skin(SkinId.DEFAULT_GUY,
                DEFAULT_GUY_NAME, DEFAULT_GUY_FILE));
        register(new Skin(SkinId.SPEEDSTER,
                SPEEDSTER_NAME,   SPEEDSTER_FILE));
        register(new Skin(SkinId.SURVIVOR,
                SURVIVOR_NAME,    SURVIVOR_FILE));
        register(new Skin(SkinId.DEMO_MAN,
                DEMO_MAN_NAME,    DEMO_MAN_FILE));
        register(new Skin(SkinId.MONEY_MAN,
                MONEY_MAN_NAME,   MONEY_MAN_FILE));
        register(new Skin(SkinId.PRO,
                PRO_NAME,         PRO_FILE));
        register(new Skin(SkinId.TANK,
                TANK_NAME,        TANK_FILE));
    }

    /**
     * Registers a new skin in the registry.
     *
     * @param skin The skin to be registered.
     */
    private static void register(final Skin skin) {
        SKINS.put(skin.getId(), skin);
    }

    /**
     * Retrieves all registered skins.
     *
     * @return A collection of all registered skins.
     */
    public static Collection<Skin> getAllSkins() {
        return SKINS.values();
    }

    /**
     * Retrieves a skin by its unique ID.
     * If the skin ID is not found, the default skin is returned.
     *
     * @param skinId The unique ID of the skin to retrieve.
     * @return The skin associated with the given ID,
     * or the default skin if not found.
     */
    public static Skin getById(final SkinId skinId) {
        return SKINS.getOrDefault(skinId, SKINS.get(SkinId.DEFAULT_GUY));
    }
}
