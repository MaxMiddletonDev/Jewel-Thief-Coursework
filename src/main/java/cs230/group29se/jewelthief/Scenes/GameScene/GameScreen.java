package cs230.group29se.jewelthief.Scenes.GameScene;
import cs230.group29se.jewelthief.Entities.Direction;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.GameProfileHelper;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Entities.Player;
import cs230.group29se.jewelthief.Scenes.LevelFailedScene.LevelFailedScreen;
import cs230.group29se.jewelthief.Scenes.LevelFinished.LevelFinishedScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Represents the game screen in the game.
 * Handles the initialization of the level, player movement key bindings,
 * and updates the game state during gameplay.
 *
 * @author Gustas Rove
 */
public class GameScreen extends Screen {

    private GameController controller;
    private Timeline autosaveTimeline;

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
    // You will probably pass this in from LevelSelectScreen later
    private String activeProfileName = "testProfile"; // or "Amsyar"
    @Override
    public void initialize() {
        int levelNum = GameManager.getCurrentLevelNumber();
        if (levelNum == 0) {
            levelNum = 1;
            GameManager.setCurrentLevelNumber(levelNum);
        }

        String profileName = GameProfileHelper.getActiveProfileName();
        GameManager.loadLevelForProfile(profileName, levelNum, controller);

        Level level = GameManager.getCurrentLevel();
        Player player = GameManager.getCurrentLevel().getPlayer();

        // Keyboard movement – unchanged
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
            }
        });
        //Adds saving every second.
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
     * Updates the game state by updating the current level and drawing the game screen.
     * If the level has failed, it transitions to the level failed screen.
     */
    @Override
    public void update() {
        Level level = GameManager.getCurrentLevel();
        if (level == null) {
            // No level loaded (profile just deleted) → nothing to update
            return;
        }

        level.update();
        draw();

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
        if (getCanvas() == null || getGraphicsContext() == null) {
            return; // nothing to draw yet (defensive check)
        }
        getGraphicsContext().clearRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
        GameManager.getCurrentLevel().draw(getGraphicsContext());
    }
    /**
     * Loads the level failed screen and marks the current screen as finished.
     */
    public void loadFailedLevelScreen() {
        // Stop autosaving
        if (autosaveTimeline != null) {
            autosaveTimeline.stop();
        }
        setNextScreen(new LevelFailedScreen());
        setFinished(true);
    }

    public void loadLevelFinishedScreen() {
        // Stop autosaving when finishing the level
        if (autosaveTimeline != null) {
            autosaveTimeline.stop();
        }
        setNextScreen(new LevelFinishedScreen());
        setFinished(true);
    }

    @Override
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/cs230/group29se/jewelthief/game-view.fxml")
            );
            root = loader.load();
            controller = loader.getController();
            // Binds controller to this screen
            controller.setScreen(this);

            // Use the controllers canvas as this Screen's canvas
            Canvas gameCanvas = controller.getCanvas();
            setCanvas(gameCanvas);
            if (gameCanvas != null) {
                setGraphicsContext(gameCanvas.getGraphicsContext2D());
            }

            scene = new Scene(root, 1028, 900);
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
