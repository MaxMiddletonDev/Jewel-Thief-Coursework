package cs230.group29se.jewelthief.Scenes.EquipablesScene;

import cs230.group29se.jewelthief.Cosmetics.Skin;
import cs230.group29se.jewelthief.Cosmetics.SkinId;
import cs230.group29se.jewelthief.Cosmetics.SkinRegistry;
import cs230.group29se.jewelthief.Game.Achievements;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller for the Equipables screen.
 * Handles the display and selection of equipable skins.
 *
 * @author Gustas Rove
 */
public class EquipablesController extends BaseController implements Initializable {

    /**
     * Title label for the Equipables screen.
     */
    @FXML public Label titleLabel;

    /**
     * Back button to return to the previous screen.
     */
    @FXML public Button backButton;

    /**
     * ImageView for displaying the first skin.
     */
    @FXML public ImageView skin1Image;

    /**
     * Button to select the first skin.
     */
    @FXML public Button skin1Button;

    /**
     * ImageView for displaying the second skin.
     */
    @FXML public ImageView skin2Image;

    /**
     * Button to select the second skin.
     */
    @FXML public Button skin2Button;

    /**
     * ImageView for displaying the third skin.
     */
    @FXML public ImageView skin3Image;

    /**
     * Button to select the third skin.
     */
    @FXML public Button skin3Button;

    /**
     * ImageView for displaying the fourth skin.
     */
    @FXML public ImageView skin4Image;

    /**
     * Button to select the fourth skin.
     */
    @FXML public Button skin4Button;

    /**
     * ImageView for displaying the fifth skin.
     */
    @FXML public ImageView skin5Image;

    /**
     * Button to select the fifth skin.
     */
    @FXML public Button skin5Button;

    /**
     * ImageView for displaying the sixth skin.
     */
    @FXML public ImageView skin6Image;

    /**
     * Button to select the sixth skin.
     */
    @FXML public Button skin6Button;

    /**
     * ImageView for displaying the seventh skin.
     */
    @FXML public ImageView skin7Image;

    /**
     * Button to select the seventh skin.
     */
    @FXML public Button skin7Button;


    /**
     * Returns the canvas associated with this screen.
     * This screen does not use a canvas, so it returns null.
     *
     * @return null
     */
    @Override
    public Canvas getCanvas() {
        return null;
    }

    /**
     * List of unlocked achievements for the current profile.
     */
    private List<String> unlockedAchievements;

    /**
     * Initializes the controller after the FXML file has been loaded.
     * Loads the skin images and enables/disables buttons based on unlock status.
     *
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        unlockedAchievements =
                PersistenceManager.getCurrentProfile().getUnlockedAchievements();

        // Map skin slots to their corresponding ImageView and Button
        Map<SkinId, ImageView> imageMap = Map.of(
                SkinId.DEFAULT_GUY, skin1Image,
                SkinId.SPEEDSTER,   skin2Image,
                SkinId.SURVIVOR,    skin3Image,
                SkinId.DEMO_MAN,    skin4Image,
                SkinId.MONEY_MAN,   skin5Image,
                SkinId.PRO,         skin6Image,
                SkinId.TANK,        skin7Image
        );

        Map<SkinId, Button> buttonMap = Map.of(
                SkinId.DEFAULT_GUY, skin1Button,
                SkinId.SPEEDSTER,   skin2Button,
                SkinId.SURVIVOR,    skin3Button,
                SkinId.DEMO_MAN,    skin4Button,
                SkinId.MONEY_MAN,   skin5Button,
                SkinId.PRO,         skin6Button,
                SkinId.TANK,        skin7Button
        );

        // Load default skin images and enable/disable buttons based on unlock status
        for (Skin skin : SkinRegistry.getAllSkins()) {
            SkinId skinId = skin.getId();

            ImageView img = imageMap.get(skinId);
            Button btn = buttonMap.get(skinId);

            if (img != null)
                img.setImage(skin.getImage());

            boolean isUnlocked = isSkinUnlocked(skinId);

            if (btn != null) {
                btn.setDisable(!isUnlocked);
                if (!isUnlocked)
                    btn.setText("Locked");
            }
        }
    }

    /**
     * Handles the "Back" button click event.
     * Marks the screen as finished, allowing navigation back to the previous screen.
     */
    @FXML
    public void onBackClicked() {
        getScreen().setFinished(true);
    }

    /**
     * Checks if a skin is unlocked based on the player's achievements.
     *
     * @param skinId the ID of the skin to check
     * @return true if the skin is unlocked, false otherwise
     */
    public boolean isSkinUnlocked(SkinId skinId) {
        return switch (skinId) {
            case DEFAULT_GUY -> true;
            case SPEEDSTER -> unlockedAchievements.contains(Achievements.SPEEDSTER.toString());
            case SURVIVOR -> unlockedAchievements.contains(Achievements.SURVIVOR.toString());
            case DEMO_MAN -> unlockedAchievements.contains(Achievements.DEMO_MAN.toString());
            case MONEY_MAN -> unlockedAchievements.contains(Achievements.MONEY_MAN.toString());
            case PRO -> unlockedAchievements.contains(Achievements.PRO.toString());
            case TANK -> unlockedAchievements.contains(Achievements.TANK.toString());
            default -> false;
        };
    }

    /**
     * Selects the first skin and marks it as the currently equipped skin.
     */
    @FXML
    public void selectSkin1() {
        selectSkin(SkinRegistry.getById(SkinId.DEFAULT_GUY));
    }

    /**
     * Selects the second skin and marks it as the currently equipped skin.
     */
    @FXML
    public void selectSkin2() {
        selectSkin(SkinRegistry.getById(SkinId.SPEEDSTER));
    }

    /**
     * Selects the third skin and marks it as the currently equipped skin.
     */
    @FXML
    public void selectSkin3() {
        selectSkin(SkinRegistry.getById(SkinId.SURVIVOR));
    }

    /**
     * Selects the fourth skin and marks it as the currently equipped skin.
     */
    @FXML
    public void selectSkin4() {
        selectSkin(SkinRegistry.getById(SkinId.DEMO_MAN));
    }

    /**
     * Selects the fifth skin and marks it as the currently equipped skin.
     */
    @FXML
    public void selectSkin5() {
        selectSkin(SkinRegistry.getById(SkinId.MONEY_MAN));
    }

    /**
     * Selects the sixth skin and marks it as the currently equipped skin.
     */
    @FXML
    public void selectSkin6() {
        selectSkin(SkinRegistry.getById(SkinId.PRO));
    }

    /**
     * Selects the seventh skin and marks it as the currently equipped skin.
     */
    @FXML
    public void selectSkin7() {
        selectSkin(SkinRegistry.getById(SkinId.TANK));
    }

    /**
     * Marks the given skin as the currently equipped skin and saves the selection.
     *
     * @param skin the skin to select
     */
    private void selectSkin(Skin skin) {
        PersistenceManager.writeSelectedSkin(skin);
        getScreen().setFinished(true);
    }
}
