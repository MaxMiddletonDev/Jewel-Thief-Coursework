package cs230.group29se.jewelthief.Scenes.AchievementsScene;

import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;

public class AchievementsScreen extends Screen{
    public AchievementsScreen() {
        setScreenTitle("Achievements");
        setScreenFXMLPath("/cs230/group29se/jewelthief/achievements-view.fxml");
    }

    @Override
    public void onInitialize() {
        if (getController() instanceof AchievementsController c) {
            c.loadAchievements();
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
        return new MainMenuScreen();
    }
}
