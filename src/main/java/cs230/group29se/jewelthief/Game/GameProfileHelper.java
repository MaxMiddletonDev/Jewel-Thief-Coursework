package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Profile.ProfileManager;

import java.util.ArrayList;
import java.util.List;

public class GameProfileHelper {

//    private static final PersistenceManager PM = GameManager.getPersistenceManager();

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

    public static List<String> listProfiles() {
        return PersistenceManager.listProfiles();
    }

    public static void ensureProfileExists(String name) {
        if (manager.isDuplicateName(name)) {
            throw new IllegalArgumentException("Profile name '" + name + "' already exists!");
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

    public static void setActiveProfileName(String name) {
        PersistenceManager.setActiveProfileName(name);
        ProfileData p = PersistenceManager.loadProfile();
        if (p != null) {
            if (!manager.isDuplicateName(p.getProfileName())) {
                manager.addProfile(p);
            }
            PersistenceManager.setCachedProfile(p);
        }
    }

    public static String getActiveProfileName() {
        return PersistenceManager.getActiveProfileName(); // or equivalent getter
    }

    public static void deleteProfile(String name) {
        PersistenceManager.deleteProfile(name);
        manager.getProfiles().removeIf(p -> p.getProfileName().equalsIgnoreCase(name));
        System.out.println("Deleted profile " + name);
    }
    public static boolean renameProfile(String oldName, String newName) {
        if (oldName == null || newName == null) return false;
        newName = newName.trim();
        if (newName.isEmpty()) return false;
        if (oldName.equals(newName)) return true;

        if (manager.isDuplicateName(newName)) {
            return false;
        }

        //if (listProfiles().contains(newName)) return false;

        try {
            PersistenceManager.setActiveProfileName(oldName);
            ProfileData p = PersistenceManager.loadProfile();
            if (p == null) return false;

            // 改名并缓存
            p.setProfileName(newName);
            manager.addProfile(p);
            PersistenceManager.setCachedProfile(p);

            PersistenceManager.setActiveProfileName(newName);
            PersistenceManager.saveProfile();

            PersistenceManager.deleteProfile(oldName);
            manager.getProfiles().removeIf(x -> x.getProfileName().equalsIgnoreCase(oldName));
            return true;
        } catch (Exception e) {
            System.out.println("rename failed: " + e.getMessage());
            return false;
        }
    }
}
