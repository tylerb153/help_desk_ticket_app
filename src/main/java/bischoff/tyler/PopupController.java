package bischoff.tyler;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;


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
        modifying = true;
        this.ticketToModify = ticketToModify;
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

    private Ticket ticketToModify;

    private Boolean modifying = false;

    @FXML
    private void save() {
        System.out.println("Data Added");
        Statement stmt = App.startSQLStatement();
        String dateComplete = dateCompleteTextField.getText();
        Boolean isComplete = false;
        if (dateComplete.compareTo("") != 0) {
            isComplete = true;
        }

        if (modifying) {
            modify(stmt, dateComplete, isComplete, ticketToModify);
        }
        else {
            add(stmt, dateComplete, isComplete);
        }

        cancel();
    }
    @FXML 
    private void cancel() {
        System.out.println("Window Canceled");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void add(Statement stmt, String dateComplete, Boolean isComplete) {
        try {
            stmt.execute(String.format("INSERT INTO Tickets (title, techAssigned, complete, dateRequested, description, dateComplete, notes) " +
            "VALUES ('%s', '%s', %b, '%s', '%s', '%s', '%s');", 
            titleTextField.getText(), techAssignedTextField.getText(), isComplete, dateRequestedTextField.getText(), descriptionTextField.getText(), dateComplete, notesTextField.getText()));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void modify(Statement stmt, String dateComplete, Boolean isComplete, Ticket ticketToModify) {
        try {
            stmt.execute(String.format("UPDATE Tickets SET title = '%s', techAssigned = '%s', complete = %b, dateRequested = '%s', description = '%s', dateComplete = '%s', notes = '%s' WHERE id = %d;",
            titleTextField.getText(), techAssignedTextField.getText(), isComplete, dateRequestedTextField.getText(), descriptionTextField.getText(), dateCompleteTextField.getText(), notesTextField.getText(), ticketToModify.getId()));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
