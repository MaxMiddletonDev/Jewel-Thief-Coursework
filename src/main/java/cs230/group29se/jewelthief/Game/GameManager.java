package cs230.group29se.jewelthief.Game;

import javafx.scene.canvas.GraphicsContext;

/**
 * Manages the overall game state, including starting a new game,
 * ending the game, and rendering the current level.
 * @author Gustas Rove
 * @version 1.0
 */
public final class GameManager {
    private static Level currentLevel;
    private GameManager() {
    }

    public static void startNewGame(Level level) {
        currentLevel = level;
        // Further game initialization logic goes here
    }

    public static void gameEnd() {
        // Logic for ending the game goes here
    }

    public static void draw(GraphicsContext gc) {
        if (currentLevel != null) {
            currentLevel.draw(gc);
        }

    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }
}
