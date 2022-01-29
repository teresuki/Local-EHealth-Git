package Controller;

import Connection.DBControl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {

    @FXML
    private Button button_save;
    @FXML
    private Button button_cancel;
    @FXML
    private Label label_editProfileMessage;

    @FXML
    private ImageView image_background;

    //Teresuki
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_firstName;
    @FXML
    private TextField tf_lastName;
    @FXML
    private DatePicker tf_dateOfBirth;
    @FXML
    private RadioButton tf_gender_male;
    @FXML
    private RadioButton tf_gender_female;
    @FXML
    private RadioButton tf_gender_other;
    @FXML
    private TextField tf_address;
    @FXML
    private TextField tf_insuranceID;
    @FXML
    private RadioButton tf_insuranceType_private;
    @FXML
    private RadioButton tf_insuranceType_public;
    @FXML
    private Button tf_clear_dateOfBirth;


    enum InsuranceType {
        Public,
        Private
    }

    enum Gender {
        M,
        F,
        O
    }

    final java.sql.Date DEFAULT_DATE = java.sql.Date.valueOf("1900-01-31");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Display background image
        File backgroundFile = new File("stuff/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        image_background.setImage(backgroundImage);

        //Load loggedInUsername to Username TextField
        tf_username.setText(LoginController.loggedInUsername);

        //Load userprofile in Database, if data exist in Database it will be shown in the Edit scene
        try {
            ResultSet rs = getUserProfileResultSet(LoginController.loggedInUsername);
            loadUserProfileToScene(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void cancelButtonOnAction(ActionEvent event) throws Exception {
//          After click, return to the after login stage
        Parent root = FXMLLoader.load(getClass().getResource("after_login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ;

    public void saveButtonOnAction(ActionEvent event) {
        saveUserProfile();
    }

    public void saveUserProfile() {
        //loggedInUsername
        String username = tf_username.getText();

        //Get user Input
        String firstName = tf_firstName.getText();
        String lastName = tf_lastName.getText();
        LocalDate localDateOfBirth = tf_dateOfBirth.getValue();
        java.sql.Date dateOfBirth = DEFAULT_DATE;


        try {
            dateOfBirth = java.sql.Date.valueOf(localDateOfBirth);
        } catch (Exception e) {
            System.out.println(e);
        }
        Gender gender = getGenderInput();
        String address = tf_address.getText();
        String insuranceID = tf_insuranceID.getText();
        InsuranceType insuranceType = getInsuranceTypeInput();

        try {
            PreparedStatement stmt = DBControl.dbConnection.prepareStatement
                    ("UPDATE user \n" +
                            "SET firstName = ?, lastName = ?, address = ?, insuranceID = ?," +
                            "insuranceType = ?, gender = ?, dateOfBirth = ? \n" +
                            "WHERE username = ?"
                    );
            if (firstName == null || firstName == "")
                stmt.setNull(1, Types.VARCHAR);
            else
                stmt.setString(1, firstName);

            if (lastName == null || lastName == "")
                stmt.setNull(2, Types.VARCHAR);
            else
                stmt.setString(2, lastName);

            if (address == null || address == "")
                stmt.setNull(3, Types.VARCHAR);
            else
                stmt.setString(3, address);

            if (insuranceID == null || insuranceID == "")
                stmt.setNull(4, Types.VARCHAR);
            else
                stmt.setString(4, insuranceID);

            if (insuranceType == null)
                stmt.setNull(5, Types.NULL);
            else
                stmt.setString(5, insuranceType.toString());

            if (gender == null)
                stmt.setNull(6, Types.NULL);
            else
                stmt.setString(6, gender.toString());

            if (dateOfBirth == DEFAULT_DATE)
                stmt.setNull(7, Types.DATE);
            else
                stmt.setDate(7, dateOfBirth);

            stmt.setString(8, username);

            stmt.executeUpdate();
            System.out.println("Profile updated Successfully!");
            label_editProfileMessage.setText("Profile saved!");
        } catch (Exception e) {
            System.out.println("Profile updated Failed!");
            label_editProfileMessage.setText("Edit Profile Failed!");
            System.out.println(e);
        }

    }

    @FXML
    void clearDateOfBirthOnAction() {
        tf_dateOfBirth.setValue(null);
    }

    ResultSet getUserProfileResultSet(String username) throws SQLException {
        String sql = ("SELECT firstName, lastName, address, insuranceID, " +
                "insuranceType, gender, dateOfBirth \n" +
                "FROM user \n" +
                "WHERE username = ?");
        PreparedStatement pst = DBControl.dbConnection.prepareStatement(sql);
        pst.setString(1, LoginController.loggedInUsername); //fix here
        ResultSet rs = pst.executeQuery();
        return rs;
    }

    void loadUserProfileToScene(ResultSet rs) throws SQLException {
        rs.next();
        tf_firstName.setText(rs.getString(1));
        tf_lastName.setText(rs.getString(2));
        tf_address.setText(rs.getString(3));
        tf_insuranceID.setText(rs.getString(4));

        //InsuranceType
        String insuranceType = rs.getString(5);
        try {
            if (insuranceType.equals("Private"))
                tf_insuranceType_private.setSelected(true);
            else if (insuranceType.equals("Public"))
                tf_insuranceType_public.setSelected(true);

        } catch (Exception e) {
            System.out.println("Note: No Insurance Type yet");
        }

        //Gender
        String gender = rs.getString(6);
        try {

            if (gender != null && gender.equals("M"))
                tf_gender_male.setSelected(true);
            else if (gender.equals("F"))
                tf_gender_female.setSelected(true);
            else if (gender.equals("O"))
                tf_gender_other.setSelected(true);
        } catch (Exception e) {
            System.out.println("Note: No gender yet");
        }

        LocalDate dateOfBirth;
        try {
            dateOfBirth = rs.getDate(7).toLocalDate();
            tf_dateOfBirth.setValue(dateOfBirth);
        } catch (Exception e) {
            System.out.println("Note: No Date yet");
        }
    }

    @FXML
    public void genderMaleOnAction() {
        tf_gender_female.setSelected(false);
        tf_gender_other.setSelected(false);
    }

    @FXML
    public void genderFemaleOnAction() {
        tf_gender_male.setSelected(false);
        tf_gender_other.setSelected(false);
    }

    @FXML
    public void genderOtherOnAction() {
        tf_gender_male.setSelected(false);
        tf_gender_female.setSelected(false);
    }

    @FXML
    public void insuranceTypePrivateOnAction() {
        tf_insuranceType_public.setSelected(false);
    }

    @FXML
    public void insuranceTypePublicOnAction() {
        tf_insuranceType_private.setSelected(false);
    }

    Gender getGenderInput() {
        Gender gender = null;
        if (tf_gender_male.isSelected()) {
            gender = Gender.M;
        } else if (tf_gender_female.isSelected()) {
            gender = Gender.F;
        } else if (tf_gender_other.isSelected()) {
            gender = Gender.O;
        }
        return gender;
    }

    InsuranceType getInsuranceTypeInput() {
        InsuranceType insuranceType = null;
        if (tf_insuranceType_private.isSelected()) {
            insuranceType = InsuranceType.Private;
        } else if (tf_insuranceType_public.isSelected()) {
            insuranceType = InsuranceType.Public;
        }
        return insuranceType;
    }

}
