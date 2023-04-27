package bischoff.tyler;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class PopupController {
    
    public void initialize() {

    }

    public void initialize(Ticket ticketToModify) {
        titleTextField.setText(ticketToModify.getTitle());
        dateRequestedTextField.setText(ticketToModify.getDateRequested());
        techAssignedTextField.setText(ticketToModify.getTechAssigned());
        dateCompleteTextField.setText(ticketToModify.getDateComplete());
        descriptionTextField.setText(ticketToModify.getDescription());
        notesTextField.setText(ticketToModify.getNotes());
    }
    

    @FXML 
    Button cancelButton;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField dateRequestedTextField;
    @FXML
    private TextField techAssignedTextField;
    @FXML
    private TextField dateCompleteTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField notesTextField;


    @FXML
    private void save() {
        System.out.println("Data Added");
        //do whatever to the table blah blah blah
        cancel();
    }
    @FXML 
    private void cancel() {
        System.out.println("Window Canceled");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
