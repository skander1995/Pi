/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Utilities.ToolsUtilities;
import com.codename1.components.InfiniteProgress;
import com.codename1.demos.signin.UserForm;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.io.AccessToken;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Oauth2;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.social.GoogleConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.StringUtil;
import com.mycompany.Entite.User;
import com.mycompany.myapp.MyApplication;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author cobwi
 */
public class SignInUi extends Form {

    private Form main;
    private Form current;
    public static String TOKEN;

    private Resources theme;
    private Login login;
    final TextField username = new TextField();
    final TextField email = new TextField();
    final TextField nom = new TextField();
    final TextField prenom = new TextField();
    final TextField password = new TextField();

    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();
    private com.codename1.ui.ComponentGroup gui_Component_Group_1 = new com.codename1.ui.ComponentGroup();
    private com.codename1.ui.TextField gui_Text_Field_2 = new com.codename1.ui.TextField();
    private com.codename1.ui.TextField gui_Text_Field_1 = new com.codename1.ui.TextField();
    private com.codename1.ui.Button gui_Button_2 = new com.codename1.ui.Button();
    private com.codename1.ui.Button gui_Button_3 = new com.codename1.ui.Button();
    private com.codename1.ui.Button gui_Button_1 = new com.codename1.ui.Button();

    public SignInUi(Form f) {
        super("Sign In Form");
        this.current = this;

        setLayout(new com.codename1.ui.layouts.BorderLayout());
        setTitle("Sign In");
        setName("SignInForm");
        addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_1);
        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");
        gui_Container_1.addComponent(gui_Label_1);
        gui_Container_1.addComponent(gui_Component_Group_1);
        gui_Component_Group_1.setName("Component_Group_1");
        gui_Component_Group_1.addComponent(gui_Text_Field_2);
        gui_Component_Group_1.addComponent(gui_Text_Field_1);
        gui_Text_Field_2.setText("TextField");
        gui_Text_Field_2.setName("Text_Field_2");
        gui_Text_Field_1.setText("TextField");
        gui_Text_Field_1.setName("Text_Field_1");
        gui_Container_1.addComponent(gui_Button_2);
        gui_Container_1.addComponent(gui_Button_3);
        gui_Label_1.setUIID("CenterLabel");
        gui_Label_1.setName("Label_1");
//        gui_Label_1.setIcon(resourceObjectInstance.getImage("profile_image.png"));
        gui_Component_Group_1.setName("Component_Group_1");
        gui_Button_2.setText("Sign In");
        gui_Button_2.setName("Button_2");
        gui_Button_3.setText("Forgot Your Password");
        gui_Button_3.setUIID("CenterLabelSmall");
        gui_Button_3.setName("Button_3");
        addComponent(com.codename1.ui.layouts.BorderLayout.SOUTH, gui_Button_1);
        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");
        gui_Button_1.setText("Create New Account");
        gui_Button_1.setUIID("CenterLabel");
        gui_Button_1.setName("Button_1");

    }

    private void signIn(Form sender) {
// perform some checks
    }

    public User mapToJson(String json) {
        User user = new User();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> users = j.parseJSON(new CharArrayReader(json.toCharArray()));

            System.out.println(users);
            int userId = Integer.valueOf(users.get("id").toString().substring(0, users.get("id").toString().indexOf('.')));
            if (users != null) {
                user.setId(userId);
                user.setPassword(users.get("password").toString());
                user.setLogin(users.get("username").toString());
                try {
                    user.setDateNaissance(ToolsUtilities.formater.parse(users.get("datenaissance").toString()));
                } catch (ParseException ex) {

                }
                user.setEmail(users.get("email").toString());
                user.setEnabled(users.get("enabled").toString().equals("true") ? 1 : 0);
                user.setNom(users.get("nom").toString());
                user.setPrenom(users.get("prenom").toString());
                user.setPhotoProfil(users.get("photo_profile").toString());

                System.out.println("profile" + user.getPhotoProfil());

            }
        } catch (IOException ex) {
        }
        return user;

    }

}
