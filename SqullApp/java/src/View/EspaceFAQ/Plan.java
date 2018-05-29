/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceFAQ;

import Com_quest.Commentaire_ques;
import Entities.User;
import Evaluation.Evaluation;
import EnumPack.MailType;
import Services.Commentaire_quesDAO;
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
    private TableView<Commentaire_ques> TabCom;
    @FXML
    private TableColumn<Commentaire_ques, String> nom;
    @FXML
    private TableColumn<Commentaire_ques, String> prenom;

    @FXML
    private TableColumn<Commentaire_ques, String> commentaire;

    //////////////////////////////////////////////////////////////////////////
    private int id_usr;
    private int id_pub;
    private int id_current_user;
    ///////////////////////////////////////////

    //private Image StarPleine = new Image("/prototypev1/images.png");
    // private Image StarVide = new Image("/prototypev1/staremty.png");
    /**
     * \prototypev1
     *
     * @param url
     * @param rb
     */
    @FXML
    private AnchorPane anchor2;
    @FXML
    private Button back;
    private TextField note_ev;
    private int note = 0;

    Commentaire_quesDAO se = new Commentaire_quesDAO();
    EvaluationService ev = new EvaluationService();
    ObservableList<Commentaire_ques> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_current_user=User.getIdOfConnectedUser();
if (maincontroller.retour==1)
        {
        id_pub =maincontroller.es.getId_pub();
         id_usr= maincontroller.es.getId_usr();
        }
        else {
        id_pub = crud_containerController.id_pub;
        id_usr = crud_containerController.id_usr;
        
        }
        
        System.out.println("id pub:" + id_pub);
        System.out.println("id_usr" + id_current_user);
        System.out.println("id moula l pub"+id_usr);

        se = new Commentaire_quesDAO();
        list = FXCollections.observableArrayList(se.datalist(id_pub));
        ev = new EvaluationService();

        prenom.setCellValueFactory(new PropertyValueFactory<Commentaire_ques, String>("last_name"));
        nom.setCellValueFactory(new PropertyValueFactory<Commentaire_ques, String>("first_name"));
        commentaire.setCellValueFactory(new PropertyValueFactory<Commentaire_ques, String>("contenu"));
        TabCom.setItems(list);

    }

    @FXML

    private void comPlan(ActionEvent event) {

        Commentaire_ques cm;
        cm = new Commentaire_ques(id_usr, ComTXT.getText(), id_pub);

        Commentaire_quesDAO com = new Commentaire_quesDAO();
        com.AjouterCommentaire(cm);

        String mail = com.selectmail(id_usr);
        System.out.println(mail);
        //MailSender.send(mail, MailType.commentaire, "une personne a commenté votre publication");

        list.clear();
        list.addAll(se.datalist(id_pub));
        TabCom.setItems(list);

    }

    private void ClickEvaluer(MouseEvent event) {

        Evaluation e = new Evaluation(id_current_user, id_pub, note);

        ev.ajouterev(e, id_current_user);
        note_ev.setText("" + ev.notetotale(id_pub));
    }

    @FXML
    private void backto(ActionEvent event) throws IOException {
//        AnchorPane pane = FXMLLoader.load(getClass().getResource("/View/EspaceFAQ/main2.fxml"));
//        anchor2.getChildren().setAll(pane);
  Navigator.LoadScene("/View/EspaceFAQ/main2.fxml");
    }

}
