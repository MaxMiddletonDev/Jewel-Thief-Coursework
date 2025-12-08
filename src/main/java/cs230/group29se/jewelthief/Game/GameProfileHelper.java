package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Profile.ProfileManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for managing game profiles.
 * Provides utility methods for listing, creating, renaming, and deleting profiles.
 *
 * @author Iyaad
 */
public class GameProfileHelper {

    /** Shared ProfileManager initialized with saved profiles. */
    private static final ProfileManager manager = loadManagerFromPersistence();

    /**
     * Loads the ProfileManager with profiles from persistence storage.
     *
     * @return A ProfileManager instance containing the saved profiles.
     */
    private static ProfileManager loadManagerFromPersistence() {
        List<ProfileData> savedProfiles = new ArrayList<>();
        for (String name : PersistenceManager.listProfiles()) {
            PersistenceManager.setActiveProfileName(name);
            ProfileData p = PersistenceManager.loadProfile();
            if (p != null) {
                savedProfiles.add(p);
            }
        }
        return new ProfileManager(savedProfiles);
    }

    /**
     * Returns a list of player profile names.
     *
     * @return The list of player profile names.
     */
    public static List<String> listProfiles() {
        return PersistenceManager.listProfiles();
    }

    /**
     * Ensures that a profile with the given name exists.
     * If the profile does not exist, it creates a new one.
     *
     * @param name The name of the profile.
     * @throws IllegalArgumentException if the profile name already exists.
     */
    public static void ensureProfileExists(final String name) {
        if (manager.isDuplicateName(name)) {
            throw new IllegalArgumentException("Profile name '"
                    + name + "' already exists!");
        }

        PersistenceManager.setActiveProfileName(name);
        ProfileData p = PersistenceManager.loadProfile();
        if (p == null) {
            p = new ProfileData();
            p.setProfileName(name);
            p.setMaxUnlockedLvl(1);
            manager.addProfile(p);
            PersistenceManager.setCachedProfile(p);
            PersistenceManager.saveProfile();
        }
    }

    /**
     * Gets the name of the currently active profile.
     *
     * @return The name of the active profile.
     */
    public static String getActiveProfileName() {
        return PersistenceManager.getActiveProfileName();
    }

    /**
     * Deletes the profile with the given name.
     *
     * @param name The name of the profile to delete.
     */
    public static void deleteProfile(final String name) {
        PersistenceManager.deleteProfile(name);
        manager.getProfiles().removeIf(p ->
                p.getProfileName().equalsIgnoreCase(name));
        System.out.println("Deleted profile " + name);
    }

    /**
     * Renames a profile from the old name to a new name.
     *
     * @param oldName The current name of the profile.
     * @param newName The desired new name for the profile.
     * @return true if the rename operation was successful, false otherwise.
     */
    public static boolean renameProfile(String oldName, String newName) {
        if (oldName == null || newName == null) {
            return false;
        }
        newName = newName.trim();
        if (newName.isEmpty()) {
            return false;
        }
        if (oldName.equals(newName)) {
            return true;
        }

        if (manager.isDuplicateName(newName)) {
            return false;
        }

        try {
            PersistenceManager.setActiveProfileName(oldName);
            ProfileData p = PersistenceManager.loadProfile();
            if (p == null) {
                return false;
            }

            // Rename and cache the profile
            p.setProfileName(newName);
            manager.addProfile(p);
            PersistenceManager.setCachedProfile(p);

            PersistenceManager.setActiveProfileName(newName);
            PersistenceManager.saveProfile();

            PersistenceManager.deleteProfile(oldName);
            manager.getProfiles().removeIf(x ->
                    x.getProfileName().equalsIgnoreCase(oldName));
            return true;
        } catch (Exception e) {
            System.out.println("rename failed: " + e.getMessage());
            return false;
        }
    }
}
