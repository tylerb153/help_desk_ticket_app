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
    private TableColumn<Ticket, String> ticketRequests;

    public void initialize() {
        ticketRequests.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name"));

        
        

        ticketTable.setItems(FXCollections.observableArrayList(RefreshData()));
    }


    private List<Ticket> RefreshData() {
        List<Ticket> data = new ArrayList<>();
        data.add(new Ticket("cum"));
        data.add(new Ticket("in"));
        data.add(new Ticket("me"));
        return data;
    }
}
