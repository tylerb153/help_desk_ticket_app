module bischoff.tyler {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens bischoff.tyler to javafx.fxml;
    exports bischoff.tyler;
}
