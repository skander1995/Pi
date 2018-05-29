/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import CustomContainers.EventContainer;
import Utilities.ToolsUtilities;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import static com.codename1.ui.CN.CENTER_BEHAVIOR_CENTER_ABSOLUTE;
import static com.codename1.ui.CN.deleteStorageFile;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridBagLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.util.StringUtil;
import com.mycompany.Entite.Evenement;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import sun.awt.image.URLImageSource;

/**
 *
 * @author SELLAMI
 */
public class showEventsForm extends AbstractForm {

    public showEventsForm(Form sender) {
        super("Evenements", sender);
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/index/event/");

        req.setHttpMethod("POST");
        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
           Map<String,Object> events = mapToJson(s);
           /*this.setLayout(new GridLayout(4));
           this.setLayout(new BorderLayout(CENTER_BEHAVIOR_CENTER_ABSOLUTE));
           Container c = new Container(new GridLayout(1,1));*/
           Container all = new Container();
           all.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
           all.setScrollableY(true);
           //all.setScrollableX(true);
            //System.out.println(events.size());
           
           // System.out.println(all.isScrollableY());
           String link="";
           for (int i=0;i<((ArrayList)events.get("root")).size();i++){
               Evenement e = new Evenement();
               e.setAffiche(((String)((Map)((ArrayList)events.get("root")).get(i)).get("affiche")));
               System.out.println(((String)((Map)((ArrayList)events.get("root")).get(i)).get("affiche")));
               e.setDescription((String)((Map)((ArrayList)events.get("root")).get(i)).get("description"));
               e.setNom((String)((Map)((ArrayList)events.get("root")).get(i)).get("nom_event"));
               String dd = (String)((Map)((ArrayList)events.get("root")).get(i)).get("datedebut");
               dd = dd.substring(0,10 );
               String df = (String)((Map)((ArrayList)events.get("root")).get(i)).get("datefin");
               df=df.substring(0,10);
               DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
               Date dated = null;
               Date datef = null;
                try {
                     dated = formatter.parse(dd);
                     datef = formatter.parse(df);
                } catch (ParseException ex) {
                    System.out.println(ex.toString());;
                }
                System.out.println("les dates :"+dated+ "    ________      "+datef);
               e.setDate_debut(dated);
              e.setDate_fin(datef);
               System.out.println((String)((Map)((ArrayList)events.get("root")).get(i)).get("datedebut"));
               int nbp = ((Double)((Map)((ArrayList)events.get("root")).get(i)).get("place_dispo")).intValue();
               e.setPlaces_dispo(nbp);
               e.setId(((Double) ((Map) ((ArrayList) events.get("root")).get(i)).get("id")).intValue());
               String username =(String) ((Map) ((Map) ((ArrayList) events.get("root")).get(i)).get("id_usr")).get("nom");
               Container cc = new EventContainer(e,username);
                all.addComponent(cc);
                all.addComponent(new SpanLabel("____________________________"));
               System.out.println("new container"+i);
               //deleteStorageFile("img");
           }
           this.addComponent(BorderLayout.CENTER,all);
           //this.scrollComponentToVisible(all);
          // this.addComponent(BorderLayout.CENTER,c);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public Map<String, Object> mapToJson(String json) {
        ArrayList<Evenement> list = new ArrayList<Evenement>();
        Map<String, Object> events= null;
        try {
            JSONParser j = new JSONParser();
            events = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(events);
            System.out.println(((ArrayList) events.get("root")));
            System.out.println(((ArrayList) events.get("root")).size());

            
            System.out.println((String) ((Map) ((Map) ((ArrayList) events.get("root")).get(0)).get("id_usr")).get("email"));
        } catch (IOException ex) {
        }
        return events ;

    }

}
