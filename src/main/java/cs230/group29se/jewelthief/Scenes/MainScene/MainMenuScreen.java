package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.MainApplication;
import cs230.group29se.jewelthief.Scenes.LevelSelectScene.LevelSelectScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainMenuScreen extends Screen {

    public MainMenuScreen() {
        setScreenTitle("Main Menu");
        setScreenFXMLPath("/cs230/group29se/jewelthief/main-view.fxml");
    }
    @Override
    public void initialize(){
        setNextScreen(new LevelSelectScreen());
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }
}
