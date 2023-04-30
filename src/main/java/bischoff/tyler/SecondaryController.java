package bischoff.tyler;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SecondaryController {

    //Variables
    
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private void switchToPrimary() throws IOException {
        App.user = usernameTextField.getText(); //Sets the SQL Username in App.java
        App.password = passwordTextField.getText(); //Sets the SQL Password in App.java

        App.setRoot("primary"); //switches to the "Main" page
    }
}