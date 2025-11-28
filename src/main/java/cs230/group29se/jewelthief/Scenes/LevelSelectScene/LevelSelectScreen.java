package cs230.group29se.jewelthief.Scenes.LevelSelectScene;

import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class LevelSelectScreen extends Screen {
    //Only needed if we plan to draw directly onto the canvas for level select buttons, remove if not
    private Canvas levelSelectCanvas;
    private GraphicsContext gc;
    private LevelSelectController controller;

    @Override
    public void initialize(){
        try {
            drawInitial();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }

    public void drawInitial() {
    }
    @Override
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/cs230/group29se/jewelthief/level-select-view.fxml")
            );

            root = loader.load();
            controller = loader.getController();

            //Bind this screen to the controller
            controller.setScreen(this);

//            levelSelectCanvas = controller.levelSelectCanvas;
//            gc = levelSelectCanvas.getGraphicsContext2D();
            scene = new Scene(root, 320, 240);
            return scene;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
