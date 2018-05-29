/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import Services.ReclamationService;
import Utilities.ToolsUtilities;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;

import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Entite.Reclamation;
import java.io.IOException;

/**
 *
 * @author cobwi
 */
public class ReclamationSwipeForm extends AbstractForm {

    Form f;
    SpanLabel lb;
    String url = ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/FosBundleProj/web/upload/users/";

    public ReclamationSwipeForm(Form f) {
        super("EspaceEducatif", f);
        this.addComponent(BorderLayout.CENTER, init(f));
    }

    public Component init(Form sender) {
        ReclamationService ser = new ReclamationService();
        Form container = new Form();
        f = new Form("Espace Educatif ", BoxLayout.y());
        lb = new SpanLabel("");

        for (Reclamation e : ser.getAllByUser()) {
            SpanLabel description = new SpanLabel(e.getDescription());
            Label desc = new Label("Description: ");

            Label document = new Label(e.getDescription());
            Label doc = new Label("Document: ");
            Label doc2 = new Label("icon");

            Container cont4 = new Container(BoxLayout.x());
            cont4.add(doc2).add(document);

            Label section = new Label(e.getSujet());
            Label sec = new Label("Section: ");

            Label matiere = new Label(e.getType());
            Label mat = new Label("Matiére: ");

            Label username = new Label(e.getEtat());

            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            String dd = ToolsUtilities.formater.format(e.getDatePub());
            Label date = new Label(dd);

            Label profile = new Label("image profile");

            Container cont = new Container(BoxLayout.y());
            Container cont2 = new Container(BoxLayout.x());
            Container cont3 = new Container(BoxLayout.x());
            Button consulter = new Button();
            consulter.setText("Consulter");

            EncodedImage img1 = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(), 150), true);
            //URLImage imgg1 = theme.getImage("enattente.png");
//            imgg1.fetch();
            //profile.setIcon(imgg1.scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 6)));

            cont3.add(profile);
            cont3.add(username);
            cont3.add(date);

            cont2.add(mat).add(matiere).add(sec).add(section);
            cont.add(cont3).add(desc).add(description).add(doc).add(cont4).add(cont2);
            cont.add(consulter);
            Slider s = new Slider();
            cont.add(s);

            Button delete = new Button();
            delete.addActionListener((evt) -> {
                ser.deleteReclamation(e.getId_pub());
            });
            delete.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, delete.getUnselectedStyle()).toImage());
            SwipeableContainer conti = new SwipeableContainer(delete, cont);
            conti.setSwipeActivated(true);
            Container fina = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            fina.add(conti);

            container.add(fina);

        }
        return container;
    }

}
