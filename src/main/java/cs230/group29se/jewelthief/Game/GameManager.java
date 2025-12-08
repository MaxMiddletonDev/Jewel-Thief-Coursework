package cs230.group29se.jewelthief.Game;

import cs230.group29se.jewelthief.Cosmetics.SkinId;
import cs230.group29se.jewelthief.Cosmetics.SkinRegistry;
import cs230.group29se.jewelthief.Entities.Direction;
import cs230.group29se.jewelthief.Entities.FloorThief;
import cs230.group29se.jewelthief.Entities.NonPlayableCharacter;
import cs230.group29se.jewelthief.Items.Gate;
import cs230.group29se.jewelthief.Items.Item;
import cs230.group29se.jewelthief.Items.Loot;
import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Profile.SaveData;
import cs230.group29se.jewelthief.Persistence.Storage.*;
import cs230.group29se.jewelthief.Scenes.GameScene.GameController;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.nio.file.Path;
import java.util.Map;

/**
 * Manages the overall game state, including starting a new game,
 * ending the game, and rendering the current level.
 *
 * @author Gustas Rove
 * @version 1.0
 */
public final class GameManager {
    private static int levelNumber;
    private static Level currentLevel;
    private static Screen currentScreen;

    private GameManager() {
    }

    /**
     * Setting the place where the JSON files are stored.
     * edited by Iyaad
     */
    private static final LevelLoader LEVEL_LOADER = new LevelLoader();
    private static String selectedProfileName = PersistenceManager.readSelectedProfile();

    /**
     * Load a level for a given profile:
     * 1) If JSON SaveData exists for (profileName, levelNumber), use that.
     * 2) Otherwise, load levelX.txt via LevelLoader and create initial SaveData.
     * 3) Create a new Level object and register it as currentLevel.
     *
     * @param profileName active profile name
     * @param levelNum    level number (1, 2, 3, ...)
     * @param controller  GameController for the new Level
     * @author Iyaad
     */
    public static void loadLevelForProfile(String profileName,
                                           int levelNum,
                                           GameController controller) {

        setCurrentLevelNumber(levelNum);
        String levelId = String.valueOf(levelNum);
        String levelFileName = "level" + levelId + ".txt";

        PersistenceManager.setActiveProfileName(profileName);//remove
        PersistenceManager.setActiveLevelId(levelId);// keep...


//        // profiles cut to the appropriate screen
//        ProfileData profile = PM.loadProfile();
//        if (profile == null) {
//            profile = new ProfileData();
//            profile.setProfileName(profileName);
//            profile.setMaxUnlockedLvl(1);
//            PM.setCachedProfile(profile);
//            PM.saveProfile();
//        } else {
//            PM.setCachedProfile(profile);
//        }
        // saves: THIS is the "load JSON if exists" logic
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
                default -> param = "none";
            }

            param = param.toUpperCase();

            itemState.put("param", param);
            itemStates.put(itemId, itemState);
        }

        //gates states
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
     * Ends the current game session.
     * Handles cleanup, high score saving, and transitioning to end screens.
     */
    public static void gameEnd() {
        // Logic for ending the game goes here
        // TODO: implement end-of-game logic, e.g. submitScore + go to end screen
    }

    /**
     * TODO: REMOVE, we draw this in GameScreen now
     * Draws the current level using the provided GraphicsContext.
     *
     * @param gc the GraphicsContext to draw on
     */
    public static void draw(GraphicsContext gc) {
        if (currentLevel != null) {
            currentLevel.draw(gc);
        }
    }

    /**
     * Prevents game from accessing a profile when it's not supposed to
     */
    public static void clearCurrentLevel() {
        currentLevel = null;
    }

    // Getters/setters

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

//    public static PersistenceManager getPersistenceManager() {
//
//    }

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
     * Gets the GraphicsContext from the current level's GameController.
     *
     * @return the GraphicsContext object, or null if no current level.
     */
    public GraphicsContext getGraphicsContext() {
        if (currentLevel != null) {
            return currentLevel.getGameController().getCanvas().getGraphicsContext2D();
        }
        return null;
    }

    public static void setSelectedProfileName(String profileName) {
        selectedProfileName = profileName;
        PersistenceManager.writeSelectedProfile(selectedProfileName);

    }

    public static String getSelectedProfileName() {
        return selectedProfileName;
    }

    public static String getSelectedPlayerSkinId() {
        ProfileData data = PersistenceManager.getCurrentProfile();
        return data.getSelectedSkinId();
    }

    public static Image getSelectedPlayerSkinImage() {
        ProfileData data = PersistenceManager.getCurrentProfile();
        SkinId skinId = SkinId.fromString(data.getSelectedSkinId());
        return SkinRegistry.getById(skinId).getImage();
    }

}