/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Covoiturage;

import Entities.Covoiturage;
import EnumPack.MailType;
import JDBC.JdbcInstance;
import Services.CovoiturageService;
import Utilities.MailSender;
import Utilities.ToolsUtilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Moez
 */
public class AfficherTousCovoiturageController implements Initializable {
    //private final Connection connection = JdbcInstance.getInstance();

       CovoiturageService co=new CovoiturageService();
       ObservableList<Covoiturage> ccc=FXCollections.observableArrayList(co.afficherCovoiturage());
   /* private ComboBox<String> lieudepart;
    private ComboBox<String> lieuarrive;*/
    @FXML
    private TextField recherche;
    @FXML
    private TableView<Covoiturage> table;
    @FXML
    private TableColumn<Covoiturage,String> lieuarrivee;
    @FXML
    private TableColumn<Covoiturage,String> lieudedepart;
    @FXML
    private TableColumn<Covoiturage,String> dates;
    @FXML
    private TableColumn<Covoiturage,Integer> nbplace;
    @FXML
    private TableColumn<Covoiturage,Float> prix;
    @FXML
    private Button sms;
    @FXML
    private Button mail;
    @FXML
    private Button retour;
    @FXML
    private Button map;
    @FXML
    private ComboBox<String> rechercher;
    @FXML
    private TableColumn<Covoiturage,String> description;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         rechercher.getItems().add("Lieu de départ");
         rechercher.getItems().add("Lieu d'arrive");
                
                
        lieudedepart.setCellValueFactory(new PropertyValueFactory<Covoiturage,String>("LIEUDEPART"));
        lieuarrivee.setCellValueFactory(new PropertyValueFactory<Covoiturage,String>("LIEUARRIVE"));
        dates.setCellValueFactory(new PropertyValueFactory<Covoiturage,String>("DATEDEPART"));
        nbplace.setCellValueFactory(new PropertyValueFactory<Covoiturage,Integer>("NBPLACE"));
        prix.setCellValueFactory(new PropertyValueFactory<Covoiturage,Float>("PRIX")); 
        description.setCellValueFactory(new PropertyValueFactory<Covoiturage,String>("DESCRIPTION")); 

        
                  table.setItems(ccc);
                  ini();       
    }
        @FXML

    private void retour(ActionEvent event) throws IOException {
         Parent page_select_my = FXMLLoader.load(getClass().getResource("pageAcceuil.fxml"));        
        Scene scene = new Scene(page_select_my);
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    public void ini (){
        
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
                    if(rechercher.getSelectionModel().getSelectedItem().equals("Lieu de départ")){
                     if (covoiturage.getLIEUDEPART().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                        return true; // Filter matches last name.
                    } else if (covoiturage.getLIEUDEPART().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (covoiturage.getDATEDEPART().toString().contains(lowerCaseFilter)) {
                        return true;
                    
                    }}
                     if(rechercher.getSelectionModel().getSelectedItem().equals("Lieu d'arrive")){
                     if (covoiturage.getLIEUARRIVE().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                        return true; // Filter matches last name.
                    } else if (covoiturage.getLIEUARRIVE().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }}
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
    private void map(ActionEvent event) throws IOException {
        MapController.c = table.getSelectionModel().getSelectedItem();
        Parent page_select_my = FXMLLoader.load(getClass().getResource("map.fxml"));        
        Scene scene = new Scene(page_select_my);
        
        
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
        
    }

    @FXML
    private void envoieMail(ActionEvent event) throws IOException {
        MapController.c = table.getSelectionModel().getSelectedItem();
        Parent page_select_my = FXMLLoader.load(getClass().getResource("sendMail.fxml"));        
        Scene scene = new Scene(page_select_my);
        
        
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
        
        
    }

    @FXML
    private void sms(ActionEvent event) throws IOException {
        MapController.c = table.getSelectionModel().getSelectedItem();
       try {
			// Construct data
			String apiKey = "apikey=b8uiGCGKcgM-bMnTaWgjmx4DFtQR5rjAJQ1KulvqiI" ;
			String message = "&message=Vous covoiturage a ètè enregistrer avec succées" ;
			String sender = "&sender=CovoiturageTunisie" ;
                        String numbers = "&numbers=+21655254611";
                	
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
                        System.out.println("envoyé");
			
			//return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			//return "Error "+e;
		}
        
        
    }
    
        
    
}
