/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomContainers;

import Forms.ShowEventSingleForm;
import Utilities.ToolsUtilities;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.components.SpanLabel;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.util.StringUtil;
import com.mycompany.Entite.Evenement;


/**
 *
 * @author SELLAMI
 */
public class EventContainer extends Container{
    
    public EventContainer(Evenement e , String nomprenom){
        Container c = new Container ();
        this.setLayout(new GridLayout(1,2));
        c.setLayout(new GridLayout(5,1));
        SpanLabel username = new SpanLabel("Publié par: "+nomprenom);
        SpanLabel titre = new SpanLabel(e.getNom());
        SpanLabel descr = new SpanLabel(e.getDescription());
        SpanLabel l =new SpanLabel("places:"+e.getPlaces_dispo());
        Button b = new Button("consulter");
        b.addActionListener((evt) -> {
            ShowEventSingleForm es = new ShowEventSingleForm(this.getComponentForm() ,e, nomprenom);
            es.show();
        
        });
        
        c.add(titre);;
        c.add(descr);
        c.add(l);
        c.add(username);
        c.add(b);
        Image i = Image.createImage(Display.getInstance().getDisplayWidth()/4, Display.getInstance().getDisplayHeight()/8, 0xff000000);
        EncodedImage placeholder = EncodedImage.createFromImage(i, true);
        String link = StringUtil.replaceAll(e.getAffiche(), "\\", "/");
        System.out.println(link);
        ScaleImageLabel src =new ScaleImageLabel(URLImage.createToStorage(placeholder,String.valueOf(e.getId()) ,link, URLImage.RESIZE_SCALE));  
        this.add(src);
        this.add(c);


        
        
        
    }
    
}
