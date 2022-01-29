package Controller;

import Connection.DBControl;
import Models.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListDoctorController implements Initializable {

    @FXML
    private TableView<Doctor> table_doctors;
    @FXML
    private TableColumn<Doctor, String> col_firstname;
    @FXML
    private TableColumn<Doctor, String> col_lastname;
    @FXML
    private TableColumn<Doctor, String> col_address;
    @FXML
    private TableColumn<Doctor, String> col_clinicname;
    @FXML
    private TableColumn<Doctor, String> col_button;

    @FXML
    private Button button_back;

    @FXML
    private ImageView image_background;


    ObservableList<Doctor> doctorSearchModelObserverableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Make image displayable
        File backgroundFile = new File("stuff/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        image_background.setImage(backgroundImage);

        String doctorViewQuery = "SELECT firstName, lastName, address, clinicName FROM doctor";


        try{
            Statement statement = DBControl.dbConnection.createStatement();
            ResultSet queryOutput = statement.executeQuery(doctorViewQuery);

            while(queryOutput.next()){
                String queryFirstName = queryOutput.getString("firstName");
                String queryLastName = queryOutput.getString("lastName");
                String queryAddress = queryOutput.getString("address");
                String queryClinicName = queryOutput.getString("clinicName");

                doctorSearchModelObserverableList.add(new Doctor(queryFirstName, queryLastName, queryAddress, queryClinicName));

                col_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
                col_clinicname.setCellValueFactory(new PropertyValueFactory<>("clinicName"));

                table_doctors.setItems(doctorSearchModelObserverableList);
            }

        } catch (Exception e) {
            Logger.getLogger(ListDoctorController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
    }

    public void cancelButtonOnAction(ActionEvent event) throws Exception  {
        //        After click, return to the after login stage
        Parent root = FXMLLoader.load(getClass().getResource("book_appointment.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
