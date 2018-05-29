/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Utilities.ToolsUtilities;
import View.customComponent.SideBar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JPopupMenu;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class MainInterfaceController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;
    private AnchorPane AnchorContainer = new AnchorPane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // TODO

            // create SideBar actioner
            Separator separator = new Separator();

            // load SLIDE BAR and initialize depend on clicked button
            FXMLLoader loader = new FXMLLoader();
            Pane sideBarPane = (Pane) loader.load(getClass().getResourceAsStream("/View/TopBar.fxml"));
            //SideBar sidebar = new SideBar(190, sideBarPane);
            SideBar sidebar = new SideBar(80, sideBarPane, separator);

            //mainBorderPane = new BorderPane();
            Pane mainPane = VBoxBuilder.create().spacing(3)
                    .children(
                            separator,
                            AnchorContainer
                    ).build();
            mainBorderPane.setTop(sidebar);
            mainBorderPane.setCenter(mainPane);
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched from main InterfaceController= " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void setAPance(Node a) {
        //fix the fit problem
        System.out.println("setting this pance");
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        //AnchorContainer = new AnchorPane(a);
        AnchorContainer.getChildren().setAll(a);
        AnchorContainer.autosize();
        AnchorContainer.getStylesheets().add(getClass().getResource("Assets/css/application.css").toExternalForm());

    }

    public void LoadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle(title);

            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from loadWindow main interface" + ex.getMessage());
            }
        }
    }

}
