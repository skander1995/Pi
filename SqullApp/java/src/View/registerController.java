/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import EnumPack.MailType;
import EnumPack.TypeUser;
import IServices.IUserService;
import Services.UserService;
import Utilities.ToolsUtilities;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import javax.xml.bind.Unmarshaller.Listener;

/**
 * FXML Controller class
 *
 * @author adel
 */
public class registerController implements Initializable {

    @FXML
    private JFXPasswordField pwd1;
    @FXML
    private JFXPasswordField pwd2;
    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField prn;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phone;
    @FXML
    private DatePicker date;
    @FXML
    private Label emailLabel;
    @FXML
    private JFXTextField phoneID;

    @FXML
    void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mpane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.authentification));
        Stage stage1 = null;
        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage1.hide();
        stage1.setScene(createScene(mpane));
        stage1.show();
    }

    @FXML
    void Inscription(ActionEvent event) {

        if (nom.getText()
                .isEmpty()) {
            nom.setPromptText("Nom Obligatoire");
            nom.setStyle("-fx-border-color:red;");
            return;
        }

        if (prn.getText()
                .isEmpty()) {
            prn.setPromptText("Prenom Obligatoire");
            prn.setStyle("-fx-border-color:red;");
            return;
        }

        if (email.getText()
                .isEmpty()) {
            email.setPromptText("Email Obligatoire");
            email.setStyle("-fx-border-color:red;");
            return;
        }

        if (!ToolsUtilities.mailValidator(email.getText())) {
            email.setPromptText("Email non valide");
            email.setStyle("-fx-border-color:red;");
            return;
        }

        try {
            if (date.getValue().getYear() < 2000) {
                date.setPromptText("Date naissance < 2000");
                date.setStyle("-fx-background-color:red;");
                return;
            }
        } catch (NullPointerException ne) {
            date.setStyle("-fx-background-color:red;");
            date.setPromptText("Date naissance Obligatoire");
            return;
        }

        String number = phone.getText();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            PhoneNumber numberProto = phoneUtil.parse(number, "");
            System.out.println("Number is of region - "
                    + phoneUtil.getRegionCodeForNumber(numberProto));

            System.out
                    .println("Is the input number valid - "
                            + (phoneUtil.isValidNumber(numberProto) == true ? "Yes"
                            : "No"));
            phoneID.setText(phoneUtil.getRegionCodeForNumber(numberProto));
        } catch (NumberParseException e) {
            if (phone.getText().length() == 8) {
                // numero tunisien
                phoneID.setText("TN");
            } else {
                System.err.println("NumberParseException was thrown: " + e.toString());
                phone.setPromptText("Numero invalide");
                return;
            }

        }

        if (pwd1.getText().isEmpty() && pwd1.getText().length() < 3) {
            pwd1.setPromptText("Mot de passe valide Obligatoire");
            pwd1.setStyle("-fx-border-color:red;");
            return;
        }

        if (pwd2.getText().isEmpty()) {
            pwd2.setPromptText("Mot de passe Obligatoire");
            pwd2.setStyle("-fx-border-color:red;");
            return;
        }

        if (!pwd2.getText().equals(pwd1.getText())) {
            pwd2.setPromptText("Mot de passe Incorrecte");
            pwd2.setStyle("-fx-border-color:red;");
            return;
        }

        try {
            // information primaire
            // then after confirmation : from profil : complete profile informaton
            User user = new User(nom.getText(), prn.getText(), ToolsUtilities.formater.parse(date.getValue().toString()), email.getText(), phone.getText(), pwd1.getText(), TypeUser.user);
            IUserService userservice = new UserService();
            int execRst = userservice.add(user);
            if (execRst != -1) {
                //set id of connected user  
                userservice.setConnected(User.getIdOfConnectedUser());
                // proceed code validation process
                if (ToolsUtilities.DEBUG) {
                    System.out.println("Sending mail .. ");
                }
                // ToolsUtilities.sendValidationMail(user, "Validation du compte");
                ToolsUtilities.sendValidationMail(user, MailType.activation);
                //Navigator.LoadScene(Navigator.confirmation);

                // load main pane 
                FXMLLoader loader = new FXMLLoader();
                Pane mpane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.confirmation));
                Stage stage1 = null;
                stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage1.hide();
                stage1.setScene(createScene(mpane));
                stage1.show();
            } else {
                // error occured
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur occured while add operation");
                alert.showAndWait();
            }
            // send Verification mail
        } catch (ParseException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched register Controller = " + ex.getMessage());
            }
        } catch (Exception me) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched register Controller= " + me.getMessage());
            }
        }

    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );
        return scene;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService userService = new UserService();
        email.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                if (userService.testUserExistanceByEmail(email.getText())) {
                    email.setStyle("-fx-border-color:red;");
                    emailLabel.setVisible(true);
                } else {
                    email.setStyle("");
                    emailLabel.setVisible(false);
                }
            }
        });
    }
}
