package Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.controlsfx.control.action.Action;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;



public class AfterLoginController implements Initializable{
    @FXML
    private Label label_welcomemessage;

    @FXML
    private Button button_editprofile;
    @FXML
    private Button button_bookappointment;
    @FXML
    private Button button_viewappointment;
    @FXML
    private Button button_signout;

    @FXML
    private ImageView image_background;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Make image displayable on run
        File backgroundFile = new File("stuff/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        image_background.setImage(backgroundImage);
    }

    public void editProfileButtonOnAction(ActionEvent event) throws Exception{
//        Switch to edit profile stage
        Parent root = FXMLLoader.load(getClass().getResource("edit_profile.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void bookAppointmentButtonOnActon(ActionEvent event) throws Exception{
//        Switch to edit profile stage
        Parent root = FXMLLoader.load(getClass().getResource("book_appointment.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void viewAppointmentButtonOnAction(){
    }

    public void signoutButtonOnAction(ActionEvent event) throws Exception{
//        After pressing sign out, it will return to the main screen
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }








}
