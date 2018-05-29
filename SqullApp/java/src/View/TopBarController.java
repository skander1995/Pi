/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import Services.UserService;
import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class TopBarController implements Initializable {

    @FXML
    private JFXButton chat;
    @FXML
    private ImageView acceuilButton;
    @FXML
    private JFXButton publicationButton;
    @FXML
    private JFXButton profileButton;
    @FXML
    private JFXButton faqButton;
    @FXML
    private JFXButton contactButton;
    @FXML
    private JFXButton logoutButton;

    PopOver popOver = new PopOver();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void loadAcceuil(ActionEvent event) {
        Navigator.LoadScene(Navigator.accueil);
    }

    @FXML
    private void loadPublier(ActionEvent event) {
        // pop up publication chooser
    }

    @FXML
    private void loadProfil(ActionEvent event) {
        //Navigator.LoadScene(Navigator.ProfilUser);
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.ProfilUser));
            ProfileUserController mainController = loader.getController();
            mainController.initInterfaceWithConnectedUser();
            Navigator.LoadScene(mainPane);
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void loadFaq(ActionEvent event) {

    }

    @FXML
    private void loadContact(ActionEvent event) {
    }

    @FXML
    private void exitSoftware(ActionEvent event) {
        try {
            // disconnect
            FXMLLoader loader = new FXMLLoader();
            Pane mpane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.authentification));
            Stage stage1 = null;
            stage1 = (Stage) logoutButton.getScene().getWindow();
            stage1.hide();
            stage1.setScene(new Scene(mpane));
            stage1.show();
        } catch (IOException ex) {
            Logger.getLogger(TopBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadFaq(MouseEvent event) {
        try {
            // load fxml inside
            Node content = null;

            FXMLLoader fxmlLoader = new FXMLLoader();
            content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/pop.fxml"));
            popOver.headerAlwaysVisibleProperty().setValue(Boolean.TRUE);
            popOver.consumeAutoHidingEventsProperty().setValue(Boolean.FALSE);
            //PopOverContentController popOverController = new PopOverContentController();
            PopController popOverController = fxmlLoader.getController();
            popOver.setContentNode(content);
            popOver.show(faqButton.getParent(), event.getSceneX(), event.getSceneY());
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from handleEditwith popover" + ex.getMessage());
            }
        }
    }

}
