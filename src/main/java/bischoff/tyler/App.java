package bischoff.tyler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static String user; //SQL username
    public static String password; //SQL password

    @Override
    public void start(Stage stage) throws IOException { //Starts the application
        scene = new Scene(loadFXML("secondary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static Statement startSQLStatement() { //Returns an SQL Statement for the database ticketdb
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", user, password);
            Statement stmt = conn.createStatement();
            return stmt;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in startSQLStatement");
        }
        return null;
    
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}