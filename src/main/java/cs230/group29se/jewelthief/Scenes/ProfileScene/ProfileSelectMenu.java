package cs230.group29se.jewelthief.Scenes.ProfileScene;

import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class ProfileSelectMenu extends Screen {

    public enum NextAction { CONTINUE, BACK }

    private NextAction nextAction = NextAction.CONTINUE;
    private ProfileSelectController controller;
    private String selectedProfile;

    @Override
    public void initialize() {
        if (controller != null) {
            controller.populateProfiles();
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
                    getClass().getResource("/cs230/group29se/jewelthief/profile-select-view.fxml")
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
}