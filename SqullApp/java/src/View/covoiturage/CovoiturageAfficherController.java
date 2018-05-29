/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Covoiturage;

import Entities.Covoiturage;
import Entities.User;
import JDBC.JdbcInstance;
import Services.CovoiturageService;
import Utilities.ToolsUtilities;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Moez
 */
public class CovoiturageAfficherController implements Initializable {

    private final Connection connection = JdbcInstance.getInstance();

    CovoiturageService co = new CovoiturageService();
    ObservableList<Covoiturage> ccc = FXCollections.observableArrayList(co.Recherche(User.getIdOfConnectedUser()));

    @FXML
    private TextField recherche;
    @FXML
    private TableView<Covoiturage> table;
    @FXML
    private TableColumn<Covoiturage, String> lieuarrivee;
    @FXML
    private TableColumn<Covoiturage, String> lieudedepart;
    @FXML
    private TableColumn<Covoiturage, String> dates;
    @FXML
    private TableColumn<Covoiturage, Integer> nbplace;
    @FXML
    private TableColumn<Covoiturage, Float> prix;
    @FXML
    private Button modifier;
    @FXML
    private Button suprimer;
    @FXML
    private Button retour;
    @FXML
    private ComboBox<String> rechercher;
    @FXML
    private TableColumn<Covoiturage, String> description;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rechercher.getItems().add("Lieu de départ");
        rechercher.getItems().add("Lieu d'arrive");

        //modifier.setDisable(true);
        lieudedepart.setCellValueFactory(new PropertyValueFactory<Covoiturage, String>("LIEUDEPART"));
        lieuarrivee.setCellValueFactory(new PropertyValueFactory<Covoiturage, String>("LIEUARRIVE"));
        dates.setCellValueFactory(new PropertyValueFactory<Covoiturage, String>("DATEDEPART"));
        nbplace.setCellValueFactory(new PropertyValueFactory<Covoiturage, Integer>("NBPLACE"));
        prix.setCellValueFactory(new PropertyValueFactory<Covoiturage, Float>("PRIX"));
        description.setCellValueFactory(new PropertyValueFactory<Covoiturage, String>("DESCRIPTION"));

        table.setItems(ccc);
        ini();

    }

    public void ini() {

        FilteredList<Covoiturage> filteredData = new FilteredList<>(ccc, p -> true);

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(covoiturage -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    if (rechercher.getSelectionModel().getSelectedItem().equals("Lieu de départ")) {
                        if (covoiturage.getLIEUDEPART().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                            return true; // Filter matches last name.
                        } else if (covoiturage.getLIEUARRIVE().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (covoiturage.getDATEDEPART().toString().contains(lowerCaseFilter)) {
                            return true;

                        }
                    }
                    if (rechercher.getSelectionModel().getSelectedItem().equals("Lieu d'arrive")) {
                        if (covoiturage.getLIEUDEPART().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                            return true; // Filter matches last name.
                        } else if (covoiturage.getLIEUARRIVE().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
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
        SortedList<Covoiturage> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }

    @FXML
    private void change(ActionEvent event) throws IOException {

        ((Node) event.getSource()).getScene().getWindow().hide();

        modifier.setDisable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("covoiturageModifier.fxml"));
        Parent root = (Parent) loader.load();
        CovoiturageModifierController cmc = loader.getController();
        cmc.setLabel(table.getSelectionModel().getSelectedItem().getLIEUDEPART(), table.getSelectionModel().getSelectedItem().getLIEUARRIVE(),
                String.valueOf(table.getSelectionModel().getSelectedItem().getNBPLACE()), table.getSelectionModel().getSelectedItem().getPRIX(),
                (java.sql.Date) table.getSelectionModel().getSelectedItem().getDATEDEPART(), table.getSelectionModel().getSelectedItem().getDESCRIPTION(), table.getSelectionModel().getSelectedItem().getID_PUB()
        );
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        /*Parent page_select_my = FXMLLoader.load(getClass().getResource("covoiturageModifier.fxml"));        
        Scene scene = new Scene(page_select_my);
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
         */
    }

    private void ref() {
        ccc.clear();
        ccc.addAll(co.afficherCovoiturage());
        table.setItems(ccc);

    }

    @FXML
    private void del(ActionEvent event) {
        suprimer.setDisable(false);
        CovoiturageService en = new CovoiturageService();
        int i = table.getSelectionModel().getSelectedItem().getID_PUB();

        en.deleteCovoiturage(i);
        table.getSelectionModel().clearSelection();
        table.getItems().remove(ccc);
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setTitle("INFORMATION");
        al.setHeaderText(null);
        al.setContentText("Publicatiion supprimée");
        al.showAndWait();

        ref();
        //clearfields();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("co_add.fxml"));
        Scene scene = new Scene(page_select_my);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    @FXML
    private void disable(MouseEvent event) {
        modifier.setDisable(false);
        suprimer.setDisable(false);

    }

}
