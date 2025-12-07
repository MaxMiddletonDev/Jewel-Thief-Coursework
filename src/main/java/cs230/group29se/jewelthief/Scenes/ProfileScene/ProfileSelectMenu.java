package cs230.group29se.jewelthief.Scenes.ProfileScene;

import cs230.group29se.jewelthief.Game.GameProfileHelper;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.LevelSelectScene.LevelSelectScreen;
import cs230.group29se.jewelthief.Scenes.Screen;

public class ProfileSelectMenu extends Screen {

    public enum NextAction { CONTINUE, BACK }

    private NextAction nextAction = NextAction.CONTINUE;
    private String selectedProfile;

    public ProfileSelectMenu() {
        setScreenTitle("Select Profile");
        setScreenFXMLPath("/cs230/group29se/jewelthief/profile-select-view.fxml");
    }

    @Override
    public void onInitialize() {
        if (getController() instanceof ProfileSelectController c) {
            c.populateProfiles();
        }
    }

    @Override
    public void update() { }

    @Override
    public void draw() { }

    public void onProfileChosen(String profileName) {
        this.selectedProfile = profileName;
        this.nextAction = NextAction.CONTINUE;
        this.finished = true;
    }

    public void onBackClicked() {
        this.nextAction = NextAction.BACK;
        this.finished = true;
    }

    public String getSelectedProfile() {
        return selectedProfile;
    }

    public NextAction getNextAction() {
        return nextAction;
    }

    @Override
    public Screen getNextScreen() {
        if (nextAction == NextAction.BACK) {
            return new MainMenuScreen();
        }
        // CONTINUE: go to Level Select after setting active profile
        if (selectedProfile != null && !selectedProfile.isEmpty()) {
            GameProfileHelper.setActiveProfileName(selectedProfile);
        }
        return new LevelSelectScreen();
    }
}