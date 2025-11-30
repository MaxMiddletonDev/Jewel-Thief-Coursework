package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Profile.SaveData;
import cs230.group29se.jewelthief.Persistence.Profile.SaveFactory;
import cs230.group29se.jewelthief.Persistence.Storage.*;
import cs230.group29se.jewelthief.Scenes.GameScene.GameController;
import javafx.scene.canvas.GraphicsContext;

import java.nio.file.Path;

/**
 * Manages the overall game state, including starting a new game,
 * ending the game, and rendering the current level.
 * @author Gustas Rove
 * @version 1.0
 */
public final class GameManager {
    private static int levelNumber;
    private static Level currentLevel;
    private GameManager() {
    }

    /**
     * Setting the place where the JSON files are stored.
     * edited by Iyaad
     */
    private static final Path BASE_DIR =
            Path.of("data", "jewelthief");
    private static final FileStore FILE_STORE = new FileStore(BASE_DIR);
    private static final JsonSerializer JSON = new JsonSerializer();
    private static final PersistenceManager PM =
            new PersistenceManager(FILE_STORE, JSON);
    private static final LevelLoader LEVEL_LOADER = new LevelLoader();

    /**
     * Load a level for a given profile:
     * 1) If JSON SaveData exists for (profileName, levelNumber), use that.
     * 2) Otherwise, load levelX.txt via LevelLoader and create initial SaveData.
     * 3) Create a new Level object and register it as currentLevel.
     *
     * @param profileName   active profile name
     * @param levelNum      level number (1, 2, 3, ...)
     * @param controller    GameController for the new Level
     * @author Iyaad
     */
    public static void loadLevelForProfile(String profileName,
                                           int levelNum,
                                           GameController controller) {

        setCurrentLevelNumber(levelNum);
        String levelId = String.valueOf(levelNum);
        String levelFileName = "level" + levelId + ".txt";

        PM.setActiveProfileName(profileName);
        PM.setActiveLevelId(levelId);

        // profiles
        ProfileData profile = PM.loadProfile();
        if (profile == null) {
            profile = new ProfileData();
            profile.setProfileName(profileName);
            profile.setMaxUnlockedLvl(1);
            PM.setCachedProfile(profile);
            PM.saveProfile();
        } else {
            PM.setCachedProfile(profile);
        }

        // saves: THIS is the "load JSON if exists" logic
        SaveData save = PM.getSaveData();
        if (save == null) {
            // No JSON save yet → load from txt and create initial save
            System.out.println("No save for " + profileName + " L" + levelNum + " → loading txt");
            java.nio.file.Path levelPath = java.nio.file.Path.of("levels", levelFileName);
            LevelDef def = LEVEL_LOADER.loadLevel(levelId, levelPath);
            save = SaveFactory.fromLevelDef(def, profileName);
            PM.setCachedSave(save);
            PM.saveGame();  // writes saves/<profile>/level-<id>.json
        } else {
            // JSON save exists → use it
            System.out.println("Using JSON save for " + profileName + " L" + levelNum);

            PM.setCachedSave(save);
        }

        // Create the Level object (Level.readLevelFile will build grid etc.)
        Level level = new Level(levelFileName, controller, save);
        setCurrentLevel(level);
    }
    /**
     * Saving the game per-profile
     * @author Iyaad
     */
    public static void saveCurrentGameState() {
        if (currentLevel == null) return;

        String profile = PM.getSaveData().getProfileName();
        String levelId = String.valueOf(levelNumber);

        SaveData s = PM.getSaveData();
        if (s == null) {
            s = new SaveData();
            s.setProfileName(profile);
            s.setLevelId(levelId);
        }

        // Example: store player x/y as first two entries in playerState
        double px = currentLevel.dummyPlayer.getTranslateX();
        double py = currentLevel.dummyPlayer.getTranslateY();
        s.setPlayerState(new Object[] { px, py });

        PM.setCachedSave(s);
        PM.saveGame();
    }

    /**
     * Ends the current game session.
     * Handles cleanup, high score saving, and transitioning to end screens.
     */
    public static void gameEnd() {
        // Logic for ending the game goes here
        // TODO: implement end-of-game logic, e.g. submitScore + go to end screen
    }

    /**
     * Draws the current level using the provided GraphicsContext.
     * @param gc the GraphicsContext to draw on
     */
    public static void draw(GraphicsContext gc) {
        if (currentLevel != null) {
            currentLevel.draw(gc);
        }
    }

    // Getters/setters

    public static void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    /**
     * Gets the current level of the game.
     * @return the current Level object
     */
    public static Level getCurrentLevel() {
        return currentLevel;
    }
    public static void setCurrentLevelNumber(int levelNum) {
        levelNumber = levelNum;
    }
    public static int getCurrentLevelNumber() {
        return levelNumber;
    }
}