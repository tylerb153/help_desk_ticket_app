package bischoff.tyler;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("primary");
        System.out.println("Refreshed");
    }
    
    @FXML
    private void filter() {
        System.out.println("Filter Button Pushed");
    }

    @FXML
    private void addModify() {
        System.out.println("Add/Modify Button Pushed");
    }

    @FXML
    private void delete() {
        System.out.println("Delete Button Pushed");
    }
}
