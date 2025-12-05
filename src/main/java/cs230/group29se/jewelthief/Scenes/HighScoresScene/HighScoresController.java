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

public class HighScoresController extends BaseController {

    @FXML
    private TableView<HighScoreEntry> globalTable;

    @FXML
    private TableView<HighScoreEntry> levelTable;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> levelComboBox;

    // Cache of per-level scores from PersistenceManager
    private Map<String, List<HighScoreEntry>> perLevelCache = Map.of();

    @Override
    public Canvas getCanvas() {
        return null; // no canvas in this screen
    }

    // Called from HighScoresScreen.initialize()
    public void loadData() {
        System.out.println("[HighScoresController] loadData called");

        setupColumns();

        // Global scores
        List<HighScoreEntry> global = GameHighScoresHelper.loadGlobalHighScores();
        System.out.println("Global highscores size = " + global.size());
        for (HighScoreEntry e : global) {
            System.out.println("  GLOBAL " + e.getProfileName() + " " + e.getScore());
        }
        globalTable.getItems().setAll(global);

        // Per-level scores
        perLevelCache = GameHighScoresHelper.loadPerLevelHighScores();
        System.out.println("Per-level keys = " + perLevelCache.keySet());

        var levelIds = new java.util.ArrayList<>(perLevelCache.keySet());
        levelIds.sort(java.util.Comparator.comparingInt(Integer::parseInt));
        levelComboBox.getItems().setAll(levelIds);

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
    private void setupColumns() {
        if (globalTable.getColumns().isEmpty()) {
            TableColumn<HighScoreEntry, String> nameCol = new TableColumn<>("Profile");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("profileName"));

            TableColumn<HighScoreEntry, Integer> scoreCol = new TableColumn<>("Score");
            scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

            TableColumn<HighScoreEntry, String> timeCol = new TableColumn<>("Time Achieved");
            timeCol.setCellValueFactory(new PropertyValueFactory<>("formattedTime")); // or "timestamp"

            globalTable.getColumns().addAll(nameCol, scoreCol, timeCol);
        }

        if (levelTable.getColumns().isEmpty()) {
            TableColumn<HighScoreEntry, String> nameCol = new TableColumn<>("Profile");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("profileName"));

            TableColumn<HighScoreEntry, Integer> scoreCol = new TableColumn<>("Score");
            scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

            TableColumn<HighScoreEntry, String> timeCol = new TableColumn<>("Time Achieved");
            timeCol.setCellValueFactory(new PropertyValueFactory<>("formattedTime"));

            levelTable.getColumns().addAll(nameCol, scoreCol, timeCol);
        }
    }

    @FXML
    private void handleLevelChanged() {
        String selected = levelComboBox.getSelectionModel().getSelectedItem();
        if (selected != null) {
            updateLevelTable(selected);
        }
    }

    private void updateLevelTable(String levelId) {
        if (perLevelCache == null) return;
        List<HighScoreEntry> entries = perLevelCache.getOrDefault(levelId, List.of());
        levelTable.getItems().setAll(entries);
    }

    @FXML
    private void handleBackClicked() {
        Screen s = getScreen();
        if (s instanceof HighScoresScreen hs) {
            hs.onBackClicked();
        }
    }
}