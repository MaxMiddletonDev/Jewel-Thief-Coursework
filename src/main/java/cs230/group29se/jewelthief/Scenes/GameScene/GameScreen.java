package cs230.group29se.jewelthief.Scenes.GameScene;
import cs230.group29se.jewelthief.Direction;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.MainApplication;
import cs230.group29se.jewelthief.Player;
import cs230.group29se.jewelthief.Scenes.LevelFailedScene.LevelFailedScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents the game screen in the game.
 * Handles the initialization of the level, player movement key bindings,
 * and updates the game state during gameplay.
 *
 * @author Gustas Rove
 */
public class GameScreen extends Screen {

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
    public void initialize() {
        initializeLevel();
        bindPlayerMoveKeybinds();
    }

    /**
     * Initializes the level by loading the appropriate level file.
     * If save data is available, it uses the saved level file; otherwise, it loads the current level.
     */
    private void initializeLevel() {
        String levelFileName;
        if (levelHasSaveData()) {
            levelFileName = ""; // TODO: Get level file name from save data
        } else {
            levelFileName = "level" + GameManager.getCurrentLevelNumber() + ".txt";
        }
        Level level = new Level(levelFileName, (GameController) getController());
        GameManager.setCurrentLevel(level);
    }

    /**
     * Binds key events to player movement actions.
     * Updates the player's direction and moves the player based on the key pressed.
     */
    private void bindPlayerMoveKeybinds() {
        scene.setOnKeyPressed(event -> {
            Player player = GameManager.getCurrentLevel().getPlayer();
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
    }

    /**
     * Placeholder method for determining if the current level has saved data.
     * This method currently always returns false.
     *
     * @return false, indicating no save data is available.
     */
    public boolean levelHasSaveData() {
        return false;
    }

    /**
     * Updates the game state by updating the current level and drawing the game screen.
     * If the level has failed, it transitions to the level failed screen.
     */
    @Override
    public void update() {
        GameManager.getCurrentLevel().update();
        draw();

        if (GameManager.getCurrentLevel().isLevelFailed()) {
            loadFailedLevelScreen();
        }
    }

    /**
     * Draws the current state of the game on the canvas.
     * Clears the canvas and renders the current level.
     */
    @Override
    public void draw() {
        getGraphicsContext().clearRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
        GameManager.getCurrentLevel().draw(getGraphicsContext());
    }

    /**
     * Loads the level failed screen and marks the current screen as finished.
     */
    public void loadFailedLevelScreen() {
        setNextScreen(new LevelFailedScreen());
        setFinished(true);
    }
}
