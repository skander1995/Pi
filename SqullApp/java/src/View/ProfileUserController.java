/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import EnumPack.DocumentType;
import Services.UserService;
import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.File;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class ProfileUserController implements Initializable {

    @FXML
    private ImageView usrPhotoProfile;
    @FXML
    private Label loginLabel;
    @FXML
    private Label namePrnLabel;
    @FXML
    private Label dnLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;

    public User user = null;

    @FXML
    private Label info1;
    @FXML
    private Label info3;
    @FXML
    private Label info4;
    @FXML
    private Label info2;
    @FXML
    private VBox vBoxContainer;
    @FXML
    private Text infoCov;
    @FXML
    private Text infoEv;
    @FXML
    private Text infoAide;
    @FXML
    private Text infoCol;
    @FXML
    private JFXButton reportButton;

    PopOver popOver = new PopOver();
    @FXML
    private JFXButton modifierButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //initInterfaceWithConnectedUser();
    }

    public void initWithGivenUser(User usr) {
        UserService usrService = new UserService();
        user = usrService.findById(usr.getId());

        try {
            usrPhotoProfile.setImage(new Image(usr.getPhotoProfil()));
        } catch (NullPointerException ne) {

        }
        usrPhotoProfile.setDisable(true);
        modifierButton.setVisible(false);
        loginLabel.setText("@" + usr.getLogin());
        namePrnLabel.setText(usr.getPrenom() + " " + usr.getNom());
        dnLabel.setText(ToolsUtilities.formater.format(usr.getDateNaissance()));
        emailLabel.setText(usr.getEmail());
        phoneLabel.setText(usr.getTelephone());
        initUserPublication();
        initInfoUser();
    }

    public void initInterfaceWithConnectedUser() {
        UserService usrService = new UserService();
        user = usrService.findById(User.getIdOfConnectedUser());
        try {
            usrPhotoProfile.setImage(new Image("http://localhost/FosBundleProj/web/upload/users/"+user.getPhotoProfil()));
        } catch (NullPointerException ne) {

        }

        loginLabel.setText("@" + user.getLogin());
        namePrnLabel.setText(user.getPrenom() + " " + user.getNom());
        dnLabel.setText(ToolsUtilities.formater.format(user.getDateNaissance()));
        phoneLabel.setText(user.getTelephone());
        emailLabel.setText(user.getEmail());
        //disable report button
        reportButton.setVisible(false);
        initUserPublication();
        initInfoUser();
        try {
            popOver.hide(Duration.ONE);
        } catch (Exception ex) {

        }
    }

    public void initUserPublication() {
        try {
            Connection connection = JDBC.JdbcInstance.getInstance();
            PreparedStatement st = connection.prepareStatement("SELECT ID_PUB, e.ID_USR,`LOGIN`, `ETAT` , DATEPUB ,`NOM_EVENT` as titre, concat (DESCRIPTION,'\\n - ',`DATEDEBUT`, ' --> ', `DATEFIN` ,' à ',`LIEU`) as DESCRIPTION, `AFFICHE`,'evenement' FROM `evenement` e, user u\n"
                    + "WHERE e.ID_USR = u.ID_USR AND e.ID_USR = " + user.getId()
                    + " UNION\n"
                    + "SELECT ID_PUB, e.ID_USR,`LOGIN`, `ETAT` , DATEPUB , concat(LIEUDEPART,' - ',LIEUARRIVE) as titre,  DESCRIPTION , '-', 'covoiturage' FROM `covoiturage` e, user u\n"
                    + "WHERE e.ID_USR = u.ID_USR AND e.ID_USR = " + user.getId()
                    + " UNION\n"
                    + "SELECT ID_PUB, e.ID_USR, `LOGIN`,`ETAT` , DATEPUB ,concat(TYPELOGEMENT,' - ',TYPECHAMBRE,' - ',IMGCOUVERTURE,' - ',NBCHAMBRE) as titre, DESCRIPTION , '-','colocation' FROM `colocation` e, user u\n"
                    + "WHERE e.ID_USR = u.ID_USR AND e.ID_USR = " + user.getId()
                    + " UNION\n"
                    + "SELECT ID_PUB, e.ID_USR, `LOGIN`,ETAT,DATEPUB,'Demande d aide' as titre , concat( DESCRIPTION,' - ',section, ' - ',matiere),DOCUMENT,'aide' FROM `aide` e, user u\n"
                    + "WHERE e.ID_USR = u.ID_USR AND e.ID_USR = " + user.getId()
                    + " GROUP BY DATEPUB desc");
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
                mainPane.setEffect(new DropShadow(10, Color.GREY));
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

    @FXML
    private void photoSelecteur(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        fileChooser.setTitle("Choisir une Photo profil");
        File file = fileChooser.showOpenDialog((Stage) usrPhotoProfile.getScene().getWindow());
        if (null != file) {
            try {
                UserService usrService = new UserService();
                User user = User.getActifUser();
                // update current user
                User.getActifUser().setPhotoProfil(file.getPath());
                // update file to www profile user
                String generatedLink = ToolsUtilities.uploadToServer(DocumentType.image, file);
                // update user Database
                HashMap<String, String> args = new HashMap<>();
                args.put("PHOTO_PROFILE", generatedLink);
                usrService.update(User.getActifUser(), args);
                // setting actual photo in ui
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                System.out.println("Printing actual profile user" + User.getActifUser());
                usrPhotoProfile.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(ProfileUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void initInfoUser() {
        UserService usrService = new UserService();
        try {
            HashMap<String, String> mapData = usrService.generateUserInfo(user.getId());

            infoCov.setText(mapData.get("covoiturage"));
            infoEv.setText(mapData.get("evenement"));
            infoCol.setText(mapData.get("colocation"));
            infoAide.setText(mapData.get("aide"));
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

    private void handleEditWithPopOver(User user, double sceneX, double sceneY) {
        try {
            // load fxml inside
            Node content = null;
            popOver.headerAlwaysVisibleProperty().setValue(Boolean.TRUE);
            popOver.consumeAutoHidingEventsProperty().setValue(Boolean.FALSE);
            FXMLLoader fxmlLoader = new FXMLLoader();
            content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/popOverContent.fxml"));

            PopOverContentController popOverController = fxmlLoader.getController();
            popOverController.initWithData(this, user);
            popOver.setContentNode(content);
            popOver.show(reportButton.getParent(), sceneX, sceneY);
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from handleEditwith popover" + ex.getMessage());
            }
        }
    }

    @FXML
    private void popReportWindow(MouseEvent event) {
        try {
            // load fxml inside
            Node content = null;
            popOver.headerAlwaysVisibleProperty().setValue(Boolean.TRUE);
            popOver.consumeAutoHidingEventsProperty().setValue(Boolean.FALSE);
            FXMLLoader fxmlLoader = new FXMLLoader();
            content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/ReportPopOver.fxml"));

            ReportPopOverController popOverController = fxmlLoader.getController();
            popOverController.idProfile = user.getId();
            popOver.setContentNode(content);
            popOver.show(reportButton.getParent(), event.getSceneX(), event.getSceneY());
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from handleEditwith popover" + ex.getMessage());
            }
        }
    }

    @FXML
    private void popUserUpdater(MouseEvent event) {
        handleEditWithPopOver(user, event.getSceneX(), event.getSceneY());
    }


    private void goeven(ActionEvent event) {
        Navigator.LoadScene(Navigator.AfficherMesEvenements);
    }

    private void goesped(ActionEvent event) {
        Navigator.LoadScene(Navigator.EspaceEducatif_user);
    }

    private void goespfaq(ActionEvent event) {
        Navigator.LoadScene(Navigator.EspaceFAQ_user);
    }

}
