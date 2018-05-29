/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceFAQ;


import AbstractClass.AbstractForm;
import EspaceEducatif.publication;
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
import com.mycompany.myapp.MyApplication;
import java.io.IOException;



/**
 *
 * @author Skander
 */
public class affichage_faq extends AbstractForm{

   

    /**
     *
     */
    public affichage_faq(Form sender)  {
        
       super("EspaceFAQ", sender);
       this.addComponent(BorderLayout.CENTER, init(sender));
       this.refreshTheme();
    }
    public Component init (Form sender)
            
    {Form f = new Form();
        String url = ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/upload/users/";
        System.out.println(url);
        Container c = new Container();
       EspaceFAQservice ser = new EspaceFAQservice();

       
        for (EspaceFAQ e : ser.consulterespace()) {
            SpanLabel description = new SpanLabel(e.getDescription());
            Label desc = new Label("Description: ");
           
            Label username = new Label(e.getId_usr().getLogin());
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            String dd = formater.format(e.getDatepub());
            Label date = new Label(dd);
            
            Label profile = new Label();
            
            Container cont = new Container(BoxLayout.y());
            Container cont3 = new Container(BoxLayout.x());
            Button b = new Button();
            b.setText("Consulter");
            //System.out.println(e.getId_usr().getPhotoProfil());
            EncodedImage img1 = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(), 150), true);
            URLImage imgg1 = URLImage.createToStorage(img1, e.getId_usr().getPhotoProfil(), url + e.getId_usr().getPhotoProfil() );
            imgg1.fetch();
            profile.setIcon(imgg1.scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 6)));
          //  System.out.println(url + e.getId_usr().getPhotoProfil());
            cont3.add(profile);
            cont3.add(username);
            cont3.add(date);
            
          
            cont.add(cont3).add(desc).add(description);
            cont.add(b);
            Slider s = new Slider();
            cont.add(s);

            b.addActionListener((evt) -> {
                try {
                    publication_faq p = new publication_faq(e,sender); // Logger.getLogger(affichage_faq.class.getName()).log(Level.SEVERE, null, ex);
                    p.show();
                } catch (IOException ex) {
                    System.out.println("error loading publication");
                }
            });

           
            
           int iduser=com.mycompany.Entite.User.getActifUser().getId();
            System.out.println(e.getId_usr()+"    "+iduser);
            if(e.getId_usr().getId()==iduser){
            Button delete = new Button();
            delete.addActionListener((evt) -> {
                System.out.println("delete: "+e.getId_pub());
                ser.deletepub(e.getId_pub());
                  new affichage_faq(sender).show();
            });
            delete.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE,delete.getUnselectedStyle()).toImage());
            SwipeableContainer conti = new SwipeableContainer(delete, cont);
            conti.setSwipeActivated(true);
            Container fina = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            fina.add(conti);
                    
            f.add(fina);}
            else {
            f.add(cont);
            }
            f.getContentPane().addPullToRefresh(() -> {
                new affichage_faq(sender).show();
        
        });
        }return f;
        
/*        f.getToolbar().addCommandToOverflowMenu("ajout", null, (event) -> {
            ajout_faq a = new ajout_faq();
            a.getF().show();
        });
        f.getToolbar().addCommandToOverflowMenu("home", null, (event) -> {
            MyApplication f = new MyApplication();
            f.start();
        });*/
    

    }

}
