/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceFAQ;

import Entities.EspaceFAQ;
import Entities.User;
import Services.EspaceFAQservice;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class crud_containerController implements Initializable {

    @FXML
    private TextField description;
   
    @FXML
    private Button consulter;
    @FXML
    private Button supp;
    private Button browse;
    @FXML
    private Button add;
    static int id_pub;
    static int id_usr;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private Button back;
    @FXML
    private Button comm;
   public int id_currentuser=User.getIdOfConnectedUser();
    static int id_test=5;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       int bt=View.EspaceFAQ.SelectController.bt;
        if (bt==0)
        {
            add.setDisable(true);
        }
        
        add.setGraphic(new ImageView("/add.png"));
        supp.setGraphic(new ImageView("/delete.png"));
        consulter.setGraphic(new ImageView("/save.png"));
        // TODO
    }

    void initwithdata(SelectController caller, EspaceFAQ e) {
        id_pub = e.getId_pub();
        id_usr=e.getId_usr();
        description.setText(e.getDescription());
       

    }

    @FXML
    private void addE(ActionEvent event)  {
        EspaceFAQservice en = new EspaceFAQservice();
        if (description.getText().isEmpty() ) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);

            al.setContentText("veuillez remplir tous les champs");
            al.showAndWait();

        } else {
            EspaceFAQ es = new EspaceFAQ(description.getText(),id_currentuser);
            en.Createpub(es,id_currentuser);
            
            //MailSender.send("skander.benothman@esprit.tn", MailType.autre, "votre pub a été creer");
            Notifications nf = Notifications.create().title("Update")
                    .text("Publication ajoutée")
                    .graphic(new ImageView("ok.png"))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BASELINE_RIGHT).darkStyle();
            nf.show();
            clearfields();
            

        }
    }
     private void clearfields() {

        description.clear();
       
    }

   
    @FXML
    private void update(ActionEvent event) throws IOException {
        EspaceFAQservice en = new EspaceFAQservice();
        if (description.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);
            al.setContentText("veuillez remplir tous les champs");

            al.showAndWait();

        } else {
            System.out.println(id_pub);
            EspaceFAQ es = new EspaceFAQ(description.getText(),id_currentuser);
            en.updatepub(es, id_pub);
            Notifications nf = Notifications.create().title("Update")
                    .text("Modification Enregistrée")
                    .graphic(new ImageView("ok.png"))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BASELINE_RIGHT).darkStyle();
            nf.show();
            
        }

    }

    @FXML
    private void del(ActionEvent event) throws IOException {
        EspaceFAQservice en = new EspaceFAQservice();

        en.deletepub(id_pub);
        Notifications nf = Notifications.create().title("Update")
                .text("Publication supprimée")
                .graphic(new ImageView("ok.png"))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BASELINE_RIGHT).darkStyle();
        nf.show();
        
    }

    private void goback() throws IOException {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished((event) -> {
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceFAQ/select.fxml"));
                anchor2.getChildren().setAll(pane);
            } catch (IOException ex) {
                Logger.getLogger(crud_containerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        delay.play();
    }

    @FXML
    private void backto(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceFAQ/select.fxml"));
        anchor2.getChildren().setAll(pane);
    }

  

    @FXML
    private void eval(MouseEvent event) {
        Node container = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            container = loader.load(getClass().getResource("/View/EspaceFAQ/Plan.fxml"));
            Plan poop = loader.getController();
            

        } catch (IOException ex) {
            System.out.println(ex.getCause());
        }
        anchor2.getChildren().setAll(container);
    }

}
