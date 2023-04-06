module bischoff.tyler {
    requires javafx.controls;
    requires javafx.fxml;

    opens bischoff.tyler to javafx.fxml;
    exports bischoff.tyler;
}
