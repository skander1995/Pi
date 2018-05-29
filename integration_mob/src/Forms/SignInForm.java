/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Services.UserManager;
import Utilities.ToolsUtilities;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.social.Login;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 *
 * @author cobwi
 */
public class SignInForm extends Form {

    private Form sender;
    private static Form current;
    public static String TOKEN;

    private Resources theme;
    private Login login;
    final TextField username = new TextField();
    final TextField email = new TextField();
    final TextField nom = new TextField();
    final TextField prenom = new TextField();
    final TextField password = new TextField();
    final Picker datePick = new Picker();

    public SignInForm(Form f) {
        //super("Sign In Form");
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        this.current = this;

        theme = UIManager.initNamedTheme("/theme2", "Theme");
        UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));

        this.sender = f;
        setUIID("SigninForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenu !", "WelcomeWhite"),
                new Label("Inscris toi", "WelcomeBlue"),
                new Label(" en un clic", "WelcomeBlue")
        );

        this.setToolbar(new Toolbar());
        this.getToolbar().addCommandToLeftBar("Back", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                sender.show();
            }
        });

        this.setLayout(new BorderLayout());
        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("ContainerWithPadding");

        Image logo = theme.getImage("CodenameOne.png");
        Label l = new Label(logo);
        Container flow = new Container(new FlowLayout(Component.CENTER));
        flow.addComponent(l);
        center.addComponent(flow);

        username.setHint("Username");
        password.setHint("Password");
        email.setHint("Email");
        nom.setHint("Nom");
        prenom.setHint("Prenom");

        password.setConstraint(TextField.PASSWORD);

        username.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);

        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Label emailIcon = new Label("", "TextField");
        Label nameIcon = new Label("", "TextField");
        Label prnIcon = new Label("", "TextField");
        emailIcon.getAllStyles().setMargin(RIGHT, 0);
        nameIcon.getAllStyles().setMargin(RIGHT, 0);
        prnIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(emailIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(nameIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        FontImage.setMaterialIcon(prnIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        Button signIn = new Button("S'enregistrer");
        signIn.addActionListener(x -> {
            signIn();
        });

        email.setConstraint(TextArea.EMAILADDR);
        Validator v = new Validator();
        v
                .addConstraint(username, new LengthConstraint(3))
                .addConstraint(email, new LengthConstraint(5))
                .addConstraint(nom, new LengthConstraint(3))
                .addConstraint(prenom, new LengthConstraint(3))
                .addConstraint(password, new LengthConstraint(3));

        v.setShowErrorMessageForFocusedComponent(true);

        v.addSubmitButtons(signIn);

        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                BorderLayout.center(username).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                BorderLayout.center(email).
                        add(BorderLayout.WEST, emailIcon),
                BorderLayout.center(nom).
                        add(BorderLayout.WEST, nameIcon),
                BorderLayout.center(prenom).
                        add(BorderLayout.WEST, prnIcon),
                datePick,
                signIn
        );
        add(BorderLayout.CENTER, by);

        by.setScrollableY(true);
        by.setScrollVisible(false);
    }

    private void signIn() {
// perform some checks
//http://localhost/piproj/web/app_dev.php/new/user/ly/ly/ly@ly.com/ly/ly/1993-08-09

        UserManager em = new UserManager();
        User user = new User(6,
                username.getText(),
                nom.getText(),
                prenom.getText(),
                ToolsUtilities.formater.format(datePick.getDate()),
                email.getText(),
                password.getText()
        );

        User persistedUser = em.persist(user);

        if (persistedUser != null) {
            user.setDateNaissance(datePick.getDate());

            User.setActifUser(user);
            User.setIdOfConnectedUser(user.getId());
            //redirect to Acceuil 
            AcceuilForm acceuil = new AcceuilForm(current);
            acceuil.show();
        } else {
            //stay here
        }
//
//// USE THE NEWLY CREATED METHOD
//        ConnectionRequest req = new ConnectionRequest();
//        //req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piwebService/webresources/com.mycompany.piwebservice.user/login/" + username.getText() + "");
//        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/user/"
//                + username.getText() + "/"
//                + password.getText() + "/"
//                + email.getText() + "/"
//                + nom.getText() + "/"
//                + prenom.getText() + "/"
//                + ToolsUtilities.formater.format(datePick.getDate()) + "/"
//                + "");
//
//        System.out.println("REQU : " + req.getUrl());
//        req.setHttpMethod("GET");
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                byte[] data = (byte[]) req.getResponseData();
//                String s = new String(data);
//                User user = mapToJson(s);
//                if (s.equals("success")) {
//                    System.out.println("SUCCESS " + password.getText());
//                    User.setActifUser(user);
//                    User.setIdOfConnectedUser(user.getId());
//                    //redirect 
//                    //sender.updateLoginPhoto();
//
//                    AcceuilForm acceuil = new AcceuilForm(current);
//                    acceuil.show();
//
//                    //  sender.show();
//                } else {
//                    System.out.println("FAILED");
//                    Dialog.show("Erreur", "Merci de vérifier vos paramétres de connexion", "Ok", null);
//                }
//            }
//        });
//
//        /*
//        NetworkManager.getInstance().addToQueue(req);
//       InfiniteProgress prog = new InfiniteProgress();
//        Dialog dlg = prog.showInifiniteBlocking();
//      req.setDisposeOnCompletion(dlg);

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
