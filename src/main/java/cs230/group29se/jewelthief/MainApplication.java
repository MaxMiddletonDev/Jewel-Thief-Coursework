package cs230.group29se.jewelthief;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.LevelSelectScene.LevelSelectScreen;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import cs230.group29se.jewelthief.Scenes.ProfileScene.ProfileSelectMenu;
import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.GameProfileHelper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
// DO NOT TOUCH THIS FILE AT ALL
public class MainApplication extends Application {
    private Timeline tickTimeline;
    public static Screen currentScreen; //TODO: if fuckery is going on, probs cos timeline is creating a new thread and this shit aint thread safe
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        currentScreen = new MainMenuScreen();
        scene = currentScreen.createScene();
        currentScreen.initialize();

        tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick(stage)));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            if (tickTimeline != null) {
                tickTimeline.stop();
            }
            GameManager.saveCurrentGameState();
        });
    }

    public void tick(Stage stage) { //TODO: Exit program if exitProgram  == true
        switch (currentScreen) {
            case MainMenuScreen mainMenuScreen -> {
                if (mainMenuScreen.isFinished()) {
                    currentScreen = new ProfileSelectMenu();
                    stage.setTitle("Select Profile");
                    scene = currentScreen.createScene();
                    stage.setScene(scene);
                    stage.show();
                    currentScreen.initialize();
                } else {
                    mainMenuScreen.update();
                }
            }

            case ProfileSelectMenu profileSelectMenu -> {
                if (profileSelectMenu.isFinished()) {
                    String chosen = profileSelectMenu.getSelectedProfile();
                    if (chosen != null && !chosen.isEmpty()) {
                        GameProfileHelper.setActiveProfileName(chosen);
                    }
                    currentScreen = new LevelSelectScreen();
                    stage.setTitle("Level Select");
                    scene = currentScreen.createScene();
                    stage.setScene(scene);
                    stage.show();
                    currentScreen.initialize();
                } else {
                    profileSelectMenu.update();
                }
            }

            case LevelSelectScreen levelSelectScreen -> {
                if (levelSelectScreen.isFinished()) {
                    currentScreen = new GameScreen();
                    stage.setTitle("Playing Game");
                    scene = currentScreen.createScene();
                    stage.setScene(scene);
                    stage.show();
                    currentScreen.initialize();
                } else {
                    levelSelectScreen.update();
                }
            }

            case GameScreen gameScreen -> {
                if (gameScreen.isFinished()) {
                    currentScreen = new MainMenuScreen();
                    stage.setTitle("Main Menu");
                    scene = currentScreen.createScene();
                    stage.setScene(scene);
                    stage.show();
                    currentScreen.initialize();
                } else {
                    gameScreen.update();
                }
            }

            default -> {
                System.out.println("unknown current screen ");
                throw new IllegalStateException("Unexpected value: " + currentScreen);
            }
        }
    }

    public static double getWindowWidth(){
        return scene.getWidth();
    }

    public static double getWindowHeight(){
        return scene.getHeight();
    }

    public static void main(String[] args) {
        launch();
    }
}