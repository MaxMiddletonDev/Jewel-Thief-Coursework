package cs230.group29se.jewelthief.Scenes;

import cs230.group29se.jewelthief.MainApplication;
import cs230.group29se.jewelthief.Scenes.GameScene.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 * Abstract class representing a Screen in the application.
 * Each screen must implement methods for initialization, updating, and drawing.
 *
 * @author Gustas Rove
 * @version 1.0
 */
abstract public class Screen {
    public static final int DEFAULT_SCENE_WIDTH = 800;
    public static final int DEFAULT_SCENE_HEIGHT = 600;

    private String screenFXMLPath;

    private Screen nextScreen;

    private Canvas canvas;
    private GraphicsContext gc;
    private BaseController controller;

    protected Pane root;
    protected Scene scene;
    protected boolean finished = false;

    private String screenTitle;

    /**
     * Initialize, Scene Setup happens here
     */
    public abstract void initialize();

    /**
     * Update, Game Logic happens here
     */
    public abstract void update();

    /**
     * Draw, Rendering happens here
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
     * Create and return the Scene for this screen.
     *
     * @return the Scene object
     */
    public Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(getScreenFXMLPath())
            );
            root = loader.load();

            Object controllerObj = loader.getController();

            // Only set controller/canvas/gc if it is BaseController (was giving errors before)
            if (controllerObj instanceof BaseController baseController) {
                setController(baseController);
                baseController.setScreen(this);

                setCanvas(baseController.getCanvas());
                if (getCanvas() != null) {
                    GraphicsContext gc = getCanvas().getGraphicsContext2D();
                    setGraphicsContext(gc);
                }
            } else {
                // For controllers that don't extend BaseController (e.g. MainMenuController),
                // can still wire manually in their own Screen subclass if needed.
                setController(null);
            }

            double x = MainApplication.getWindowWidth();
            double y = MainApplication.getWindowHeight();
            if (x <= 0 || y <= 0) {
                x = DEFAULT_SCENE_WIDTH;
                y = DEFAULT_SCENE_HEIGHT;
            }

            scene = new Scene(root, x, y);
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
