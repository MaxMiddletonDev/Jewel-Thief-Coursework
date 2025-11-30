package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;

import java.util.List;

public class GameProfileHelper {

    private static final PersistenceManager PM = GameManager.getPersistenceManager();

    public static List<String> listProfiles() {
        return PM.listProfiles();
    }

    public static void ensureProfileExists(String name) {
        PM.setActiveProfileName(name);
        ProfileData p = PM.loadProfile();
        if (p == null) {
            p = new ProfileData();
            p.setProfileName(name);
            p.setMaxUnlockedLvl(1);
            PM.setCachedProfile(p);
            PM.saveProfile();
        }
    }

    public static void setActiveProfileName(String name) {
        PM.setActiveProfileName(name);
        ProfileData p = PM.loadProfile();
        if (p != null) {
            PM.setCachedProfile(p);
        }
    }

    public static String getActiveProfileName() {
        return PM.getActiveProfileName(); // or equivalent getter
    }

    public static void deleteProfile(String name) {
        PM.deleteProfile(name);
        System.out.println("Deleted profile " + name);
    }

}