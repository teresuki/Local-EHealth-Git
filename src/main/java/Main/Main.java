package Main;

import Connection.DBControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Controller/login.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Welcome!");
        primaryStage.show();

        //Connect To Database:
        DBControl.connectToDatabase();


    }

    public static void main(String[] args) {

        launch();
    }
}