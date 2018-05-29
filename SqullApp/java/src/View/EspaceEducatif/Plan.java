/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceEducatif;

import Entities.Commentaire;
import Entities.User;
import Evaluation.Evaluation;
import EnumPack.MailType;
import Services.CommentaireDAO;
import Services.EvaluationService;
import Utilities.MailSender;
import View.Navigator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author a7med
 */
public class Plan implements Initializable {

    @FXML
    private Button Commenter;
    @FXML
    private TextField ComTXT;
    @FXML
    private TableView<Commentaire> TabCom;

    @FXML
    private TableColumn<Commentaire, String> commentaire;

    // EvaluationDAO eval = new EvaluationDAO();
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;
    @FXML
    private ImageView star4;
    @FXML
    private ImageView star3;
    @FXML
    private ImageView star5;
    @FXML
    private Button evaluer;
    @FXML
    private ImageView Est1;
    @FXML
    private ImageView Est2;
    @FXML
    private ImageView Est5;
    @FXML
    private ImageView Est3;
    @FXML
    private ImageView Est4;

    //////////////////////////////////////////////////////////////////////////
    private int id_usr;
    private int id_pub;
    private int id_current_user;
    ///////////////////////////////////////////

    @FXML
    private TableColumn<Commentaire, String> nom;
    @FXML
    private TableColumn<Commentaire, String> prenom;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private Button back;
    @FXML
    private TextField note_ev;
    private int note = 0;
    CommentaireDAO se;
    ObservableList<Commentaire> list;
    EvaluationService ev;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_current_user = User.getIdOfConnectedUser();
        
        if (maincontroller.retour == 1) {
            id_usr = maincontroller.es.getId_usr();
            id_pub = maincontroller.es.getId_pub();

        } else {
            id_usr = Popup_crud_containerController.id_usr;
            id_pub = Popup_crud_containerController.id_pub;
            id_current_user = Popup_crud_containerController.id_usr;
        }

        System.out.println("id pub:" + id_pub);
        System.out.println("id_usr" + id_current_user);

        se = new CommentaireDAO();
        list = FXCollections.observableArrayList(se.datalist(id_pub));
        ev = new EvaluationService();

        prenom.setCellValueFactory(new PropertyValueFactory<Commentaire, String>("last_name"));
        nom.setCellValueFactory(new PropertyValueFactory<Commentaire, String>("first_name"));
        commentaire.setCellValueFactory(new PropertyValueFactory<Commentaire, String>("contenu"));
        TabCom.setItems(list);

        System.out.println("la note totale est " + ev.notetotale(id_pub));
        note_ev.setText("" + ev.notetotale(id_pub));
        Est1.setOnMouseClicked((MouseEvent E1) -> {
            Est1.setVisible(true);
            Est2.setVisible(false);
            Est3.setVisible(false);
            Est4.setVisible(false);
            Est5.setVisible(false);

            note = 1;
            System.out.println(note);

        });

        Est1.setOnMouseExited((MouseEvent E2) -> {
            if (note != 0) {
                Est1.setVisible(true);
            } else {
                Est1.setVisible(false);
            }
        });

        star1.setOnMouseEntered((MouseEvent E3) -> {

            Est1.setVisible(true);
        });

        Est2.setOnMouseClicked((MouseEvent E1) -> {

            Est1.setVisible(true);
            Est2.setVisible(true);
            Est3.setVisible(false);
            Est4.setVisible(false);
            Est5.setVisible(false);
            note = 2;
            System.out.println(note);
        });

        Est2.setOnMouseExited((MouseEvent E2) -> {
            if (note <= 1) {
                Est2.setVisible(false);
            } else {
                Est2.setVisible(true);
            }
        });

        star2.setOnMouseEntered((MouseEvent) -> {
            Est1.setVisible(true);
            Est2.setVisible(true);
        });

        Est3.setOnMouseClicked((MouseEvent E1) -> {

            Est1.setVisible(true);
            Est2.setVisible(true);
            Est3.setVisible(true);
            Est4.setVisible(false);
            Est5.setVisible(false);

            note = 3;
            System.out.println(note);
        });

        Est3.setOnMouseExited((MouseEvent E2) -> {
            if (note <= 2) {
                Est3.setVisible(false);
            } else {

                Est3.setVisible(true);

            }
        });

        star3.setOnMouseEntered((MouseEvent) -> {

            Est1.setVisible(true);
            Est2.setVisible(true);
            Est3.setVisible(true);
        });

        Est4.setOnMouseClicked((MouseEvent E1) -> {

            Est1.setVisible(true);
            Est2.setVisible(true);
            Est3.setVisible(true);
            Est4.setVisible(true);
            Est5.setVisible(false);

            note = 4;
            System.out.println(note);

        });

        Est4.setOnMouseExited((MouseEvent E2) -> {
            if (note <= 3) {
                Est4.setVisible(false);

            } else {
                Est4.setVisible(true);
            }
        });

        star4.setOnMouseEntered((MouseEvent) -> {

            Est1.setVisible(true);
            Est2.setVisible(true);
            Est3.setVisible(true);
            Est4.setVisible(true);
        });

        Est5.setOnMouseClicked((MouseEvent E1) -> {

            Est1.setVisible(true);
            Est2.setVisible(true);
            Est3.setVisible(true);
            Est4.setVisible(true);
            Est5.setVisible(true);

            note = 5;
            System.out.println(note);

        });

        Est5.setOnMouseExited((MouseEvent E2) -> {
            if (note <= 4) {
                Est5.setVisible(false);
            } else {
                Est5.setVisible(true);
            }
        });
        star5.setOnMouseEntered((MouseEvent) -> {

            Est1.setVisible(true);
            Est2.setVisible(true);
            Est3.setVisible(true);
            Est4.setVisible(true);
            Est5.setVisible(true);
        });

    }

   

    @FXML
    private void comPlan(ActionEvent event) {

        //System.out.println("Ajout d'une ligne!");
        Commentaire cm;
        cm = new Commentaire(id_current_user, ComTXT.getText(), id_pub);

        CommentaireDAO com = new CommentaireDAO();
        com.AjouterCommentaire(cm);
        System.out.println("id moula l pub " + id_usr);
        String mail = com.selectmail(id_usr);
        System.out.println(mail);
        //MailSender.send(mail, MailType.commentaire, "une personne a commenté votre publication");

        list.clear();
        list.addAll(se.datalist(id_pub));

        TabCom.setItems(list);

    }

    
    @FXML
    private void ClickEvaluer(MouseEvent event) {

        Evaluation e = new Evaluation(id_current_user, id_pub, note);

        ev.ajouterev(e, id_current_user);
        note_ev.setText("" + ev.notetotale(id_pub));
    }

   
    @FXML
    private void backto(ActionEvent event) throws IOException {
        if (maincontroller.retour == 1) {
            Navigator.LoadScene(Navigator.EspaceEducatif_main);
//AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/main.fxml"));

            //anchor2.getChildren().setAll(pane);
        } else {
            Navigator.LoadScene("/View/EspaceEducatif/select.fxml");
           // AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceEducatif/select.fxml"));
            //anchor2.getChildren().setAll(pane);
        }
    }

    @FXML
    private void ClickStar1(MouseEvent event) {
    }

    @FXML
    private void ClickStar2(MouseEvent event) {
    }

    @FXML
    private void ClickStar4(MouseEvent event) {
    }

    @FXML
    private void ClickStar3(MouseEvent event) {
    }

    @FXML
    private void ClickStar5(MouseEvent event) {
    }

    @FXML
    private void STAR1(MouseEvent event) {
    }

    @FXML
    private void STAR2(MouseEvent event) {
    }

    @FXML
    private void STAR5(MouseEvent event) {
    }

    @FXML
    private void STAR3(MouseEvent event) {
    }

    @FXML
    private void STAR4(MouseEvent event) {
    }

}
