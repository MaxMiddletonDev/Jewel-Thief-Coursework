package cs230.group29se.jewelthief.Scenes.ProfileScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.GameProfileHelper;
import cs230.group29se.jewelthief.Persistence.Profile.ProfileData;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.LevelSelectScene.LevelSelectScreen;
import cs230.group29se.jewelthief.Scenes.Screen;

import static cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager.loadProfile;

public class ProfileSelectScreen extends Screen {

    public enum NextAction { CONTINUE, BACK }

    private NextAction nextAction = NextAction.CONTINUE;
    private String selectedProfile;

    public ProfileSelectScreen() {
        setScreenTitle("Select Profile");
        setScreenFXMLPath("/cs230/group29se/jewelthief/profile-select-view.fxml");
        setNextScreen(new MainMenuScreen());
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
        GameManager.setSelectedProfileName(profileName);
        loadProfile(profileName);
        setFinished(true);
    }



    public void onBackClicked() {
        setFinished(true);
    }

    public String getSelectedProfile() {
        return selectedProfile;
    }

    public NextAction getNextAction() {
        return nextAction;
    }
}