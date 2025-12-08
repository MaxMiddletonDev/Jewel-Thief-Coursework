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

public class EquipablesController extends BaseController implements Initializable {

    @FXML public Label titleLabel;
    @FXML public Button backButton;

    @FXML public ImageView skin1Image;
    @FXML public Button skin1Button;

    @FXML public ImageView skin2Image;
    @FXML public Button skin2Button;

    @FXML public ImageView skin3Image;
    @FXML public Button skin3Button;

    @FXML public ImageView skin4Image;
    @FXML public Button skin4Button;

    @FXML public ImageView skin5Image;
    @FXML public Button skin5Button;

    @FXML public ImageView skin6Image;
    @FXML public Button skin6Button;

    @FXML public ImageView skin7Image;
    @FXML public Button skin7Button;


    @Override
    public Canvas getCanvas() {
        return null;
    }
    List<String> unlockedAchievements;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        unlockedAchievements =
                PersistenceManager.getCurrentProfile().getUnlockedAchievements();

        // Map skin slots:
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

        // Load default skin images + enable/disable buttons
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

    @FXML
    public void onBackClicked() {
        getScreen().setFinished(true);
    }

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

    // -------------------------------
    // Individual skin selection methods
    // -------------------------------

    @FXML
    public void selectSkin1() {
        selectSkin(SkinRegistry.getById(SkinId.DEFAULT_GUY));
    }

    @FXML
    public void selectSkin2() {
        selectSkin(SkinRegistry.getById(SkinId.SPEEDSTER));
    }

    @FXML
    public void selectSkin3() {
        selectSkin(SkinRegistry.getById(SkinId.SURVIVOR));
    }

    @FXML
    public void selectSkin4() {
        selectSkin(SkinRegistry.getById(SkinId.DEMO_MAN));
    }

    @FXML
    public void selectSkin5() {
        selectSkin(SkinRegistry.getById(SkinId.MONEY_MAN));
    }

    @FXML
    public void selectSkin6() {
        selectSkin(SkinRegistry.getById(SkinId.PRO));
    }

    @FXML
    public void selectSkin7() {
        selectSkin(SkinRegistry.getById(SkinId.TANK));
    }

    private void selectSkin(Skin skin) {
        PersistenceManager.writeSelectedSkin(skin);
    }
}
