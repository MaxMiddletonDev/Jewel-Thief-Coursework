package cs230.group29se.jewelthief.Scenes.EquipablesScene;

import cs230.group29se.jewelthief.Cosmetics.Skin;
import cs230.group29se.jewelthief.Cosmetics.SkinId;
import cs230.group29se.jewelthief.Cosmetics.SkinRegistry;
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

    @FXML public ImageView skin8Image;
    @FXML public Button skin8Button;

    @FXML public ImageView skin9Image;
    @FXML public Button skin9Button;

    @Override
    public Canvas getCanvas() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> unlocked =
                PersistenceManager.getCurrentProfile().getUnlockedAchievements();

        // Map skin slots:
        Map<SkinId, ImageView> imageMap = Map.of(
                SkinId.DEFAULT_GUY, skin1Image,
                SkinId.SPEED_GUY,   skin2Image,
                SkinId.CAT_CAT,     skin3Image,
                SkinId.SPEEDSTER,   skin4Image,
                SkinId.SURVIVOR,    skin5Image,
                SkinId.DEMO_MAN,    skin6Image,
                SkinId.MONEY_MAN,   skin7Image,
                SkinId.PRO,         skin8Image,
                SkinId.TANK,        skin9Image

        );

        Map<SkinId, Button> buttonMap = Map.of(
                SkinId.DEFAULT_GUY, skin1Button,
                SkinId.SPEED_GUY,   skin2Button,
                SkinId.CAT_CAT,     skin3Button,
                SkinId.SPEEDSTER,   skin4Button,
                SkinId.SURVIVOR,    skin5Button,
                SkinId.DEMO_MAN,    skin6Button,
                SkinId.MONEY_MAN,   skin7Button,
                SkinId.PRO,         skin8Button,
                SkinId.TANK,        skin9Button
        );

        // Load default skin images + enable/disable buttons
        for (Skin skin : SkinRegistry.getAllSkins()) {
            SkinId id = skin.getId();

            ImageView img = imageMap.get(id);
            Button btn = buttonMap.get(id);

            if (img != null)
                img.setImage(skin.getImage());

            boolean isUnlocked =
                    id == SkinId.DEFAULT_GUY || unlocked.contains(id.name());

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

    // -------------------------------
    // Individual skin selection methods
    // -------------------------------

    @FXML
    public void selectSkin1() {
        selectSkin(SkinRegistry.getById(SkinId.DEFAULT_GUY));
    }

    @FXML
    public void selectSkin2() {
        selectSkin(SkinRegistry.getById(SkinId.SPEED_GUY));
    }

    @FXML
    public void selectSkin3() {
        selectSkin(SkinRegistry.getById(SkinId.CAT_CAT));
    }

    @FXML
    public void selectSkin4() {
        selectSkin(SkinRegistry.getById(SkinId.SPEEDSTER));
    }

    @FXML
    public void selectSkin5() {
        selectSkin(SkinRegistry.getById(SkinId.SURVIVOR));
    }

    @FXML
    public void selectSkin6() {
        selectSkin(SkinRegistry.getById(SkinId.DEMO_MAN));
    }

    @FXML
    public void selectSkin7() {
        selectSkin(SkinRegistry.getById(SkinId.MONEY_MAN));
    }

    @FXML
    public void selectSkin8() {
        selectSkin(SkinRegistry.getById(SkinId.PRO));
    }

    @FXML
    public void selectSkin9() {
        selectSkin(SkinRegistry.getById(SkinId.TANK));
    }

    private void selectSkin(Skin skin) {
        PersistenceManager.writeSelectedSkin(skin);
    }
}
