package cs230.group29se.jewelthief.Scenes.ProfileScene;

import cs230.group29se.jewelthief.Game.GameManager;
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

/**
 * Controller for the Profile Select screen.
 * Handles user interactions such as creating, selecting, renaming, and deleting profiles.
 *
 * @author Iyaad
 */
public class ProfileSelectController extends BaseController {

    public static final String PADDING = "10";
    public static final int MAX_PROFILES = 10;
    public static final String SAVE_LIMIT_WARNING = "You can only have up to " + MAX_PROFILES + " save slots.";
    public static final int SPACING = 10;
    public static final int MIN_PROFILES = 0;
    public static final int NEXT_SLOT = 1;
    public static final String RADIUS = "8";
    public static final String HIGHLIGHTED = "-fx-background-color: #e0e0e0;" +
            "-fx-background-radius: " + RADIUS + ";" +
            "-fx-padding: " + PADDING + ";" +
            "-fx-border-color: #4a90e2;" +
            "-fx-border-radius: " + RADIUS + ";" +
            "-fx-border-width: 2;";
    public static final String BACKGROUND_COLOUR_PROFILE_SLOTS = "#E" + RADIUS + "E9EB";
    public static final String UNHIGHLIGHTED = "-fx-background-color: " + BACKGROUND_COLOUR_PROFILE_SLOTS + ";" +
            "-fx-background-radius: " + RADIUS + ";" +
            "-fx-padding: " + PADDING + ";";
    public static final String BACKGROUND_COLOUR_PROFILE_NAME = "#222222";
    public static final String LARGE_FONT = "16";
    public static final String PROFILE_SLOT = "Profile Slot";
    public static final String RADIUS8 = "88888";
    public static final String SMALL_FONT = "12";
    public static final String TEXT_COLOUR = "#777777";
    public static final String MEDIUM_FONT = "14";
    public static final int NUMLABEL_MIN_WIDTH = 30;
    public static final String MAX_SAVES_REACHED = "Max Saves Reached";
    public static final String DUPLICATE_PROFILE_MESSAGE = "Duplicate Profile";
    public static final String DUPLICATE_PROFILE_WARNING = "Profile name '%s' already exists. Duplicates are not allowed.";
    public static final String PUBLIC_PROFILE_MESSAGE = "PublicProfile";
    public static final String CANNOT_DELETE_HEADER = "Cannot delete";
    public static final String CANNOT_DELETE_MESSAGE = "The PublicProfile cannot be deleted.";
    public static final String DELETE_SAVE = "Delete Save";
    public static final String DELETE_SAVE_WARNING = "This will delete the profile and its saves. This cannot be undone.";
    public static final String INVALID_NAME_HEADER = "Invalid name";
    public static final String INVALID_NAME_WARNING = "Please select a profile and type a new name.";
    public static final String NAME_EXISTS_HEADER = "Name exists";
    public static final String NAME_EXISTS_WARNING = "A profile with that name already exists.";
    public static final String RENAME_FAILED = "Rename failed";
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

        if (profiles.size() > MAX_PROFILES) {
            profiles = profiles.subList(MIN_PROFILES, MAX_PROFILES);
        }

        for (int i = 0; i < profiles.size(); i++) {
            String name = profiles.get(i);
            int slotNumber = i + NEXT_SLOT;
            HBox slot = createSlotRow(slotNumber, name);
            slotsContainer.getChildren().add(slot);
        }

        if (selectedProfile != null && !profiles.contains(selectedProfile)) {
            selectedProfile = null;
        }

        createButton.setDisable(profiles.size() >= MAX_PROFILES);
    }

    /**
     * Creates a row for a profile slot.
     *
     * @param slotNumber  The slot number to display.
     * @param profileName The name of the profile.
     * @return An HBox representing the profile slot.
     */
    private HBox createSlotRow(int slotNumber, String profileName) {
        HBox row = new HBox(SPACING);
        row.setStyle("-fx-background-color: " + BACKGROUND_COLOUR_PROFILE_SLOTS + "; -fx-background-radius: " + RADIUS + "; -fx-padding: " + PADDING + ";");
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        row.setUserData(profileName);

        Label nameLabel = new Label(profileName);
        nameLabel.setStyle("-fx-text-fill: " + BACKGROUND_COLOUR_PROFILE_NAME + "; -fx-font-size: " + LARGE_FONT + "px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        Label hintLabel = new Label(PROFILE_SLOT);
        hintLabel.setStyle("-fx-text-fill: #" + RADIUS + RADIUS8 + "; -fx-font-size: " + SMALL_FONT + "px;");

        Label numberLabel = new Label(String.valueOf(slotNumber));
        numberLabel.setStyle("-fx-text-fill: " + TEXT_COLOUR + "; -fx-font-size: " + MEDIUM_FONT + "px;");
        numberLabel.setMinWidth(NUMLABEL_MIN_WIDTH);

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
                        HIGHLIGHTED
                );
            } else {
                row.setStyle(
                        UNHIGHLIGHTED
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
        if (profiles.size() >= MAX_PROFILES) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(MAX_SAVES_REACHED);
            alert.setHeaderText(null);
            alert.setContentText(SAVE_LIMIT_WARNING);
            alert.showAndWait();
            return;
        }

        String name = newProfileNameField.getText().trim();
        if (name.isEmpty()) return;

        try {
            GameProfileHelper.ensureProfileExists(name);
        } catch (IllegalArgumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(DUPLICATE_PROFILE_MESSAGE);
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
        if (selectedProfile.equals(PUBLIC_PROFILE_MESSAGE)) {
            showWarn(CANNOT_DELETE_HEADER, CANNOT_DELETE_MESSAGE);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(DELETE_SAVE);
        alert.setHeaderText("Delete \"" + selectedProfile + "\"?");
        alert.setContentText(DELETE_SAVE_WARNING);

        var result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        GameProfileHelper.deleteProfile(selectedProfile);

        if (GameManager.getSelectedProfileName().equals(selectedProfile)) {
            ((ProfileSelectScreen) getScreen()).onProfileChosen(PUBLIC_PROFILE_MESSAGE);
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
            showWarn(INVALID_NAME_HEADER, INVALID_NAME_WARNING);
            return;
        }
        newName = newName.trim();
        try {
            boolean ok = GameProfileHelper.renameProfile(selectedProfile, newName);
            if (!ok) {
                showWarn(NAME_EXISTS_HEADER, NAME_EXISTS_WARNING);
                return;
            }
            selectedProfile = newName;
            populateProfiles();
            newProfileNameField.clear();
        } catch (Exception e) {
            showWarn(RENAME_FAILED, e.getMessage());
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
