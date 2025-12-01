package cs230.group29se.jewelthief.Scenes.HighScoresScene;

import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class HighScoresScreen extends Screen {

    private HighScoresController controller;

    @Override
    public void initialize() {
        if (controller != null) {
            controller.loadData();
        }
    }

    @Override
    public void update() { }

    @Override
    public void draw() { }

    @Override
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/cs230/group29se/jewelthief/highscores-view.fxml")
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

    public void onBackClicked() {
        this.finished = true;
    }
}