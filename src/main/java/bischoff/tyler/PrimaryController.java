package bischoff.tyler;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.sql.*;

public class PrimaryController {  //Main App Page
    @FXML
    private void add() throws IOException { //Add Button Pushed, calls initialize() in PopupController.java
        System.out.println("Add Button Pushed");

        Node rootNode = HBox;
        rootNode.setDisable(true); //Disables interaction on the "primary" page

        //Set up and display a popup with the popup.fxml style
        FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
        VBox root = loader.load();
        Scene scene = new Scene(root);
        Stage popupStage = new Stage();
        popupStage.setScene(scene);
        popupStage.showAndWait();

        rootNode.setDisable(false); //Enables interaction on the "primary" page

        RefreshTableData(); //Refreshes the data in the TableView
    }

    @FXML
    private void modify() throws IOException { //Modify Button Pushed, calls initialize(ticketToModify) in PopupController.java
        System.out.println("Modify Button Pushed");
        
        //Get the current ticket that is selected in the TableView
        Ticket ticketSelected = ticketTable.getSelectionModel().getSelectedItem();
        if (ticketSelected != null) { //If a ticket is selected create a popup
            Node rootNode = HBox;
            rootNode.setDisable(true); //Disables interaction on the "primary" page

            FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
            VBox root = loader.load();
            PopupController controller = loader.getController(); //Gets the controller
            controller.initialize(ticketSelected); //calls initiallize(ticketSelected) instead of initialize()
            Scene scene = new Scene(root);
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.showAndWait();

            rootNode.setDisable(false); //Enables interaction on the "primary" page
        }
        RefreshTableData(); //Refreshes the data in the TableView
    }

    @FXML
    private void delete() { //Delete Button Pushed, deletes the selected ticket
        System.out.println("Delete Button Pushed");

        //Get current ticket selected in the table
        Ticket ticketSelected = ticketTable.getSelectionModel().getSelectedItem();
        Statement stmt = App.startSQLStatement(); //Log into SQL Database
        if (ticketSelected != null && stmt != null) { //If a ticket is selected and there's an SQL Statement then delete the ticket
            System.out.println(ticketSelected.toString());
            String sqlDelete = "Delete from Tickets Where id = " + ticketSelected.getId(); //SQL DELETE using the id of the selected ticket
            try {
                stmt.execute(sqlDelete);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in delete()");
            }
        }
        RefreshTableData();

    }

    @FXML
    private void tableClicked(MouseEvent event){ //Table Clicked, handles selecting/deselecting the table
        //Gets the ticket that was selected
        Ticket ticketSelected = ticketTable.getSelectionModel().getSelectedItem();
        //If a ticket is selected pass it to the ListView
        if (ticketSelected != null && event.getTarget().toString().compareTo("TableColumn$1$1[id=ticketRequestsName, styleClass=cell indexed-cell table-cell table-column]'null'") != 0) {
            RefreshListData(ticketSelected);
        }
        else {
            //Deselects item if no ticket is selected
            ticketTable.getSelectionModel().clearSelection();
            RefreshTableData();
        }
        
    }

    //Variables
    @FXML
    private HBox HBox;
    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, String> ticketRequestsName;
    @FXML
    private TableColumn<Ticket, Boolean> ticketRequestsCompleted;
    @FXML
    private ListView<String> detailsList;
    @FXML
    private ScrollPane scrollPane;

    private List<Ticket> listOfTickets;

    public void initialize() {     
        //Table Setup
        ticketRequestsName.setCellValueFactory(new PropertyValueFactory<Ticket, String>("title"));
        ticketRequestsName.setSortable(false);
        ticketRequestsCompleted.setSortable(false);
        ticketRequestsCompleted.setCellValueFactory(new PropertyValueFactory<Ticket, Boolean>("complete"));

        //Setup Text Wrap for ListView
        detailsList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            Text text = new Text();
            cell.setPrefWidth(detailsList.getWidth() - 2);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            cell.setGraphic(text);
            return cell ;
        });

        //Setup scrolling in ScrollPane
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        RefreshTableData();
    }

    




    private void RefreshTableData() {
        List<Ticket> data = new ArrayList<>();
        //Connect to database
        try (
            
            Statement stmt = App.startSQLStatement();
        ) {
            if (stmt == null) {
                throw new SQLException();
            }

            //Select everything from the table
            String sqlSelect = "select * from Tickets";
            ResultSet rset = stmt.executeQuery(sqlSelect);
            //Add the tickets to the list
            while (rset.next()) {
                data.add(new Ticket(rset.getInt("id"), rset.getString("title"), rset.getBoolean("complete"), rset.getString("dateRequested"), rset.getString("description"), rset.getString("techAssigned"), rset.getString("dateComplete"), rset.getString("notes")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in RefreshTableData()");
        }

        listOfTickets = data;
        //Sort the data by complete
        listOfTickets.sort(Comparator.comparing(Ticket::getComplete));
        //list the tickets in the table
        ticketTable.setItems(FXCollections.observableArrayList(listOfTickets));
        //Refreshes the list with no selected ticket
        RefreshListData(null);
    }

    private void RefreshListData(Ticket ticketSelected) { //Edits values in the ListView/Details Window
        ObservableList<String> details = FXCollections.observableArrayList();
        
        if (ticketSelected != null) { //If theres a ticket selected call the ticket's getStringList() or display nothing is selected
            details.addAll(ticketSelected.getStringList());
        }
        else {
            details.add("Nothing Selected");
        }

        detailsList.setItems(details); //Sets the display
    }


}
