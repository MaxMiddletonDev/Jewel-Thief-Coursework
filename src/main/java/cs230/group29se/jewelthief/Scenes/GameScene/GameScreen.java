package cs230.group29se.jewelthief.Scenes.GameScene;

import cs230.group29se.jewelthief.Entities.Direction;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Entities.Player;
import cs230.group29se.jewelthief.Game.SoundManager;
import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.LevelFailedScene.LevelFailedScreen;
import cs230.group29se.jewelthief.Scenes.LevelFinished.LevelFinishedScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Represents the game screen in the game.
 * Handles the initialization of the level, player movement key bindings,
 * and updates the game state during gameplay.
 *
 * @author Gustas Rove
 */
public class GameScreen extends Screen {

    private Timeline autosaveTimeline;
    private boolean paused = false; // Indicates whether the game is paused

    /**
     * Constructs a GameScreen and sets its title and FXML path.
     */
    public GameScreen() {
        setScreenTitle("Game");
        setScreenFXMLPath("/cs230/group29se/jewelthief/game-view.fxml");
    }

    /**
     * Initializes the game screen by setting up the level and player movement key bindings.
     */
    @Override
    public void onInitialize() {
        SoundManager.playGame();
        int levelNum = GameManager.getCurrentLevelNumber();

        GameManager.loadLevelForProfile(levelNum, (GameController) getController());

        Player player = GameManager.getCurrentLevel().getPlayer();

        setupKeyBinds(player);

        // Adds saving every second.
        autosaveTimeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        e -> GameManager.saveCurrentGameState()
                )
        );
        autosaveTimeline.setCycleCount(Animation.INDEFINITE);
        autosaveTimeline.play();
    }

    /**
     * Sets up key bindings for player movement and game controls.
     *
     * @param player the player object to control
     */
    private void setupKeyBinds(Player player) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    player.setDirection(Direction.UP);
                    player.move();
                }
                case DOWN -> {
                    player.setDirection(Direction.DOWN);
                    player.move();
                }
                case LEFT -> {
                    player.setDirection(Direction.LEFT);
                    player.move();
                }
                case RIGHT -> {
                    player.setDirection(Direction.RIGHT);
                    player.move();
                }
                case P, ESCAPE -> {
                    setPaused(!isPaused());
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case R -> {
                    System.out.println("Restarting level...");
                    restartLevel();
                }
            }
        });
    }

    /**
     * Updates the game state by updating the current level and drawing the game screen.
     * If the level has failed, it transitions to the level failed screen.
     */
    @Override
    public void update() {
        if (paused) return;

        Level level = GameManager.getCurrentLevel();
        if (level == null) {
            // No level loaded (profile just deleted) → nothing to update
            return;
        }

        level.update();

        if (level.isLevelFailed()) {
            loadFailedLevelScreen();
        } else if (level.isFinishedLevel()) {
            loadLevelFinishedScreen();
        }
    }

    /**
     * Draws the current state of the game on the canvas.
     * Clears the canvas and renders the current level.
     */
    @Override
    public void draw() {
        if (getCanvas() == null || getGraphicsContext() == null || GameManager.getCurrentLevel() == null) {
            return; // nothing to draw yet (defensive check)
        }

        if (paused) {
            getGraphicsContext().setFill(new Color(0, 0, 0, 0.5));
            getGraphicsContext().fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());

            getGraphicsContext().setFill(Color.WHITE);
            getGraphicsContext().setFont(new Font(40));
            getGraphicsContext().fillText("PAUSED", getCanvas().getWidth() / 2 - 80, getCanvas().getHeight() / 2);
        } else {
            getGraphicsContext().clearRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
            GameManager.getCurrentLevel().draw(getGraphicsContext());
        }
    }

    /**
     * Loads the level failed screen and marks the current screen as finished.
     */
    public void loadFailedLevelScreen() {
        // Stop autosaving
        if (autosaveTimeline != null) {
            autosaveTimeline.stop();
        }

        // Delete save for current level
        PersistenceManager.deleteSaveForCurrentLevel();

        setNextScreen(new LevelFailedScreen());
        setFinished(true);
    }

    /**
     * Restarts the current level by deleting its save and reloading the screen.
     */
    public void restartLevel() {
        Level level = GameManager.getCurrentLevel();
        if (level != null) {
            PersistenceManager.deleteSaveForCurrentLevel();
            setNextScreen(new GameScreen());
            setFinished(true);
        }
    }

    /**
     * Loads the level finished screen and updates the player's progress.
     * Marks the current screen as finished.
     */
    public void loadLevelFinishedScreen() {
        ProfileData profile = PersistenceManager.getCurrentProfile();
        int maxLevelUnlocked = profile.getMaxUnlockedLvl();
        if (GameManager.getCurrentLevelNumber() >= maxLevelUnlocked) {
            profile.setMaxUnlockedLvl(GameManager.getCurrentLevelNumber() + 1);
            PersistenceManager.saveProfile();
        }

        // Stop autosaving when finishing the level
        if (autosaveTimeline != null) {
            autosaveTimeline.stop();
        }

        // Delete save for current level
        PersistenceManager.deleteSaveForCurrentLevel();

        setNextScreen(new LevelFinishedScreen());
        setFinished(true);
    }

    /**
     * Sets the paused state of the game.
     * If paused, the autosave timeline is paused; otherwise, it is played.
     *
     * @param paused true to pause the game, false to resume
     */
    public void setPaused(boolean paused) {
        SoundManager.playPause();
        this.paused = paused;
        if (paused) {
            autosaveTimeline.pause();
        } else {
            autosaveTimeline.play();
            GameManager.getCurrentLevel().resetLastUpdateTime();
        }
    }

    /**
     * Returns whether the game is currently paused.
     *
     * @return true if the game is paused, false otherwise
     */
    public boolean isPaused() {
        return paused;
    }
}
