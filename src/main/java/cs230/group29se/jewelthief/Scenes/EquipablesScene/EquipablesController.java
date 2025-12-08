package cs230.group29se.jewelthief.Scenes.EquipablesScene;

import cs230.group29se.jewelthief.Cosmetics.Skin;
import cs230.group29se.jewelthief.Cosmetics.SkinId;
import cs230.group29se.jewelthief.Cosmetics.SkinRegistry;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EquipablesController extends BaseController implements Initializable {
    @FXML
    public Label titleLabel;
    public Button backButton;
    public ImageView skin1Image;
    public Button skin1Button;
    public ImageView skin2Image;
    public Button skin2Button;
    public ImageView skin3Image;
    public Button skin3Button;
    public ImageView skin4Image;
    public Button skin4Button;

    private List<ImageView> imageSlots;
    private List<Button> buttonSlots;
    @Override
    public Canvas getCanvas() {
        return null;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up ordered UI slots
        imageSlots = List.of(skin1Image, skin2Image, skin3Image, skin4Image);
        buttonSlots = List.of(skin1Button, skin2Button, skin3Button, skin4Button);

        // Load all skins from registry
        int index = 0;
        for (Skin skin : SkinRegistry.getAllSkins()) {
            if (index >= imageSlots.size())
                break; // avoid out-of-bounds if more skins exist

            ImageView img = imageSlots.get(index);
            Button btn = buttonSlots.get(index);

            // Load skin image
            img.setImage(skin.getImage());

            // Set button text
            btn.setText("Select " + skin.getDisplayName());

            // Assign button click → choose this skin
            btn.setOnAction(e -> setSkinButton(skin));
            index++;
        }
    }

    @FXML
    public void onBackClicked() {
        getScreen().setFinished(true);
    }

    public void setSkinButton(Skin skin){

        PersistenceManager.writeSelectedSkin(skin);
    }
}
