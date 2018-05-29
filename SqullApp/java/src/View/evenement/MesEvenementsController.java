/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import Entities.Evenement;
import Entities.User;
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
public class MesEvenementsController implements Initializable {

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
    private TableColumn<Evenement, String> nb;
    @FXML
    private Button supprimer;

    private EvenementService es = new EvenementService();
    ;
    final ObservableList<Evenement> events = FXCollections.observableArrayList(es.afficherEvenementsById(User.getIdOfConnectedUser()));
    @FXML
    private Button modifier;
    @FXML
    private Button ajouter;
    @FXML
    private TextField chercheField;
    PopOver popover = new PopOver();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nom.setCellValueFactory(new PropertyValueFactory<Evenement, String>("nom"));
        desc.setCellValueFactory(new PropertyValueFactory<Evenement, String>("description"));
        lieu.setCellValueFactory(new PropertyValueFactory<Evenement, String>("lieu"));
        dated.setCellValueFactory(new PropertyValueFactory<Evenement, Date>("date_debut"));
        datef.setCellValueFactory(new PropertyValueFactory<Evenement, Date>("date_fin"));
        nb.setCellValueFactory(new PropertyValueFactory<Evenement, String>("places_dispo"));

        table.setItems(events);
        initial();

    }

    private void ref() {
        events.clear();
        events.addAll(es.afficherEvenementsById(User.getIdOfConnectedUser()));
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

    @FXML
    private void edit(ActionEvent event) {
        ModifierEventController.idEvt = table.getSelectionModel().getSelectedItem().getId();
        ModifierEventController.descrip = table.getSelectionModel().getSelectedItem().getDescription();
        ModifierEventController.nbP = table.getSelectionModel().getSelectedItem().getPlaces_dispo();
        ModifierEventController.name = table.getSelectionModel().getSelectedItem().getNom();
        ModifierEventController.place = table.getSelectionModel().getSelectedItem().getLieu();
        ModifierEventController.dd = table.getSelectionModel().getSelectedItem().getDate_debut();
        ModifierEventController.df = table.getSelectionModel().getSelectedItem().getDate_fin();
        ModifierEventController.affiche = table.getSelectionModel().getSelectedItem().getAffiche();

        Navigator.LoadScene(Navigator.ModifierEvent);

    }

    @FXML
    private void ajouter(ActionEvent event) {
        Navigator.LoadScene(Navigator.AjouterEvenement);
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
                    } else if (ev.getDescription().toLowerCase().contains(lowerCaseFilter)) {
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
            try {
                // load fxml inside
                Node content = null;

                FXMLLoader fxmlLoader = new FXMLLoader();
                System.out.println("nnnnnnnnnnn");
                content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/ConsultMyEvent.fxml"));
                System.out.println("aaaaaa");
                //PopOverContentController popOverController = new PopOverContentController();
                ConsultMyEventController popOverController = new ConsultMyEventController();
                popOverController = fxmlLoader.getController();
                popOverController.ev = table.getSelectionModel().getSelectedItem();
                popOverController.start();
                popover.setContentNode(content);
                popover.show(table.getParent(), table.getScene().getX(), table.getScene().getY());
            } catch (IOException ex) {
                ex.printStackTrace();
                if (ToolsUtilities.DEBUG) {
                    System.out.println("Catched = from handleEditwith popover" + ex.getMessage());
                }
            }

        }
    }
}
