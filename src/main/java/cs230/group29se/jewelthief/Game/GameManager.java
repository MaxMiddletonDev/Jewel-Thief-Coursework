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
        String levelFileName = "level" + levelId + ".txt";

        PersistenceManager.setActiveLevelId(levelId);// keep...

        SaveData save = PersistenceManager.getSaveData();

        // if save exists but timeRemainingMs == 0, treat as no save (reset level)
        if (save != null && save.getTimeRemainingMs() == 0) {
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
            state.put("x", ex);
            state.put("y", ey);
            state.put("dir", dir != null ? dir.name() : null);
            state.put("alive", npc.isAlive());

            // add other NPC-specific state as needed
            switch (npc.getClass().getSimpleName()) {
                case "FloorThief" -> {
                    state.put("colour", ((FloorThief) npc).getColour().toString().charAt(0));
                }
                // add cases for other NPC types as needed
            }

            npcStates.put(id, state);
        }

        //item states
        Map<String, Object> itemStates = new java.util.HashMap<>();
        for (Item item : currentLevel.getItems()) {
            String itemType = item.getClass().getSimpleName();
            String itemId = (item.getClass().getSimpleName() + "#" + item.getX() + "#" + item.getY()).toUpperCase();
            java.util.Map<String, Object> itemState = new java.util.HashMap<>();
            itemState.put("x", item.getX());
            itemState.put("y", item.getY());

            //The item param specific to each item. E.g for lever its the color.
            String param;
            switch (itemType) {
                case "Lever" -> {
                    char colourLetter = ((cs230.group29se.jewelthief.Items.Lever) item).getColour().toString().charAt(0);
                    param = String.valueOf(colourLetter);
                }
                case "Loot" -> {
                    param = ((Loot) item).getType().toString();
                }
                case "Bomb" -> {
                    String countDownLeft = String.valueOf(((Bomb) item).getCountDownLeft());
                    String countdownTickProgress = String.valueOf(((Bomb) item).getCountdownTickProgress());
                    String nextBoomCountdown = String.valueOf(((Bomb) item).getNextBoomCountdown());
                    String explosions = String.valueOf(((Bomb) item).getExplosions());
                    String armed = String.valueOf(((Bomb) item).getArmed());
                    String exploding = String.valueOf(((Bomb) item).getExploding());
                    param = countDownLeft + "#" + countdownTickProgress + "#" + nextBoomCountdown +
                            "#" + explosions + "#" + armed + "#" + exploding;
                }
                default -> param = "none";
            }

            param = param.toUpperCase();

            itemState.put("param", param);
            itemStates.put(itemId, itemState);
        }

        //Gates states
        Map<String, Object> gateStates = new java.util.HashMap<>();
        for (Gate gate : currentLevel.getGates()) {
            String gateId = (gate.getClass().getSimpleName() + "#" + gate.getX() + "#" + gate.getY()).toUpperCase();

            java.util.Map<String, Object> gateState = new java.util.HashMap<>();
            gateState.put("x", gate.getX());
            gateState.put("y", gate.getY());

            char colourLetter = gate.getColour().toString().charAt(0);
            String param = String.valueOf(colourLetter);

            param = param.toUpperCase();

            gateState.put("param", param);
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

}
