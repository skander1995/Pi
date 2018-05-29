/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import Services.UserService;
import Utilities.ToolsUtilities;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class AcceuilController implements Initializable {

    @FXML
    private VBox vBoxContainer;
    @FXML
    private ImageView photoUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // load publications
        initializeUserProfile();
        loadPublications();
    }

    public void loadPublications() {
        try {
            Connection connection = JDBC.JdbcInstance.getInstance();
            PreparedStatement st = connection.prepareStatement("SELECT ID_PUB, e.idUsr,`username`, `ETAT` , DATEPUB ,`NOM_EVENT` as titre, concat (DESCRIPTION,'\\\\n - ',`DATEDEBUT`, ' --> ', `DATEFIN` ,' à ',`LIEU`) as DESCRIPTION, `AFFICHE`,'evenement' FROM `evenements` e, user u\n" +
"                    WHERE e.idUsr = u.id\n" +
"                     UNION\n" +
"                    SELECT ID_PUB, e.id_usr,`username`, `ETAT` , DATEPUB , concat(LIEUDEPART,' - ',LIEUARRIVE) as titre,  DESCRIPTION , '-', 'covoiturage' FROM `covoiturage` e, user u\n" +
"                    WHERE e.id_usr = u.id\n" +
"                    UNION\n" +
"                    SELECT ID_PUB, e.idUsr, `username`,`LIEU` , DATEPUB ,concat(Commodite,' - ',IMGCOUVERTURE,' - ',NBCHAMBRE) as titre, DESCRIPTION , '-','colocation' FROM `colocation` e, user u\n" +
"                    WHERE e.idUsr = u.id\n" +
"                    UNION\n" +
"                    SELECT idPub, e.idUsr, `username`,\"encours\",DATEPUB,'Demande d aide' as titre , concat( DESCRIPTION,' - ',section, ' - ',matiere),DOCUMENT,'aide' FROM `aide` e, user u\n" +
"                    WHERE e.idUsr = u.id\n" +
"                    GROUP BY DATEPUB desc");
            ResultSet rs = st.executeQuery();
            HashMap<String, String> args = new HashMap<>();
            ArrayList<String> list = new ArrayList<>();

            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader();
                Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream("/View/PublicationSkull.fxml"));
                //rs.getMetaData().getColumnCount();
                PublicationSkullController mainController = loader.getController();
                // lest s work with maps

                for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                    args.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                }
                // init new publication
                mainController.initializeComponenentWithMap(args, rs.getString(9));
                //mainPane.setEffect(new DropShadow(10, Color.GREY));
                vBoxContainer.getChildren().add(mainPane);
            }

        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        } catch (IOException ex) {
            Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadConnectedUserPublication() {
        int connectedUser = User.getIdOfConnectedUser();
    }

    private void initializeUserProfile() {
        Connection connection = JDBC.JdbcInstance.getInstance();
        UserService usrService = new UserService();
        User user = usrService.findById(User.getIdOfConnectedUser());

        try {
            Image image = new Image("http://localhost/FosBundleProj/web/upload/users/"+user.getPhotoProfil());
            photoUser.setImage(image);
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

}
