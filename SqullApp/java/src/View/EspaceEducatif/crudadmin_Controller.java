/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceEducatif;
import Entities.EspaceEduc;
import Entities.User;
import Utilities.MailSender;
import EnumPack.MailType;
import Services.EspaceEducservice;
import java.io.File;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class crudadmin_Controller  implements  Initializable{

    @FXML
    private TextField description;
 
    @FXML
    private ComboBox<String> section;
    @FXML
    private TextField document;
    @FXML
    private TextField matiére;
    private Button consulter;
    @FXML
    private Button supp;
    @FXML
    private Button browse;
    private Button add;
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
        section.getItems().add("1ére");
        section.getItems().add("2éme");
        section.getItems().add("3éme");
        section.getItems().add("4éme");
        section.getItems().add("5éme");
        browse.setGraphic(new ImageView("/upload.png"));
        //add.setGraphic(new ImageView("/add.png"));
        supp.setGraphic(new ImageView("/delete.png"));
        
    }

    void initwithdata(Select_adminController caller, EspaceEduc e) {
        i = e.getId_pub();
        description.setText(e.getDescription());
        section.getSelectionModel().select(e.getSection());
        document.setText(e.getDocument());
        matiére.setText(e.getMatiere());

    }

    private void addE(ActionEvent event) throws IOException {
        EspaceEducservice en = new EspaceEducservice();
        if (description.getText().isEmpty()  || section.getValue().isEmpty() || matiére.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);

            al.setContentText("veuillez remplir tous les champs");
            al.showAndWait();

        } else {
            EspaceEduc es = new EspaceEduc(description.getText(), section.getValue(), matiére.getText(), document.getText());
            en.Createpub(es,id_currentuser);
           // MailSender.send("skander.benothman@esprit.tn", MailType.activation, "votre pub a été creer");
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
    private void Browse(ActionEvent event) {
        JFileChooser file = new JFileChooser();
        file.showOpenDialog(null);
        File f = file.getSelectedFile();
        document.setText(f.getAbsolutePath());
    }
 @FXML
    private void del(ActionEvent event) throws IOException {
          EspaceEducservice en = new EspaceEducservice();

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
        delay.setOnFinished((event) -> {
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/select_admin.fxml"));
                anchor2.getChildren().setAll(pane);
            } catch (IOException ex) {
                Logger.getLogger(Popup_crud_containerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        delay.play();
    }
    @FXML
    private void backto(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/select_admin.fxml"));
        anchor2.getChildren().setAll(pane);
    }

   

}
