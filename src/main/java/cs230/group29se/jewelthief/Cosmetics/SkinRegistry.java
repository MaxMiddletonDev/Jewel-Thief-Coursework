package cs230.group29se.jewelthief.Cosmetics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry for managing and storing all available skins in the game.
 * Skins are statically initialized and can be retrieved by their unique ID.
 * This class provides methods to register, retrieve, and list all skins.
 * <p>
 * @author Gustas Rove
 */
public class SkinRegistry {
    // A map storing all registered skins, keyed by their unique SkinId.
    private static final Map<SkinId, Skin> SKINS = new HashMap<>();

    // Static block to initialize and register all predefined skins.
    static {
        register(new Skin(SkinId.DEFAULT_GUY, "Default Guy", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER.png"));
        register(new Skin(SkinId.SPEEDSTER, "Speedster", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER_SPEEDSTER.png"));
        register(new Skin(SkinId.SURVIVOR, "Survivor", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER_SURVIVOR.png"));
        register(new Skin(SkinId.DEMO_MAN, "Demolition Expert", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER_DEMOMAN.png"));
        register(new Skin(SkinId.MONEY_MAN, "Money Man ", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER_MONEYMAN.png"));
        register(new Skin(SkinId.PRO, "Professional", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER_PRO.png"));
        register(new Skin(SkinId.TANK, "Tank", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER_TANK.png"));
    }

    /**
     * Registers a new skin in the registry.
     *
     * @param skin The skin to be registered.
     */
    private static void register(Skin skin) {
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
     * @return The skin associated with the given ID, or the default skin if not found.
     */
    public static Skin getById(SkinId skinId) {
        return SKINS.getOrDefault(skinId, SKINS.get(SkinId.DEFAULT_GUY));
    }
}
