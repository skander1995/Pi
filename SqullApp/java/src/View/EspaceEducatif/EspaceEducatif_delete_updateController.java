/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceEducatif;

import Entities.EspaceEduc;
import Entities.User;
import Services.EspaceEducservice;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author Skander
 */

public class EspaceEducatif_delete_updateController implements Initializable {
    public int id_currentuser=User.getIdOfConnectedUser();
    EspaceEducservice esp = new EspaceEducservice();
    ObservableList<EspaceEduc> sss = FXCollections.observableArrayList(esp.consulterespace());

    @FXML
    private TableView<EspaceEduc> table;
    @FXML
    private TableColumn<EspaceEduc, String> Date;
    @FXML
    private TableColumn<EspaceEduc, String> Matiére;
    @FXML
    private TableColumn<EspaceEduc, String> Description;
    @FXML
    private TableColumn<EspaceEduc, String> Document;

    @FXML
    private Button add;
    @FXML
    private TextField description;
    
    @FXML
    private ComboBox<String> section;
    @FXML
    private TextField document;
    @FXML
    private Button browse;
    @FXML
    private TextField matiére;
    @FXML
    private Button consulter;
    @FXML
    private Button supp;
    //private TableColumn<?, ?> type_aide;

    PopOver pop = new PopOver();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       section.getItems().add("1ére");
        section.getItems().add("2éme");
        section.getItems().add("3éme");
        section.getItems().add("4éme");
        section.getItems().add("5éme");
        // TODO
        Date.setCellValueFactory(new PropertyValueFactory<EspaceEduc, String>("datepub"));
        Description.setCellValueFactory(new PropertyValueFactory<EspaceEduc, String>("description"));
        Matiére.setCellValueFactory(new PropertyValueFactory<EspaceEduc, String>("Matiere"));
        Document.setCellValueFactory(new PropertyValueFactory<EspaceEduc, String>("document"));

        table.setItems(sss);
    }

    @FXML
    private void addE(ActionEvent event) {
        EspaceEducservice en = new EspaceEducservice();
        if (description.getText().isEmpty()  || section.getValue().isEmpty() || matiére.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);
            al.setContentText("veuillez remplir tous les champs");
            al.showAndWait();

        } else {
            EspaceEduc es = new EspaceEduc(description.getText(), section.getValue(), matiére.getText(), document.getText());
            en.Createpub(es,id_currentuser);
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("INFORMATION");
            al.setHeaderText(null);
            al.setContentText("ok");
            al.showAndWait();
            ref();
            clearfields();
        }
    }

    @FXML
    private void Browse(ActionEvent event) {
        JFileChooser file = new JFileChooser();
        file.showOpenDialog(null);
        File f = file.getSelectedFile();
        document.setText(f.getAbsolutePath());
    }

    @FXML
    private void update(ActionEvent event) {
        EspaceEducservice en = new EspaceEducservice();
        if (description.getText().isEmpty() || section.getValue().isEmpty() || matiére.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);
            al.setContentText("veuillez remplir tous les champs");
            al.showAndWait();

        } else {
            int i = table.getSelectionModel().getSelectedItem().getId_pub();
            EspaceEduc es = new EspaceEduc(description.getText(), section.getValue(), matiére.getText(), document.getText());
            en.updatepub(es, i);
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("INFORMATION");
            al.setHeaderText(null);
            al.setContentText("Mise à jour effectuée");
            al.showAndWait();
            ref();
            clearfields();

        }
    }

    @FXML
    private void del(ActionEvent event) {
        EspaceEducservice en = new EspaceEducservice();
        int i = table.getSelectionModel().getSelectedItem().getId_pub();

        en.deletepub(i);
        Notifications nf = Notifications.create().title("Update")
                .text("Publication supprimée")
                .graphic(new ImageView("ok.png"))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BASELINE_RIGHT).darkStyle();
        nf.show();
        clearfields();

    }

    private void clearfields() {

        description.clear();
       
        section.getItems().removeAll(section.getItems());
        section.getItems().addAll("1ére", "2éme", "3éme", "4éme", "5éme");
        document.clear();
        matiére.clear();
    }

    private void ref() {
        sss.clear();
        sss.addAll(esp.consulterespace());
        table.setItems(sss);

    }

    @FXML
    private void fill(MouseEvent event) {

        description.setText(table.getSelectionModel().getSelectedItem().getDescription());
        section.getSelectionModel().select(table.getSelectionModel().getSelectedItem().getSection());
        document.setText(table.getSelectionModel().getSelectedItem().getDocument());
        matiére.setText(table.getSelectionModel().getSelectedItem().getMatiere());
        EspaceEduc u = table.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {

            Handlepopover(u, event.getSceneX(), event.getSceneY());
        }

    }

    private void Handlepopover(EspaceEduc u, double sceneX, double sceneY) {
        try {
            Node container = null;
            FXMLLoader loader = new FXMLLoader();
            container = (Parent) loader.load(getClass().getResourceAsStream("/View/EspaceEducatif/popup_crud_container.fxml"));
            Popup_crud_containerController poop = loader.getController();
            // poop.initwithdata(this, u);
            pop.setContentNode(container);
            pop.show(table.getParent(), sceneX, sceneY);

        } catch (IOException ex) {
            Logger.getLogger(EspaceEducatif_delete_updateController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
