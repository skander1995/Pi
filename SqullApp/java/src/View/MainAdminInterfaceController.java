/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXHamburger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class MainAdminInterfaceController implements Initializable {

    @FXML
    private AnchorPane child;
    @FXML
    private AnchorPane navList;
    @FXML
    private JFXHamburger menuBurger;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        prepareSlideMenuAnimation();
    }

    public void setAPance(Node a) {
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        child.getChildren().setAll(a);
        child.getStylesheets().add(getClass().getResource("Assets/css/application.css").toExternalForm());
    }

    public void LoadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareSlideMenuAnimation() {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);

        menuBurger.setOnMouseClicked((event) -> {
            if (navList.getTranslateX() != 0) {
                openNav.play();
            } else {
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
    }

    @FXML
    private void disconnect(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane mpane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.authentification));
            Stage stage1 = null;
            stage1 = (Stage) menuBurger.getScene().getWindow();
            stage1.hide();
            stage1.setScene(new Scene(mpane));
            stage1.show();
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

    @FXML
    private void fireUserAdminUi(ActionEvent event) {
        Navigator.LoadScene(Navigator.UserAdminPanel);
    }

    @FXML
    private void fireUserAdminChart(ActionEvent event) {
        Navigator.LoadScene(Navigator.UserAdminChart);
    }

    @FXML
    private void fireReclamationPane(ActionEvent event) {
        Navigator.LoadScene(Navigator.ReclamationAdminPanel);
    }

}
