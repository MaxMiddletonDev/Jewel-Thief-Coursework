package cs230.group29se.jewelthief.Scenes.HighScoresScene;

import cs230.group29se.jewelthief.Game.GameHighScoresHelper;
import cs230.group29se.jewelthief.Persistence.Profile.HighScoreEntry;
import cs230.group29se.jewelthief.Scenes.BaseController;
import cs230.group29se.jewelthief.Scenes.Screen;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;

/**
 * Controller for the High Scores screen.
 * <p>
 * This controller manages both the global high score table and the
 * per-level high score table. Data is fetched via {@link GameHighScoresHelper}
 * and displayed in JavaFX {@link TableView} components. The user can also
 * switch between level-specific scoreboards using the level selection
 * {@link ComboBox}, and return to the previous screen via the Back button.
 *
 * @author Iyaad
 */
public class HighScoresController extends BaseController {

    /** Table showing global high scores across all levels. */
    @FXML
    private TableView<HighScoreEntry> globalTable;

    /** Table showing high scores filtered by level. */
    @FXML
    private TableView<HighScoreEntry> levelTable;

    /** Button to return to the previous screen. */
    @FXML
    private Button backButton;

    /** Dropdown allowing the user to select a level to view scores for. */
    @FXML
    private ComboBox<String> levelComboBox;

    /**
     * Cached map of per-level high score lists.
     * <p>
     * Keys are level IDs as strings; values are lists of high score entries.
     */
    private Map<String, List<HighScoreEntry>> perLevelCache = Map.of();

    /**
     * This screen does not use a canvas, so {@code null} is returned.
     *
     * @return {@code null} always, as no drawing canvas is used on this screen.
     */
    @Override
    public Canvas getCanvas() {
        return null;
    }

    /**
     * Loads all global and per-level high score data into the UI.
     * <p>
     * This method is called from {@code HighScoresScreen.initialize()} and:
     * <ul>
     *     <li>Initializes table columns if needed</li>
     *     <li>Loads and displays global high scores</li>
     *     <li>Loads, caches, and displays per-level high scores</li>
     *     <li>Populates the level selection dropdown</li>
     * </ul>
     */
    public void loadData() {
        System.out.println("[HighScoresController] loadData called");

        setupColumns();

        // Load global highscores
        List<HighScoreEntry> global = GameHighScoresHelper.loadGlobalHighScores();
        System.out.println("Global highscores size = " + global.size());
        for (HighScoreEntry e : global) {
            System.out.println("  GLOBAL " + e.getProfileName() + " " + e.getScore());
        }
        globalTable.getItems().setAll(global);

        // Load per-level highscores
        perLevelCache = GameHighScoresHelper.loadPerLevelHighScores();
        System.out.println("Per-level keys = " + perLevelCache.keySet());

        var levelIds = new java.util.ArrayList<>(perLevelCache.keySet());
        levelIds.sort(java.util.Comparator.comparingInt(Integer::parseInt));
        levelComboBox.getItems().setAll(levelIds);

        // Select default level (prefer level 1)
        String defaultLevel = levelIds.contains("1")
                ? "1"
                : (levelIds.isEmpty() ? null : levelIds.get(0));

        if (defaultLevel != null) {
            levelComboBox.getSelectionModel().select(defaultLevel);
            updateLevelTable(defaultLevel);
        } else {
            levelTable.getItems().clear();
        }
    }

    /**
     * Ensures the table columns for both tables are created exactly once.
     * <p>
     * Columns include:
     * <ul>
     *     <li>Profile name</li>
     *     <li>Score value</li>
     * </ul>
     */
    private void setupColumns() {
        if (globalTable.getColumns().isEmpty()) {
            TableColumn<HighScoreEntry, String> nameCol = new TableColumn<>("Profile");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("profileName"));

            TableColumn<HighScoreEntry, Integer> scoreCol = new TableColumn<>("Score");
            scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

            globalTable.getColumns().addAll(nameCol, scoreCol);
        }

        if (levelTable.getColumns().isEmpty()) {
            TableColumn<HighScoreEntry, String> nameCol = new TableColumn<>("Profile");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("profileName"));

            TableColumn<HighScoreEntry, Integer> scoreCol = new TableColumn<>("Score");
            scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

            levelTable.getColumns().addAll(nameCol, scoreCol);
        }
    }

    /**
     * Event handler triggered when the user selects a different level
     * in the {@link ComboBox}. Updates the per-level table accordingly.
     */
    @FXML
    private void handleLevelChanged() {
        String selected = levelComboBox.getSelectionModel().getSelectedItem();
        if (selected != null) {
            updateLevelTable(selected);
        }
    }

    /**
     * Updates the per-level table to show high scores for the given level.
     *
     * @param levelId the ID of the level whose scores should be displayed
     */
    private void updateLevelTable(String levelId) {
        if (perLevelCache == null) return;
        List<HighScoreEntry> entries = perLevelCache.getOrDefault(levelId, List.of());
        levelTable.getItems().setAll(entries);
    }

    /**
     * Handles clicking the Back button.
     * <p>
     * Sets the current screen as finished, triggering a return to the previous screen.
     */
    @FXML
    private void handleBackClicked() {
        getScreen().setFinished(true);
    }
}
