package cs230.group29se.jewelthief.Scenes.MainScene;

import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class MainMenuScreen extends Screen {

    private MainMenuController controller;

    @Override
    public void initialize() { }

    @Override
    public void update() { }

    @Override
    public void draw() { }

    @Override
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/cs230/group29se/jewelthief/main-view.fxml")
            );
            root = loader.load();
            controller = loader.getController();
            controller.setScreen(this);
            scene = new Scene(root, 500, 500);
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onStartClicked() {
        this.finished = true;
    }
}