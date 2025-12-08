package cs230.group29se.jewelthief.Cosmetics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SkinRegistry {
    private static final Map<SkinId, Skin> SKINS = new HashMap<>();

    static {
        register(new Skin(SkinId.DEFAULT_GUY, "Default Guy", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER.png"));
        register(new Skin(SkinId.SPEED_GUY, "Speed guy", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER_SKIN_1.png"));
        register(new Skin(SkinId.CAT_CAT, "Cat Cat", "/cs230/group29se/jewelthief/Images/Entities/Player/PLAYER_SKIN_2.png"));
        register(new Skin(SkinId.SPEEDSTER, "Speedster", "/cs230/group29se/jewelthief/Images/Achievements/Speedster.png"));
        register(new Skin(SkinId.SURVIVOR, "Survivor", "/cs230/group29se/jewelthief/Images/Achievements/Survivor.png"));
        register(new Skin(SkinId.DEMO_MAN, "Demolition Expert", "/cs230/group29se/jewelthief/Images/Achievements/Demoman.png"));
        register(new Skin(SkinId.MONEY_MAN, "Money Man ", "/cs230/group29se/jewelthief/Images/Achievements/MoneyMan.png"));
        register(new Skin(SkinId.PRO, "Professional", "/cs230/group29se/jewelthief/Images/Achievements/Pro.png"));
        register(new Skin(SkinId.TANK, "Tank", "/cs230/group29se/jewelthief/Images/Achievements/Tank.png"));
    }

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
