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
import Forms.AcceuilForm;
import Forms.AfficherCovoiturageForm;
import Forms.AjoutEvtForm;
import Forms.ColocationAffichForm;
import Forms.ColocationForm;
import Forms.CovoiturageForm;
import Forms.ListCovoiturage;
import Forms.LoginForm;
import Forms.ProfileForm;
import Forms.ReclamationForm;
import Forms.ReclamationSwipeForm;
import Forms.satat;
import Forms.showEventsForm;
import Utilities.ToolsUtilities;
import com.codename1.components.FloatingActionButton;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.User;
import java.io.IOException;

/**
 *
 * @author cobwi
 */
public abstract class AbstractForm extends Form {

    public Form sender;
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

        Toolbar.setGlobalToolbar(true);

        //Storage.getInstance().clearCache();
        Storage.getInstance().deleteStorageFile("imgprofile");
        EncodedImage placeholder = EncodedImage.createFromImage(getTheme().getImage("fbuser.jpg"), true);

        Image src = null;
        try {
            String imgLink = User.getActifUser().getPhotoProfil();

            if (imgLink.indexOf("http") > -1) {
                src = URLImage.createToStorage(placeholder, "imgprofile", User.getActifUser().getPhotoProfil(), URLImage.RESIZE_SCALE);
            } else {
                src = URLImage.createToStorage(placeholder, "imgprofile", ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/upload/users/"+ User.getActifUser().getPhotoProfil(), URLImage.RESIZE_SCALE);
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
            }
        };

        this.addCommand(profileCommand);

        final Command acceuilCommand = new Command("Acceuil") {
            public void actionPerformed(ActionEvent evt) {
                // updateLoginPhoto();
                AcceuilForm profileForm = new AcceuilForm(sender);
                profileForm.show();
            }
        };

        this.addCommand(acceuilCommand);

        final Command colocationCommand = new Command("Colocation") {
            public void actionPerformed(ActionEvent evt) {
                ColocationForm colocForm = new ColocationForm(sender);
                colocForm.show();
            }
        };

        this.addCommand(colocationCommand);

        final Command reclamationCommand = new Command("Reclamation") {
            public void actionPerformed(ActionEvent evt) {
                new ReclamationForm(sender).show();
            }
        };

        this.addCommand(reclamationCommand);

        /**
         * ************************
         */
        final Command colocationAffCommand = new Command("Affichage Colocation") {
            public void actionPerformed(ActionEvent evt) {
                new ColocationAffichForm(sender).show();
            }
        };

        this.addCommand(colocationAffCommand);

        final Command covAffCommand = new Command("Affichage Covoiturage") {
            public void actionPerformed(ActionEvent evt) {
                new AfficherCovoiturageForm(sender).show();
            }
        };

        this.addCommand(covAffCommand);
        final Command ajtcovAffCommand = new Command("Ajouter Covoiturage") {
            public void actionPerformed(ActionEvent evt) {
                new CovoiturageForm(sender).show();
            }
        };

        this.addCommand(ajtcovAffCommand);

        final Command eventAffCommand = new Command("Affichage Evenement") {
            public void actionPerformed(ActionEvent evt) {
                new showEventsForm(sender).show();
            }
        };

        this.addCommand(eventAffCommand);

        final Command eventaddCommand = new Command("Ajouter Evenement") {
            public void actionPerformed(ActionEvent evt) {
                new AjoutEvtForm(sender).show();
            }
        };

        this.addCommand(eventaddCommand);

        
        
        final Command eventStatCommand = new Command("Stat Evenement") {
            public void actionPerformed(ActionEvent evt) {
                new satat("Statistique", sender).show();
            }
        };

        this.addCommand(eventStatCommand);

        /**
         * **********************************************
         */
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

        final Command educ_add = new Command("Espace Educatif ajout") {
            public void actionPerformed(ActionEvent evt) {
                ajout_educ colocForm = new ajout_educ(sender);
                //affichage_educ colocForm = new affichage_educ(sender);
                colocForm.show();

            }
        };

        this.addCommand(educ_add);

        /**
         * ****************************************
         */
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

        Command cc1 = new Command("ACTIONS");
        Label l1 = new Label("ACTIONS") {

            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        };
        l1.setUIID("Separator");
        cc1.putClientProperty("SideComponent", l1);
        this.addCommand(cc1);

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

        /**
         * **********************************************
         */
        // floatin BUTTON
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(this.getContentPane());
        fab.addActionListener(e -> {
            fab.setUIID("FloatingActionButtonClose");
            Image oldImage = fab.getIcon();
            FontImage image = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, "FloatingActionButton", 3.8f);
            fab.setIcon(image);
            Dialog popup = new Dialog();
            popup.setDialogUIID("Container");
            popup.setLayout(new LayeredLayout());

            Button c1 = new Button(theme.getImage("reclittle.png"));
            Button c2 = new Button(theme.getImage("littlecov.png"));
            Button c3 = new Button(theme.getImage("littlecoloc.png"));
            Button trans = new Button(" ");
            trans.setUIID("Container");
            c1.setUIID("Container");
            c2.setUIID("Container");
            c3.setUIID("Container");
            Style c1s = c1.getAllStyles();
            Style c2s = c2.getAllStyles();
            Style c3s = c3.getAllStyles();

            c1s.setMarginUnit(Style.UNIT_TYPE_DIPS);
            c2s.setMarginUnit(Style.UNIT_TYPE_DIPS);
            c3s.setMarginUnit(Style.UNIT_TYPE_DIPS);

            c1s.setMarginBottom(16);
            c1s.setMarginLeft(12);
            c1s.setMarginRight(3);

            c2s.setMarginLeft(4);
            c2s.setMarginTop(5);
            c2s.setMarginBottom(10);
            c3s.setMarginRight(14);

            c3s.setMarginTop(12);
            c3s.setMarginRight(16);

            popup.add(trans).
                    add(FlowLayout.encloseIn(c1)).
                    add(FlowLayout.encloseIn(c2)).
                    add(FlowLayout.encloseIn(c3));

            ActionListener a = ee -> new ReclamationForm(sender).show();

            trans.addActionListener(a);
            c1.addActionListener(a);
            c2.addActionListener(a);
            c3.addActionListener(a);

            popup.setTransitionInAnimator(CommonTransitions.createEmpty());
            popup.setTransitionOutAnimator(CommonTransitions.createEmpty());
            popup.setDisposeWhenPointerOutOfBounds(true);

            popup.showPopupDialog(new Rectangle(this.getWidth() - 10, this.getHeight() - 10, 10, 10));
            fab.setUIID("FloatingActionButton");
            fab.setIcon(oldImage);
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
