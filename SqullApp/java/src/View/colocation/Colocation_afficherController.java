/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.colocation;

import Entities.Colocation;
import JDBC.JdbcInstance;
import Services.ColocationService;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author ASUS I7
 */
public class Colocation_afficherController implements Initializable {
    private final Connection connection = JdbcInstance.getInstance();

       ColocationService co=new ColocationService();
       
       ObservableList<Colocation> ccc=FXCollections.observableArrayList(co.afficherColocation());
    
    ColocationService csr;
     @FXML
    private ComboBox<String> lieuc;
    @FXML
    private TableColumn<Colocation, String> titre;
    @FXML
    private TableColumn<Colocation, String> datepublication;
    @FXML
    private TableColumn<Colocation, String> datedisponibilite;
    @FXML
    private TableColumn<Colocation, String> lieu;
    @FXML
    private TableColumn<Colocation, Float> loyer;
    @FXML
    private TableColumn<Colocation, String> description;
    @FXML
    private Button chercher;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private TableView<Colocation> table;
    @FXML
    private Button retour;
    
    static Colocation coli ;
    @FXML
    private AnchorPane anchor1;
    
    PopOver popOver= new PopOver();
    @FXML
    private Button stat;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titre.setCellValueFactory(new PropertyValueFactory<Colocation,String>("TITRE"));
       datepublication.setCellValueFactory(new PropertyValueFactory<Colocation,String>("DATEPUB"));
       datedisponibilite.setCellValueFactory(new PropertyValueFactory<Colocation,String>("DATEDISPONIBILITE"));
       lieu.setCellValueFactory(new PropertyValueFactory<Colocation,String>("LIEU"));
       description.setCellValueFactory(new PropertyValueFactory<Colocation,String>("DESCRIPTION"));
       loyer.setCellValueFactory(new PropertyValueFactory<Colocation,Float>("LOYERMENSUEL")); 
        
                  table.setItems(ccc);
                  ini();                  
        lieuc.getItems().add("Ariana");
        lieuc.getItems().add("Tunis");
        lieuc.getItems().add("Nasr");
        lieuc.getItems().add("Ariana soghra");
        lieuc.getItems().add("Chotrana");
        lieuc.getItems().add("Nour Jaafer");
        lieuc.getItems().add("Raoued");
        lieuc.getItems().add("Soukra");
        lieuc.getItems().add("Ghazela");
        lieuc.getItems().add("Tout afficher");
    }
      
       public void ini (){
        
        FilteredList<Colocation> filteredData = new FilteredList<>(ccc, p -> true);

        chercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(colocation -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    
                     if (colocation.getLIEU().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                        return true; // Filter matches last name.
                    
                    
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
        SortedList<Colocation> sortedData = new SortedList<>(filteredData);
        
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        
        table.setItems(sortedData);
    }

    @FXML
    private void chercher(ActionEvent event) {
        FilteredList<Colocation> filteredData = new FilteredList <> (ccc,p->true);
        filteredData.setPredicate(col->{
            if(col.getLIEU().contains(lieuc.getSelectionModel().getSelectedItem()))
                return(true);
            else {
               if(lieuc.getSelectionModel().getSelectedItem().equals("Tout afficher")){
                   return true;
               }
            }
         return(false);   
        });
        SortedList<Colocation> sortedData = new SortedList<>(filteredData);
        
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        
        table.setItems(sortedData);
    }

    @FXML
    private void changer(ActionEvent event)  {
        
        try {
            Colocation_modifierController.d= table.getSelectionModel().getSelectedItem().getDESCRIPTION();
            Colocation_modifierController.l= table.getSelectionModel().getSelectedItem().getLIEU();
            Colocation_modifierController.p= table.getSelectionModel().getSelectedItem().getLOYERMENSUEL();
            Colocation_modifierController.t= table.getSelectionModel().getSelectedItem().getTITRE();
            Colocation_modifierController.dd= table.getSelectionModel().getSelectedItem().getDATEDISPONIBILITE();
            
           Parent page_select_my = FXMLLoader.load(getClass().getResource("Colocation_modifier.fxml"));        
        Scene scene = new Scene(page_select_my);
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
        } catch (IOException ex) {
           System.out.println(ex.toString());
        }
        
        
      
        
       
    }
    private void ref() {
        ccc.clear();
        ccc.addAll(co.afficherColocation());
        table.setItems(ccc);
        
    }


    @FXML
    private void del(ActionEvent event)  {
            supprimer.setDisable(false);
         ColocationService en = new ColocationService();
         Colocation c =table.getSelectionModel().getSelectedItem();
      
            en.supprimerColocation(c);
            table.getSelectionModel().clearSelection();
            table.getItems().remove(ccc);
            Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("INFORMATION");
            al.setHeaderText(null);
            al.setContentText("Publicatiion supprimée");
            al.showAndWait();
            
            
            ref();
    }
    @FXML
    private void go(MouseEvent event) 
        {
           if (event.getClickCount() == 2) {
            try {
                // load fxml inside
                Node content = null;

                FXMLLoader fxmlLoader = new FXMLLoader();
                System.out.println("nnnnnnnnnnn");
                content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/colocation/ConsultationColocation.fxml"));
                System.out.println("aaaaaa");
                //PopOverContentController popOverController = new PopOverContentController();
                ConsultationColocationController popOverController = new ConsultationColocationController();
                popOverController = fxmlLoader.getController();
                 popOverController.c = table.getSelectionModel().getSelectedItem();
                 popOverController.start();
                popOver.setContentNode(content);
                popOver.show(table.getParent(), table.getScene().getX(), table.getScene().getY());
            } catch (IOException ex) {
                ex.printStackTrace();
                if (ToolsUtilities.DEBUG) {
                    System.out.println("Catched = from handleEditwith popover" + ex.getMessage());
                }
            }

        }
            
        }
    
    private void disable(MouseEvent event) {
        modifier.setDisable(false);
        supprimer.setDisable(false);

    }
        
    @FXML
    private void Retourner(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("/View/colocation/AccueilColocation.fxml"));        
        Scene scene = new Scene(page_select_my);
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    @FXML
    private void afficheStat(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("/View/colocation/Stat.fxml"));        
        Scene scene = new Scene(page_select_my);
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
          }    

 
    

