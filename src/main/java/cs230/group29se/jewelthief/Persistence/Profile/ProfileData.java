package cs230.group29se.jewelthief.Persistence.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * It represents the player's profile for the game.
 * it stores all the details related to the player,
 * like it's name, maximum level unlocked.
 * It is used by the persistence and retrieval of player's progress.
 * @author Iyaad
 * @version 1.0
 */
public class ProfileData {
    private String profileName;
    private List<String> unlockedAchievements = new ArrayList<>();
    private int maxUnlockedLvl;
    private String selectedSkinId;

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
    public void setProfileName(final String profileName) {
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
     * This method get the selected skin id by the player.
     * @return The selected skin id.
     */
    public String getSelectedSkinId() {
        return selectedSkinId;
    }

    /**
     * This method sets the selected skin id by the player.
     * @param selectedSkinId The new selected skin id.
     */
    public void setSelectedSkinId(final String selectedSkinId) {
        this.selectedSkinId = selectedSkinId;
    }

    /**
     *This method sets the maximum level Unlocked by the player.
     * @param maxUnlockedLvl The new maximum level Unlocked.
     */
    public void setMaxUnlockedLvl(final int maxUnlockedLvl) {
        this.maxUnlockedLvl = maxUnlockedLvl;
    }

    /**
     * This method get the unlocked achievements by the player.
     * @return The Unlocked Achievements.
     */
    public List<String> getUnlockedAchievements() {
        if (unlockedAchievements == null) {
            unlockedAchievements = new ArrayList<>();
        }
        return unlockedAchievements;
    }

    /**
     * This method sets the unlocked achievements by the player.
     * @param unlockedAchievements The list of Unlocked Achievements.
     */
    public void setUnlockedAchievements(final List<String>
                                        unlockedAchievements) {
        this.unlockedAchievements = unlockedAchievements;
    }

    /**
     * This method unlocks the achievements by the player.
     * @param achievementName The name of a specific achievement.
     */
    public void unlockAchievement(final String achievementName) {
        if (!getUnlockedAchievements().contains(achievementName)) {
            unlockedAchievements.add(achievementName);
        }
    }
}
