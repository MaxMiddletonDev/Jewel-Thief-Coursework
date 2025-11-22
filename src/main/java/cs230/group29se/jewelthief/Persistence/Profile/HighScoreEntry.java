package cs230.group29se.jewelthief.Persistence.Profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores individual highscores of a user.
 * @author Iyaad
 * @version 1.0
 */
public class HighScoreEntry {
    private String profileName;
    private int score;
    private String timestamp; // kept as String to match UML

    @JsonCreator
    public HighScoreEntry(
            @JsonProperty("profileName") String profileName,
            @JsonProperty("score") int score,
            @JsonProperty("timestamp") String timestamp) {
        this.profileName = profileName;
        this.score = score;
        this.timestamp = timestamp;
    }

    public HighScoreEntry() { }

    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override public String toString() {
        return "HighScoreEntry{" +
                "profileName='" + profileName + '\'' +
                ", score=" + score +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}