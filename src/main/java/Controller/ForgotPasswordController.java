package Controller;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backgroundFile = new File("stuff/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        image_background.setImage(backgroundImage);
        //Make recoverCode field not editable before sending recovery code:
        tf_recoverCode.setEditable(false);
    }

    @FXML
    public void recoverSendEmailButtonOnAction() throws MessagingException {
        if(tf_recoverEmail.getLength() == 0 && tf_recoverUsername.getLength() == 0)
        {
            tf_recoverMessage.setText("Please enter your email or username");
        }
        else
        {
            //Implement Email sending here:
            recoverCode = generateRandomString(6);
            System.out.println("DEBUG: RECOVERY CODE:" + recoverCode);

            String username = "deltadeer.yf@gmail.com";// change accordingly
            String password = "MischaEisenstein";// change accordingly
            EmailControl user1 = new EmailControl(username, password);

            user1.sendMail("hoangdinh.yf@gmail.com","EHealth Recovery Code","Hello,\n\nYour recovery code can be found below:\n\n"+ recoverCode +"\n\n\nBest Regards,\nYour Best Developer YF");
            //user1.sendScheduledMail(10,"hoangdsieud@gmail.com","Send mail after 10s","Hello");



            //State change in scene:
            emailSent = true;
            tf_recoverMessage.setText("Recovery code sent over your email!");
            tf_recoverCode.setEditable(true);
        }
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
    public void confirmButtonOnAction()
    {
        if(emailSent == false)
        {
            tf_recoverMessage.setText("Please enter your email or username!");
        }
        else if(tf_recoverMessage == null || tf_recoverCode.getLength() == 0)
        {
            tf_recoverMessage.setText("Please enter your recovery code!");
        }
        else if(tf_recoverCode.getText().equals(recoverCode)) // check here
        {
            tf_recoverMessage.setText("Code matched!");
            //Change scene here:
        }
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
