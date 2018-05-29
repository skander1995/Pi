/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Utilities.ToolsUtilities;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author wildchild
 */
public class Navigator {

    public static final String ListAnnonces = "/View/ListAnnonces.fxml";
    public static final String Main = "/View/MainFXMLController.fxml";
    public static final String AfficherAnnonce = "/View/AfficherAnnonce.fxml";
    public static final String Email = "/View/Email.fxml";
    public static final String ajouterannonce = "/View/AjouterAnnonces.fxml";
    public static final String modifierannonce = "/View/ModifierAnnonce.fxml";
    public static final String authentification = "/View/Authentification.fxml";
    public static final String inscription = "/View/register.fxml";
    public static final String accueil = "/View/Acceuil.fxml";
    public static final String confirmation = "/View/ConfirmAccount.fxml";
    public static final String recherche = "/View/RechercheAnnonces.fxml";
    public static final String affrech = "/View/AnnonceAff.fxml";
    public static final String ajoutrec = "/View/AjouterReclamation.fxml";
    public static final String listrec = "/View/ListeReclamation.fxml";
    public static final String Repondre = "/View/RepondreFXML.fxml";
    public static final String ListeReclamationAdmin = "/View/ListReclamationAdmin.fxml";
    public static final String UserAdminPanel = "/View/UserAdminPanel.fxml";
    public static final String MainInterface = "/View/MainInterface.fxml";
    public static final String AdminMainInterface = "/View/MainAdminInterface.fxml";
    public static final String ProfilUser = "/View/ProfileUser.fxml";
    public static final String UserAdminChart = "/View/UserAdminChart.fxml";
    public static String PublicationSkull = "/View/PublicationSkull.fxml";
    static String ReclamationAdminPanel = "/View/ReclamationAdminPanel.fxml";

    // espace educ, faq , 
    public static final String EspaceEducatif_main = "/View/EspaceEducatif/main.fxml";
    public static final String EspaceEducatif_user_crud = "/View/EspaceEducatif/crud_user.fxml";
    public static final String EspaceEducatif_user = "/View/EspaceEducatif/select.fxml";

    public static final String EspaceEducatif_admin = "/View/EspaceEducatif/select_admin.fxml";
    public static final String EspaceEducatif_admin_crud = "/View/EspaceEducatif/crud_admin.fxml";

    public static final String EspaceFAQ_user_crud = "/View/EspaceFAQ/crud_user.fxml";
    public static final String EspaceFAQ_user = "/View/EspaceFAQ/select.fxml";

    public static final String test = "/View/test/Test.fxml";

    public static final String EspaceFAQ_main = "/View/EspaceFAQ/main2.fxml";
    public static final String EspaceFAQ_admin = "/View/EspaceFAQ/select_admin.fxml";
    public static final String EspaceFAQ_admin_crud = "/View/EspaceFAQ/crud_admin.fxml";

    /**
     * ******************************* SELLAMI ******
     */
    public static final String last = "/View/evenement/DerniereAnnonces.fxml";
    public static final String ModifierVoiture = "/View/evenement/modifiervoiture.fxml";
    public static final String listRes = "/View/evenement/ListReservation.fxml";
    public static final String AjouterEvenement = "/View/evenement/AjouterEvenement.fxml";
    public static final String AfficherEvenements = "/View/evenement/AfficherEvenements.fxml";
    public static final String AccEvenements = "/View/evenement/AccueilEv.fxml";
    public static final String AfficherMesEvenements = "/View/evenement/MesEvenements.fxml";
    public static final String ModifierEvent = "/View/evenement/ModifierEvent.fxml";
    public static final String AdminEvents = "/View/evenement/AdminEvents.fxml";
    public static final String ConsultEvent = "/View/evenement/ConsultEvent.fxml";

    public static final String accCovoiturage = "/View/Covoiturage/pageAcceuil.fxml";
    public static final String ajouterCovoiturage = "/View/Covoiturage/co_add.fxml";
    public static final String ConsulterCovoiturage = "/View/Covoiturage/AfficherTousCovoiturage.fxml";

    public static final String ConsulterColo = "/View/colocation/AccueilColocation.fxml";

    private static MainAdminInterfaceController mainAdminControlleur = null;
    private static MainInterfaceController mainInterfaceControlleur = null;

    public static void disableMainController() {
        Navigator.mainAdminControlleur = null;
        Navigator.mainInterfaceControlleur = null;
    }

    public static void setMainController(MainAdminInterfaceController mainControlleur) {
        Navigator.mainAdminControlleur = mainControlleur;
        Navigator.mainInterfaceControlleur = null;
    }

    public static void setMainController(MainInterfaceController mainControlleur) {
        Navigator.mainAdminControlleur = null;
        Navigator.mainInterfaceControlleur = mainControlleur;
    }

    public static void LoadScene(String fxml) {
        try {
            if (Navigator.mainInterfaceControlleur != null) {
                mainInterfaceControlleur.setAPance(FXMLLoader.load(Navigator.class.getResource(fxml)));
            } else if (Navigator.mainAdminControlleur != null) {
                mainAdminControlleur.setAPance(FXMLLoader.load(Navigator.class.getResource(fxml)));
            } else {
                System.out.println("this is without any main");
                disableMainController();
                // loading panel without any main controller
            }
        } catch (Exception ex) {
            if (ToolsUtilities.DEBUG) {
                ex.printStackTrace();
                System.out.println("Catched from navigator = " + ex.getMessage());
            }
        }
    }

    public static void LoadScene(Pane pane) {
        try {
            if (Navigator.mainInterfaceControlleur != null) {
                mainInterfaceControlleur.setAPance(pane);
            } else if (Navigator.mainAdminControlleur != null) {
                mainAdminControlleur.setAPance(pane);
            } else {
                System.out.println("this is without any main");
                disableMainController();
                // loading panel without any main controller
            }
        } catch (Exception ex) {
            if (ToolsUtilities.DEBUG) {
                ex.printStackTrace();
                System.out.println("Catched from navigator = " + ex.getMessage());
            }
        }
    }
}
