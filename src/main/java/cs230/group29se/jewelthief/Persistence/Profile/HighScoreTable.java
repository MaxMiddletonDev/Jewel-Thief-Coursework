package cs230.group29se.jewelthief.Persistence.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A highScore table to hold and manage entries, of n length.
 * The table is defaulted to store 5 entries.
 * Representing the highest sc ores achieved by players.
 *
 * @author Iyaad
 * @version 1.0
 */
public class HighScoreTable {

    private static final int INITIAL_INDEX = 0;

    /**
     * This is the list of high score entries organized by the table.
     */
    private final List<HighScoreEntry> entries = new ArrayList<>();

    /**
     * This method retrieves an unchangeable list of the high score entries.
     * This ensures that the list cannot be modified externally.
     *
     * @return An unchangeable list of the high score entries.
     */
    public List<HighScoreEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * This method adds a new high score entry to the table.
     * The entry is added to the list.
     * While the list is sorted in descending order by scores
     * and only the highest remains.
     *
     * @param e the high score entry to be added.
     */
    public void add(HighScoreEntry e) {
        entries.add(e);
        entries.sort(Comparator.comparingInt(HighScoreEntry::getScore).reversed());
    }

    /**
     * It retrieves the top {@code n} high score entries from the table.
     * if {@code n} is greater than the number of the entries, then the method
     * returns all the entries.
     * The lis is sorted in descending order by scores added.
     *
     * @param n The maximum number of entries to be returned.
     * @return A list containing up to {@code n} top high score entries.
     */
    public List<HighScoreEntry> topN(int n) {
        return entries.subList(INITIAL_INDEX, Math.min(n, entries.size()));
    }

    /**
     * This method clears all high score from the table.
     * It removes all entries from the list therefore resetting the table
     */
    public void clear() {
        entries.clear();
    }
}
