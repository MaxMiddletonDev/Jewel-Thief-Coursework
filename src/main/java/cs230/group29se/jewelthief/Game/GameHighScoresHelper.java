package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Persistence.Profile.HighScoreEntry;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;

import java.util.List;
import java.util.Map;

public class GameHighScoresHelper {

    private static final PersistenceManager PM = GameManager.getPersistenceManager();

    public static List<HighScoreEntry> loadGlobalHighScores() {
        return PM.loadGlobalHighScores();
    }

    public static Map<String, List<HighScoreEntry>> loadPerLevelHighScores() {
        return PM.loadPerLevelHighScores();
    }
}