package cs230.group29se.jewelthief.Persistence.Profile;

import java.util.Map;

/**
 * current user profile.
 * @author Iyaad
 * @version 1.0
 */
public class ProfileData {
    private String profileName;
    private int maxUnlockedLvl;

    public String getProfileName() {
        return profileName;
    }
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getMaxUnlockedLvl() {
        return maxUnlockedLvl;
    }
    public void setMaxUnlockedLvl(int maxUnlockedLvl) {
        this.maxUnlockedLvl = maxUnlockedLvl;
    }
}

