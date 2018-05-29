/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import EnumPack.DocumentType;
import Services.EvenementService;
import Utilities.ToolsUtilities;
import View.Navigator;
import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
//import java.sql.Date;
//import java.time.LocalDate;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author SELLAMI
 */
public class ModifierEventController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField lieu;
    @FXML
    private TextField nb;
    @FXML
    private TextArea desc;
    @FXML
    private DatePicker dated;
    @FXML
    private DatePicker datef;

    public static String name;
    public static String place;
    public static String descrip;
    public static Date dd;
    public static Date df;
    public static int idEvt;
    public static int nbP;
    public static String affiche;
    @FXML
    private Button Valider;
    @FXML
    private Button annuler;
    @FXML
    private TextField imagetext;
    @FXML
    private Button imagebutton;
    private File fichier;
    @FXML
    private ImageView oldimage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nom.setText(name);
        lieu.setText(place);
        desc.setText(descrip);
        nb.setText(String.valueOf(nbP));
        LocalDate localDated = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dd));
        LocalDate localDatef = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(df));
        dated.setValue(localDated);
        datef.setValue(localDatef);
        Image i = new Image(affiche);
        oldimage.setImage(i);

    }

    @FXML
    private void checkNumber(KeyEvent event) {
    }

    @FXML
    private void Modifier(ActionEvent event) {
        EvenementService es = new EvenementService();
        try {
            System.out.print("modif");
            File file = new File(imagetext.getText());
            String link = ToolsUtilities.uploadToServer(DocumentType.image, file);
            System.out.println(link);
            es.modifierEvenement(nom.getText(), lieu.getText(), Integer.parseInt(nb.getText()), desc.getText(), ToolsUtilities.formater.parse(dated.getValue().toString()), ToolsUtilities.formater.parse(datef.getValue().toString()), idEvt, link);
            Navigator.LoadScene(Navigator.AfficherMesEvenements);
        } catch (ParseException ex) {
            Logger.getLogger(ModifierEventController.class.getName()).log(Level.SEVERE, null, ex);
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
        imagetext.setText(fichier.getAbsolutePath());

    }

}
