/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Covoiturage;

import Entities.Covoiturage;
import Entities.User;
import Services.CovoiturageService;
import Utilities.ToolsUtilities;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * FXML Controller class
 *
 * @author Moez
 */
public class CovoiturageAddController implements Initializable {

    //private final Connection connection = JdbcInstance.getInstance();
    CovoiturageService csr;

    @FXML
    private ComboBox<String> lieudepart;
    @FXML
    private ComboBox<String> lieuarrive;
    @FXML
    private TextField nbplace;
    @FXML
    private TextField prix;
    @FXML
    private Button btn;
    @FXML
    private DatePicker date;
    @FXML
    private Button afficher;
    @FXML
    private Button retour;
    @FXML
    private Label controlSaisieDate;
    @FXML
    private Label controleSsaisieNbplace;
    @FXML
    private Label controleSaisielieu;
    @FXML
    private TextArea description;

    @FXML
    public void addC(ActionEvent e) {
        controlSaisieDate.setText("");
        controleSsaisieNbplace.setText("");
        controleSaisielieu.setText("");

        CovoiturageService cs = new CovoiturageService();
        if (nbplace.getText().isEmpty() | prix.getText().isEmpty() | date.getValue() == null | lieudepart.getValue().isEmpty() | lieuarrive.getValue().isEmpty() | prix.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("champs vides");
            a.showAndWait();

        } else {
            try {
                //Date datex = Date.valueOf(date.getValue());
                Date datex = ToolsUtilities.formater.parse(date.getValue().toString());

                if (lieuarrive.getItems().get(lieuarrive.getSelectionModel().getSelectedIndex()).equals(lieudepart.getItems().get(lieudepart.getSelectionModel().getSelectedIndex()))) {
                    controleSaisielieu.setText("Vous devez selectionne deux lieu different");

                } else {
                    if (datex.compareTo(ToolsUtilities.formater.parse(ToolsUtilities.formater.format(new Date()))) < 0) {
                        controlSaisieDate.setText("La date doit Etre superieure ou égale à celle d'aujourd'hui");
                    } else {
                        if (Integer.parseInt(nbplace.getText()) > 4) {
                            controleSsaisieNbplace.setText("Le nombre de place doit etre inféerier a 5");
                        } else {

                            Covoiturage co = new Covoiturage(datex, lieudepart.getValue(), lieuarrive.getValue(), Float.valueOf(prix.getText()), Integer.valueOf(nbplace.getText()), description.getText());

                            //co.setID_PUB();
                            co.setCHECKPOINTS("test");
                            co.setDATEDEPART(datex);
                            co.setDATEPUB(datex);
                            co.setETAT("test");
                            co.setID_USR(1);
                            co.setID_VILLE(2);
                            co.setVIL_ID_VILLE(2);
                            System.out.print("test");
                            co.setID_USR(User.getIdOfConnectedUser());

                            cs.insertCovoiturage(co);
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle("INFORMATION");
                            al.setHeaderText(null);
                            al.setContentText("Votre ajout a ete effectue");
                            al.showAndWait();
                            lieudepart.setValue("");
                            lieuarrive.setValue("");
                            nbplace.setText("");
                            prix.setText("");
                            date.setValue(null);
                            controlSaisieDate.setText("");
                            description.setText("");

                        }
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(CovoiturageAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        controlSaisieDate.setText("");

        lieudepart.getItems().add("Ariana");
        lieudepart.getItems().add("Béja");
        lieudepart.getItems().add("Ben Arous");
        lieudepart.getItems().add("Bizerte");
        lieudepart.getItems().add("Gabes");
        lieudepart.getItems().add("Gafsa");
        lieudepart.getItems().add("Jendouba");
        lieudepart.getItems().add("Kairouan");
        lieudepart.getItems().add("Kasserine");
        lieudepart.getItems().add("Kebili");
        lieudepart.getItems().add("La Manouba");
        lieudepart.getItems().add("Le Kef");
        lieudepart.getItems().add("Mahdia");
        lieudepart.getItems().add("Médenine");
        lieudepart.getItems().add("Monastir");
        lieudepart.getItems().add("Nabeul");
        lieudepart.getItems().add("Sfax");
        lieudepart.getItems().add("Sidi Bouzid");
        lieudepart.getItems().add("Siliana");
        lieudepart.getItems().add("Sousse");
        lieudepart.getItems().add("Tataouine");
        lieudepart.getItems().add("Tozeur");
        lieudepart.getItems().add("Tunis");
        lieudepart.getItems().add("Zaghouan");

        lieuarrive.getItems().add("Ariana");
        lieuarrive.getItems().add("Béja");
        lieuarrive.getItems().add("Ben Arous");
        lieuarrive.getItems().add("Bizerte");
        lieuarrive.getItems().add("Gabes");
        lieuarrive.getItems().add("Gafsa");
        lieuarrive.getItems().add("Jendouba");
        lieuarrive.getItems().add("Kairouan");
        lieuarrive.getItems().add("Kasserine");
        lieuarrive.getItems().add("Kebili");
        lieuarrive.getItems().add("La Manouba");
        lieuarrive.getItems().add("Le Kef");
        lieuarrive.getItems().add("Mahdia");
        lieuarrive.getItems().add("Médenine");
        lieuarrive.getItems().add("Monastir");
        lieuarrive.getItems().add("Nabeul");
        lieuarrive.getItems().add("Sfax");
        lieuarrive.getItems().add("Sidi Bouzid");
        lieuarrive.getItems().add("Siliana");
        lieuarrive.getItems().add("Sousse");
        lieuarrive.getItems().add("Tataouine");
        lieuarrive.getItems().add("Tozeur");
        lieuarrive.getItems().add("Tunis");
        lieuarrive.getItems().add("Zaghouan");

    }

    @FXML
    private void afficher(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("covoiturageAfficher.fxml"));
        Scene scene = new Scene(page_select_my);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("pageAcceuil.fxml"));
        Scene scene = new Scene(page_select_my);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

}
