package cs230.group29se.jewelthief.Persistence.Profile;

import java.util.Map;

/**
 * It represents the player's profile for the game.
 * it stores all the details related to the player,
 * like it's name, maximum level unlocked.
 * It is used by the persistence and retrieval of player's progress.
 * @author Iyaad
 * @version 1.0
 */
public class ProfileData {
    /**
     * The name of the player's profile.
     */
    private String profileName;
    /**
     * The maximum level unlocked by the player.
     */
    private int maxUnlockedLvl;
    /**
     * This method get the profile name.
     * @return The profile name.
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * This method sets the profile name.
     * @param profileName The new profile name.
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * This method get the maximum level Unlocked by the player.
     * @return The maximum level Unlocked.
     */
    public int getMaxUnlockedLvl() {
        return maxUnlockedLvl;
    }

    /**
     *This method sets the maximum level Unlocked by the player.
     * @param maxUnlockedLvl The new maximum level Unlocked.
     */
    public void setMaxUnlockedLvl(int maxUnlockedLvl) {
        this.maxUnlockedLvl = maxUnlockedLvl;
    }
}
