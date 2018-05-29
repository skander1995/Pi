/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Reclamation;
import Entities.User;
import EnumPack.Etat;
import EnumPack.ReclamationType;
import Services.ReclamationService;
import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class ReportPopOverController implements Initializable {

    @FXML
    private JFXComboBox<ReclamationType> comboTypeSignale;
    @FXML
    private JFXTextArea descSignaler;
    @FXML
    private JFXTextField titleSignaler;

    public int idProfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //comboTypeSignale.getItems().addAll("Message Innapproprié", "Photos Innapproprié", "Contenu Innaproprié", "Autre");
        comboTypeSignale.getItems().addAll(
                ReclamationType.ContenuInnaproprié,
                ReclamationType.PhotosInnapproprié,
                ReclamationType.MessageInnapproprie,
                ReclamationType.autre
        );
    }

    @FXML
    private void submitReport(ActionEvent event) {

        try {
            ReclamationService recService = new ReclamationService();
            Reclamation reclamation = new Reclamation(0,
                    User.getIdOfConnectedUser(),
                    idProfile,
                    ToolsUtilities.formater.parse(ToolsUtilities.formater.format(new Date())),
                    titleSignaler.getText(), descSignaler.getText(), Etat.encours, comboTypeSignale.getSelectionModel().getSelectedItem());
            if (recService.add(reclamation) != 0) {
                // réussi : notification
                Notifications NotificationBuilder = Notifications.create()
                        .title("Opération réussi")
                        .text("Un administrateur traitera votre signalement dans le plus bref délai")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT);
                NotificationBuilder.showInformation();
            } else {
                Notifications NotificationBuilder = Notifications.create()
                        .title("Opération échoué")
                        .text("Une erreur s'est produite")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT);
                NotificationBuilder.showError();
            }
        } catch (ParseException ex) {
            Logger.getLogger(ReportPopOverController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
