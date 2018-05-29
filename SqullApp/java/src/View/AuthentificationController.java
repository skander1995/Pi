/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import EnumPack.MailType;
import IServices.IUserService;
import Services.UserService;
import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
//import com.restfb.util.DateUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import View.Animation.ShakeTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import org.controlsfx.control.HyperlinkLabel;
import org.controlsfx.control.PopOver;
//import org.joda.time.DateTime;

/**
 *
 * @author adel
 */
public class AuthentificationController implements Initializable {

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField login;

    @FXML
    private Label label;

    PopOver popOver = new PopOver();
    @FXML
    private HyperlinkLabel hyperLabel;

    @FXML
    void auth(ActionEvent event) throws IOException {
        label.setVisible(false);
        Stage stage1 = null;
        IUserService userservice = new UserService();
        boolean check = userservice.Authentification(login.getText(), password.getText());
        if (check == true) {
            User user = userservice.findById(User.getIdOfConnectedUser());
            User.setActifUser(user);
            if (User.getEnabled() == 0) {
                //*********************         a revoir

                FXMLLoader loader = new FXMLLoader();
                Pane mpane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.confirmation));
                stage1 = null;
                stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage1.hide();
                stage1.setScene(createScene(mpane));
                stage1.show();

                ToolsUtilities.sendValidationMail(user, MailType.activation);

            } else {
                // enabled
                if (user.getType().equals("admin")) {
                    //Set navigator to admin panel
                    stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage1.hide();
                    stage1.setScene(createScene(loadMainPane("admin")));
                    stage1.show();
                } else {
                    userservice.setConnected(User.getIdOfConnectedUser());
                    // set main interface

                    stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage1.hide();
                    stage1.setScene(createScene(loadMainPane("user")));
                    stage1.show();

                }
            }
        } else {
            //shake animation
            ShakeTransition anim = new ShakeTransition(password);
            anim.playFromStart();
            label.setVisible(true);
        }
    }

    @FXML
    void inscription(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mpane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.inscription));
        Stage stage1 = null;
        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage1.hide();
        stage1.setScene(createScene(mpane));
        stage1.show();
        //Navigator.LoadScene(Navigator.UserAdminPanel);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private Pane loadMainPane(String type) throws IOException {
// what if admin
        Pane mainPane = null;

        switch (type) {
            case "user": {
                FXMLLoader loader = new FXMLLoader();
                mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.MainInterface));
                MainInterfaceController mainInterface = loader.getController();
                Navigator.setMainController(mainInterface);

                Navigator.LoadScene(Navigator.accueil);

            }
            break;
            case "admin": {
                FXMLLoader loader = new FXMLLoader();
                mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.AdminMainInterface));
                MainAdminInterfaceController mainInterface = loader.getController();
                Navigator.setMainController(mainInterface);

                Navigator.LoadScene(Navigator.UserAdminPanel);
            }
            break;
            default:
                mainPane = null;
        }
        System.out.println("returning");
        System.out.println(mainPane);
        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );
        return scene;
    }

    @FXML
    private void popPwdPane(MouseEvent event) {
        // pop 
        try {
            // load fxml inside
            Node content = null;

            FXMLLoader fxmlLoader = new FXMLLoader();
            content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/PasswordGetter.fxml"));

            Screen screen = Screen.getPrimary();
            Rectangle2D sbounds = screen.getBounds();

            double sw = sbounds.getWidth();
            double sh = sbounds.getHeight();
            popOver.headerAlwaysVisibleProperty().setValue(Boolean.TRUE);
            popOver.consumeAutoHidingEventsProperty().setValue(Boolean.FALSE);
            //PopOverContentController popOverController = new PopOverContentController();
            PasswordGetterController popOverController = fxmlLoader.getController();
            popOver.setContentNode(content);
            // popOver.show(hyperLabel.getParent(), event.getSceneX(), event.getSceneY());
            popOver.show(hyperLabel.getParent(), sw / 3.5, sh / 4);
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from password pane popover" + ex.getMessage());
            }
        }
    }

}
