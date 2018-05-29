/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.colocation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Entities.Colocation;
import Services.ColocationService;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.xml.bind.DatatypeConverter;
/**
 * FXML Controller class
 *
 * @author Skander
 */
public class Colocation_add_adminController implements Initializable {
ColocationService co=new ColocationService();

    ObservableList<Colocation> ccc= FXCollections.observableArrayList(co.afficherColocation());
    
    @FXML
    private DatePicker Date_Disponibilite;
    @FXML
    private TextField Lieu;
    @FXML
    private TextField description;
    @FXML
    private TableView<Colocation> table;
    @FXML
    private TableColumn<Colocation, String> Date_Disp;
    @FXML
    private TableColumn<Colocation, String> LIEU;
    @FXML
    private TableColumn<Colocation, String> LOYER;
    @FXML
    private TableColumn<Colocation, String> DESCRIPTION;
    
    @FXML
    private Button add;
    @FXML
    private Button supp;
    @FXML
    private TextField prix;
    @FXML
    private TableColumn<?, ?> Lieu1;

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Date_Disp.setCellValueFactory(new PropertyValueFactory<Colocation,String>("DATEDISPONIBILITE"));
         LIEU.setCellValueFactory(new PropertyValueFactory<Colocation,String>("LIEU"));
          LOYER.setCellValueFactory(new PropertyValueFactory<Colocation,String>("LOYERMENSUEL"));
           DESCRIPTION.setCellValueFactory(new PropertyValueFactory<Colocation,String>("DESCRIPTION"));
           table.setItems(ccc);
           
    }

  /*  @FXML
    private void addE(ActionEvent event) throws SQLException {
        //ColocationService co = new ColocationService();
            Date date=Date.valueOf(Date_Disponibilite.getValue());
            
           
            Float a= Float.parseFloat(prix.getText());
           Colocation col=new Colocation(description.getText(), Lieu.getText(),a,date,titre);
           System.out.println(col);
           co.ajouterColocation(col); 
           ref();
           clearfields();
    
    }

    private void update(ActionEvent event) {
   //A voir
   Date date=Date.valueOf(Date_Disponibilite.getValue());
    Float a= Float.parseFloat(prix.getText());
        System.out.println("here");
        if ((date== null)||Lieu.getText().isEmpty()||a==null||description.getText().isEmpty())
        {   System.out.println("if");
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);
            al.setContentText("veuillez remplir tous les champs");
            al.showAndWait();
            
        }
        else {
            
            
            int i=table.getSelectionModel().getSelectedItem().getID_PUB();
           
            Date d = Date.valueOf(Date_Disponibilite.getValue());
        //magic happens
     Colocation es =new Colocation(description.getText(),Lieu.getText(),Float.parseFloat(prix.getText()),d);
            co.updatecoloc(es,i);
            Alert al=new Alert(Alert.AlertType.INFORMATION);
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
       
       ColocationService co = new ColocationService();
       Colocation c=table.getSelectionModel().getSelectedItem();
       co.supprimerColocation(c);
       co.supprimerColocation(c);
      Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("INFORMATION");
            al.setHeaderText(null);
            al.setContentText("Publicatiion supprimée");
            al.showAndWait();
            ref();
            clearfields();
    }
 private void clearfields()
    {
        
    Date_Disponibilite.setValue(null);
    Lieu.clear();
 
    prix.clear();
    description.clear();
   
    }
 private void ref() {
      ccc.clear();
        ccc.addAll(co.afficherColocation());
        table.setItems(ccc);
        
    }

    

    @FXML
    private void fillih(javafx.scene.input.MouseEvent event) {
       //LocalDate l=LocalDate.parse((CharSequence) );
        LocalDate date= table.getSelectionModel().getSelectedItem().getDATEDISPONIBILITE().toLocalDate();
    Date_Disponibilite.setValue(date);
 
    float i= table.getSelectionModel().getSelectedItem().getLOYERMENSUEL();
   String n=String.valueOf(i);
    prix.setText(DatatypeConverter.parseString(n));
    description.setText(table.getSelectionModel().getSelectedItem().getDESCRIPTION());
      Lieu.setText(table.getSelectionModel().getSelectedItem().getLIEU());
    }
 */
    
}
