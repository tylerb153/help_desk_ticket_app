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
        Ticket ticketSelected = ticketTable.getSelectionModel().getSelectedItem();
        Statement stmt = App.startSQLStatement();
        if (ticketSelected != null && stmt != null) {
            System.out.println(ticketSelected.toString());
            String sqlDelete = "Delete from Tickets Where id = " + ticketSelected.getId();
            try {
                stmt.execute(sqlDelete);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        RefreshTableData();

    }

    @FXML
    private void tableClicked(MouseEvent event){
        Ticket ticketSelected = ticketTable.getSelectionModel().getSelectedItem();
        if (ticketSelected != null && event.getTarget().toString().compareTo("TableColumn$1$1[id=ticketRequestsName, styleClass=cell indexed-cell table-cell table-column]'null'") != 0) {
            RefreshListData(ticketSelected);
        }
        else {
            ticketTable.getSelectionModel().clearSelection();
            RefreshTableData();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        listOfTickets = data;
        //Sort the data by complete
        listOfTickets.sort(Comparator.comparing(Ticket::getComplete));
        //list the tickets in the table
        ticketTable.setItems(FXCollections.observableArrayList(listOfTickets));
        RefreshListData(null);
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
