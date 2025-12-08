package cs230.group29se.jewelthief.Persistence.Profile;

import cs230.group29se.jewelthief.Cosmetics.SkinId;

import java.util.ArrayList;
import java.util.List;
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
     * A list of unlocked achievements.
     */
    private List<String> unlockedAchievements = new ArrayList<>();
    /**
     * The maximum level unlocked by the player.
     */
    private int maxUnlockedLvl;

    /**
     * The selected skin ID for the player's character.
     */
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

    public String getSelectedSkinId() {
        return selectedSkinId;
    }
    public void setSelectedSkinId(String selectedSkinId) {
        this.selectedSkinId = selectedSkinId;
    }

//    public void setSelectedSkinId(SkinId selectedSkinId) {
//        this.selectedSkinId = selectedSkinId.toString();
//    }
//
//    public SkinId getSelectedSkinId() {
//        return SkinId.fromString(this.selectedSkinId);
//    }

    /**
     *This method sets the maximum level Unlocked by the player.
     * @param maxUnlockedLvl The new maximum level Unlocked.
     */
    public void setMaxUnlockedLvl(int maxUnlockedLvl) {
        this.maxUnlockedLvl = maxUnlockedLvl;
    }

    /**
     * This method get the unlocked achievements by the player.
     * @return The Unlocked Achievements.
     */
    public List<String> getUnlockedAchievements() {
        if (unlockedAchievements == null) unlockedAchievements = new ArrayList<>();
        return unlockedAchievements;
    }

    /**
     * This method sets the unlocked achievements by the player.
     * @param unlockedAchievements The list of Unlocked Achievements.
     */
    public void setUnlockedAchievements(List<String> unlockedAchievements) {
        this.unlockedAchievements = unlockedAchievements;
    }

    /**
     * This method unlocks the achievements by the player.
     * @param achievementName The name of a specific achievement.
     */
    public void unlockAchievement(String achievementName) {
        if (!getUnlockedAchievements().contains(achievementName)) {
            unlockedAchievements.add(achievementName);
        }
    }
}
