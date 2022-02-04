package Controller;

import Connection.DBControl;
import Models.Doctor;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminEditController implements Initializable {
    @FXML
    ImageView image_background;

    @FXML
    TableView<User> userTableView;
    @FXML
    TableColumn<User, String> usernameCol;
    @FXML
    TableColumn<User, String> emailCol;
    @FXML
    TableColumn<User, String> firstNameCol;
    @FXML
    TableColumn<User, String> lastNameCol;
    @FXML
    TableColumn<User, String> addressCol;
    @FXML
    TableColumn<User, String> insuranceIDCol;
    @FXML
    TableColumn<User, String> insuranceTypeCol;
    @FXML
    TableColumn<User, String> genderCol;
    @FXML
    TableColumn<User, Date> dateOfBirthCol;

    @FXML
    TableView<Doctor> doctorTableView;
    @FXML
    TableColumn<Doctor, String> doctorFirstNameCol;
    @FXML
    TableColumn<Doctor, String> doctorLastNameCol;
    @FXML
    TableColumn<Doctor, String> doctorAddressCol;
    @FXML
    TableColumn<Doctor, String> clinicNameCol;
    @FXML
    TableColumn<Doctor, String> clinicLongitudeCol;
    @FXML
    TableColumn<Doctor, String> clinicLatitudeCol;

    @FXML
    ScrollPane userScrollPane;

    final java.sql.Date DEFAULT_DATE = java.sql.Date.valueOf("1900-01-31");

    ObservableList<User> userObserverableList = FXCollections.observableArrayList();
    ObservableList<Doctor> doctorObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backgroundFile = new File("stuff/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        image_background.setImage(backgroundImage);

        try {
            loadDataFromDB();
            userTableView.setItems(userObserverableList);
        } catch (SQLException e) {
            System.out.println(e);
        }

        userScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    void loadDataFromDB() throws SQLException {
        loadUserFromDB();
        loadDoctorFromDB();
    }

    //Load User information to User table
    void loadUserFromDB() throws SQLException {
        Statement stm = DBControl.dbConnection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT username, email, firstName, lastName, address, insuranceID, insuranceType, gender, dateOfBirth\n" +
                "FROM user");
        while (rs.next()) {
            String _username = rs.getString(1);
            String _email = rs.getString(2);
            String _firstName = "";
            String _lastName = "";
            String _address = "";
            String _insuranceID = "";
            String _insuranceType = "";
            String _gender = "";
            java.sql.Date _dateOfBirth = null;

            try {
                _firstName = rs.getString(3);
                _lastName = rs.getString(4);
                _address = rs.getString(5);
                _insuranceID = rs.getString(6);
                _insuranceType = rs.getString(7);
                _gender = rs.getString(8);
                _dateOfBirth = rs.getDate(9);
            } catch (Exception e) {
                System.out.println(e);
            }

//            if(_dateOfBirth == null)
//                _dateOfBirth = DEFAULT_DATE;

            //Populate the observable list
            userObserverableList.add(
                    new User(_username, _email, _firstName, _lastName, _address,
                            _insuranceID, _insuranceType, _gender, _dateOfBirth));

        }

        //aColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));

        emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
        insuranceIDCol.setCellValueFactory(new PropertyValueFactory<User, String>("insuranceID"));
        insuranceTypeCol.setCellValueFactory(new PropertyValueFactory<User, String>("insuranceType"));
        genderCol.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<User, java.sql.Date>("dateOfBirth"));

        userTableView.setItems(userObserverableList);

    }

    void loadDoctorFromDB() throws SQLException
    {
        Statement stm = DBControl.dbConnection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT firstName, lastName, address, clinicName, doctorClinicLongitude, doctorClinicLatitude\n" +
                "FROM doctor");
        while(rs.next())
        {
            String _firstname = rs.getString(1);
            String _lastName = rs.getString(2);
            String _address = rs.getString(3);
            String _clinicName = rs.getString(4);
            double _clinicLongitude = rs.getDouble(5);
            double _clinicLatitude = rs.getDouble(6);

            doctorObservableList.add(
                    new Doctor(_firstname, _lastName, _address, _clinicName,
                    _clinicLongitude, _clinicLatitude)
            );
        }

        doctorFirstNameCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("firstName"));
        doctorLastNameCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("lastName"));
        doctorAddressCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("address"));
        clinicNameCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("clinicName"));
        clinicLongitudeCol.setCellValueFactory(new PropertyValueFactory<>("clinicLongitude"));
        clinicLatitudeCol.setCellValueFactory(new PropertyValueFactory<>("clinicLatitude"));

        doctorTableView.setItems(doctorObservableList);
    }
}
