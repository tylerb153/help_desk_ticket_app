package bischoff.tyler;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private void addModify() {
        System.out.println("Add/Modify Button Pushed");
    }

    @FXML
    private void delete() {
        System.out.println("Delete Button Pushed");
    }

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, String> ticketRequestsName;

    @FXML
    private TableColumn<Ticket, Boolean> ticketRequestsCompleted;


    public void initialize() {
        ticketRequestsName.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name"));
        ticketRequestsName.setSortable(false);
        ticketRequestsCompleted.setSortable(false);
        ticketRequestsCompleted.setCellValueFactory(new PropertyValueFactory<Ticket, Boolean>("complete"));

        RefreshTableData();
        
    }


    private void RefreshTableData() {
        List<Ticket> data = new ArrayList<>();
        data.add(new Ticket("Tyler", false));
        data.add(new Ticket("Loves", true));
        data.add(new Ticket("Bandy", true));
    

        //Sort the data by complete
        data.sort(Comparator.comparing(Ticket::getComplete));

        ticketTable.setItems(FXCollections.observableArrayList(data));
    }
}
