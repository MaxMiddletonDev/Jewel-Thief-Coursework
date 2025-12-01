package cs230.group29se.jewelthief.Game;

import javafx.scene.canvas.GraphicsContext;

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
     * Set the current level.
     * @param level the Level object.
     */
    public static void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    /**
     * Loads the next level from a saved game file.
     * @param saveFilePath the path to the saved game file
     */
    public static void loadNextLevel(String saveFilePath) {
        // Figure out next level from save file and load it
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
