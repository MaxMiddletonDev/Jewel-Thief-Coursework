package cs230.group29se.jewelthief.Persistence.Storage;

import cs230.group29se.jewelthief.Cosmetics.Skin;
import cs230.group29se.jewelthief.Game.Achievements;
import cs230.group29se.jewelthief.Persistence.Profile.HighScoreEntry;
import cs230.group29se.jewelthief.Persistence.Profile.HighScoreTable;
import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Profile.SaveData;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages persistence operations for profiles, saves, and high scores.
 * Provides methods to load, save, delete, and manipulate game data.
 * This class acts as a central point for handling file-based storage.
 * <p>
 * Authors: Iyaad, Gustas
 * @version 1.0
 */
public final class PersistenceManager {
    private static final Path BASE_DIR = Path.of("data", "jewelthief");
    private static final FileStore FILE_STORE = new FileStore(BASE_DIR);
    private static final JsonSerializer JSON_SERIALIZER = new JsonSerializer();
    private static final int PATH_DECREMENT = 5;
    private static final int INITIAL_INDEX = 0;
    private static final int NEXT_INDEX = 1;
    private static final String PROFILE_FAIL_MSG =
            "Profile '%s' does not exist. Creating new profile.";
    private static final String PROFILE_DEL_MSG =
            "Deleted profile '%s' and its saves folder";
    private static final String SAVE_FAIL_MSG =
            "No save file exists for profile '%s' level '%s'";


    private PersistenceManager() {
    }

    // Helper methods for constructing file paths
    private static String pathProfile(final String profileName) {
        return "profiles/" + profileName + ".json";
    }
    private static String pathSave(final String profileName,
                                   final String levelId) {
        return "saves/" + profileName + "/level-" + levelId + ".json";
    }
    private static String pathHighScores() {
        return "scores/highscores.json";
    }
    private static String pathGenericSave() {
        return "./genericSave.json";
    }

    private static String activeProfileName = "Amsyar";
    private static String activeLevelId = "1";
    private int activeRunScore = 0;
    private static ProfileData cachedProfile = null;
    private static SaveData cachedSave = null;

    public static void setActiveProfileName(final String name) {
        activeProfileName = name;
    }
    public static void setActiveLevelId(final String levelId) {
        activeLevelId = levelId;
    }
    public void setActiveRunScore(final int score) {
        this.activeRunScore = score;
    }
    public static void setCachedProfile(final ProfileData p) {
        cachedProfile = p;
    }
    public static void setCachedSave(final SaveData s) {
        cachedSave = s;
    }

    // Retrieves the current profile data, creating a default if none exists
    private static ProfileData currentProfile() {
        if (cachedProfile != null) {
            return cachedProfile;
        }
        ProfileData p = new ProfileData();
        p.setProfileName(activeProfileName);
        return p;
    }

    /**
     * Loads or creates a profile with the given name.
     * @param profileName The name of the profile to load or create.
     */
    public static void loadProfile(final String profileName) {
        String path = pathProfile(profileName);
        if (!FILE_STORE.exists(path)) {
            System.out.printf((PROFILE_FAIL_MSG) + "%n", profileName);
        }
        ProfileData profile = JSON_SERIALIZER.fromJson(FILE_STORE.read(path),
                ProfileData.class);
        setCachedProfile(profile);
        PersistenceManager.setActiveProfileName(profileName);
    }

    /**
     * Returns the current active profile data without changing the game state.
     * @return The current active `ProfileData`.
     */
    public static ProfileData getCurrentProfile() {
        return currentProfile();
    }

    private static String currentProfileName() {
        return activeProfileName;
    }
    private static String rememberedProfileName() {
        return activeProfileName;
    }

    private static SaveData currentSaveData() {
        if (cachedSave != null) {
            return cachedSave;
        }
        SaveData s = new SaveData();
        s.setProfileName(activeProfileName);
        s.setLevelId(activeLevelId);
        s.setPlayerState(new Object[]{});
        return s;
    }

    private static String currentLevelId() {
        return activeLevelId;
    }
    private int currentRunScore() {
        return activeRunScore;
    }
    private void applySaveToGame(final SaveData s) {
        this.cachedSave = s;
    }

    /**
     * Saves the current profile data to the file system.
     * @return `true` if the profile was successfully saved, `false` otherwise.
     */
    public static Boolean saveProfile() {
        ProfileData p = currentProfile();
        if (p == null || currentProfileName() == null) {
            return false;
        }
        FILE_STORE.write(pathProfile(currentProfileName()),
                JSON_SERIALIZER.toJson(p));
        return true;
    }

    /**
     * Loads a profile from the file system.
     * @return The loaded `ProfileData`, or `null` if no profile exists.
     */
    public static ProfileData loadProfile() {
        String name = rememberedProfileName();
        if (name != null) {
            String path = pathProfile(name);
            if (!FILE_STORE.exists(path)) {
                return null;
            }
            return JSON_SERIALIZER.fromJson(FILE_STORE.read(path),
                    ProfileData.class);
        }
        var files = FILE_STORE.list("profiles");
        if (files.isEmpty()) {
            return null;
        }
        return JSON_SERIALIZER.fromJson(FILE_STORE.read(
                files.get(INITIAL_INDEX)),
                ProfileData.class);
    }

    /**
     * Saves the current game state to the file system.
     */
    public static void saveGame() {
        SaveData s = currentSaveData();
        String profile = currentProfileName();
        String levelId = currentLevelId();
        if (profile == null || levelId == null || s == null) {
            return;
        }
        FILE_STORE.write(pathSave(profile, levelId), JSON_SERIALIZER.toJson(s));
    }

    /**
     * Loads the game state for the current profile and level.
     */
    public void loadGame() {
        String profile = currentProfileName();
        String levelId = currentLevelId();
        if (profile == null || levelId == null) {
            return;
        }
        SaveData s = JSON_SERIALIZER.fromJson(FILE_STORE.read(
                pathSave(profile, levelId)), SaveData.class);
        applySaveToGame(s);
    }

    /**
     * Retrieves the save data for the current profile and level.
     * @return The `SaveData`, or `null` if no save exists.
     */
    public static SaveData getSaveData() {
        String profile = currentProfileName();
        String levelId = currentLevelId();
        if (profile == null || levelId == null) {
            return null;
        }
        try {
            return JSON_SERIALIZER.fromJson(FILE_STORE.read(pathSave(
                profile, levelId)), SaveData.class);
        } catch (RuntimeException ex) {
            return null;
        }
    }

    /**
     * Gets the name of the currently active profile.
     * @return The name of the active profile.
     */
    public static String getActiveProfileName() {
        return activeProfileName;
    }

    /**
     * Gets the ID of the currently active level.
     * @return The ID of the active level.
     */
    public String getActiveLevelId() {
        return activeLevelId;
    }

    /**
     * Submits a high score for a profile and level.
     *
     * @param profileName The name of the profile.
     * @param levelId The ID of the level.
     * @param score The score to submit.
     * @param timestampIso The timestamp of the score submission.
     */
    public static void submitScore(final String profileName,
                                   final String levelId,
                                   final int score,
                                   final String timestampIso) {
        HighScoreEntry entry = new HighScoreEntry(
                profileName, score, timestampIso);
        HighScoresDTO dto = loadHighScoresDTO();

        dto.getGlobalRanking().add(entry);
        dto.getGlobalRanking().sort(java.util.Comparator.comparingInt(
                HighScoreEntry::getScore).reversed());

        if (levelId != null) {
            var list = dto.getPerLevelBest().computeIfAbsent(levelId,
                    k -> new java.util.ArrayList<>());
            list.add(entry);
            list.sort(java.util.Comparator.comparingInt(
                    HighScoreEntry::getScore).reversed());
        }

        FILE_STORE.write(pathHighScores(), JSON_SERIALIZER.toJson(dto));
    }

    static class GenericSaveDTO {
        private String selectedProfileName;

        /**
         * Returns the currently selected profile name.
         * @return the selected profile name, or null if none is selected
         */
        public String getSelectedProfileName() {
            return selectedProfileName;
        }

        /**
         * Sets the currently selected profile name.
         * @param selectedProfileName the profile name to mark as selected
         */
        public void setSelectedProfileName(final String selectedProfileName) {
            this.selectedProfileName = selectedProfileName;
        }    }

    /**
     * Writes the selected profile name to a generic save file.
     * @param profileName The name of the profile to save.
     */
    public static void writeSelectedProfile(final String profileName) {
        FILE_STORE.write(pathGenericSave(), JSON_SERIALIZER.toJson(
                new GenericSaveDTO() {{
            setSelectedProfileName(profileName);
        }}));
    }

    /**
     * Updates the selected skin for the current profile.
     *
     * @param skin The skin to set as selected.
     */
    public static void writeSelectedSkin(final Skin skin) {
        ProfileData p = currentProfile();
        p.setSelectedSkinId(skin.getId().toString());
        saveProfile();
    }

    /**
     * Unlocks an achievement for the current profile.
     *
     * @param achievements The achievement to unlock.
     */
    public static void writeUnlockedAchievement(
            final Achievements achievements) {
        ProfileData p = currentProfile();
        p.unlockAchievement(achievements.name());
        saveProfile();
    }

    /**
     * Reads the selected profile name from the generic save file.
     *
     * @return The selected profile name, or `null` if not found.
     */
    public static String readSelectedProfile() {
        try {
            String json = FILE_STORE.read(pathGenericSave());
            GenericSaveDTO dto = JSON_SERIALIZER.fromJson(
                    json, GenericSaveDTO.class);
            return dto.getSelectedProfileName();
        } catch (RuntimeException ex) {
            return null;
        }
    }

    public void submitScore() {
        String profile = currentProfileName();
        int score = currentRunScore();
        String levelId = currentLevelId();
        String timestamp = java.time.Instant.now().toString();
        submitScore(profile, levelId, score, timestamp);
    }

    /**
     * Loads the high scores and returns a table view.
     *
     * @return A `HighScoreTable` containing the high scores.
     */
    public HighScoreTable loadHighScores() {
        HighScoresDTO dto = loadHighScoresDTO();
        HighScoreTable table = new HighScoreTable();
        for (HighScoreEntry e : dto.getGlobalRanking()) {
            table.add(e);
        }
        return table;
    }
    /**
     * Lists all profile names from the profiles directory.
     *
     * @return A list of profile names.
     */
    public static List<String> listProfiles() {
        List<String> files = FILE_STORE.list("profiles");
        List<String> out = new ArrayList<>();

        for (String rel : files) {
            if (!rel.endsWith(".json")) {
                continue;
            }

            String base = rel.replace('\\', '/');
            base = base.substring(base.lastIndexOf('/') + NEXT_INDEX);

            out.add(base.substring(INITIAL_INDEX, base.length()
                    - PATH_DECREMENT));
        }
        return out;
    }

    /**
     * Deletes a profile and all its associated saves.
     *
     * @param profileName The name of the profile to delete.
     */
    public static void deleteProfile(final String profileName) {
        if (profileName == null || profileName.isEmpty()) {
            return;
        }

        String profilePath = pathProfile(profileName);
        FILE_STORE.delete(profilePath);

        String savesDir = "saves/" + profileName;
        FILE_STORE.deleteDirectory(savesDir);

        if (profileName.equals(activeProfileName)) {
            activeProfileName = null;
            cachedProfile = null;
            cachedSave = null;
        }

        System.out.printf(PROFILE_DEL_MSG, profileName);
    }

    /**
     * Deletes the save data for the current level.
     */
    public static void deleteSaveForCurrentLevel() {
        String profile = currentProfileName();
        String levelId = currentLevelId();
        if (profile == null || levelId == null) {
            return;
        }

        String path = pathSave(profile, levelId);
        if (!FILE_STORE.exists(path)) {
            System.out.printf(SAVE_FAIL_MSG, profile, levelId);
            return;
        }
        FILE_STORE.delete(path);
        cachedSave = null;
    }

    /**
     * DTO for storing all high score data.
     * Contains both per-level best scores and the global ranking.
     */
    static class HighScoresDTO {

        /**
         * Best scores per level.
         * Key is level ID, value is list of high score entries
         * for that level.
         */
        private java.util.Map<String, java.util.List<HighScoreEntry>>
                perLevelBest = new java.util.HashMap<>();

        /**
         * Global ranking of high scores across all levels.
         */
        private java.util.List<HighScoreEntry> globalRanking =
                new java.util.ArrayList<>();

        /**
         * Returns the per-level the best high scores.
         * @return map of level IDs to lists of high score entries
         */
        public java.util.Map<String, java.util.List<HighScoreEntry>>
        getPerLevelBest() {
            return perLevelBest;
        }

        /**
         * Sets the per-level the best high scores.
         * @param perLevelBest map of level IDs to lists of high score entries
         */
        public void setPerLevelBest(final java.util.Map<String,
                java.util.List<HighScoreEntry>> perLevelBest) {
            this.perLevelBest = perLevelBest;
        }

        /**
         * Returns the global ranking of high scores.
         * @return list of global high score entries
         */
        public java.util.List<HighScoreEntry> getGlobalRanking() {
            return globalRanking;
        }

        /**
         * Sets the global ranking of high scores.
         * @param globalRanking list of global high score entries
         */
        public void setGlobalRanking(final java.util.List<HighScoreEntry>
                                             globalRanking) {
            this.globalRanking = globalRanking;
        }
    }

    /**
     * Loads the high scores data transfer object from the file system.
     * @return the loaded {@code HighScoresDTO}
     * or a new empty instance if loading fails
     */
    private static HighScoresDTO loadHighScoresDTO() {
        String path = pathHighScores();
        try {
            String json = FILE_STORE.read(path);
            return JSON_SERIALIZER.fromJson(json, HighScoresDTO.class);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new HighScoresDTO();
        }
    }

    /**
     * Loads per-level high scores as a map.
     * @return a map of level IDs to lists of high score entries
     */
    public static java.util.Map<String, java.util.List<HighScoreEntry>>
    loadPerLevelHighScores() {
        return loadHighScoresDTO().getPerLevelBest();
    }

    /**
     * Loads the global high scores list.
     * @return a list of global high score entries
     */
    public static java.util.List<HighScoreEntry> loadGlobalHighScores() {
        return loadHighScoresDTO().getGlobalRanking();
    }
}
