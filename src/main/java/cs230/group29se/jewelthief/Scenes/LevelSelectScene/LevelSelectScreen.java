package cs230.group29se.jewelthief.Scenes.LevelSelectScene;

import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class LevelSelectScreen extends Screen {
    // Only needed if we plan to draw directly onto the canvas for level select buttons, remove if not
    private Canvas levelSelectCanvas;
    private GraphicsContext gc;
    private LevelSelectController controller;

    // --- NEW: store which level was selected (and optionally profile) ---
    private int selectedLevel = 1;      // default to level 1
    private String selectedProfile = "Amsyar"; // or whatever you use

    @Override
    public void initialize(){
        try {
            drawInitial();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() { }

    @Override
    public void draw() { }

    public void drawInitial() { }

    @Override
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/cs230/group29se/jewelthief/level-select-view.fxml")
            );
            root = loader.load();
            controller = loader.getController();
            // Bind this screen to the controller
            controller.setScreen(this);
            scene = new Scene(root, 320, 240);
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * called by controller when a level button is clicked.
     * @author Iyaad
     */
    public void onLevelChosen(int levelNum) {
        this.selectedLevel = levelNum;
        // you may also set isFinished = true here so MainApplication switches to GameScreen
        this.finished = true; // assuming Screen has a 'finished' flag + isFinished()
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public String getSelectedProfile() {
        return selectedProfile;
    }

    public void setSelectedProfile(String profileName) {
        this.selectedProfile = profileName;
    }
}