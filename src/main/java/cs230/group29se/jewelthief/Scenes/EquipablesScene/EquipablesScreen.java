package cs230.group29se.jewelthief.Scenes.EquipablesScene;

import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;

public class EquipablesScreen extends Screen {

    public EquipablesScreen() {
        setScreenTitle("Equipables");
        setScreenFXMLPath("/cs230/group29se/jewelthief/equipables-view.fxml");
        setNextScreen(new MainMenuScreen()); // No next screen defined
    }

    @Override
    public void onInitialize() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }
}
