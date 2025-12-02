package cs230.group29se.jewelthief.Scenes.ProfileScene;

import cs230.group29se.jewelthief.Game.GameProfileHelper;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProfileSelectController extends BaseController {

    @FXML
    private Button renameButton;

    @FXML
    private VBox slotsContainer;

    @FXML
    private TextField newProfileNameField;

    @FXML
    private Button createButton;

    @FXML
    private Button selectButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    // Track which profile is selected in this menu
    private String selectedProfile;

    @Override
    public Canvas getCanvas() {
        return null; // profile menu has no canvas
    }

    // Called from ProfileSelectMenu.initialize()
    public void populateProfiles() {
        slotsContainer.getChildren().clear();

        List<String> profiles = GameProfileHelper.listProfiles(); // ["save1", "save2",...]

        // Cap at 10
        if (profiles.size() > 10) {
            profiles = profiles.subList(0, 10);
        }

        for (int i = 0; i < profiles.size(); i++) {
            String name = profiles.get(i);
            int slotNumber = i + 1;  // 1..10 from top to bottom
            HBox slot = createSlotRow(slotNumber, name);
            slotsContainer.getChildren().add(slot);
        }

        if (selectedProfile != null && !profiles.contains(selectedProfile)) {
            selectedProfile = null;
        }

        // Disable "Create" if we already have 10 slots
        createButton.setDisable(profiles.size() >= 10);
    }

    private HBox createSlotRow(int slotNumber, String profileName) {
        HBox row = new HBox(10);
        // base style for unselected slot
        row.setStyle("-fx-background-color: #E8E9EB; -fx-background-radius: 8; -fx-padding: 10;");
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Remember profile name on this row
        row.setUserData(profileName);

        Label nameLabel = new Label(profileName);
        nameLabel.setStyle("-fx-text-fill: #222222; -fx-font-size: 16px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        Label hintLabel = new Label("Profile Slot");
        hintLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 12px;");

        Label numberLabel = new Label(String.valueOf(slotNumber));
        numberLabel.setStyle("-fx-text-fill: #777777; -fx-font-size: 14px;");
        numberLabel.setMinWidth(30);

        row.getChildren().addAll(nameLabel, spacer, hintLabel, numberLabel);

        row.setOnMouseClicked(e -> {
            selectedProfile = profileName;
            highlightSelection();
        });

        return row;
    }

    private void highlightSelection() {
        for (javafx.scene.Node node : slotsContainer.getChildren()) {
            if (!(node instanceof HBox row)) continue;
            Object data = row.getUserData();
            String name = (data instanceof String) ? (String) data : null;
            if (name != null && name.equals(selectedProfile)) {
                // selected: slightly darker border / background
                row.setStyle(
                        "-fx-background-color: #e0e0e0;" +
                                "-fx-background-radius: 8;" +
                                "-fx-padding: 10;" +
                                "-fx-border-color: #4a90e2;" +
                                "-fx-border-radius: 8;" +
                                "-fx-border-width: 2;"
                );
            } else {
                // unselected: subtle grey card
                row.setStyle(
                        "-fx-background-color: #E8E9EB;" +
                                "-fx-background-radius: 8;" +
                                "-fx-padding: 10;"
                );
            }
        }
    }

    @FXML
    private void handleCreateClicked() {
        List<String> profiles = GameProfileHelper.listProfiles();
        if (profiles.size() >= 10) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Max Saves Reached");
            alert.setHeaderText(null);
            alert.setContentText("You can only have up to 10 save slots.");
            alert.showAndWait();
            return;
        }

        String name = newProfileNameField.getText().trim();
        if (name.isEmpty()) return;

        GameProfileHelper.ensureProfileExists(name);
        populateProfiles();
        selectedProfile = name;
        highlightSelection();
        newProfileNameField.clear();
    }

    @FXML
    private void handleSelectClicked() {
        if (selectedProfile == null) return;

        Screen s = getScreen();
        if (s instanceof ProfileSelectMenu menu) {
            menu.onProfileChosen(selectedProfile);
        }
    }

    @FXML
    private void handleDeleteClicked() {
        if (selectedProfile == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Save");
        alert.setHeaderText("Delete \"" + selectedProfile + "\"?");
        alert.setContentText("This will delete the profile and its saves. This cannot be undone.");

        var result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        GameProfileHelper.deleteProfile(selectedProfile);
        selectedProfile = null;
        populateProfiles();
    }

    @FXML
    private void handleRenameClicked() {
        String newName = newProfileNameField.getText();
        if (selectedProfile == null || newName == null || newName.trim().isEmpty()) {
            showWarn("Invalid name", "Please select a profile and type a new name.");
            return;
        }
        newName = newName.trim();
        try {
            boolean ok = GameProfileHelper.renameProfile(selectedProfile, newName);
            if (!ok) {
                showWarn("Name exists", "A profile with that name already exists.");
                return;
            }
            selectedProfile = newName;
            populateProfiles();
            newProfileNameField.clear();
        } catch (Exception e) {
            showWarn("Rename failed", e.getMessage());
        }
    }

    @FXML
    private void handleBackClicked() {
        Screen s = getScreen();
        if (s instanceof ProfileSelectMenu menu) {
            menu.onBackClicked();
        }
    }

    private void showWarn(String header, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}