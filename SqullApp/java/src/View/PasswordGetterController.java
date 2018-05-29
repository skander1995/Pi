/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import EnumPack.MailType;
import Services.UserService;
import Utilities.BCrypt;
import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class PasswordGetterController implements Initializable {

    @FXML
    private JFXTextField emailField;
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UserService userService = new UserService();
        emailField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                if (userService.testUserExistanceByEmail(emailField.getText())) {
                    emailField.setStyle("");
                    errorLabel.setVisible(false);
                } else {
                    emailField.setStyle("-fx-border-color:red;");
                    errorLabel.setVisible(true);
                }
            }
        });
    }

    @FXML
    private void firePwdGetter(ActionEvent event) {
        if (emailField.getText()
                .isEmpty()) {
            emailField.setPromptText("Email Obligatoire");
            emailField.setStyle("-fx-border-color:red;");
            return;
        }

        if (!ToolsUtilities.mailValidator(emailField.getText())) {
            emailField.setPromptText("Email non valide");
            emailField.setStyle("-fx-border-color:red;");
            return;
        }
        UserService userService = new UserService();
        HashMap<String, String> args = new HashMap<>();
        args.put("EMAIL", emailField.getText());
        List<User> users = userService.findByAny(args);
        if (users.size() > 0) {
            User user = users.get(0);
            System.out.println("INFORMATION DENVOI");
            System.out.println("MOTPASSE hashed " + user.getPassword());
            //System.out.println(" NON HASH2"+BCrypt.);
            String content = "Votre demande de mot de passe a été éffectuer avec succés.\n"
                    + "Ceci est votre login :" + user.getLogin()
                    + "\nMot de passe : " + user.getPassword();// hashed
            ToolsUtilities.sendMailTo(user.getEmail(), content, MailType.autre);

        } else {
            System.err.println(" erreur pas de resultat retourné");
        }
    }

}
