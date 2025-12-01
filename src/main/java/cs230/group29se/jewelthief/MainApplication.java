package cs230.group29se.jewelthief;

import cs230.group29se.jewelthief.Scenes.GameScene.GameScreen;
import cs230.group29se.jewelthief.Scenes.LevelFailedScene.LevelFailedScreen;
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

/**
 * The main application class for the Jewel Thief game, extending the JavaFX Application class.
 * This class initializes the primary stage, manages the game loop, and handles screen transitions.
 *
 * @author Gustas Rove, Ben Poole
 * @version 1.0
 */
public class MainApplication extends Application {
    /**
     * The timeline responsible for simulating the game loop with periodic ticks.
     */
    private Timeline tickTimeline;

    /**
     * The current screen being displayed in the application.
     */
    public static Screen currentScreen;

    /**
     * The JavaFX scene currently being displayed.
     */
    private static Scene scene;

    /**
     * Starts the JavaFX application by initializing the main stage, setting up the game loop,
     * and loading the initial screen.
     *
     * @param stage The primary JavaFX stage where the application will be displayed.
     * @throws IOException If an error occurs while loading resources for the application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        boolean exitProgram = false;

        tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick(stage)));

        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();

        loadNextScreen(stage, new MainMenuScreen());
    }

    /**
     * Simulates a tick in the game loop, updating the current screen based on its state.
     * Depending on the type of the current screen, this method performs the appropriate
     * logic, such as updating the screen or transitioning to the next screen.
     *
     * @param stage The JavaFX stage used to display the application.
     * @throws IllegalStateException if the current screen is of an unexpected type.
     */
    public void tick(Stage stage) {
        //TODO: Exit program if exitProgram  == true
        switch (currentScreen) {
            case MainMenuScreen mainMenuScreen -> {
                //Handle main menu logic here
                if (mainMenuScreen.isFinished()) {
                    loadNextScreen(stage, mainMenuScreen.getNextScreen());
                } else {
                    mainMenuScreen.update();
                }

            }
            case LevelSelectScreen levelSelectScreen -> {
                // Handle level select logic here
                if (levelSelectScreen.isFinished()) {
                    loadNextScreen(stage, levelSelectScreen.getNextScreen());
                } else {
                    levelSelectScreen.update();
                }
            }
            case GameScreen gameScreen -> {
                // Handle game logic here
                if (gameScreen.isFinished()) {
                    loadNextScreen(stage, gameScreen.getNextScreen());
                } else {
                    gameScreen.update();
                }
            }
            case LevelFailedScreen levelFailedScreen -> {
                // Handle level failed logic here
                if (levelFailedScreen.isFinished()) {
                    loadNextScreen(stage, levelFailedScreen.getNextScreen());
                } else {
                    levelFailedScreen.update();
                }
            }
            default -> {
                System.out.println("unknown current screen ");
                throw new IllegalStateException("Unexpected value: " + currentScreen);
            }
        }
    }

    /**
     * Loads the next screen in the application by setting the current screen, updating the stage title,
     * creating and setting the new scene, and initializing the new screen.
     *
     * @param stage      The primary JavaFX stage where the scene will be displayed.
     * @param nextScreen The next screen to be loaded, which implements the Screen interface.
     */
    private void loadNextScreen(Stage stage, Screen nextScreen) {
        currentScreen = nextScreen;
        stage.setTitle(nextScreen.getScreenTitle());
        scene = currentScreen.createScene();
        stage.setScene(scene);
        stage.show();
        currentScreen.initialize();

    }

    /**
     * Retrieves the current width of the application window.
     * If the scene is not initialized, returns the default scene width.
     *
     * @return The width of the window or the default scene width if the scene is null.
     */
    public static double getWindowWidth() {
        if (scene == null) {
            return Screen.DEFAULT_SCENE_WIDTH;
        }
        return scene.getWidth();
    }

    /**
     * Retrieves the current height of the application window.
     * If the scene is not initialized, returns the default scene height.
     *
     * @return The height of the window or the default scene height if the scene is null.
     */
    public static double getWindowHeight() {
        if (scene == null) {
            return Screen.DEFAULT_SCENE_HEIGHT;
        }
        return scene.getHeight();
    }

    /**
     * The main entry point for the application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }
}