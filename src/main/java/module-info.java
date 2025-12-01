module cs230.group29se.jewelthief {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens cs230.group29se.jewelthief to javafx.fxml;
    exports cs230.group29se.jewelthief;
    exports cs230.group29se.jewelthief.Scenes;
    opens cs230.group29se.jewelthief.Scenes to javafx.fxml;
    exports cs230.group29se.jewelthief.Scenes.MainScene;
    opens cs230.group29se.jewelthief.Scenes.MainScene to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.GameScene to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.LevelSelectScene to javafx.fxml;
    opens cs230.group29se.jewelthief.Scenes.LevelFailedScene to javafx.fxml;
    exports cs230.group29se.jewelthief.Scenes.LevelSelectScene;
    exports cs230.group29se.jewelthief.Scenes.GameScene;
    exports cs230.group29se.jewelthief.Scenes.LevelFailedScene;


}