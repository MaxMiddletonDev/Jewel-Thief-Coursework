package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Cosmetics.SkinId;
import cs230.group29se.jewelthief.Cosmetics.SkinRegistry;
import cs230.group29se.jewelthief.Entities.Direction;
import cs230.group29se.jewelthief.Entities.FloorThief;
import cs230.group29se.jewelthief.Entities.NonPlayableCharacter;
import cs230.group29se.jewelthief.Items.Bomb;
import cs230.group29se.jewelthief.Items.Gate;
import cs230.group29se.jewelthief.Items.Item;
import cs230.group29se.jewelthief.Items.Loot;
import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Profile.SaveData;
import cs230.group29se.jewelthief.Persistence.Storage.*;
import cs230.group29se.jewelthief.Scenes.GameScene.GameController;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Manages the overall game state, including starting a new game,
 * ending the game, and rendering the current level.
 *
 * @author Gustas Rove, Iyaad
 * @version 1.0
 */
public final class GameManager {
    // Level loading
    private static final String LEVEL_FILE_PREFIX = "level";
    private static final String LEVEL_FILE_SUFFIX = ".txt";

    // JSON keys for NPC states
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_DIR = "dir";
    private static final String KEY_ALIVE = "alive";
    private static final String KEY_COLOUR = "colour";

    // JSON keys for items
    private static final String KEY_PARAM = "param";

    // Item param codes
    private static final String PARAM_NONE = "NONE";

    // Bomb param packing separator
    private static final String PARAM_SEPARATOR = "#";

    // Switch-on-class-name strings
    private static final String NPC_FLOOR_THIEF = "FloorThief";
    private static final String ITEM_LEVER = "Lever";
    private static final String ITEM_LOOT = "Loot";
    private static final String ITEM_BOMB = "Bomb";

    // Gates
    private static final String TYPE_GATE = "Gate";

    // ID generation format
    private static final String ITEM_ID_FORMAT = "%s#%d#%d";
    private static final String GATE_ID_FORMAT = "%s#%d#%d";

    // Failure constants
    private static final int NO_TIME_LEFT = 0;

    public static final int LEVEL_COUNT = 10; // Total number of levels in the game

    // Current state information
    private static int levelNumber;
    private static Level currentLevel;
    private static Screen currentScreen;

    /**
     * Private constructor to prevent instantiation
     */
    private GameManager() {
    }

    private static String selectedProfileName = PersistenceManager.readSelectedProfile();

    /**
     * Load a level for a given profile:
     * 1) If JSON SaveData exists for (profileName, levelNumber), use that.
     * 2) Otherwise, load levelX.txt via LevelLoader and create initial SaveData.
     * 3) Create a new Level object and register it as currentLevel.
     *
     * @param levelNum   level number (1, 2, 3, ...)
     * @param controller GameController for the new Level
     * @author Iyaad
     */
    public static void loadLevelForProfile(int levelNum,
                                           GameController controller) {

        setCurrentLevelNumber(levelNum);
        String levelId = String.valueOf(levelNum);
        String levelFileName = LEVEL_FILE_PREFIX + levelId + LEVEL_FILE_SUFFIX;

        PersistenceManager.setActiveLevelId(levelId);// keep...

        SaveData save = PersistenceManager.getSaveData();

        if (save != null && save.getTimeRemainingMs() == NO_TIME_LEFT) {
            save = null;
        }


        PersistenceManager.setCachedSave(save);

        // Create the Level object (Level.readLevelFile will build grid etc.)
        Level level = new Level(levelFileName, controller, save);
        setCurrentLevel(level);
    }

    /**
     * Saving the game per-profile
     *
     * @author Iyaad
     */
    public static void saveCurrentGameState() {
        if (currentLevel == null) return;

        SaveData s = PersistenceManager.getSaveData();
        String profile = PersistenceManager.getActiveProfileName(); // or s.getProfileName()
        String levelId = String.valueOf(levelNumber);

        if (s == null) {
            s = new SaveData();
            s.setProfileName(profile);
            s.setLevelId(levelId);
        }

        // Store tile coordinates, not pixels
        int[] pos = currentLevel.getPlayer().getPosition(); // returns [xTile, yTile]
        int px = pos[0];
        int py = pos[1];

        s.setPlayerState(new Object[]{px, py});
        // (timeRemaining is milliseconds, but via getter we convert to seconds; we want raw ms)
        int timeMs = (int) currentLevel.getTimeRemainingMs();
        s.setTimeRemainingMs(timeMs);

        //Save score
        int score = currentLevel.getScore();
        s.setScore(score);

        // enemy positions & directions
        Map<String, Object> npcStates = new java.util.HashMap<>();
        for (NonPlayableCharacter npc : currentLevel.getEnemies()) {
            if (!npc.isAlive()) continue; // optionally skip dead enemies
            String id = npc.getId();
            int[] npcPos = npc.getPosition();
            int ex = npcPos[0];
            int ey = npcPos[1];
            Direction dir = npc.getDirection();

            java.util.Map<String, Object> state = new java.util.HashMap<>();
            state.put(KEY_X, ex);
            state.put(KEY_Y, ey);
            state.put(KEY_DIR, dir != null ? dir.name() : null);
            state.put(KEY_ALIVE, npc.isAlive());

            switch (npc.getClass().getSimpleName()) {
                case NPC_FLOOR_THIEF -> {
                    state.put(KEY_COLOUR, ((FloorThief) npc).getColour().toString().charAt(0));
                }
            }

            npcStates.put(id, state);
        }

        //item states
        Map<String, Object> itemStates = new java.util.HashMap<>();
        for (Item item : currentLevel.getItems()) {
            String itemType = item.getClass().getSimpleName();
            String itemId = String.format(
                    ITEM_ID_FORMAT,
                    item.getClass().getSimpleName(),
                    item.getX(),
                    item.getY()
            ).toUpperCase();
            java.util.Map<String, Object> itemState = new java.util.HashMap<>();
            itemState.put(KEY_X, item.getX());
            itemState.put(KEY_Y, item.getY());

            //The item param specific to each item. E.g for lever its the color.
            String param;
            switch (itemType) {
                case ITEM_LEVER -> {
                    char c = ((cs230.group29se.jewelthief.Items.Lever) item)
                            .getColour().toString().charAt(0);
                    param = String.valueOf(c);
                }
                case ITEM_LOOT -> {
                    param = ((Loot) item).getType().toString();
                }
                case ITEM_BOMB -> {
                    Bomb b = (Bomb) item;
                    param = b.getCountDownLeft() + PARAM_SEPARATOR +
                            b.getCountdownTickProgress() + PARAM_SEPARATOR +
                            b.getNextBoomCountdown() + PARAM_SEPARATOR +
                            b.getExplosions() + PARAM_SEPARATOR +
                            b.getArmed() + PARAM_SEPARATOR +
                            b.getExploding();
                }
                default -> param = PARAM_NONE;
            }


            param = param.toUpperCase();

            itemState.put(KEY_PARAM, param.toUpperCase());
            itemStates.put(itemId, itemState);
        }

        //Gates states
        Map<String, Object> gateStates = new java.util.HashMap<>();
        for (Gate gate : currentLevel.getGates()) {
            String gateId = String.format(
                    GATE_ID_FORMAT,
                    gate.getClass().getSimpleName(),
                    gate.getX(),
                    gate.getY()
            ).toUpperCase();

            java.util.Map<String, Object> gateState = new java.util.HashMap<>();
            gateState.put(KEY_X, gate.getX());
            gateState.put(KEY_Y, gate.getY());

            char colourLetter = gate.getColour().toString().charAt(0);
            String param = String.valueOf(colourLetter);

            param = param.toUpperCase();

            gateState.put(KEY_PARAM, param);
            gateStates.put(gateId.toUpperCase(), gateState);
        }

        s.setNpcStates(npcStates);
        s.setItems(itemStates);
        s.setGates(gateStates);

        PersistenceManager.setCachedSave(s);
        PersistenceManager.saveGame();
    }

    /**
     * Sets the current level of the game.
     *
     * @param level the new Level object
     */
    public static void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    /**
     * Gets the current level of the game.
     *
     * @return the current Level object
     */
    public static Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level number of the game.
     *
     * @param levelNum the new level number.
     */
    public static void setCurrentLevelNumber(int levelNum) {
        levelNumber = levelNum;
    }

    /**
     * Gets the current level number of the game.
     *
     * @return current level number.
     */
    public static int getCurrentLevelNumber() {
        return levelNumber;
    }

    /**
     * Sets the current screen of the game.
     *
     * @param screen the new Screen object
     */
    public static void setCurrentScreen(Screen screen) {
        currentScreen = screen;
    }

    /**
     * Gets the current screen of the game.
     *
     * @return the current Screen object
     */
    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Sets the selected profile name.
     *
     * @param profileName the profile name to set
     */
    public static void setSelectedProfileName(String profileName) {
        selectedProfileName = profileName;
        PersistenceManager.writeSelectedProfile(selectedProfileName);

    }

    /**
     * Gets the current screen of the game.
     *
     * @return the current Screen object
     */
    public static String getSelectedProfileName() {
        return selectedProfileName;
    }

    /**
     * Gets the selected player skin ID from the current profile.
     *
     * @return the selected skin ID as a String
     */
    public static String getSelectedPlayerSkinId() {
        ProfileData data = PersistenceManager.getCurrentProfile();
        return data.getSelectedSkinId();
    }

    /**
     * Gets the selected player skin Image from the current profile.
     *
     * @return the selected skin Image
     */
    public static Image getSelectedPlayerSkinImage() {
        ProfileData data = PersistenceManager.getCurrentProfile();
        SkinId skinId = SkinId.fromString(data.getSelectedSkinId());
        return SkinRegistry.getById(skinId).getImage();
    }

    public static boolean isLastLevel() {
        return levelNumber >= LEVEL_COUNT;
    }

}
