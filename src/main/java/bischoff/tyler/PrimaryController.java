package bischoff.tyler;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.*;

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
    private void add() {
        System.out.println("Add Button Pushed");
        //add stuff to sql table
        RefreshTableData();
    }

    @FXML
    private void modify() {
        System.out.println("Modify Button Pushed");
        Ticket ticketSelected = ticketTable.getSelectionModel().getSelectedItem();
        if (ticketSelected != null) {
            ticketSelected.setTechAssigned("bandy"); //do modification stuff
            RefreshListData(ticketSelected);
        }
    }

    @FXML
    private void delete() {
        System.out.println("Delete Button Pushed");
    }

    @FXML
    private void tableClicked(){
        Ticket ticketSelected = ticketTable.getSelectionModel().getSelectedItem();
        if (ticketSelected != null) {
            RefreshListData(ticketSelected);
        }
    }

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, String> ticketRequestsName;

    @FXML
    private TableColumn<Ticket, Boolean> ticketRequestsCompleted;

    @FXML
    private ListView<String> detailsList;

    Ticket placeholderTicket = new Ticket("Tyler", false);

    public void initialize() {
        //Table Setup
        ticketRequestsName.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name"));
        ticketRequestsName.setSortable(false);
        ticketRequestsCompleted.setSortable(false);
        ticketRequestsCompleted.setCellValueFactory(new PropertyValueFactory<Ticket, Boolean>("complete"));

        RefreshTableData();
    }

    




    private void RefreshTableData() {
        List<Ticket> data = new ArrayList<>();
        data.add(placeholderTicket);
        data.add(new Ticket("Bandy", false));
    
    



        //Sort the data by complete
        data.sort(Comparator.comparing(Ticket::getComplete));

        ticketTable.setItems(FXCollections.observableArrayList(data));
    }

    private void RefreshListData(Ticket ticketSelected) {
        ObservableList<String> details = FXCollections.observableArrayList();
        
        if (ticketSelected != null) {
            details.addAll(ticketSelected.getStringList());
        }
        else {
            details.add("Nothing Selected");
        }

        detailsList.setItems(details);
    }
}
