package bischoff.tyler;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private void switchToPrimary() throws IOException {
        App.user = usernameTextField.getText();
        App.password = passwordTextField.getText();

        App.setRoot("primary");
    }
}