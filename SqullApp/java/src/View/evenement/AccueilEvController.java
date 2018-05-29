/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import View.Navigator;
import View.colocation.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS I7
 */
public class AccueilEvController implements Initializable {

    @FXML
    private Button AfficherC;
    @FXML
    private Button ajouterC;
    @FXML
    private Button retour;
    @FXML
    private Button Consmes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void afficherC(ActionEvent event) throws IOException {
        Navigator.LoadScene(Navigator.AfficherEvenements);
       
    }

    @FXML
    private void addCo(ActionEvent event) throws IOException {
     Navigator.LoadScene(Navigator.AjouterEvenement);
    }

    @FXML
    private void Retounrer(ActionEvent event) {
         Navigator.LoadScene(Navigator.accueil);
    }

    @FXML
    private void ConsMes(ActionEvent event) {
        Navigator.LoadScene(Navigator.AfficherMesEvenements);
    }
    
}
