module cs230.group29se.jewelthief {
    requires javafx.controls;
    requires javafx.fxml;


    opens cs230.group29se.jewelthief to javafx.fxml;
    exports cs230.group29se.jewelthief;
}