package cs230.group29se.jewelthief.Persistence.Profile;

import cs230.group29se.jewelthief.Persistence.Profile.SaveData;
import cs230.group29se.jewelthief.Persistence.Storage.LevelDef;
import cs230.group29se.jewelthief.Persistence.Storage.EntityDef;

import java.util.HashMap;
import java.util.Map;

public class SaveFactory {

    /**
     * Creates a fresh SaveData from a LevelDef for a given profile.
     * This is used when there is no existing JSON save yet.
     */
    public static SaveData fromLevelDef(LevelDef def, String profileName) {
        SaveData s = new SaveData();
        s.setProfileName(profileName);
        s.setLevelId(def.levelId);
        s.setElapsedSeconds(0);
        s.setTimeRemainingMs(def.timeLimitSec * 1000);

        // Example simple player state: [x, y]
        if (def.playerStart != null) {
            Object[] playerState = new Object[] { def.playerStart.x, def.playerStart.y };
            s.setPlayerState(playerState);
        } else {
            s.setPlayerState(new Object[] { 0, 0 }); // fallback
        }

        // Example: store npcStates / gates / items as simple maps keyed by "type#x#y"
        Map<String, Object> npcStates = new HashMap<>();
        for (EntityDef e : def.npcStartStates) {
            String key = e.type + "#" + e.x + "#" + e.y;
            npcStates.put(key, e.arg1); // or a small DTO if you want
        }
        s.setNpcStates(npcStates);

        Map<String, Object> gates = new HashMap<>();
        for (EntityDef e : def.gates) {
            String key = "GATE#" + e.x + "#" + e.y;
            gates.put(key, e.arg1); // e.g. colour "R"
        }
        s.setGates(gates);

        Map<String, Object> items = new HashMap<>();
        for (EntityDef e : def.items) {
            String key = e.type + "#" + e.x + "#" + e.y;
            items.put(key, e.arg1); // e.g. "DOLLAR"
        }
        s.setItems(items);

        return s;
    }
}