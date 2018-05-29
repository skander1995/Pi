/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceFAQ;
import Entities.EspaceFAQ;
import Entities.User;
import Utilities.MailSender;
import EnumPack.MailType;
import Services.EspaceFAQservice;

import View.Navigator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class crudadmin_Controller  implements  Initializable{

    @FXML
    private TextField description;
    
    private Button consulter;
    @FXML
    private Button supp;
    
    private int i;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private Button back;
public int id_currentuser=User.getIdOfConnectedUser();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        supp.setGraphic(new ImageView("/delete.png"));
        
    }

    void initwithdata(Select_adminController caller, EspaceFAQ e) {
        i = e.getId_pub();
        description.setText(e.getDescription());
        

    }

    private void addE(ActionEvent event) throws IOException {
        EspaceFAQservice en = new EspaceFAQservice();
        if (description.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);

            al.setContentText("veuillez remplir tous les champs");
            al.showAndWait();

        } else {
            EspaceFAQ es = new EspaceFAQ(description.getText(),id_currentuser);
            en.Createpub(es,id_currentuser);
            //MailSender.send("skander.benothman@esprit.tn", MailType.activation, "votre pub a été creer");
            Notifications nf = Notifications.create().title("Update")
                    .text("Publication ajoutée")
                    .graphic(new ImageView("ok.png"))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BASELINE_RIGHT).darkStyle();
            nf.show();
            goback();
             
        }
    }

  
 @FXML
    private void del(ActionEvent event) throws IOException {
          EspaceFAQservice en = new EspaceFAQservice();

        en.deletepub(i);
        Notifications nf = Notifications.create().title("Update")
                .text("Publication supprimée")
                .graphic(new ImageView("ok.png"))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BASELINE_RIGHT).darkStyle();
        nf.show();
        goback();
    }
   
private void goback() throws IOException {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AnchorPane pane = FXMLLoader.load(crudadmin_Controller.this.getClass().getResource(Navigator.EspaceFAQ_admin));
                    anchor2.getChildren().setAll(pane);
                }catch (IOException ex) {
                    Logger.getLogger(crud_containerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        delay.play();
    }
    @FXML
    private void backto(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceFAQ/select_admin.fxml"));
        anchor2.getChildren().setAll(pane);
    }

   

}
