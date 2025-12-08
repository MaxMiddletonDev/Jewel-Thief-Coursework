package cs230.group29se.jewelthief.Scenes;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 * Abstract class representing a Screen in the application.
 * Each screen must implement methods for initialization, updating, and drawing.
 * Provides common functionality for managing screen transitions, FXML loading, and rendering.
 *
 * @author Gustas Rove
 * @version 1.0
 */
abstract public class Screen {
    public static final int DEFAULT_SCENE_WIDTH = 1000; // Default width of the scene
    public static final int DEFAULT_SCENE_HEIGHT = 900; // Default height of the scene

    private String screenFXMLPath; // Path to the FXML file for this screen
    private Screen nextScreen; // The next screen to transition to
    private Canvas canvas; // Canvas for rendering
    private GraphicsContext gc; // Graphics context for drawing on the canvas
    private BaseController controller; // Controller associated with this screen

    protected Pane root; // Root pane of the screen
    protected Scene scene; // JavaFX Scene object
    protected boolean finished = false; // Indicates if the screen is finished

    private String screenTitle; // Title of the screen

    /**
     * Final initialize wrapper. Ensures the GameManager's current scene is set,
     * then calls the subclass hook {@link #onInitialize()}.
     */
    public final void initialize() {
        GameManager.setCurrentScreen(this); // Set the current screen in the GameManager
        onInitialize(); // Call the subclass-specific initialization logic
    }

    /**
     * Subclasses implement this instead of overriding {@link #initialize()}.
     * Initialization logic for each scene goes here.
     */
    public abstract void onInitialize();

    /**
     * Update method where game logic happens.
     * Subclasses must implement this method.
     */
    public abstract void update();

    /**
     * Draw method where rendering happens.
     * Subclasses must implement this method.
     */
    public abstract void draw();

    /**
     * Check if the screen is finished.
     * Used to transition to the next screen.
     *
     * @return true if finished, false otherwise
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Set the finished state of the screen.
     *
     * @param finished true if finished, false otherwise
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Set the canvas for this screen.
     *
     * @param canvas the Canvas object
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Get the canvas for this screen.
     *
     * @return the Canvas object
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Set the graphics context for this screen.
     *
     * @param gc the GraphicsContext object
     */
    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * Get the graphics context for this screen.
     *
     * @return the GraphicsContext object
     */
    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    /**
     * Set the game controller for this screen.
     *
     * @param controller the GameController object
     */
    public void setController(BaseController controller) {
        this.controller = controller;
    }

    /**
     * Get the game controller for this screen.
     *
     * @return the GameController object
     */
    public BaseController getController() {
        return controller;
    }

    /**
     * Set the next screen to transition to.
     *
     * @param nextScreen the next Screen object
     */
    public void setNextScreen(Screen nextScreen) {
        this.nextScreen = nextScreen;
    }

    /**
     * Get the next screen to transition to.
     *
     * @return the next Screen object
     */
    public Screen getNextScreen() {
        return nextScreen;
    }

    /**
     * Get the title of this screen.
     *
     * @return the screen title
     */
    public String getScreenTitle() {
        return screenTitle;
    }

    /**
     * Set the title of this screen.
     *
     * @param screenTitle the screen title
     */
    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }

    /**
     * Set the FXML path for this screen.
     * Example: "/cs230/group29se/jewelthief/level-select-view.fxml"
     *
     * @param path the FXML file path
     */
    public void setScreenFXMLPath(String path) {
        this.screenFXMLPath = path;
    }

    /**
     * Get the FXML path for this screen.
     *
     * @return the FXML file path
     */
    public String getScreenFXMLPath() {
        return screenFXMLPath;
    }

    /**
     * Creates and initializes the JavaFX Scene for this screen.
     * Loads the FXML file, sets up the controller, and binds the canvas and graphics context.
     *
     * @return the created Scene object, or null if an error occurs
     */
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(getScreenFXMLPath()));
            root = loader.load(); // Load the FXML file
            setController(loader.getController()); // Set the controller
            getController().setScreen(this); // Bind this screen to the controller
            setCanvas(getController().getCanvas()); // Set the canvas

            if (getCanvas() != null) {
                GraphicsContext gc = getCanvas().getGraphicsContext2D();
                setGraphicsContext(gc); // Set the graphics context
            }

            double x = MainApplication.getWindowWidth();
            double y = MainApplication.getWindowHeight();

            if (x <= 0 || y <= 0) {
                x = DEFAULT_SCENE_WIDTH;
                y = DEFAULT_SCENE_HEIGHT;
            }

            scene = new Scene(root, x, y); // Create the scene with the specified dimensions
            return scene;
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
            return null; // Return null if an error occurs
        }
    }
}
