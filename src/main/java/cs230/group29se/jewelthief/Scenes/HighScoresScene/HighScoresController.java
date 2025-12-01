package cs230.group29se.jewelthief.Scenes.HighScoresScene;

import cs230.group29se.jewelthief.Game.GameHighScoresHelper;
import cs230.group29se.jewelthief.Persistence.Profile.HighScoreEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;

public class HighScoresController {

    @FXML
    private TableView<HighScoreEntry> globalTable;

    @FXML
    private TableView<HighScoreEntry> levelTable;

    @FXML
    private Button backButton;
    @FXML
    private ComboBox<String> levelComboBox;
    private Map<String, List<HighScoreEntry>> perLevelCache = Map.of();

    private HighScoresScreen screen;

    public void setScreen(HighScoresScreen screen) {
        this.screen = screen;
    }

    public void loadData() {
        setupColumns();

        // Global
        List<HighScoreEntry> global = GameHighScoresHelper.loadGlobalHighScores();
        globalTable.getItems().setAll(global);

        // Per-level
        perLevelCache = GameHighScoresHelper.loadPerLevelHighScores();

        // Fill ComboBox with sorted level IDs (e.g. "1", "2", "3")
        var levelIds = new java.util.ArrayList<>(perLevelCache.keySet());
        java.util.Collections.sort(levelIds, java.util.Comparator.comparingInt(Integer::parseInt));

        levelComboBox.getItems().setAll(levelIds);

        // Default selection: level "1" if present, else first available
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

            TableColumn<HighScoreEntry, String> timeCol = new TableColumn<>("Time Time Achieved");
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
        if (screen != null) {
            screen.onBackClicked();
        }
    }
}