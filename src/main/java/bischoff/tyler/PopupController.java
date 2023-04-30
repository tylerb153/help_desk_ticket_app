package bischoff.tyler;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class PopupController {
    
    public void initialize() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        dateRequestedTextField.setText(formattedDate);

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
                "VALUES ('%s', '%s', %b, '%s', '%s', '%s', '%s')", 
                titleTextField.getText().replace("'", "''"),
                techAssignedTextField.getText().replace("'", "''"),
                isComplete,
                dateRequestedTextField.getText().replace("'", "''"),
                descriptionTextField.getText().replace("'", "''"), 
                dateCompleteTextField.getText().replace("'", "''"), 
                notesTextField.getText().replace("'", "''")));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void modify(Statement stmt, String dateComplete, Boolean isComplete, Ticket ticketToModify) {
        try {
            stmt.execute(String.format("UPDATE Tickets SET title='%s', techAssigned='%s', complete=%b, dateRequested='%s', description='%s', dateComplete='%s', notes='%s' WHERE id=%d",
                titleTextField.getText().replace("'", "''"), 
                techAssignedTextField.getText().replace("'", "''"), 
                isComplete, 
                dateRequestedTextField.getText().replace("'", "''"), 
                descriptionTextField.getText().replace("'", "''"), 
                dateCompleteTextField.getText().replace("'", "''"), 
                notesTextField.getText().replace("'", "''"), 
                ticketToModify.getId()));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
