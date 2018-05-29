/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.colocation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS I7
 */
public class Colocation_modifierController implements Initializable {

    @FXML
    private AnchorPane annulerA;
    @FXML
    private TextField titre;
    private TextField Lieu;
    @FXML
    private TextField prix;
    @FXML
    private TextArea description;
    @FXML
    private DatePicker Date_Disponibilite;
    @FXML
    private Button modifier;
    @FXML
    private Button annuler;
    public static String t;
     public static float p;
      public static String d;
       public static Date dd;
        public static String l;
    @FXML
    private ComboBox<String> lieuC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lieuC.getItems().add("Ariana");
        lieuC.getItems().add("Tunis");
        lieuC.getItems().add("Nasr");
        lieuC.getItems().add("Ariana soghra");
        lieuC.getItems().add("Chotrana");
        lieuC.getItems().add("Nour Jaafer");
        lieuC.getItems().add("Raoued");
        lieuC.getItems().add("Soukra");
        lieuC.getItems().add("Ghazela");
        description.setText(d);
        titre.setText(t);
        prix.setText(Float.toString(p));
        Date_Disponibilite.setValue(dd.toLocalDate());
        lieuC.getSelectionModel().select(l);
        
    }    

      
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("Colocation_afficher.fxml"));        
        Scene scene = new Scene(page_select_my);
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

     public void setLabel(String TITRE,Date date, String LIEU, float LOYER,  String DESCRIPTION)
    {
       
        titre.setText(TITRE);
      
        this.Date_Disponibilite.setValue(date.toLocalDate());
        Lieu.setText(LIEU);
        this.prix.setText(String.valueOf(LOYER));
        description.setText(DESCRIPTION);
    }

    @FXML
    private void modifier(ActionEvent event) {
    }
/*
    void setLabel(String titre, String lieu, String description, float loyermensuel, Date date, Date date0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setLabel(String titre, String lieu, Date date, float loyermensuel, String description, Date date0, int id_pub) {
          System.out.println("ggggg");
    }
    */
}
