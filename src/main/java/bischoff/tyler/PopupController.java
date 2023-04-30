package bischoff.tyler;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class PopupController { //This is the scene that pops up when you click the Add or Modify Buttons
    
    public void initialize() { //initialize function called when Add button is pushed
        LocalDate currentDate = LocalDate.now(); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        //Assumes the date requested will be the current date and fills it in
        dateRequestedTextField.setText(formattedDate);

    }

    public void initialize(Ticket ticketToModify) { //initialize funciton called when Modify button is pushed, takes a ticket passed from the table
        //Sets all the fields to be filled in with information from the passed ticket
        titleTextField.setText(ticketToModify.getTitle());
        dateRequestedTextField.setText(ticketToModify.getDateRequested());
        techAssignedTextField.setText(ticketToModify.getTechAssigned());
        dateCompleteTextField.setText(ticketToModify.getDateComplete());
        descriptionTextField.setText(ticketToModify.getDescription());
        notesTextField.setText(ticketToModify.getNotes());
        modifying = true; //tells the save() function which SQL statment to write
        this.ticketToModify = ticketToModify;
    }
    
    //Variables
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

    //Called when the save button is pressed
    @FXML
    private void save() {
        System.out.println("Save Button Pressed");
        Statement stmt = App.startSQLStatement(); //Gets SQL Statement
        String dateComplete = dateCompleteTextField.getText(); //Gets the contents of the date complete field
        Boolean isComplete = false;
        if (dateComplete.compareTo("") != 0) { //If the completion date isn't blank then it must be complete
            isComplete = true;
        }

        if (modifying) { //finds out which initialize function was called
            modify(stmt, dateComplete, isComplete, ticketToModify); //Calls a SQL statement that modifies ticketToModify
        }
        else {
            add(stmt, dateComplete, isComplete); //Calls a SQL statement that adds a Ticket into the database
        }

        cancel(); //dismiss popup
    }
    @FXML 
    private void cancel() { //Happens when the cancel button is pressed
        System.out.println("Window Canceled");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close(); //Closes the stage/popup
    }

    private void add(Statement stmt, String dateComplete, Boolean isComplete) {
        try {
            //Executes an INSERT INTO SQL command using all the information from the fields
            stmt.execute(String.format("INSERT INTO Tickets (title, techAssigned, complete, dateRequested, description, dateComplete, notes) " + 
                "VALUES ('%s', '%s', %b, '%s', '%s', '%s', '%s')", 
                titleTextField.getText().replace("'", "''"), //replaces ' with '' so the command doesn't break
                techAssignedTextField.getText().replace("'", "''"),
                isComplete,
                dateRequestedTextField.getText().replace("'", "''"),
                descriptionTextField.getText().replace("'", "''"), 
                dateCompleteTextField.getText().replace("'", "''"), 
                notesTextField.getText().replace("'", "''")));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in add()");
        }
    }

    private void modify(Statement stmt, String dateComplete, Boolean isComplete, Ticket ticketToModify) {
        try {
            //Executes an UPDATE SET SQL command using the information from the fields. Finds the id from ticketToModify which was passed from the table into this stage
            stmt.execute(String.format("UPDATE Tickets SET title='%s', techAssigned='%s', complete=%b, dateRequested='%s', description='%s', dateComplete='%s', notes='%s' WHERE id=%d",
                titleTextField.getText().replace("'", "''"), //Replaces ' with '' because SQL uses ' and it would break if i didn't
                techAssignedTextField.getText().replace("'", "''"), 
                isComplete, 
                dateRequestedTextField.getText().replace("'", "''"), 
                descriptionTextField.getText().replace("'", "''"), 
                dateCompleteTextField.getText().replace("'", "''"), 
                notesTextField.getText().replace("'", "''"), 
                ticketToModify.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in modify()");
        }
    }
}
