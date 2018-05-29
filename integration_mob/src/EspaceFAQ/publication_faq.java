/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceFAQ;

import AbstractClass.AbstractForm;
import Utilities.ToolsUtilities;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Entite.User;
import commentaire.question.Commentaire_ques;
import commentaire.question.Commentaire_quesDAO;

import java.io.IOException;

/**
 *
 * @author Skander
 */
public class publication_faq extends AbstractForm{

    
    String url = ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/upload/users/";

    public publication_faq(EspaceFAQ e,Form sender) throws IOException
    {
        super("publication", sender);
        this.addComponent(BorderLayout.CENTER,init(e));
    
    }
    
    
    public Form init(EspaceFAQ e) throws IOException {
        int idcurrentuser=User.getActifUser().getId();
        System.out.println(idcurrentuser);
        Commentaire_quesDAO ser = new Commentaire_quesDAO();
        Form f = new Form("", BoxLayout.y());

        SpanLabel description = new SpanLabel(e.getDescription());
        Label desc = new Label("Description: ");

        Label username = new Label(e.getId_usr().getLogin());
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String dd = formater.format(e.getDatepub());
        Label date = new Label(dd);

        Label profile = new Label();

        Container cont = new Container(BoxLayout.y());
        Container cont3 = new Container(BoxLayout.x());

        EncodedImage img1 = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(), 150), true);
        URLImage imgg1 = URLImage.createToStorage(img1, e.getId_usr().getPhotoProfil(), url + e.getId_usr().getPhotoProfil());
        imgg1.fetch();
        profile.setIcon(imgg1.scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 6)));

        cont3.add(profile);
        cont3.add(username);
        cont3.add(date);

        cont.add(cont3).add(desc).add(description);

        Slider s = new Slider();
        cont.add(s);
                
          for (Commentaire_ques c : ser.consulterespace()) {
            if (c.getId_Plan() == e.getId_pub()) {
                EncodedImage imgp = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(), 150), true);
                URLImage imggp = URLImage.createToStorage(imgp, c.getId_user().getPhotoProfil(), url + c.getId_user().getPhotoProfil());
                imggp.fetch();
                Label phot = new Label();
                phot.setIcon(imggp.scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 6)));
                Label nom = new Label(c.getId_user().getLogin());
                SpanLabel commentaire = new SpanLabel(c.getContenu());
                Container com = new Container(BoxLayout.x());
                com.addAll(phot, nom, commentaire);
                cont.add(com);
                System.out.println(url + c.getId_user().getPhotoProfil());
            }
        }
        TextField co = new TextField("", "commentaire");
        Button val = new Button("commenter");
        Container cccom = new Container(BoxLayout.y());
        cccom.addAll(co, val);
        val.addActionListener((evt) -> {
            try {
                Commentaire_ques comi = new Commentaire_ques();
                comi.setContenu(co.getText());
                comi.setId_Plan(e.getId_pub());
                ser.AjouterCommentaire_ques(comi, idcurrentuser);
                publication_faq p = new publication_faq(e, f);
                p.show();
            } catch (IOException ex) {
                System.out.println("mochkkla fel rafraichir ba3d el ajout commentaire");
            }

        });
       
        cont.add(cccom);
        f.add(cont);

/*        f.getToolbar().addCommandToOverflowMenu("ajout", null, (event) -> {
            ajout_faq a = new ajout_faq();
            a.getF().show();
        });
        f.getToolbar().addCommandToOverflowMenu("home", null, (event) -> {
            MyApplication f = new MyApplication();
            f.start();
        });

        f.getToolbar().addCommandToOverflowMenu("affichage", null, (event) -> {
            try {
                affichage_faq f;

                f = new affichage_faq();

                f.getF().show();
            } catch (IOException ex) {
                System.out.println("can't open");
            }

        });
*/
        return f;
    }


}
