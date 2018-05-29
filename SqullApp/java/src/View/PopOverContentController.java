/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import IServices.IUserService;
import Services.UserService;
import Utilities.BCrypt;
import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class PopOverContentController implements Initializable {

    @FXML
    private DatePicker dateField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField prnField;
    @FXML
    private JFXTextField phoneField;
    @FXML
    private JFXButton modiferButton;
    @FXML
    private JFXButton supprimerButton;
    @FXML
    private ImageView profileImg;
    User user = null;
    @FXML
    private Label loginLabel;

    private UserAdminPanelController caller = null;
    private ProfileUserController userProfilecaller = null;
    @FXML
    private JFXPasswordField mdpField;
    @FXML
    private Label mdpLabel;

    private boolean prompted = false;
    private boolean pwdChanged;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void initWithData(UserAdminPanelController caller, User user) {
        this.caller = caller;
        this.user = user;

        //init with admin UI
        mdpField.setVisible(false);
        mdpLabel.setVisible(false);

        try {
            loginLabel.setText(user.getLogin());
        } catch (NullPointerException ex) {

        }

        try {
            LocalDate local = user.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dateField.setValue(local);
        } catch (NullPointerException ne) {
            dateField.setPromptText("Pas de données");
        }

        try {
            nameField.setText(user.getNom());
        } catch (NullPointerException ne) {
            nameField.setPromptText("Pas de données");
        }
        try {
            prnField.setText(user.getPrenom());
        } catch (NullPointerException ne) {
            prnField.setPromptText("Pas de données");
        }

        try {
            phoneField.setText(user.getTelephone());
        } catch (NullPointerException ne) {
            phoneField.setPromptText("Pas de données");
        }

        try {
            Image image = new Image(user.getPhotoProfil());
            profileImg.setImage(image);
        } catch (NullPointerException ne) {

        }
    }

    public void initWithData(ProfileUserController caller, User user) {
        this.userProfilecaller = caller;
        supprimerButton.setText("Supprimer mon compte");
        this.user = user;

        mdpField.setVisible(true);
        mdpLabel.setVisible(true);

        try {
            loginLabel.setText(user.getLogin());
        } catch (NullPointerException ex) {

        }

        try {
            LocalDate local = user.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dateField.setValue(local);
        } catch (NullPointerException ne) {
            dateField.setPromptText("Pas de données");
        }

        try {
            nameField.setText(user.getNom());
        } catch (NullPointerException ne) {
            nameField.setPromptText("Pas de données");
        }
        try {
            prnField.setText(user.getPrenom());
        } catch (NullPointerException ne) {
            prnField.setPromptText("Pas de données");
        }

        try {
            phoneField.setText(user.getTelephone());
        } catch (NullPointerException ne) {
            phoneField.setPromptText("Pas de données");
        }

        try {
            Image image = new Image(user.getPhotoProfil());
            profileImg.setImage(image);
        } catch (NullPointerException ne) {

        }
    }

    @FXML
    private void enableModification(KeyEvent event) {
        modiferButton.setDisable(false);
        modiferButton.setStyle("-fx-background-color :  #54ACD2;");
    }

    private void enableModification() {
        modiferButton.setDisable(false);
        modiferButton.setStyle("-fx-background-color :  #54ACD2;");
    }

    @FXML
    private void pushUpdate(ActionEvent event) {
        try {
            IUserService userServices = new UserService();
            User newUser = new User(
                    this.user.getId(),
                    nameField.getText(),
                    nameField.getText(),
                    ToolsUtilities.formater.parse(dateField.getValue().toString()),
                    phoneField.getText(),
                    profileImg.getImage().impl_getUrl()
            );
            HashMap<String, String> mapUpdate = new HashMap<>();
            mapUpdate.put("name_usr", nameField.getText());
            mapUpdate.put("prn_usr", prnField.getText());
            mapUpdate.put("datenaissance", dateField.getValue().toString());
            mapUpdate.put("telephone_usr", phoneField.getText());
            mapUpdate.put("PHOTO_PROFILE", profileImg.getImage().impl_getUrl());

            if (this.pwdChanged) {
                String encryptedPass = BCrypt.hashpw(mdpField.getText(), BCrypt.gensalt(13));
                mapUpdate.put("PWD", encryptedPass);
            }

            if (userServices.update(newUser, mapUpdate)) {
                // push notification
                Notifications NotificationBuilder = Notifications.create()
                        .title("Opération réussi")
                        .text("Utilisateur modifié avec succés")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT);
                NotificationBuilder.showInformation();
            } else {
                Notifications NotificationBuilder = Notifications.create()
                        .title("Opération échoué")
                        .text("L'Utilisateur n'a pas été modifier avec succés")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT);
                NotificationBuilder.showError();
            }

        } catch (ParseException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched from pushUpdate= " + ex.getMessage());
            }
        }
        try {
            caller.refreshView();
        } catch (NullPointerException ex) {
            userProfilecaller.initInterfaceWithConnectedUser();
        }
    }

    @FXML
    private void pushDelete(ActionEvent event) {
        IUserService userServices = new UserService();
        Alert alert = new Alert(AlertType.CONFIRMATION, "Etes vous sûr de vouloir continuer ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            //do stuff
            if (userServices.delete(this.user.getId())) {
                Notifications NotificationBuilder = Notifications.create()
                        .title("Opération réussi")
                        .text("Utilisateur modifié avec succés")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT);
                NotificationBuilder.showInformation();
            } else {
                Notifications NotificationBuilder = Notifications.create()
                        .title("Opération échoué")
                        .text("L'Utilisateur n'a pas été supprimer avec succés")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT);
                NotificationBuilder.showError();
            }
            try {
                caller.refreshView();
            } catch (NullPointerException ne) {
                Navigator.LoadScene(Navigator.authentification);
            }
        }

    }

    @FXML
    private void promptPwdWarning(InputMethodEvent event) {
        if (!this.prompted) {
            this.prompted = true;
            Alert alert = new Alert(AlertType.INFORMATION, "Vous êtes sur le point de modifier votre mot de passe,\n"
                    + "Etes vous sûr de vouloir continuer ?", ButtonType.YES, ButtonType.NO);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) {
                event.consume();
            }
        }
    }

    @FXML
    private void promptPwdWarning(KeyEvent event) {
        if (!this.prompted) {
            this.prompted = true;
            Alert alert = new Alert(AlertType.INFORMATION, "Vous êtes sur le point de modifier votre mot de passe,\n"
                    + "Etes vous sûr de vouloir continuer ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) {
                event.consume();
            } else {
                this.pwdChanged = true;
                enableModification();
            }
        }
    }

}
