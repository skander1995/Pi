/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomContainers;

import Forms.ShowEventSingleForm;
import Forms.satat;
import Forms.showEventsForm;
import Utilities.ToolsUtilities;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.util.StringUtil;
import com.mycompany.Entite.Evenement;
import com.mycompany.Entite.User;

/**
 *
 * @author SELLAMI
 */
public class EventContainer2  extends Container{
    String s=null;
    public EventContainer2(Evenement e , String nomprenom){
        Container c = new Container ();
        this.setLayout(new GridLayout(2,1));
        c.setLayout(new GridLayout(10,1));
        Label username = new Label("Publié par :"+nomprenom);
        //Label titre = new Label(e.getNom());
        Label descr = new Label("Description : "+e.getDescription());
        Label Lieu = new Label("Lieu: à "+e.getDescription());
        Label dd = new Label("Date Début :"+ToolsUtilities.formater.format(e.getDate_debut()));
        Label df = new Label("Date fin :"+ToolsUtilities.formater.format(e.getDate_fin()));
        Label l = new Label("Nombre des places dispos:"+e.getPlaces_dispo());
        Button b = new Button("Participer");
        Button a = new Button("Annuler participation");
        Button bs = new Button("Supprimer");
        Button bstat = new Button("Statistiques");
        c.add(username);
        c.add(descr);
        c.add(Lieu);
        c.add(dd);
        c.add(df);
        c.add(l);
        c.add(b);
        c.add(a);
        c.add(bs);
        c.add(bstat);
        
        ConnectionRequest rq = new ConnectionRequest();
        rq.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/event/check/");
        
        rq.setHttpMethod("POST");
        rq.addArgument("id", String.valueOf(User.getIdOfConnectedUser()));
        rq.addArgument("ev", String.valueOf(e.getId()));
         rq.addResponseListener((NetworkEvent ev) -> {
            byte[] data = (byte[]) rq.getResponseData();
             s = new String(data);
            System.out.println("RETURN_______ " + s);
             
            
        });
         
         NetworkManager.getInstance().addToQueueAndWait(rq);
         System.out.println(s);
             if (s.equals("false"))
                 b.setVisible(false);
             else
                 a.setVisible(false);

        bs.addActionListener((evt) ->{
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/event/delete/");

        req.setHttpMethod("POST");
        req.addArgument("id", String.valueOf(e.getId()));
         req.addResponseListener((NetworkEvent ev) -> {
            byte[] data = (byte[]) req.getResponseData();
            String t = new String(data);
            System.out.println("RETURN " + t);

        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        Dialog.show("Succés", "L'évenement "+e.getNom() +" est supprimé avec succés","Terminer",null);
            showEventsForm ok= new showEventsForm(this.getComponentForm());
            ok.show();
        });
        if (!(e.getUserId()== User.getIdOfConnectedUser()))
            bs.setVisible(true);
        Image i = Image.createImage(Display.getInstance().getDisplayWidth()/2, Display.getInstance().getDisplayHeight()/4, 0xff000000);
        EncodedImage placeholder = EncodedImage.createFromImage(i, true);
        String link = StringUtil.replaceAll(e.getAffiche(), "\\", "/");
        System.out.println(link);
        ScaleImageLabel src =new ScaleImageLabel(URLImage.createToStorage(placeholder,"img"+e.getNom() ,link, URLImage.RESIZE_SCALE));  
        this.add(src);
        this.add(c);
///////
b.addActionListener((evv)->{
    ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/event/part/");

        req.setHttpMethod("POST");
        req.addArgument("id", String.valueOf(User.getIdOfConnectedUser()));
        req.addArgument("ev", String.valueOf(e.getId()));
         req.addResponseListener((NetworkEvent ev) -> {
            byte[] data = (byte[]) req.getResponseData();
            String t = new String(data);
            System.out.println("RETURN " + t);

        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        Dialog.show("Succés", "Votr participation à l'évenement "+e.getNom() +" est enregistré avec succés","Terminer",null);
            l.setText("Nombre des places dispos:"+ (e.getPlaces_dispo()-1));
            e.setPlaces_dispo(e.getPlaces_dispo()-1);
            a.setVisible(true);
            b.setVisible(false);
    
});

a.addActionListener((evv)->{
    ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/event/apart/");

        req.setHttpMethod("POST");
        req.addArgument("id", String.valueOf(User.getIdOfConnectedUser()));
        req.addArgument("ev", String.valueOf(e.getId()));
         req.addResponseListener((NetworkEvent ev) -> {
            byte[] data = (byte[]) req.getResponseData();
            String t = new String(data);
            System.out.println("RETURN " + t);

        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        Dialog.show("Succés", "Votre annulation de participation à l'évenement "+e.getNom() +" est enregistré avec succés","Terminer",null);
            l.setText("Nombre des places dispos:"+(e.getPlaces_dispo()+1));
            e.setPlaces_dispo(e.getPlaces_dispo()+1);
            a.setVisible(false);
            b.setVisible(true);
    
});
 
bstat.addActionListener((ev)->{
    satat.nb= Double.valueOf(String.valueOf(e.getPlaces_dispo()));
satat fs = new satat("statistiques", this.getComponentForm());
fs.show();

});
        
        
        
    }
    
}
