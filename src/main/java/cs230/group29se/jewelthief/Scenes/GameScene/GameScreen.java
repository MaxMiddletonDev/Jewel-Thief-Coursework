package cs230.group29se.jewelthief.Scenes.GameScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GameScreen extends Screen {

    private Canvas canvas;
    private GraphicsContext gc;
    private GameController controller;

    // You will probably pass this in from LevelSelectScreen later
    private String activeProfileName = "testProfile"; // or "Amsyar"
    @Override
    public void initialize() {
        // Decide which level number we’re on; if 0, start at 1
        int levelNum = GameManager.getCurrentLevelNumber();
        if (levelNum == 0) {
            levelNum = 1;
            GameManager.setCurrentLevelNumber(levelNum);
        }

        // Ask GameManager to handle: JSON save vs txt + LevelLoader
        GameManager.loadLevelForProfile(activeProfileName, levelNum, controller);

        // Now there is a Level object ready in GameManager
        Level level = GameManager.getCurrentLevel();
        ((Pane) controller.gameCanvas.getParent()).getChildren().add(level.dummyPlayer);
        // Keyboard movement – unchanged
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    Node player = GameManager.getCurrentLevel().dummyPlayer;
                    player.setTranslateY(player.getTranslateY() - 40);
                }
                case DOWN -> {
                    Node player = GameManager.getCurrentLevel().dummyPlayer;
                    player.setTranslateY(player.getTranslateY() + 40);
                }
                case LEFT -> {
                    Node player = GameManager.getCurrentLevel().dummyPlayer;
                    player.setTranslateX(player.getTranslateX() - 40);
                }
                case RIGHT -> {
                    Node player = GameManager.getCurrentLevel().dummyPlayer;
                    player.setTranslateX(player.getTranslateX() + 40);
                }
            }
        });
    }

    /**
     * PLACEHOLDER METHOD, THIS IS WAITING FOR DATA PERSISTENCE IMPLEMENTATION
     * Determines if the current level has saved data to load from.
     */
    public boolean levelHasSaveData() {
        // If you want to keep this, you can delegate to PersistenceManager
        // once you have a reference here. For now, GameManager.loadLevelForProfile
        // already checks for SaveData, so this method can be unused.
        return false;
    }

    @Override
    public void update() {
        draw();
        GameManager.getCurrentLevel().update();
    }

    @Override
    public void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        GameManager.draw(gc);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/cs230/group29se/jewelthief/game-view.fxml")
            );
            root = loader.load();
            controller = loader.getController();
            controller.setScreen(this);
            this.canvas = controller.gameCanvas;
            this.gc = canvas.getGraphicsContext2D();
            scene = new Scene(root, 1028, 700);
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}