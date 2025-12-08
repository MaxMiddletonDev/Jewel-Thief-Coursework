package cs230.group29se.jewelthief.Persistence.Profile;

import cs230.group29se.jewelthief.Persistence.Storage.LevelDef;
import cs230.group29se.jewelthief.Persistence.Storage.EntityDef;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating SaveData instances.
 * Provides methods to create SaveData from LevelDef.
 * @author Iyaad
 * @version 1.0
 */
public class SaveFactory {

    private static final int MS_TO_SECOND = 1000;
    private static final int INITIAL_VALUE = 0;


    /**
     * Creates a fresh SaveData from a LevelDef for a given profile.
     * This is used when there is no existing JSON save yet.
     * @param def The LevelDef to base the SaveData on.
     * @param profileName The profile name associated with the save.
     *
     * @return A new SaveData instance initialized from the LevelDef.
     */
    public static SaveData fromLevelDef(LevelDef def, String profileName) {
        SaveData s = new SaveData();
        s.setProfileName(profileName);
        s.setLevelId(def.getLevelId());
        s.setElapsedSeconds(INITIAL_VALUE);
        s.setTimeRemainingMs(def.getTimeLimitSec() * MS_TO_SECOND);

        // Example simple player state: [x, y]
        if (def.getPlayerStart() != null) {
            Object[] playerState = new Object[] {def.getPlayerStart().getX(), def.getPlayerStart().getY()};
            s.setPlayerState(playerState);
        } else {
            s.setPlayerState(new Object[] {INITIAL_VALUE, INITIAL_VALUE}); // fallback
        }

        // store npcStates / gates / items as simple maps keyed by "type#x#y" as defined in their constructors
        Map<String, Object> npcStates = new HashMap<>();
        s.setNpcStates(npcStates);

        Map<String, Object> gates = new HashMap<>();
        for (EntityDef e : def.getGates()) {
            String key = "GATE#" + e.getX() + "#" + e.getY();
            gates.put(key, e.getArg1()); // e.g. colour "R"
        }
        s.setGates(gates);

        Map<String, Object> items = new HashMap<>();
        for (EntityDef e : def.getItems()) {
            String key = e.getType() + "#" + e.getX() + "#" + e.getY();
            items.put(key, e.getArg1()); // e.g. "DOLLAR"
        }
        s.setItems(items);

        return s;
    }
}