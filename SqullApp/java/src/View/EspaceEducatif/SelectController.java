/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceEducatif;

import Entities.EspaceEduc;
import Entities.User;
import EnumPack.DocumentType;
import Services.EspaceEducservice;

import Utilities.ToolsUtilities;
import View.Navigator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class SelectController implements Initializable {

    int id_currentuser = User.getIdOfConnectedUser();
    EspaceEducservice esp = new EspaceEducservice();
    ObservableList<EspaceEduc> sss = FXCollections.observableArrayList(esp.consulterespace(id_currentuser));
    AnchorPane pop = new AnchorPane();

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
    private Button refrech;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private TextField chercheField;
    static int bt;
    @FXML
    private TextField login;
    @FXML
    private TextField descri;
    @FXML
    private TextField mat;
    @FXML
    private Button download;
    @FXML
    private TextField doc;

    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bt = 1;
        // TODO
        Date.setCellValueFactory(new PropertyValueFactory<EspaceEduc, String>("datepub"));
        Description.setCellValueFactory(new PropertyValueFactory<EspaceEduc, String>("description"));
        Matiére.setCellValueFactory(new PropertyValueFactory<EspaceEduc, String>("Matiere"));
        Document.setCellValueFactory(new PropertyValueFactory<EspaceEduc, String>("document"));
        table.setItems(sss);
        // refrech.setGraphic(new ImageView("add.png"));
        initial();
    }

    @FXML
    private void fill(MouseEvent event) throws IOException {

        EspaceEduc u = table.getSelectionModel().getSelectedItem();
        login.setText("" + u.getId_usr());
        descri.setText(u.getDescription());
        mat.setText(u.getMatiere());
        File file = new File(table.getSelectionModel().getSelectedItem().getDocument());
        ToolsUtilities.downloadFromServer(DocumentType.image, file);

        doc.setText(file.getName());

        if (event.getClickCount() == 2) {
            bt = 0;
            Handlepopover(u, event.getSceneX(), event.getSceneY());
        }

    }

    private void Handlepopover(EspaceEduc u, double sceneX, double sceneY) throws IOException {
        try {
            Node container = null;
            FXMLLoader loader = new FXMLLoader();
            container = (Parent) loader.load(getClass().getResourceAsStream(Navigator.EspaceEducatif_user_crud));
            Popup_crud_containerController poop = loader.getController();
            poop.initwithdata(this, u);
            anchor1.getChildren().setAll(container);
            /* pop.setContentNode(container);
            pop.show(table.getParent(), sceneX, sceneY);*/

        } catch (IOException ex) {
            Logger.getLogger(EspaceEducatif_delete_updateController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*AnchorPane pane =FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/popup_crud_container.fxml"));
        anchor1.getChildren().setAll(pane);
         */
    }

    @FXML
    private void refrech(MouseEvent event) {
        sss.clear();
        sss.addAll(esp.consulterespace(5));
        table.setItems(sss);
    }

    private void initial() {
        FilteredList<EspaceEduc> filteredData = new FilteredList<>(sss, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        chercheField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(ev -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    if (ev.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (ev.getDocument().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                        return true; // Filter matches last name.
                    } else if (ev.getMatiere().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false; // Does not match.
                } catch (NullPointerException ex) {
                    if (ToolsUtilities.DEBUG) {
                        System.out.println("Catched from filter= " + ex.getMessage());
                    }
                    return false;
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<EspaceEduc> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    @FXML
    private void refr(ActionEvent event) throws IOException {
//        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/crud_user.fxml"));
//        anchor1.getChildren().setAll(pane);
Navigator.LoadScene("/View/EspaceEducatif/crud_user.fxml");
    }

    @FXML
    private void download(ActionEvent event) {
        // String s=table.getSelectionModel().getSelectedItem().getDocument();
        File file = new File(table.getSelectionModel().getSelectedItem().getDocument());
        ToolsUtilities.downloadFromServer(DocumentType.image, file);
        String u = "C:\\Users\\Skander\\Documents\\TMP\\" + file.getName();
        System.out.println(u);
        try {

            if ((file).exists()) {
                System.out.println("ok");
                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + u);
                p.waitFor();

            } else {

                System.out.println("File does not exist");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
