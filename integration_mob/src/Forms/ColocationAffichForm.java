/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import Services.ColocationService;
import Utilities.ToolsUtilities;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.StringUtil;
import com.mycompany.Entite.Colocation;

/**
 *
 * @author ASUS I7
 */
public class ColocationAffichForm extends AbstractForm {

    private Form f;
    private Resources theme;
    private Button Supprimer;
    private EncodedImage enc;
    ColocationService serviceTask = new ColocationService();

    public ColocationAffichForm(Form sender) {
        //Picker p = new Picker();
        super("Liste Colocation", sender);
        this.setScrollableY(true);
        theme = UIManager.initNamedTheme("/theme2", "Theme");
        UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);
        getContentPane().setScrollableY(true);

        this.setLayout(new GridLayout(10, 1));

        for (Colocation c : serviceTask.getList2()) {

            System.out.println("COLOC" + c);

//        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
//            try {
//     enc = EncodedImage.create("/giphy.gif");
//            } catch (IOException ex) {
//
//            }
//
//           Image i = (URLImage.createToStorage(enc, c.getCOMMODITE(), , URLImage.RESIZE_SCALE));
//            ImageViewer img2 = new ImageViewer(i.fill(900, 500));
            //this.removeAll();
            //this.refreshTheme();
            Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            System.out.println("IMAGE " + ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + c.getIMGCOUVERTURE()
            );
            EncodedImage placeholder = EncodedImage.createFromImage(theme.getImage("BackgroundRect.png"), true);
            String link = StringUtil.replaceAll(c.getIMGCOUVERTURE(), "\\", "/");
            ScaleImageLabel sl = new ScaleImageLabel(URLImage.createToStorage(placeholder, c.getIMGCOUVERTURE(),
                    ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + link,
                    URLImage.RESIZE_SCALE
            ));
            // Image image = URLImage.createToStorage(placeholder, ToolsUtilities.ServerIp + c.getIMGCOUVERTURE(), ToolsUtilities.ServerIp + c.getIMGCOUVERTURE(), URLImage.RESIZE_SCALE_TO_FILL);

            /**
             * *************************************
             */            //date.setText(serviceTask.getList2().get(0).getDatedenaissance());
            Label Description = new Label("Description: " + c.getDESCRIPTION());
            Label img = new Label("Image: " + c.getIMGCOUVERTURE());
            Label Prix = new Label("Prix: " + c.getLOYERMENSUEL());
            Label Commodite = new Label("Commodité: " + c.getCOMMODITE());
            Label Lieu = new Label("Lieu: " + c.getLIEU());
            Label Nbchambre = new Label("Nombre de chambre: " + c.getNBCHAMBRE());

//            C1.add(img);
            C2.add(Description);
            C2.add(Prix);
            C2.add(Commodite);
            C2.add(Lieu);
            C2.add(Nbchambre);
            Supprimer = new Button("Supprmier");
            Supprimer.addActionListener(evt -> {
                ColocationService sup = new ColocationService();
                sup.DeleteColoc(c.getID_PUB());
                new ColocationAffichForm(sender).show();
            });
            //    this.add(C2);
            this.addAll(
                    sl,
                    new Label("Description: " + c.getDESCRIPTION()),
                    new Label("Prix: " + c.getLOYERMENSUEL()),
                    new Label("Commodité: " + c.getCOMMODITE()),
                    new Label("Lieu: " + c.getLIEU()),
                    new Label("Nombre de chambre: " + c.getNBCHAMBRE()),
                    Supprimer,
                    new Label("___________________________")
            );

        }
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
