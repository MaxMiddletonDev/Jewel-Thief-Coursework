package cs230.group29se.jewelthief.Cosmetics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * statically generates all the skins on initialisation
 * @author Gustas Rove
 */
public class SkinRegistry {
    private static final Map<SkinId, Skin> SKINS = new HashMap<>();

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
     *
     * @param skin
     */
    private static void register(Skin skin) {
        SKINS.put(skin.getId(), skin);
    }

    public static Collection<Skin> getAllSkins() {
        return SKINS.values();
    }

    public static Skin getById(SkinId skinId) {
        return SKINS.getOrDefault(skinId, SKINS.get(SkinId.DEFAULT_GUY));
    }
}
