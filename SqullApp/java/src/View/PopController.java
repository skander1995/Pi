/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.Navigator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class PopController implements Initializable {

    @FXML
    private AnchorPane pop;
    @FXML
    private Button coloc;
    @FXML
    private Button covo;
    @FXML
    private Button even;
    @FXML
    private Button espedu;
    @FXML
    private Button espfaq;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void gocoloc(ActionEvent event) {
        Navigator.LoadScene(Navigator.ConsulterColo);
    }

    @FXML
    private void gocovo(ActionEvent event) {
        Navigator.LoadScene(Navigator.accCovoiturage);
    }

    @FXML
    private void goeven(ActionEvent event) {
        Navigator.LoadScene(Navigator.AccEvenements);
    }

    @FXML
    private void goesped(ActionEvent event) {
        Navigator.LoadScene(Navigator.EspaceEducatif_main);
    }

    @FXML
    private void goespfaq(ActionEvent event) {
        Navigator.LoadScene(Navigator.EspaceFAQ_main);
    }

}
