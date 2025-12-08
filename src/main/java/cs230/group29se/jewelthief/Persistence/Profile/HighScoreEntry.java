package cs230.group29se.jewelthief.Persistence.Profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
/**
 * This class stores individual high scores of a user
 * It is used for storing the player's profile.
 * It is used for the games persistence and retrieving of high score.
 * @author Iyaad
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HighScoreEntry {

    private String profileName;
    private int score;
    private String timestamp;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * This method is used to construct a new High Score Entry.
     *
     * @param profileName It is the name of the player's profile.
     * @param score It is the score attained by the player.
     * @param timestamp it is the time when the score is achieved by the player.
     */
    @JsonCreator
    public HighScoreEntry(
           final @JsonProperty("profileName") String profileName,
           final @JsonProperty("score") int score,
           final @JsonProperty("timestamp") String timestamp) {
        this.profileName = profileName;
        this.score = score;
        this.timestamp = timestamp;
    }
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
    public void setProfileName(final String profileName) {
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
    public void setScore(final int score) {
        this.score = score; }

    /**Derived property for the table – NOT stored in JSON.
     *
     * @return The time in local timezone format
     */
    public String getFormattedTime() {
        if (timestamp == null || timestamp.isEmpty()) {
            return "";
        }
        try {
            Instant instant = Instant.parse(timestamp);
            var zoned = instant.atZone(ZoneId.systemDefault());
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return zoned.format(fmt);
        } catch (Exception e) {
            return timestamp;
        }
    }
    /**
     * It is used to return a String representation of the HighScoreEntry.
     *
     * @return String representation of the HighScoreEntry.
     */
    @Override
    public String toString() {
        return "HighScoreEntry{"
                + "profileName='" + profileName + '\''
                + ", score=" + score
                + ", timestamp='" + timestamp + '\''
                + '}';
    }
}
