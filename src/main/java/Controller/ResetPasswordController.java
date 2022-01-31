package Controller;

import Connection.DBControl;
import javafx.event.ActionEvent;
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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ResetPasswordController implements Initializable {
    @FXML
    ImageView image_background;
    @FXML
    TextField tf_username;
    @FXML
    PasswordField tf_password;
    @FXML
    PasswordField tf_passwordAgain;
    @FXML
    Label tf_messageLabel;
    @FXML
    Button tf_cancelButton;
    @FXML
    Button tf_confirmButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backgroundFile = new File("stuff/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        image_background.setImage(backgroundImage);

        //Show recoverUsername in TextFied
        tf_username.setText(ForgotPasswordController.recoverUsername);
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) throws IOException {
        tf_messageLabel.setText("Returning to Login Menu");
        //Switch to log in scene
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void confirmButtonOnAction(ActionEvent event) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException, IOException {
        if(tf_password.getLength() == 0 || tf_passwordAgain.getLength() == 0 )
        {
            tf_messageLabel.setText("Please enter your new password!");
            return;
        }
        if(!tf_password.getText().equals(tf_passwordAgain.getText()))
        {
            tf_messageLabel.setText("Confirm password does not match new password!");
            return;
        }

        //Calculate new password's hash
        String password = tf_password.getText();
        byte[] salt = generateSalt();
        byte[] hashedPassword = generateHashedPassword(password, salt);


        PreparedStatement stmt = DBControl.dbConnection.prepareStatement
                ("UPDATE user \n" +
                        "SET hashedPassword = ?, salt = ?\n" +
                        "WHERE username = ?"
                );
        stmt.setBytes(1, hashedPassword);
        stmt.setBytes(2, salt);
        stmt.setString(3, ForgotPasswordController.recoverUsername);
        stmt.executeUpdate();

        tf_messageLabel.setText("Reset password successfully!");
        Thread.sleep(2000);

        //Switch to log in scene
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    byte[] generateHashedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
        return hashedPassword;
    }


}
