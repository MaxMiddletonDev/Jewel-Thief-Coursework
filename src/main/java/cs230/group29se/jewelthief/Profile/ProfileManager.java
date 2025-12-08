package cs230.group29se.jewelthief.Profile;

import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages user profiles within the Jewel Thief game.
 * This class allows the addition of new profiles, checks for duplicate
 * names, and retrieve all stored profiles.
 * It ensures all profile names are unique.
 *
 * @author Gustas Rove
 * @version 1.0
 */
public class ProfileManager {
    private static final String ERROR_DUPLICATE_NAME =
            "Profile name '%s' already exists!";

    /**
     * The list of all player's profile currently managed.
     */
    private final List<ProfileData> profiles = new ArrayList<>();

    /**
     * This is a default no arg constructor.
     */
    public ProfileManager() {

    }

    /**
     * This accepts existing profiles.
     * @param existingProfiles The profile to be accepted.
     */
    public ProfileManager(final List<ProfileData> existingProfiles) {
        if (existingProfiles != null) {
            profiles.addAll(existingProfiles);
        }
    }

    /**
     * This method adds new profile to the manager if the name does exist.
     * if the name does exist it throws an argument.
     *
     * @param profile the profile to be added.
     */
    public void addProfile(final ProfileData profile) {
        if (isDuplicateName(profile.getProfileName())) {
            throw new IllegalArgumentException(
                    String.format(ERROR_DUPLICATE_NAME,
                            profile.getProfileName())
            );
        }
        profiles.add(profile);
    }

    /**
     * This method checks whether a given profile name already exists.
     * @param name The profile name to be checked.
     * @return A list of all the profiles.
     */
    public boolean isDuplicateName(final String name) {
        for (ProfileData p : profiles) {
            if (p.getProfileName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method retrieves all profiles managed by this class.
     *The returned list is a copy, changes will not affect the internal state.
     * @return A list of all profiles
     */
    public List<ProfileData> getProfiles() {
        return new ArrayList<>(profiles);
    }
}
