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

public class GameScreen extends Screen {

    public GameScreen(){
        setScreenTitle("Game");
        setScreenFXMLPath("/cs230/group29se/jewelthief/game-view.fxml");
    }


    @Override
    public void initialize() {

        //TODO:Decide if we need to load a new level or continue current level here -------------------------
        String levelFileName;
        if (levelHasSaveData()) {
            levelFileName = ""; //Todo: Get level file name from save data
        } else {
            levelFileName = "level" + GameManager.getCurrentLevelNumber() + ".txt";
        }
        Level level = new Level(levelFileName,(GameController) getController());
        //TODO:Decide if we need to load a new level or continue current level here -------------------------
        GameManager.setCurrentLevel(level);

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
     * PLACEHOLDER METHOD, THIS IS WAITING FOR DATA PERSISTENCE IMPLEMENTATION
     * Determines if the current level has saved data to load from.
     *
     * @return false
     */
    public boolean levelHasSaveData() {
        return false;
    }

    @Override
    public void update() {
        GameManager.getCurrentLevel().update();
        draw();

        if(GameManager.getCurrentLevel().isLevelFailed()) {
            loadFailedLevelScreen();
        }
    }

    @Override
    public void draw() {
        getGraphicsContext().clearRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
        GameManager.getCurrentLevel().draw(getGraphicsContext());
    }

    public void loadFailedLevelScreen() {
        setNextScreen(new LevelFailedScreen());
        setFinished(true);
    }

}