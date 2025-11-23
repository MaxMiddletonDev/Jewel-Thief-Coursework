package cs230.group29se.jewelthief.Persistence.Profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores individual high scores of a user
 * It is used for storing the player's profile.
 * It is used for the games persistence and retrieving of high score.
 * @author Iyaad
 * @version 1.0
 */
public class HighScoreEntry {
    /**
     * The name of the player's profile - associated with the score.
     */
    private String profileName;
    /**
     * The score attained by the player.
     */
    private int score;
    /**
     * The time when the score was attained.
     * It is kept as String to match the UML diagram.
     */
    private String timestamp;

    /**
     * This method is used to construct a new High Score Entry.
     *
     * @param profileName It is the name of the player's profile.
     * @param score It is the score attained by the player.
     * @param timestamp it is the time when the score is achieved by the player.
     */
    @JsonCreator
    public HighScoreEntry(
            @JsonProperty("profileName") String profileName,
            @JsonProperty("score") int score,
            @JsonProperty("timestamp") String timestamp) {
        this.profileName = profileName;
        this.score = score;
        this.timestamp = timestamp;
    }

    /**
     * This is a default no argument constructor for the high score entry.
     */
    public HighScoreEntry() { }

    /**
     * It retrieves the name of the player's profile related to HighScoreEntry.
     *
     *  @return The profile name as String.
     */
    public String getProfileName() {
        return profileName; }

    /**
     * It sets the name of the player's profile for the high score entry.
     *
     * @param profileName The new profile name to be assigned.
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName; }

    /**
     * This method is used to retrieve the score attained by the player.
     *
     * @return The player's score as an integer; It must be null.
     */
    public int getScore() {
        return score; }

    /**
     * This method is used to set the score for the high score entry.
     *
     * @param score The new score to be assigned; It mustn't be negative.
     */
    public void setScore(int score) {
        this.score = score; }

    /**
     * This method is used to retrieve the time the player attains a score.
     *
     * @return The timestamp value as String.
     */
    public String getTimestamp() {
        return timestamp; }

    /**
     * This method is used to set the timestamp for the high score entry.
     *
     * @param timestamp The new timestamp value to assign;it mustn't be null.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * It is used to return a String representation of the HighScoreEntry.
     *
     * @return String representation of the HighScoreEntry.
     */
    @Override public String toString() {
        return "HighScoreEntry{"
                + "profileName='" + profileName + '\''
                + ", score=" + score
                + ", timestamp='" + timestamp + '\''
                + '}';
    }
}
