package Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBControl {


    static public Connection dbConnection;

    static public void connectToDatabase() {
        String databaseName = "ehealth";
        String databaseUser = "admin";
        String databasePassword = "vgustudent";
        String url = "jdbc:mysql://ehealth-db.cqajckw84dii.us-east-1.rds.amazonaws.com:3306/" + databaseName;

        try {
            loadJDBCDriver();
            dbConnection = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Database Connected!");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            System.out.println("Connection Failed!");
        }
        //return databaseConnection;
    }// end getConnection()

    static public void loadJDBCDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
    }

}

