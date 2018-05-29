/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractClass;

import EspaceEducatif.affichage_educ;
import EspaceEducatif.ajout_educ;
import EspaceFAQ.affichage_faq;
import EspaceFAQ.ajout_faq;
import Forms.ColocationForm;
import Forms.LoginForm;
import Forms.ProfileForm;
import Utilities.ToolsUtilities;
import com.codename1.components.Accordion;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.User;
import java.io.IOException;

/**
 *
 * @author cobwi
 */
public abstract class AbstractForm extends Form {

    private Form sender;
    public static String TOKEN;
    public Resources theme;
    private Image userPicture = null;

    public AbstractForm(String title, Form sender) {
        super(title);
        try {
            theme = Resources.openLayered("/theme4");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sender = sender;
        this.setLayout(new BorderLayout());
        this.setScrollable(false);

        //Storage.getInstance().clearCache();
        Storage.getInstance().deleteStorageFile("imgprofile");
        EncodedImage placeholder = EncodedImage.createFromImage(getTheme().getImage("fbuser.jpg"), true);

        Image src = null;
        try {
            String imgLink = User.getActifUser().getPhotoProfil();

            if (imgLink.indexOf("http") > -1) {
                src = URLImage.createToStorage(placeholder, "imgprofile", User.getActifUser().getPhotoProfil(), URLImage.RESIZE_SCALE);
            } else {
                src = URLImage.createToStorage(placeholder, "imgprofile", ToolsUtilities.ServerIp + User.getActifUser().getPhotoProfil(), URLImage.RESIZE_SCALE);
            }
        } catch (NullPointerException ne) {
            src = URLImage.createToStorage(placeholder, "imgprofile", "", URLImage.RESIZE_SCALE);
            System.out.println("Photo null");
        }
        userPicture = src;

        final Command profileCommand = new Command("My Profile", userPicture) {
            public void actionPerformed(ActionEvent evt) {
                // updateLoginPhoto();

                ProfileForm profileForm = new ProfileForm(sender);
                profileForm.show();

                /*sender.getContentPane().removeAll();
                sender.addComponent(BorderLayout.CENTER, showMyProfile());
                sender.revalidate();
                 */
            }
        };

        this.addCommand(profileCommand);

        final Command colocationCommand = new Command("Colocation") {
            public void actionPerformed(ActionEvent evt) {
                ColocationForm colocForm = new ColocationForm(sender);
                colocForm.show();
            }
        };
        this.addCommand(colocationCommand);

        final Command Espacefaq_aff = new Command("Espace FAQ affichage ") {
            public void actionPerformed(ActionEvent evt) {
                //ajout_faq colocForm = new ajout_faq(sender);
                affichage_faq colocForm = new affichage_faq(sender);
                colocForm.show();

            }
        };
        this.addCommand(Espacefaq_aff);

        final Command Espacefaq_add = new Command("Espace FAQ ajout ") {
            public void actionPerformed(ActionEvent evt) {
                //ajout_faq colocForm = new ajout_faq(sender);
                ajout_faq colocForm = new ajout_faq(sender);
                colocForm.show();

            }
        };
        this.addCommand(Espacefaq_add);

        final Command educ_aff = new Command("Espace Educatif affichage ") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    //ajout_educ   colocForm = new ajout_educ(sender);
                    affichage_educ colocForm = new affichage_educ(sender);
                    colocForm.show();
                } catch (IOException ex) {
                    System.out.println("error loading educ ajout ");
                }

            }
        };

        this.addCommand(educ_aff);

        final Command educ_add = new Command("Espace Educatif") {
            public void actionPerformed(ActionEvent evt) {
                ajout_educ colocForm = new ajout_educ(sender);
                //affichage_educ colocForm = new affichage_educ(sender);
                colocForm.show();

            }
        };

        this.addCommand(educ_add);

        Command c = new Command("Categories");
        Label l = new Label("Categories") {
            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        };
        l.setUIID("Separator");
        c.putClientProperty("SideComponent", l);
        this.addCommand(c);

        Command c1 = new Command("ACTIONS");
        Label l1 = new Label("ACTIONS") {

            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        };
        l1.setUIID("Separator");
        c1.putClientProperty("SideComponent", l1);
        this.addCommand(c1);

        this.addCommand(new Command("Exit") {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().exitApplication();
            }
        });
        this.addCommand(new Command("Logout") {
            public void actionPerformed(ActionEvent evt) {
                if (FacebookConnect.getInstance().isFacebookSDKSupported()) {
                    FacebookConnect.getInstance().logout();
                } else {
                    LoginForm login = new LoginForm(sender);
                    login.show();
                }
            }
        });
    }

    public Form getSender() {
        return sender;
    }

    public void setSender(Form sender) {
        this.sender = sender;
    }

    public static String getTOKEN() {
        return TOKEN;
    }

    public static void setTOKEN(String TOKEN) {
        AbstractForm.TOKEN = TOKEN;
    }

    public Resources getTheme() {
        return theme;
    }

    public void setTheme(Resources theme) {
        this.theme = theme;
    }

}
