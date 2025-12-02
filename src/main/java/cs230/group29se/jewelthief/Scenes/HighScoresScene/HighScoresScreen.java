package cs230.group29se.jewelthief.Scenes.HighScoresScene;

import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;

public class HighScoresScreen extends Screen {

    private HighScoresController controller;

    public HighScoresScreen() {
        setScreenTitle("High Scores");
        setScreenFXMLPath("/cs230/group29se/jewelthief/highscores-view.fxml");
    }

    @Override
    public void initialize() {
        if (getController() instanceof HighScoresController c) {
            controller = c;
            c.loadData();
        }
    }

    @Override
    public void update() { }

    @Override
    public void draw() { }

    public void onBackClicked() {
        finished = true;
    }

    @Override
    public Screen getNextScreen() {
        // Always go back to main menu when finished
        return new MainMenuScreen();
    }
}