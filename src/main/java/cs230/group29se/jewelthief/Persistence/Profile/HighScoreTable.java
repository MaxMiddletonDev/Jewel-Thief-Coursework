package cs230.group29se.jewelthief.Persistence.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A highScore table to hold and manage entries, of n length. (should be 5)
 * @author Iyaad
 * @version 1.0
 */
public class HighScoreTable {
    private final List<HighScoreEntry> entries = new ArrayList<>();

    public List<HighScoreEntry> getEntries() { return Collections.unmodifiableList(entries); }

    public void add(HighScoreEntry e) {
        entries.add(e);
        entries.sort(Comparator.comparingInt(HighScoreEntry::getScore).reversed());
    }

    public List<HighScoreEntry> topN(int n) {
        return entries.subList(0, Math.min(n, entries.size()));
    }

    public void clear() { entries.clear(); }
}