package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Persistence.Profile.HighScoreEntry;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import java.util.List;
import java.util.Map;

/**
 * Helper class for loading game high scores.
 * @author Iyaad
 * @version 1.0
 */
public class GameHighScoresHelper {
    /**
     * Returns the top 10 high-scores.
     * @return List of high-scores.
     */
    public static List<HighScoreEntry> loadGlobalHighScores() {
        return PersistenceManager.loadGlobalHighScores();
    }

    /**
     * Returns a map of players and their high scores.
     * @return a map of names and their high scores
     */
    public static Map<String, List<HighScoreEntry>> loadPerLevelHighScores() {
        return PersistenceManager.loadPerLevelHighScores();
    }
}
