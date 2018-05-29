/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceEducatif;

import Entities.EspaceEduc;
import Entities.User;
import Utilities.MailSender;
import EnumPack.DocumentType;
import EnumPack.MailType;
import Services.EspaceEducservice;

import Utilities.ToolsUtilities;
import View.Navigator;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import org.controlsfx.control.Notifications;
import rest.file.uploader.tn.FileUploader;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class Popup_crud_containerController implements Initializable {

    @FXML
    private TextField description;

    @FXML
    private ComboBox<String> section;
    @FXML
    private TextField document;
    @FXML
    private TextField matiére;
    @FXML
    private Button consulter;
    @FXML
    private Button supp;
    @FXML
    private Button browse;
    @FXML
    private Button add;
    static int id_pub;
    static int id_usr;
    String filenam;
    String filepath;

    @FXML
    private AnchorPane anchor2;
    @FXML
    private Button back;
    @FXML
    private Button comm;
    ///////////////////////////////////////////////////////////////////////////////////////////
    public int id_currentuser = User.getIdOfConnectedUser();
////////////////////////////////////////////////////////////// 
    //static int id_test = 5;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int bt = SelectController.bt;

        if (bt == 0) {
            add.setDisable(true);
        }

        section.getItems().add("1ére");
        section.getItems().add("2éme");
        section.getItems().add("3éme");
        section.getItems().add("4éme");
        section.getItems().add("5éme");
        //browse.setGraphic(new ImageView("/upload.png"));
        //add.setGraphic(new ImageView("/add.png"));

        //supp.setGraphic(new ImageView("/delete.png"));
        //consulter.setGraphic(new ImageView("/save.png"));
        // TODO
    }

    void initwithdata(SelectController caller, EspaceEduc e) {
        id_pub = e.getId_pub();
        id_usr = e.getId_usr();
        description.setText(e.getDescription());

        section.getSelectionModel().select(e.getSection());
        document.setText(e.getDocument());
        matiére.setText(e.getMatiere());

    }

    @FXML
    private void addE(ActionEvent event) throws IOException {
        EspaceEducservice en = new EspaceEducservice();
        if (description.getText().isEmpty() || section.getValue().isEmpty() || matiére.getText().isEmpty() || document.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);

            al.setContentText("veuillez remplir tous les champs");
            al.showAndWait();

        } else {
            EspaceEduc es = new EspaceEduc(description.getText(), section.getValue(), matiére.getText(), document.getText());

            try {
                String filenameinserver = "";
                FileUploader fu = new FileUploader("http://localhost/FosBundleProj/web/upload/");
                // File f = new File(es.getDocument());
                try {
                    filenameinserver = fu.upload(filepath);
                    System.out.println("filepath:" + filepath);
                    System.out.println("fichier   :" + filenameinserver);
                    es.setDocument(filenameinserver);

                } catch (Exception e) {
                    filenameinserver = fu.upload(filepath);

                    System.out.println("fichier    : " + filenameinserver);
                    System.out.println("mochkla ajout fichier");
                }

                en.Createpub(es, id_currentuser);
//ToolsUtilities.uploadToServer(DocumentType.document, f );
                //MailSender.send("skander.benothman@esprit.tn", MailType.publication, "votre pub a été creer");
                Notifications nf = Notifications.create().title("Ajout")
                        .text("Modification Ajoutée")
                        .graphic(new ImageView("ok.png"))
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BASELINE_RIGHT).darkStyle();
                nf.show();
                clearfields();

            } catch (Exception e) {
                System.out.println("erreur ajout: "+e.getMessage());
            }

        }
    }

    private void clearfields() {

        description.clear();

        section.getItems().removeAll(section.getItems());
        section.getItems().addAll("1ére", "2éme", "3éme", "4éme", "5éme");
        document.clear();
        matiére.clear();
    }

    @FXML
    private void Browse(ActionEvent event) {
        JFileChooser file = new JFileChooser();
        file.showOpenDialog(null);
        File f = file.getSelectedFile();
        int filenamein = f.getAbsolutePath().lastIndexOf("\\");
        System.out.println(filenamein);
        filenam = f.getAbsolutePath().substring(filenamein + 1);
        filenam = filenam.replace(".", "..");
        filepath = f.getAbsolutePath();
       // filepath = filepath.replace(".", "..");
        filepath = filepath.replace("\\", "/");

        System.out.println("filenam:   " + filenam);
        System.out.println("file path: " + filepath);
        document.setText(filenam);
    }

    @FXML
    private void update(ActionEvent event) throws IOException {
        EspaceEducservice en = new EspaceEducservice();
        if (description.getText().isEmpty() || section.getValue().isEmpty() || matiére.getText().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText(null);
            al.setContentText("veuillez remplir tous les champs");

            al.showAndWait();

        } else {
            System.out.println(id_pub);
            EspaceEduc es = new EspaceEduc(description.getText(), section.getValue(), matiére.getText(), document.getText());
            en.updatepub(es, id_pub);
//            MailSender.send("skander.benothman@esprit.tn", MailType.publication, "votre pub a éte mise à jour");
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
        EspaceEducservice en = new EspaceEducservice();

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
            Navigator.LoadScene("/View/EspaceEducatif/select.fxml");
//                AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/select.fxml"));
//                anchor2.getChildren().setAll(pane);
        });
        delay.play();
    }

    @FXML
    private void backto(ActionEvent event) throws IOException {
          Navigator.LoadScene("/View/EspaceEducatif/select.fxml");
//        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/select.fxml"));
//        anchor2.getChildren().setAll(pane);
    }

    @FXML
    private void eval(MouseEvent event) {
        Node container = null;
        FXMLLoader loader = new FXMLLoader();
        //            container = loader.load(getClass().getResource("/View/EspaceEducatif/Plan.fxml"));
//            Plan poop = loader.getController();
Navigator.LoadScene("/View/EspaceEducatif/Plan.fxml");
        anchor2.getChildren().setAll(container);
    }

}
