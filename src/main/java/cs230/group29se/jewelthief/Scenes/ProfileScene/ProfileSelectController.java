package cs230.group29se.jewelthief.Scenes.ProfileScene;

import cs230.group29se.jewelthief.Game.GameManager;
import cs230.group29se.jewelthief.Game.GameProfileHelper;
import cs230.group29se.jewelthief.Persistence.Storage.PersistenceManager;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

import static java.awt.SystemColor.menu;

/**
 * Controller for the Profile Select screen.
 * Handles user interactions such as creating, selecting, renaming, and deleting profiles.
 *
 * @author Iyaad
 */
public class ProfileSelectController extends BaseController {

    @FXML
    private Button renameButton;

    @FXML
    private VBox slotsContainer; // Container for displaying profile slots

    @FXML
    private TextField newProfileNameField; // Text field for entering a new profile name

    @FXML
    private Button createButton;

    @FXML
    private Button selectButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    private String selectedProfile;

    /**
     * Returns the canvas associated with this controller.
     * The profile menu does not use a canvas, so this method returns null.
     *
     * @return null as the profile menu has no canvas
     */
    @Override
    public Canvas getCanvas() {
        return null;
    }

    /**
     * Populates the profile slots in the UI.
     * Clears the current slots and loads up to 10 profiles from the GameProfileHelper.
     * Disables the "Create" button if the maximum number of profiles is reached.
     */
    public void populateProfiles() {
        slotsContainer.getChildren().clear();

        List<String> profiles = GameProfileHelper.listProfiles();

        if (profiles.size() > 10) {
            profiles = profiles.subList(0, 10);
        }

        for (int i = 0; i < profiles.size(); i++) {
            String name = profiles.get(i);
            int slotNumber = i + 1;
            HBox slot = createSlotRow(slotNumber, name);
            slotsContainer.getChildren().add(slot);
        }

        if (selectedProfile != null && !profiles.contains(selectedProfile)) {
            selectedProfile = null;
        }

        createButton.setDisable(profiles.size() >= 10);
    }

    /**
     * Creates a row for a profile slot.
     *
     * @param slotNumber  The slot number to display.
     * @param profileName The name of the profile.
     * @return An HBox representing the profile slot.
     */
    private HBox createSlotRow(int slotNumber, String profileName) {
        HBox row = new HBox(10);
        row.setStyle("-fx-background-color: #E8E9EB; -fx-background-radius: 8; -fx-padding: 10;");
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

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

    /**
     * Highlights the currently selected profile slot.
     * Updates the styles of all slots to indicate the selected one.
     */
    private void highlightSelection() {
        for (javafx.scene.Node node : slotsContainer.getChildren()) {
            if (!(node instanceof HBox row)) continue;
            Object data = row.getUserData();
            String name = (data instanceof String) ? (String) data : null;
            if (name != null && name.equals(selectedProfile)) {
                row.setStyle(
                        "-fx-background-color: #e0e0e0;" +
                                "-fx-background-radius: 8;" +
                                "-fx-padding: 10;" +
                                "-fx-border-color: #4a90e2;" +
                                "-fx-border-radius: 8;" +
                                "-fx-border-width: 2;"
                );
            } else {
                row.setStyle(
                        "-fx-background-color: #E8E9EB;" +
                                "-fx-background-radius: 8;" +
                                "-fx-padding: 10;"
                );
            }
        }
    }

    /**
     * Handles the "Create" button click event.
     * Creates a new profile if the maximum number of profiles has not been reached.
     * Displays an alert if the profile name is invalid or already exists.
     */
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

        try {
            GameProfileHelper.ensureProfileExists(name);
        } catch (IllegalArgumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Duplicate Profile");
            alert.setHeaderText(null);
            alert.setContentText("Profile name '" + name + "' already exists. Duplicates are not allowed.");
            alert.showAndWait();
            return;
        }

        populateProfiles();
        selectedProfile = name;
        highlightSelection();
        newProfileNameField.clear();
    }

    /**
     * Handles the "Select" button click event.
     * Marks the selected profile as chosen and transitions to the next screen.
     */
    @FXML
    private void handleSelectClicked() {
        if (selectedProfile == null) return;

        Screen s = getScreen();
        if (s instanceof ProfileSelectScreen menu) {
            menu.onProfileChosen(selectedProfile);
        }
    }

    /**
     * Handles the "Delete" button click event.
     * Deletes the selected profile after user confirmation.
     * Prevents deletion of the "PublicProfile".
     */
    @FXML
    private void handleDeleteClicked() {
        if (selectedProfile == null) return;
        if (selectedProfile.equals("PublicProfile")) {
            showWarn("Cannot delete", "The PublicProfile cannot be deleted.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Save");
        alert.setHeaderText("Delete \"" + selectedProfile + "\"?");
        alert.setContentText("This will delete the profile and its saves. This cannot be undone.");

        var result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        GameProfileHelper.deleteProfile(selectedProfile);

        if (GameManager.getSelectedProfileName().equals(selectedProfile)) {
            ((ProfileSelectScreen) getScreen()).onProfileChosen("PublicProfile");
        }
        selectedProfile = null;
        populateProfiles();
    }

    /**
     * Handles the "Rename" button click event.
     * Renames the selected profile to the new name entered in the text field.
     * Displays warnings if the new name is invalid or already exists.
     */
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

    /**
     * Handles the "Back" button click event.
     * Navigates back to the previous screen.
     */
    @FXML
    private void handleBackClicked() {
        Screen s = getScreen();
        if (s instanceof ProfileSelectScreen menu) {
            menu.onBackClicked();
        }
    }

    /**
     * Displays a warning alert with the specified header and message.
     *
     * @param header The header text of the alert.
     * @param msg    The message text of the alert.
     */
    private void showWarn(String header, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
