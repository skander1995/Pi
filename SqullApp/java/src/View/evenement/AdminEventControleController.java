/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import Entities.Evenement;
import Services.EvenementService;
import Utilities.Mail;
import View.Navigator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author SELLAMI
 */
public class AdminEventControleController implements Initializable {

    @FXML
    private Button envoi;
    @FXML
    private TextArea mail;
    @FXML
    private ComboBox<String> etat;
    public static int idUser;
    public static int idEvt;
    public static String etatt;
    public static String nomEvt;
    public static Evenement ev;

    @FXML
    private Button annuler;
    @FXML
    private TextArea description;
    @FXML
    private ImageView image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        etat.getItems().add("dispo");
        etat.getItems().add("masqué");
        etat.getItems().add("invalide");
        etat.getSelectionModel().select(etatt);
        description.setText(ev.getDescription());
        Image i = new Image(ev.getAffiche());
        image.setImage(i);
        mail.setText("on a changer l'etat de votre publication");
    }

    @FXML
    private void modifier(ActionEvent event) {
        EvenementService es = new EvenementService();
        boolean x = es.changerEtat(idEvt, etat.getValue());
        //envoi de mail
        System.out.println(es.getUserMail(idUser));
        Mail.send(es.getUserMail(idUser), "Conçernant votre evenement de nom " + nomEvt, mail.getText());
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setTitle("INFORMATION");
        al.setHeaderText(null);
        al.setContentText("Etat changé et mail envoyé avec succés");
        al.showAndWait();
        Navigator.LoadScene(Navigator.AdminEvents);
    }

    @FXML
    private void cancel(ActionEvent event) {
        Navigator.LoadScene(Navigator.AdminEvents);
    }

}
