/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Publication;
import Entities.User;
import Services.UserService;
import Utilities.ToolsUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class PublicationSkullController implements Initializable {

    @FXML
    private ImageView imagePublication;
    @FXML
    private Label titlePublication;
    @FXML
    private Label descriptionText;
    @FXML
    private Label datePublication;

    /**
     * Initializes the controller class.
     */
    private Publication publication = null;
    private int idPublication;
    @FXML
    private Label userLabel;
    private String idUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public void initializeComponenent(Image image, String title, String description, String Date) {

    }

    public void initializeComponenent(Publication publication) {
        try {
            imagePublication.setImage(publication.getImage());
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }

        try {
            datePublication.setText(publication.getDate_creation().toString());
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }

        try {
            titlePublication.setText(publication.getTitle());
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
        try {
            descriptionText.setText(publication.getDescription());
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }

    }

    public void initializeComponenentWithMap(HashMap<String, String> args, String type) {

        switch (type) {
            case "aide": {
                try {
                    Image image = new Image("/View/Assets/icons/icons8-document-512.png");
                    imagePublication.setPreserveRatio(true);
                    imagePublication.setImage(image);
                } catch (Exception ex) {
                    if (ToolsUtilities.DEBUG) {
                        System.out.println("Catched = " + ex.getMessage());
                    }
                }
                break;
            }
            default: {
                try {
                    Image image = new Image(args.get("AFFICHE"));
                    imagePublication.setImage(image);
                } catch (Exception ex) {
                    if (ToolsUtilities.DEBUG) {
                        System.out.println("Catched = " + ex.getMessage());
                    }
                }
            }
        }

        try {
            userLabel.setText(args.get("LOGIN"));
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }

        try {
            System.out.println("args = " + args.get("DATEPUB"));
            datePublication.setText(args.get("DATEPUB"));
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }

        try {
            titlePublication.setText(args.get("titre"));
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
        try {
            descriptionText.setText(args.get("DESCRIPTION"));
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }

        try {
            idUser = args.get("ID_USR");
        } catch (NullPointerException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

    @FXML
    private void redirectToPublication(MouseEvent event) {

    }

    @FXML
    private void redirectToUserProfile(MouseEvent event) {
        try {
            UserService userService = new UserService();
            FXMLLoader loader = new FXMLLoader();
            Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.ProfilUser));
            ProfileUserController mainController = loader.getController();
            User user = userService.findById(Integer.valueOf(idUser));
            //mainController.user = userService.findById(Integer.valueOf(idUser));
            //System.out.println("USERRRRRRRR" + user);
            mainController.initWithGivenUser(user);
            Navigator.LoadScene(mainPane);
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

}
