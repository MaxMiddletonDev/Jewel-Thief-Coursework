package cs230.group29se.jewelthief;
import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.LevelSelectScene.LevelSelectScreen;
import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

// DO NOT TOUCH THIS FILE AT ALL
public class MainApplication extends Application {
    private Timeline tickTimeline;
    public static Screen currentScreen; //TODO: if fuckery is going on, probs cos timeline is creating a new thread and this shit aint thread safe
    private Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        boolean exitProgram = false;
        currentScreen = new MainMenuScreen();
        currentScreen.initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        try{
            scene = new Scene(fxmlLoader.load(), 320, 240);
        }
        catch (IOException e) {
            System.out.println("error: Failed to load level select screen");
            e.printStackTrace(); // UH OH
        }
        //tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick(currentScreen, stage)));
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick(stage)));

        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
        // Handles the screen transitions

    }

    public void tick(Stage stage){
        //TODO: Exit program if exitProgram  == true
        switch (currentScreen){
            case MainMenuScreen mainMenuScreen -> {
                 //Handle main menu logic here
                if(mainMenuScreen.isFinished()){
                    currentScreen = new LevelSelectScreen();
                    stage.setTitle("Level Select");
                    scene = currentScreen.createScene();
                    stage.setScene(scene);
                    stage.show();
                    currentScreen.initialize();
//                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("level-select-view.fxml"));
//                    try{
//                        scene = new Scene(fxmlLoader.load(), 320, 240);
//                    }
//                    catch (IOException e) {
//                        System.out.println("error: Failed to load level select screen");
//                        e.printStackTrace(); // UH OH
//                    }

                }else{
                    mainMenuScreen.update();
                }

            }
            case LevelSelectScreen levelSelectScreen -> {
                // Handle level select logic here
                if(levelSelectScreen.isFinished()){
                    currentScreen = new GameScreen();
                    stage.setTitle("Playing Game");
                    scene = currentScreen.createScene();
                    stage.setScene(scene);
                    stage.show();
                    currentScreen.initialize();
                }else{
                    levelSelectScreen.update();
                }
            }
            case GameScreen gameScreen -> {
                // Handle game logic here
                if(gameScreen.isFinished()){
                    currentScreen = new MainMenuScreen();
                    stage.setTitle("Main Menu");
                    scene =currentScreen.createScene();
                    stage.setScene(scene);
                    stage.show();
                    currentScreen.initialize();
                }else{
                    gameScreen.update();
                }
            }
            default -> {
                System.out.println("unknown current screen ");
                throw new IllegalStateException("Unexpected value: " + currentScreen);
            }
        }
    }
    public static void main(String[] args) {
        launch();
    }
}