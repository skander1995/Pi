/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceFAQ;

import Entities.EspaceFAQ;
import Entities.User;
import Services.EspaceFAQservice;
import View.Navigator;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.text.NavigationFilter;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class SelectController implements Initializable {

        public int id_currentuser = User.getIdOfConnectedUser();
        EspaceFAQservice esp = new EspaceFAQservice();
        ObservableList<EspaceFAQ> sss = FXCollections.observableArrayList(esp.consulterespace(id_currentuser));
        AnchorPane pop = new AnchorPane();
        static int bt;
        @FXML
        private TableView<EspaceFAQ> table;
        @FXML
        private TableColumn<EspaceFAQ, String> Date;
        @FXML
        private TableColumn<EspaceFAQ, String> Description;
        @FXML
        private Button refrech;
        @FXML
        private AnchorPane anchor1;
        @FXML
        private TextField chercheField;

        /**
         * Initializes the controller class.
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            bt = 1;
            // TODO
            Date.setCellValueFactory(new PropertyValueFactory<EspaceFAQ, String>("datepub"));
            Description.setCellValueFactory(new PropertyValueFactory<EspaceFAQ, String>("description"));
            table.setItems(sss);
            refrech.setGraphic(new ImageView("add.png"));
            initial();
        }

        @FXML
        private void fill(MouseEvent event) throws IOException {

            EspaceFAQ u = table.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2) {
                bt = 0;
                Handlepopover(u, event.getSceneX(), event.getSceneY());
            }

        }

        private void Handlepopover(EspaceFAQ u, double sceneX, double sceneY) throws IOException {
            Node container = null; /*AnchorPane pane =FXMLLoader.load(getClass().getResource("/View/EspaceFAQ/popup_crud_container.fxml"));
            anchor1.getChildren().setAll(pane);
             */
            //                FXMLLoader loader = new FXMLLoader();
//                container = (Parent) loader.load(getClass().getResourceAsStream("/View/EspaceFAQ/crud_user.fxml"));
//                crud_containerController poop = loader.getController();
//                poop.initwithdata(this, u);
Navigator.LoadScene("/View/EspaceFAQ/crud_user.fxml");              
//  anchor1.getChildren().setAll(container);
/* pop.setContentNode(container);
pop.show(table.getParent(), sceneX, sceneY);*/
        }

        @FXML
        private void refrech(MouseEvent event) {
            sss.clear();
            sss.addAll(esp.consulterespace(5));
            table.setItems(sss);
        }

        private void initial() {
            FilteredList<EspaceFAQ> filteredData = new FilteredList<>(sss, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            chercheField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(ev -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();
                    return false;

                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<EspaceFAQ> sortedData = new SortedList<>(filteredData);
            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            // 5. Add sorted (and filtered) data to the table.
            table.setItems(sortedData);
        }

        @FXML
        private void refr(ActionEvent event) throws IOException {
            Navigator.LoadScene("/View/EspaceFAQ/crud_user.fxml");
//            AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceFAQ/crud_user.fxml"));
//            anchor1.getChildren().setAll(pane);

        }

    }
