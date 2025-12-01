package cs230.group29se.jewelthief.Scenes.LevelSelectScene;

import cs230.group29se.jewelthief.MainApplication;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class LevelSelectScreen extends Screen {

    public LevelSelectScreen() {
        setScreenTitle("Level Select");
        setScreenFXMLPath("/cs230/group29se/jewelthief/level-select-view.fxml");
    }

    @Override
    public void initialize(){
        setNextScreen(new GameScreen());
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }

}
