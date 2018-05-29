/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import EnumPack.MailType;
import IServices.IUserService;
import Services.UserService;
import Utilities.ToolsUtilities;
import View.Animation.ShakeTransition;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wildchild
 */
public class ConfirmAccountController implements Initializable {

    @FXML
    private JFXButton confirmButton;
    @FXML
    private JFXTextField code;
    @FXML
    private Label errorLabel1;
    @FXML
    private Label errorLabel2;

    @FXML
    public void confirmAccount(ActionEvent event) throws IOException {
        System.out.println("executed");
        checkAccount();
    }

    @FXML
    public void checkCode(InputMethodEvent event) {
        // TODO
        if (code.getText().length() == 5) {
            System.out.println(" excutinggg length   : code.getText() = " + code.getText().length());
            checkAccount();
        }
    }

    public void generateAnotherCode(MouseEvent event) {
        //ToolsUtilities.sendValidationMail(User.getActifUser(), "Confirmation de compte");
        ToolsUtilities.sendValidationMail(User.getActifUser(), MailType.activation);
        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );
        return scene;
    }

    public void checkAccount() {
        IUserService userservice = new UserService();

        if ((code.getText()).equals(String.valueOf(User.getValidationCode()))) {
            try {
                if (ToolsUtilities.DEBUG) {
                    System.out.println("Print = " + code.getText() + "  --  " + User.getValidationCode());
                }
                User.setEnabled(1);
                userservice.setEnabledToTrue(User.getIdOfConnectedUser());

                FXMLLoader loader = new FXMLLoader();
                Pane mpane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.authentification));
                Stage stage1 = null;
                stage1 = (Stage) confirmButton.getScene().getWindow();
                stage1.hide();
                stage1.setScene(createScene(mpane));
                stage1.show();
                /*
                Pane mpane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.authentification));
                Stage stage1 = null;
                stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage1.hide();
                stage1.setScene(createScene(mpane));
                stage1.show();
                 */
            } catch (IOException ex) {
                Logger.getLogger(ConfirmAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            ShakeTransition anim = new ShakeTransition(code);
            anim.playFromStart();
            errorLabel1.setVisible(true);
            errorLabel2.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void generateAnotherCode(javafx.scene.input.MouseEvent event) {
        // ToolsUtilities.sendValidationMail(User.getActifUser(), "Confirmation de compte");
        ToolsUtilities.sendValidationMail(User.getActifUser(), MailType.activation);
        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
    }

}
