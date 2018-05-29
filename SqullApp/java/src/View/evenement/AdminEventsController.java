/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import Entities.Evenement;
import Services.EvenementService;
import Utilities.ToolsUtilities;
import View.Navigator;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author SELLAMI
 */
public class AdminEventsController implements Initializable {

    @FXML
    private TableView<Evenement> table;
    @FXML
    private TableColumn<Evenement, String> nom;
    @FXML
    private TableColumn<Evenement, String> desc;
    @FXML
    private TableColumn<Evenement, String> lieu;
    @FXML
    private TableColumn<Evenement, Date> dated;
    @FXML
    private TableColumn<Evenement, Date> datef;
    @FXML
    private TableColumn<Evenement, String> Etat;
    @FXML
    private Button supprimer;

    private EvenementService es = new EvenementService();
    private ObservableList<Evenement> events = FXCollections.observableArrayList(es.afficherEvenementsAdmin());
    @FXML
    private TextField chercheField;
    ComboBox combo = new ComboBox();

    PopOver pop = new PopOver();
    @FXML
    private Button rafrichir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //es=  new EvenementService();
        nom.setCellValueFactory(new PropertyValueFactory<Evenement, String>("nom"));
        desc.setCellValueFactory(new PropertyValueFactory<Evenement, String>("description"));
        lieu.setCellValueFactory(new PropertyValueFactory<Evenement, String>("lieu"));
        dated.setCellValueFactory(new PropertyValueFactory<Evenement, Date>("date_debut"));
        datef.setCellValueFactory(new PropertyValueFactory<Evenement, Date>("date_fin"));
        Etat.setCellValueFactory(new PropertyValueFactory<Evenement, String>("etat"));

        table.setItems(events);
        /*for(int i=0 ;i<table.getItems().size() ;i++){
       if(table.getItems().get(i).getEtat().equals("masqué")) 
           table.set
       }*/
        initial();
    }

    private void ref() {
        events.clear();
        events.addAll(es.afficherEvenementsAdmin());
        table.setItems(events);

    }

    @FXML
    private void del(ActionEvent event) {
        EvenementService en = new EvenementService();
        int i = table.getSelectionModel().getSelectedItem().getId();

        en.supprimerEvenementById(i);
        table.getSelectionModel().clearSelection();
        table.getItems().remove(events);
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setTitle("INFORMATION");
        al.setHeaderText(null);
        al.setContentText("Publicatiion supprimée");
        al.showAndWait();

        ref();
        //clearfields();
    }

    private void initial() {
        FilteredList<Evenement> filteredData = new FilteredList<>(events, p -> true);

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
                    if (ev.getNom().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (ev.getAffiche().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                        return true; // Filter matches last name.
                    } else if (ev.getLieu().toLowerCase().contains(lowerCaseFilter)) {
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
        SortedList<Evenement> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    @FXML
    private void popUp(MouseEvent event) {

        if (event.getClickCount() == 2) {
            AdminEventControleController.idEvt = table.getSelectionModel().getSelectedItem().getId();
            AdminEventControleController.idUser = table.getSelectionModel().getSelectedItem().getUserId();
            AdminEventControleController.etatt = table.getSelectionModel().getSelectedItem().getEtat();
            AdminEventControleController.ev = table.getSelectionModel().getSelectedItem();

            Navigator.LoadScene("/View/AdminEventControle.fxml");
            /*Evenement e=table.getSelectionModel().getSelectedItem();
            Handlepopover(e, event.getSceneX(), event.getSceneY());*/

        }
    }

    private void Handlepopover(Evenement e, double sceneX, double sceneY) {

        try {
            // load fxml inside
            Node content = null;

            FXMLLoader fxmlLoader = new FXMLLoader();
            content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/popupAdmin.fxml"));

            //PopOverContentController popOverController = new PopOverContentController();
            PopupAdminController popOverController = fxmlLoader.getController();
            popOverController.initwithdata(this, e);
            pop.setContentNode(content);
            pop.show(table.getParent(), sceneX, sceneY);
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from handleEditwith popover" + ex.getMessage());
            }
        }
        /*Node container = null;
        FXMLLoader loader = new FXMLLoader();
         container = (Parent) loader.load(getClass().getResourceAsStream("/View/popupAdmin.fxml"));
         PopupAdminController poop = loader.getController();
     poop.initwithdata(this, e);
     pop.getContentNode();
     pop.show(table.getParent(),sceneX,sceneY);
     } catch (IOException ex) {
         Logger.getLogger(AdminEventsController.class.getName()).log(Level.SEVERE, null, ex);
     }
         */
    }

    @FXML
    private void refresh(ActionEvent event) {
        ref();
    }

}
