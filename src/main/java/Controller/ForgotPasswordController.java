package Controller;

import Connection.DBControl;
import Email.EmailControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {

    @FXML
    ImageView image_background;
    @FXML
    TextField tf_recoverEmail;
    @FXML
    TextField tf_recoverUsername;
    @FXML
    TextField tf_recoverCode;
    @FXML
    Button tf_recoverSendEmail;
    @FXML
    Button tf_cancelButton;
    @FXML
    Button tf_confirmButton;
    @FXML
    Label tf_recoverMessage;

    private String recoverCode;
    private Boolean emailSent = false;
    static String recoverUsername; // used to pass to ResetPasswordController
    static String recoverEmail;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backgroundFile = new File("stuff/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        image_background.setImage(backgroundImage);
        //Make recoverCode field not editable before sending recovery code:
        tf_recoverCode.setEditable(false);
    }

    @FXML
    public void recoverSendEmailButtonOnAction() throws MessagingException, SQLException, InterruptedException {
        tf_recoverMessage.setText("Sending Email...");
        if(tf_recoverEmail.getLength() == 0 && tf_recoverUsername.getLength() == 0)
        {
            tf_recoverMessage.setText("Please enter your email or username!");
            return;
        }
        else if(tf_recoverEmail.getLength() > 0 && tf_recoverUsername.getLength() > 0)
        {
            tf_recoverMessage.setText("Please enter only email or username!");
            return;
        }
        else if(tf_recoverEmail.getLength() > 0) // user entered email
        {
            recoverEmail = tf_recoverEmail.getText();
            if(isEmailInDB(recoverEmail) == false)
            {
                tf_recoverMessage.setText("Username or Email does not exist!");
                return;
            }
        }
        else if(tf_recoverUsername.getLength() > 0)
        {
            String username = tf_recoverUsername.getText();
            if(isEmailInDBByUsername(username) == false)
            {
                tf_recoverMessage.setText("Username or Email does not exist!");
                return;
            }
            recoverEmail = getEmailFromUsernameDB(username);
        }
        //Implement Email sending here:
        recoverCode = generateRandomString(6);
        System.out.println("DEBUG: RECOVERY CODE:" + recoverCode);

        String username = "ehealthvgu.noreply";// change accordingly
        String password = "vgu123456";// change accordingly
        EmailControl user1 = new EmailControl(username, password);

        user1.sendMail(recoverEmail,"EHealth Recovery Code","Hello,\n\nYour recovery code can be found below:\n\n"+ recoverCode +"\n\n\nBest Regards,\nEhealth System Developers");
        //user1.sendScheduledMail(10,"hoangdsieud@gmail.com","Send mail after 10s","Hello");



        //State change in scene:
        emailSent = true;
        tf_recoverMessage.setText("Recovery code sent over your email!");
        tf_recoverCode.setEditable(true);

    }

    @FXML
    public void cancelButtonOnAction (ActionEvent event) throws IOException
    {
        //Switch to log in scene
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void confirmButtonOnAction(ActionEvent event) throws InterruptedException, SQLException, IOException {
        if(emailSent == false)
        {
            tf_recoverMessage.setText("Please enter your email or username!");
            return;
        }
        else if(tf_recoverMessage == null || tf_recoverCode.getLength() == 0)
        {
            tf_recoverMessage.setText("Please enter your recovery code!");
        }
        else if(!tf_recoverCode.getText().equals(recoverCode)) // check here
        {
            tf_recoverMessage.setText("Code does not match!");
            //Change scene here:
        }
        else
        {
            tf_recoverMessage.setText("Code matched! Redirecting...");
            Thread.sleep(1000);
            recoverUsername = getUsernameFromEmailDB(recoverEmail);

            //Switch to reset password scene
            Parent root = FXMLLoader.load(getClass().getResource("reset_password.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    Boolean isEmailInDB(String email) throws SQLException {
        String query = "SELECT email FROM user WHERE email= ?";
        PreparedStatement pst = DBControl.dbConnection.prepareStatement(query);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            System.out.println("Email does not exist in DB:");
            return false;
        }
        return true;
    }

    Boolean isEmailInDBByUsername(String username) throws SQLException {
        String query = "SELECT email FROM user WHERE username= ?";
        PreparedStatement pst = DBControl.dbConnection.prepareStatement(query);
        pst.setString(1, username);
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            System.out.println("Email does not exist in DB:");
            return false;
        }
        return true;
    }

    //Can only be called if isEmailInDBByUsername() returns true
    String getEmailFromUsernameDB(String username) throws SQLException {
        String query = "SELECT email FROM user WHERE username= ?";
        PreparedStatement pst = DBControl.dbConnection.prepareStatement(query);
        pst.setString(1, username);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    String getUsernameFromEmailDB(String email) throws SQLException {
        String query = "SELECT username FROM user WHERE email= ?";
        PreparedStatement pst = DBControl.dbConnection.prepareStatement(query);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    String generateRandomString(int stringLength) {
        Random rand = new Random();
        String result = "";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < stringLength; i++) {

            int randNum = rand.nextInt(alphabet.length());
            result += alphabet.charAt(randNum);
        }
        return result;
    }


}
