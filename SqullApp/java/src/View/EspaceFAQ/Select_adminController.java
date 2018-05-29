
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceFAQ;

import Entities.EspaceFAQ;
import Services.EspaceFAQservice;

import Utilities.ToolsUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class Select_adminController implements Initializable {
 EspaceFAQservice esp = new EspaceFAQservice();
 ObservableList<EspaceFAQ> sss = FXCollections.observableArrayList(esp.consulterespace());
    AnchorPane pop = new AnchorPane();
    
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
       
        // TODO
        Date.setCellValueFactory(new PropertyValueFactory<EspaceFAQ, String>("datepub"));
        Description.setCellValueFactory(new PropertyValueFactory<EspaceFAQ, String>("description"));
        table.setItems(sss);
        refrech.setGraphic(new ImageView("rest.png"));
        initial();
    }    
    
@FXML
    private void fill(MouseEvent event) throws IOException {
        
         EspaceFAQ u = table.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {
            
            Handlepopover(u, event.getSceneX(), event.getSceneY());
        }
        
    }
    
    private void Handlepopover(EspaceFAQ u, double sceneX, double sceneY) throws IOException {
        try {
            Node container = null;
            FXMLLoader loader = new FXMLLoader();
            container = (Parent) loader.load(getClass().getResourceAsStream("/View/EspaceFAQ/crud_admin.fxml"));
            crudadmin_Controller poop = loader.getController();
            poop.initwithdata(this, u);
            anchor1.getChildren().setAll(container);
           /* pop.setContentNode(container);
            pop.show(table.getParent(), sceneX, sceneY);*/
            
        } catch (IOException ex) {
            System.out.println("handlepopover coté admin");
        }
        /*AnchorPane pane =FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/popup_crud_container.fxml"));
        anchor1.getChildren().setAll(pane);
        */
    }

    @FXML
    private void refrech(MouseEvent event) {
        sss.clear();
        sss.addAll(esp.consulterespace());
        table.setItems(sss);
    }
     private void initial (){
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
                try {
                    if (ev.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
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
        SortedList<EspaceFAQ> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }
    
}
