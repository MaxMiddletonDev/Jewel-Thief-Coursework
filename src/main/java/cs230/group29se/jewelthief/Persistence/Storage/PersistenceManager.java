package cs230.group29se.jewelthief.Persistence.Storage;

import cs230.group29se.jewelthief.Persistence.Profile.*;

/**
 * Drop-in implementation of PersistenceManager for experimentation.
 * Also includes minimal default hooks so it runs without a UI.
 * @author Iyaad
 * @version 1.0
 */
public class PersistenceManager {
    private final FileStore fileStore;
    private final JsonSerializer serializer;

    public PersistenceManager(FileStore fileStore, JsonSerializer serializer) {
        this.fileStore = fileStore;
        this.serializer = serializer;
    }

    // helper/auxiliary functions
    private String pathProfile(String profileName) { return "profiles/" + profileName + ".json"; }
    private String pathSave(String profileName, String levelId) { return "saves/" + profileName + "/level-" + levelId + ".json"; }
    private String pathHighScores() { return "scores/highscores.json"; }

    // minimal defaults so it runs headless
    private String activeProfileName = "Amsyar";
    private String activeLevelId = "1";
    private int activeRunScore = 0;
    private ProfileData cachedProfile = null;
    private SaveData cachedSave = null;

    // tiny setters for tests or a main()
    public void setActiveProfileName(String name) { this.activeProfileName = name; }
    public void setActiveLevelId(String levelId) { this.activeLevelId = levelId; }
    public void setActiveRunScore(int score) { this.activeRunScore = score; }
    public void setCachedProfile(ProfileData p) { this.cachedProfile = p; }
    public void setCachedSave(SaveData s) { this.cachedSave = s; }

    // hook implementations (replace later with real game wiring)
    private ProfileData currentProfile() {
        if (cachedProfile != null) return cachedProfile;
        ProfileData p = new ProfileData();
        p.setProfileName(activeProfileName);
        return p;
    }
    private String currentProfileName() { return activeProfileName; }
    private String rememberedProfileName() { return activeProfileName; }
    private SaveData currentSaveData() {
        if (cachedSave != null) return cachedSave;
        SaveData s = new SaveData();
        s.setProfileName(activeProfileName);
        s.setLevelId(activeLevelId);
        s.setPlayerState(new Object[]{});
        return s;
    }
    private String currentLevelId() { return activeLevelId; }
    private int currentRunScore() { return activeRunScore; }
    private String currentProfileNameToDelete() { return activeProfileName; }
    private void applySaveToGame(SaveData s) { this.cachedSave = s; }

    /** Saves current ProfileData */
    public Boolean saveProfile() {
        ProfileData p = currentProfile();
        if (p == null || currentProfileName() == null) return false;
        fileStore.write(pathProfile(currentProfileName()), serializer.toJson(p));
        return true;
    }

    /**
     * Loads a ProfileData.
     * Returns null if no profile exists for the remembered name.
     */
    public ProfileData loadProfile() {
        String name = rememberedProfileName(); // e.g. "Amsyar"

        if (name != null) {
            String path = pathProfile(name); // "profiles/Amsyar.json"
            if (!fileStore.exists(path)) {
                // No profile saved yet for this name
                return null;
            }
            return serializer.fromJson(fileStore.read(path), ProfileData.class);
        }

        // No remembered name: try the first existing profile in the folder
        var files = fileStore.list("profiles");
        if (files.isEmpty()) {
            // No profiles at all
            return null;
        }

        // files.get(0) is already a relative path like "profiles/Someone.json"
        return serializer.fromJson(fileStore.read(files.get(0)), ProfileData.class);
    }
    /** Writes SaveData to saves/<profileName>/level-<levelId>.json */
    public void saveGame() {
        SaveData s = currentSaveData();
        String profile = currentProfileName();
        String levelId = currentLevelId();
        if (profile == null || levelId == null || s == null) return;
        fileStore.write(pathSave(profile, levelId), serializer.toJson(s));
    }

    /** Reads SaveData for current profile/level and applies it */
    public void loadGame() {
        String profile = currentProfileName();
        String levelId = currentLevelId();
        if (profile == null || levelId == null) return;
        SaveData s = serializer.fromJson(fileStore.read(pathSave(profile, levelId)), SaveData.class);
        applySaveToGame(s);
    }

    /** Returns SaveData without changing game state */
    public SaveData getSaveData() {
        String profile = currentProfileName();
        String levelId = currentLevelId();
        if (profile == null || levelId == null) return null;
        try { return serializer.fromJson(fileStore.read(pathSave(profile, levelId)), SaveData.class); }
        catch (RuntimeException ex) { return null; }
    }

    /** Appends a high score and sorts lists desc */
    public void submitScore() {
        String profile = currentProfileName();
        int score = currentRunScore();
        var entry = new HighScoreEntry(profile, score, java.time.Instant.now().toString());
        HighScoresDTO dto = loadHighScoresDTO();
        dto.globalRanking.add(entry);
        dto.globalRanking.sort(java.util.Comparator.comparingInt(HighScoreEntry::getScore).reversed());
        String levelId = currentLevelId();
        if (levelId != null) {
            var list = dto.perLevelBest.computeIfAbsent(levelId, k -> new java.util.ArrayList<>());
            list.add(entry);
            list.sort(java.util.Comparator.comparingInt(HighScoreEntry::getScore).reversed());
        }
        fileStore.write(pathHighScores(), serializer.toJson(dto));
    }

    /** Loads highscores.json and returns a table view */
    public HighScoreTable loadHighScores() {
        HighScoresDTO dto = loadHighScoresDTO();
        HighScoreTable table = new HighScoreTable();
        for (HighScoreEntry e : dto.globalRanking) table.add(e);
        return table;
    }

    /** Lists profile names from profiles/*.json */
    public java.util.List<String> listProfiles() {
        var files = fileStore.list("profiles");
        var out = new java.util.ArrayList<String>();
        for (String f : files) if (f.endsWith(".json")) {
            String base = f.substring(f.lastIndexOf('/') + 1);
            out.add(base.substring(0, base.length() - 5));
        }
        return out;
    }

    /** Deletes the profile JSON and all its saves */
    public void deleteProfile() {
        String name = currentProfileNameToDelete();
        if (name == null) return;
        fileStore.delete(pathProfile(name));
        for (String p : fileStore.list("saves/" + name)) fileStore.delete(p);
    }

    // ---- internal DTO + loader for highscores.json ----
    static class HighScoresDTO {
        public java.util.Map<String, java.util.List<HighScoreEntry>> perLevelBest = new java.util.HashMap<>();
        public java.util.List<HighScoreEntry> globalRanking = new java.util.ArrayList<>();
    }

    private HighScoresDTO loadHighScoresDTO() {
        try { return serializer.fromJson(fileStore.read(pathHighScores()), HighScoresDTO.class); }
        catch (RuntimeException ex) { return new HighScoresDTO(); }
    }
}