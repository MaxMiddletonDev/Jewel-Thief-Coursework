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

    public enum NextAction { START_GAME, SHOW_HIGHSCORES }

    private NextAction nextAction = NextAction.START_GAME;

    public void onStartClicked() {
        nextAction = NextAction.START_GAME;
        finished = true;
    }

    public void onHighScoresClicked() {
        nextAction = NextAction.SHOW_HIGHSCORES;
        finished = true;
    }

    public NextAction getNextAction() {
        return nextAction;
    }

}