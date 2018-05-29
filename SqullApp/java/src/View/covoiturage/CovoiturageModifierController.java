/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Covoiturage;

import Entities.Covoiturage;
import Services.CovoiturageService;
import Utilities.ToolsUtilities;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
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
public class CovoiturageModifierController implements Initializable {

    @FXML
    private ComboBox<String> lieudepart;
    @FXML
    private ComboBox<String> lieuarrive;
    @FXML
    private TextField nbplace;
    @FXML
    private TextField prix;
    @FXML
    private Button modifier;
    @FXML
    private DatePicker date;
    private int id_pub;
    @FXML
    private Label u;
    @FXML
    private Button Retour;
    @FXML
    private Label controleSaisielieu;
    @FXML
    private Label controlSaisieDate;
    @FXML
    private Label controleSsaisieNbplace;
    @FXML
    private TextArea Description;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void setLabel(String depart, String arrive, String nombre, float prix, Date date, String description ,int id) {
        
        
        lieudepart.setValue(depart);
        lieuarrive.setValue(arrive);
        nbplace.setText(nombre);
        this.prix.setText(String.valueOf(prix));
        this.date.setValue(date.toLocalDate());
        Description.setText(description);

        id_pub = id;
    }

    @FXML
    private void modifier(ActionEvent event) {
        controlSaisieDate.setText("");
        controleSsaisieNbplace.setText("");
        controleSaisielieu.setText("");
        CovoiturageService co = new CovoiturageService();
        if (lieudepart.getValue().isEmpty() || lieuarrive.getValue().isEmpty() || prix.getText().isEmpty() || nbplace.getText().isEmpty() || date.getValue() == null|| Description.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);
            al.setContentText("veuillez remplir tous les champs");
            al.showAndWait();

        } else {
           try {
                //Date datex = Date.valueOf(date.getValue());
                java.util.Date datex = ToolsUtilities.formater.parse(date.getValue().toString());

                
                if (lieuarrive.getItems().get(lieuarrive.getSelectionModel().getSelectedIndex()).equals(lieudepart.getItems().get(lieudepart.getSelectionModel().getSelectedIndex()))) {
                        controleSaisielieu.setText("Vous devez selectionne deux lieu different");

                    }else{
                if (datex.compareTo(ToolsUtilities.formater.parse(ToolsUtilities.formater.format(new java.util.Date()))) < 0) {
                    controlSaisieDate.setText("La date doit Etre superieure ou égale à celle d'aujourd'hui");
                }  else{
                {
                    if (Integer.parseInt(nbplace.getText()) > 4) {
                        controleSsaisieNbplace.setText("Le nombre de place doit etre inféerier a 5");
                    }
                     else {
            Covoiturage es = new Covoiturage(id_pub, lieudepart.getValue(), lieuarrive.getValue(), datex, Float.parseFloat(prix.getText()), Integer.parseInt(nbplace.getText()), Description.getText());
            System.out.println(es.toString());
            co.editCovoiturage(es);
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("INFORMATION");
            al.setHeaderText(null);
            al.setContentText("Mise à jour effectuée");
            al.showAndWait();
            
            }
        }
                }
                }
    } catch (ParseException ex) {
                Logger.getLogger(CovoiturageAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
         Parent page_select_my = FXMLLoader.load(getClass().getResource("covoiturageAfficher.fxml"));        
        Scene scene = new Scene(page_select_my);
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    

}
