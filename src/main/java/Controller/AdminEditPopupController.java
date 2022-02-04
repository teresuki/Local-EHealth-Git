package Controller;

import Connection.DBControl;
import Models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminEditPopupController implements Initializable {

    @FXML
    private ImageView image_background;
    @FXML
    TextField tf_username;
    @FXML
    TextField tf_email;
    @FXML
    TextField tf_firstName;
    @FXML
    TextField tf_lastName;
    @FXML
    TextField tf_address;
    @FXML
    TextField tf_insuranceID;
    @FXML
    ChoiceBox cb_insuranceType;
    @FXML
    ChoiceBox cb_gender;
    @FXML
    DatePicker dp_dateOfBirth;

    @FXML
    Button btn_cancel;
    @FXML
    Button btn_save;

    //Enums in User
    //User.Gender
    //User.InsuranceType

    final String UserUpdateStatement = "UPDATE user SET firstName = ?, lastName = ?, dateOfBirth = ? WHERE username = ?";

    private User UserToEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backgroundFile = new File("stuff/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        image_background.setImage(backgroundImage);
    }

    @FXML
    void btnCancelOnAction()
    {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    void btnSaveOnAction() throws SQLException {
        updateUserData();
        Stage stage = (Stage) btn_save.getScene().getWindow();
        stage.close();
    }

    private void updateUserData() throws SQLException {
        PreparedStatement statement = DBControl.dbConnection.prepareStatement(UserUpdateStatement);;

        statement.setString(1, tf_firstName.getText().isEmpty() ? null : tf_firstName.getText());
        statement.setString(2, tf_lastName.getText().isEmpty() ? null : tf_lastName.getText());
        statement.setDate(3, dp_dateOfBirth.getValue() == null ? null : Date.valueOf(dp_dateOfBirth.getValue()));
        statement.setString(4, UserToEdit.getUsername());

        statement.executeUpdate();
    }

    public void initUserData(User user) {
        UserToEdit = user;

        tf_username.setText(user.getUsername());
        tf_email.setText(user.getEmail());
        tf_firstName.setText(user.getFirstName() == null ? "" : user.getFirstName());
        tf_lastName.setText(user.getLastName() == null ? "" : user.getLastName());

        if (user.getDateOfBirth() != null) {
            dp_dateOfBirth.setValue(user.getDateOfBirth().toLocalDate());
        }
    }
}
