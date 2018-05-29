/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import Entities.Evenement;
import Services.EvenementService;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author SELLAMI
 */
public class PopupAdminController implements Initializable {

    private int i;
    @FXML
    private ComboBox<String> combo;
    @FXML
    private Button Valider;
    private int id ;
    @FXML
    private Button annuler;
    @FXML
    private Label lbl;
    @FXML
    private Label lblechec;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combo.getItems().add("dispo");
        combo.getItems().add("hidden");
        combo.getItems().add("fhfahg");
    }    
   
    void initwithdata (AdminEventsController caller,Evenement e){
         id =e.getId();
         combo.getSelectionModel().select(e.getEtat());
        
        
    }

    @FXML
    private void valider(ActionEvent event) {
        EvenementService es = new EvenementService();
        boolean x=es.changerEtat(id, combo.getValue());
        if(x==true){
        lbl.setText("Modifier avec succée");
        }
        else
        lblechec.setText("Echec de modification");
        
        }
}
