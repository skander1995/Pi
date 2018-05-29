/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.colocation;

import Entities.Colocation;
import Entities.User;
import EnumPack.MailType;
import Services.ColocationService;
import Utilities.MailSender;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author ASUS I7
 */
public class Colocation_ajouterController implements Initializable {

    @FXML
    private Button annulerA;
    @FXML
    private TextField titre;
    @FXML
    private TextField prix;
    @FXML
    private TextArea description;
    @FXML
    private Button add;
    @FXML
    private DatePicker Date_Disponibilite;
    @FXML
    private ComboBox<String> Lieu;
    private File[] fichier;
    @FXML
    private TextField imageText;
    @FXML
    private Button imageButton;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Lieu.getItems().add("Ariana");
        Lieu.getItems().add("Tunis");
        Lieu.getItems().add("Nasr");
        Lieu.getItems().add("Ariana soghra");
        Lieu.getItems().add("Chotrana");
        Lieu.getItems().add("Nour Jaafer");
        Lieu.getItems().add("Raoued");
        Lieu.getItems().add("Soukra");
        Lieu.getItems().add("Ghazela");

    }

    @FXML
    private void addE(ActionEvent event) {
        Date now = new Date(new java.util.Date().getTime());
        ColocationService cs = new ColocationService();
        if (titre.getText().isEmpty() || Date_Disponibilite.getValue() == null | Lieu.getValue().isEmpty() || prix.getText().isEmpty() || description.getText().isEmpty() || imageText.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "champs vides");

            a.showAndWait();

        } else {
            Date datex = Date.valueOf(Date_Disponibilite.getValue());
            if (datex.before(now)) {
                label.setText("la date doit etre superieur à la date d'aujourd'hui");
            } else {
                Colocation co = new Colocation(titre.getText(), datex, Lieu.getValue(), Float.valueOf(prix.getText()), description.getText());
                co.setDATEPUB(datex);
                System.out.print("test");
                co.setID_USR(User.getIdOfConnectedUser());

                cs.ajouterColocation(co);
                System.out.println(cs.getMail(User.getIdOfConnectedUser()));
                MailSender.send(cs.getMail(User.getIdOfConnectedUser()), MailType.ColocationEsprit, "votre offre colocation est ajoutée avec succes");
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("INFORMATION");
                al.setHeaderText(null);
                al.setContentText("Votre ajout a ete effectue");
                al.showAndWait();
                Lieu.setValue("");
                prix.setText("");
                Date_Disponibilite.setValue(null);
            }
        }

    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("AccueilColocation.fxml"));
        Scene scene = new Scene(page_select_my);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    @FXML
    private void select(ActionEvent event) {
        JFileChooser file = new JFileChooser();
        file.setMultiSelectionEnabled(true);
        file.showOpenDialog(null);
        fichier = file.getSelectedFiles();
        String im = "";
        for (File f : fichier) {
            im = im + f.getAbsolutePath();
        }
        imageText.setText(im);

    }

}
