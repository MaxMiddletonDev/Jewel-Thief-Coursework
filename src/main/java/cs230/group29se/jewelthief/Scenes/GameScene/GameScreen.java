package cs230.group29se.jewelthief.Scenes.GameScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.Level;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class GameScreen extends Screen {
    private Canvas canvas;
    private GraphicsContext gc;
    private GameController controller;

    @Override   
    public void initialize() {
        GameManager.startNewGame(new Level("level1.txt", controller));
        root.getChildren().add(GameManager.getCurrentLevel().dummyPlayer);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP ->{
                    Node player = GameManager.getCurrentLevel().dummyPlayer;
                    player.setTranslateY(player.getTranslateY() - 40);
                }
                case DOWN -> {
                    Node player = GameManager.getCurrentLevel().dummyPlayer;
                    player.setTranslateY(player.getTranslateY() + 40);
                }
                case LEFT -> {
                    Node player = GameManager.getCurrentLevel().dummyPlayer;
                    player.setTranslateX(player.getTranslateX() - 40);
                }
                case RIGHT -> {
                    Node player = GameManager.getCurrentLevel().dummyPlayer;
                    player.setTranslateX(player.getTranslateX() + 40);
                }
            }
        });
    }

    @Override
    public void update() {
        draw();
        GameManager.getCurrentLevel().update();
    }

    @Override
    public void draw() {
        GameManager.draw(gc);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/cs230/group29se/jewelthief/game-view.fxml")
            );
            root = loader.load();
            controller = loader.getController();

            controller.setScreen(this);

            this.canvas = controller.gameCanvas;
            this.gc = canvas.getGraphicsContext2D();
            scene = new Scene(root, 1028, 700);
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}