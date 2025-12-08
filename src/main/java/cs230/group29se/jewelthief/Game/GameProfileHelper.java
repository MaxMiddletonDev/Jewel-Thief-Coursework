package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Profile.ProfileManager;

import java.util.ArrayList;
import java.util.List;

public class GameProfileHelper {

    /** Shared ProfileManager initialized with saved profiles. */
    private static final ProfileManager manager = loadManagerFromPersistence();

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
     * @return the list of player profile names.
     */
    public static List<String> listProfiles() {
        return PersistenceManager.listProfiles();
    }

    /**
     * Checks if the players profile exists.
     * @param name name of the profile.
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
     * Sets the active profiles name.
     * @param name name to set profile to.
     */
    public static void setActiveProfileName(final String name) {
        PersistenceManager.setActiveProfileName(name);
        ProfileData p = PersistenceManager.loadProfile();
        if (p != null) {
            if (!manager.isDuplicateName(p.getProfileName())) {
                manager.addProfile(p);
            }
            PersistenceManager.setCachedProfile(p);
        }
    }

    /**
     * Gets the profile name that is in use.
     * @return the name of the active profile.
     */
    public static String getActiveProfileName() {
        return PersistenceManager.getActiveProfileName();
    }

    /**
     * Delete the profile by the name given.
     * @param name of the profile to delete.
     */
    public static void deleteProfile(final String name) {
        PersistenceManager.deleteProfile(name);
        manager.getProfiles().removeIf(p ->
                p.getProfileName().equalsIgnoreCase(name));
        System.out.println("Deleted profile " + name);
    }

    /**
     * Renames the players profile.
     * @param oldName the name of the profile prior to the change.
     * @param newName name of the desired profile.
     * @return true if the change was a success.
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

        //if (listProfiles().contains(newName)) return false;

        try {
            PersistenceManager.setActiveProfileName(oldName);
            ProfileData p = PersistenceManager.loadProfile();
            if (p == null) {
                return false;
            }

            // 改名并缓存
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
