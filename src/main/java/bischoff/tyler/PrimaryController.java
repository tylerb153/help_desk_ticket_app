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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;
import java.sql.*;

public class PrimaryController {  
    @FXML
    private void filter() {
        System.out.println("Filter Button Pushed");
    }

    @FXML
    private void add() throws IOException {
        System.out.println("Add Button Pushed");

        Node rootNode = HBox;
        rootNode.setDisable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
        VBox root = loader.load();
        Scene scene = new Scene(root);
        Stage popupStage = new Stage();
        popupStage.setScene(scene);
        popupStage.showAndWait();

        rootNode.setDisable(false);
        //add stuff to sql table
        RefreshTableData();
    }

    @FXML
    private void modify() throws IOException {
        System.out.println("Modify Button Pushed");
        
        Ticket ticketSelected = ticketTable.getSelectionModel().getSelectedItem();
        if (ticketSelected != null) {
            Node rootNode = HBox;
            rootNode.setDisable(true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
            VBox root = loader.load();
            PopupController controller = loader.getController();
            controller.initialize(ticketSelected);
            Scene scene = new Scene(root);
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.showAndWait();

            rootNode.setDisable(false);
        }
        RefreshTableData();
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
    private HBox HBox;

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, String> ticketRequestsName;

    @FXML
    private TableColumn<Ticket, Boolean> ticketRequestsCompleted;

    @FXML
    private ListView<String> detailsList;

    private List<Ticket> listOfTickets;

    public void initialize() {     
        //Table Setup
        ticketRequestsName.setCellValueFactory(new PropertyValueFactory<Ticket, String>("title"));
        ticketRequestsName.setSortable(false);
        ticketRequestsCompleted.setSortable(false);
        ticketRequestsCompleted.setCellValueFactory(new PropertyValueFactory<Ticket, Boolean>("complete"));

        RefreshTableData();
    }

    




    private void RefreshTableData() {
        List<Ticket> data = new ArrayList<>();
        //Connect to database
        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "Tyler", "");
            Statement stmt = conn.createStatement();
        ) {
            String sqlSelect = "select * from Tickets";
            ResultSet rset = stmt.executeQuery(sqlSelect);
            while (rset.next()) {
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    



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
