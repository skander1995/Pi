/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import Entities.Evenement;
import Entities.User;

import EnumPack.DocumentType;
import Services.EvenementService;
import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;
import Utilities.NumberTextField;
import Utilities.ToolsUtilities;
import View.Navigator;

/**
 * FXML Controller class
 *
 * @author SELLAMI
 */
public class AjouterEvenementController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField lieu;
    @FXML
    private NumberTextField nb;
    @FXML
    private TextArea desc;
    @FXML
    private DatePicker dated;
    @FXML
    private DatePicker datef;
    @FXML
    private Button valider;

    private EvenementService es;
    @FXML
    private Label lbl;
    @FXML
    private Button annuler;
    @FXML
    private Button imageButton;
    @FXML
    private TextField imageText;

    private File fichier;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    
    }

    @FXML
    private void ajouterEv(ActionEvent event) {
        if (nom.getText().isEmpty() || lieu.getText().isEmpty() || nb.getText().isEmpty() || desc.getText().isEmpty() || dated.getValue() == null || datef.getValue() == null) {
            lbl.setText("tous les champs doivent etre remplis");
        } else {
            //Date date = ToolsUtilities.formater.parse(dated.getValue().toString());

            LocalDate localDate = datef.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date f = Date.from(instant);
            LocalDate localDat = dated.getValue();
            Instant instant1 = Instant.from(localDat.atStartOfDay(ZoneId.systemDefault()));
            Date d = Date.from(instant1);
            Date now = new Date();
            if ((d.before(now)) || (f.before(now)) || (f.before(d))) {
                lbl.setText("les dates doivent etre superieures à celle d'aujourdhui ," + "\n" + " et la date de fin superieur à celle de début");
            } else {
                Evenement ev;
                ev = new Evenement(lieu.getText(), User.getIdOfConnectedUser(), "image", nom.getText(), d, f, 12, desc.getText(), "dispo", Integer.valueOf(nb.getText()));
                es = new EvenementService();
                System.out.println(imageText.getText());
                /*File  file=new File(imageText.getText());
            ToolsUtilities.uploadToServer(DocumentType.image, file);*/
                File file = new File(imageText.getText());
                String link = ToolsUtilities.uploadToServer(DocumentType.image, file);
                System.out.println(link);
                ev.setAffiche(link);
                boolean x = es.ajouterEvenement(ev);

                if (x == true) {
                    Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                    al.setTitle("INFORMATION");
                    al.setHeaderText(null);
                    al.setContentText("Evenement ajouté");
                    al.showAndWait();
                    Navigator.LoadScene(Navigator.AfficherMesEvenements);
                    System.out.println("ajouté");
                }
                /*FileUpload a = new FileUpload();
               try {
                   a.upload("","","","f1",fichier);
               } catch (IOException ex) {
                   Logger.getLogger(AjouterEvenementController.class.getName()).log(Level.SEVERE, null, ex);
               }*/
            }
        }

    }

    @FXML
    private void annuler(ActionEvent event) {
        Navigator.LoadScene(Navigator.AfficherMesEvenements);
    }

    @FXML
    private void select(ActionEvent event) {
        JFileChooser file = new JFileChooser();
        file.showOpenDialog(null);
        fichier = file.getSelectedFile();
        imageText.setText(fichier.getAbsolutePath());

    }

}
