package bischoff.tyler;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.user = "Tyler";
        App.password = "";

        App.setRoot("Primary");
    }
}