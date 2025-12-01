package cs230.group29se.jewelthief.Scenes.GameScene;

import cs230.group29se.jewelthief.Direction;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Player;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameScreen extends Screen {
    private Canvas canvas;
    private GraphicsContext gc;
    private GameController controller;

    @Override   
    public void initialize() {
        //TODO:Decide if we need to load a new level or continue current level here -------------------------
        String levelFileName;
        if(levelHasSaveData()){
            levelFileName = ""; //Todo: Get level file name from save data
        }else{
            levelFileName = "level" + GameManager.getCurrentLevelNumber() + ".txt";
        }
        Level level = new Level(levelFileName, controller);
        //TODO:Decide if we need to load a new level or continue current level here -------------------------
        GameManager.setCurrentLevel(level);

        scene.setOnKeyPressed(event -> {
            Player player = GameManager.getCurrentLevel().getPlayer();
            switch (event.getCode()) {
                case UP ->{
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
     * PLACEHOLDER METHOD, THIS IS WAITING FOR DATA PERSISTENCE IMPLEMENTATION
     * Determines if the current level has saved data to load from.
     * @return false
     */
    public boolean levelHasSaveData(){
        return false;
    }

    @Override
    public void update() {
        GameManager.getCurrentLevel().update();
        draw();
    }

    @Override
    public void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        GameManager.getCurrentLevel().draw(gc);
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

    public void failLevel() {
        setFinished(true);
    }

}