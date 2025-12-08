module cs230.group29se.jewelthief {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires com.fasterxml.jackson.databind;
    requires jdk.unsupported.desktop;
    requires javafx.graphics;

    opens cs230.group29se.jewelthief to javafx.fxml;
    exports cs230.group29se.jewelthief;
    exports cs230.group29se.jewelthief.Scenes;
    opens cs230.group29se.jewelthief.Scenes to javafx.fxml;
    exports cs230.group29se.jewelthief.Scenes.MainScene;
    opens cs230.group29se.jewelthief.Scenes.MainScene to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.GameScene to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.LevelSelectScene to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.LevelFailedScene to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.LevelFinished to javafx.fxml;
    exports cs230.group29se.jewelthief.Scenes.LevelSelectScene;
    opens cs230.group29se.jewelthief.Scenes.ProfileScene to javafx.fxml;
    exports cs230.group29se.jewelthief.Scenes.HighScoresScene to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.HighScoresScene to javafx.fxml;
    exports cs230.group29se.jewelthief.Scenes.ProfileScene;
    exports cs230.group29se.jewelthief.Cosmetics;
    opens cs230.group29se.jewelthief.Cosmetics to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.EquipablesScene to javafx.fxml;


    opens cs230.group29se.jewelthief.Persistence.Profile to com.fasterxml.jackson.databind, javafx.base;
    opens cs230.group29se.jewelthief.Persistence.Storage to com.fasterxml.jackson.databind;
    exports cs230.group29se.jewelthief.Scenes.GameScene;
    exports cs230.group29se.jewelthief.Scenes.LevelFailedScene;
    exports cs230.group29se.jewelthief.Scenes.LevelFinished;
    exports cs230.group29se.jewelthief.Items;
    opens cs230.group29se.jewelthief.Items to javafx.fxml;
    exports cs230.group29se.jewelthief.Entities;
    opens cs230.group29se.jewelthief.Entities to javafx.fxml;
    exports cs230.group29se.jewelthief.Game;
    opens cs230.group29se.jewelthief.Game to javafx.fxml;


}