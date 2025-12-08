package cs230.group29se.jewelthief.Scenes.EquipablesScene;

import cs230.group29se.jewelthief.Scenes.MainScene.MainMenuScreen;
import cs230.group29se.jewelthief.Scenes.Screen;

/**
 * Represents the Equipables screen in the game.
 * This screen allows players to view and manage their equipable items.
 *
 * @author Gustas Rove
 */
public class EquipablesScreen extends Screen {

    public static final String EQUIPABLES = "Equipables";
    public static final String EQUIPABLES_XML_PATH = "/cs230/group29se/jewelthief/equipables-view.fxml";

    /**
     * Constructs a new EquipablesScreen instance.
     * Sets the screen title, FXML path, and the next screen to navigate to.
     */
    public EquipablesScreen() {
        setScreenTitle(EQUIPABLES); // Sets the title of the screen
        setScreenFXMLPath(EQUIPABLES_XML_PATH); // Path to the FXML file
        setNextScreen(new MainMenuScreen()); // Sets the next screen to the main menu
    }

    /**
     * Called when the screen is initialized.
     * Currently, no initialization logic is implemented.
     */
    @Override
    public void onInitialize() {
        // No initialization logic required for this screen
    }

    /**
     * Updates the screen. Currently, no update logic is implemented.
     */
    @Override
    public void update() {
        // No update logic required for this screen
    }

    /**
     * Draws the screen. Currently, no drawing logic is implemented.
     */
    @Override
    public void draw() {
        // No drawing logic required for this screen
    }
}
