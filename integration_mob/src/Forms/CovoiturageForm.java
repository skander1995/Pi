/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import Services.CovoiturageService;
import Utilities.ToolsUtilities;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.Entite.Covoiturage;
import com.mycompany.Entite.User;

/**
 *
 * @author Moez
 */
public class CovoiturageForm  extends AbstractForm {
    
    //private Form f;
    TextField nbplace;
    TextField prix;
    TextField description;

    Button btnajout,btnaff;
    ComboBox<String> lieudepart;
    ComboBox<String> lieuarriver;
    Picker datedepart;

    public CovoiturageForm (Form sender){
    super("Covoiturage", sender);
    this.addComponent(BorderLayout.CENTER,init(sender));
    
    }
    
     public Form init(Form sender) {
        
  
       Form f = new Form();
        f.setLayout(new GridLayout(14,1));
        f.setScrollableY(true);
        nbplace = new TextField();
        prix = new TextField();
        description = new TextField();
        btnajout = new Button("ajouter");
        btnaff=new Button("Affichage");
        
   lieudepart= new ComboBox();
         lieudepart.addItem("Ariana");
        lieudepart.addItem("Béja");
        lieudepart.addItem("Ben Arous");
        lieudepart.addItem("Bizerte");
        lieudepart.addItem("Gabes");
        lieudepart.addItem("Gafsa");
        lieudepart.addItem("Jendouba");
        lieudepart.addItem("Kairouan");
        lieudepart.addItem("Kasserine");
        lieudepart.addItem("Kebili");
        lieudepart.addItem("La Manouba");
        lieudepart.addItem("Le Kef");
        lieudepart.addItem("Mahdia");
        lieudepart.addItem("Médenine");
        lieudepart.addItem("Monastir");
        lieudepart.addItem("Nabeul");
        lieudepart.addItem("Sfax");
        lieudepart.addItem("Sidi Bouzid");
        lieudepart.addItem("Siliana");
        lieudepart.addItem("Sousse");
        lieudepart.addItem("Tataouine");
        lieudepart.addItem("Tozeur");
        lieudepart.addItem("Tunis");
        lieudepart.addItem("Zaghouan");

  lieuarriver= new ComboBox();
         lieuarriver.addItem("Ariana");
        lieuarriver.addItem("Béja");
        lieuarriver.addItem("Ben Arous");
        lieuarriver.addItem("Bizerte");
        lieuarriver.addItem("Gabes");
        lieuarriver.addItem("Gafsa");
        lieuarriver.addItem("Jendouba");
        lieuarriver.addItem("Kairouan");
        lieuarriver.addItem("Kasserine");
        lieuarriver.addItem("Kebili");
        lieuarriver.addItem("La Manouba");
        lieuarriver.addItem("Le Kef");
        lieuarriver.addItem("Mahdia");
        lieuarriver.addItem("Médenine");
        lieuarriver.addItem("Monastir");
        lieuarriver.addItem("Nabeul");
        lieuarriver.addItem("Sfax");
        lieuarriver.addItem("Sidi Bouzid");
        lieuarriver.addItem("Siliana");
        lieuarriver.addItem("Sousse");
        lieuarriver.addItem("Tataouine");
        lieuarriver.addItem("Tozeur");
        lieuarriver.addItem("Tunis");
        lieuarriver.addItem("Zaghouan");
        
         datedepart= new Picker();
        
        
        f.addComponent(new Label("Lieu de départ:"));
        f.add(lieudepart);

        f.addComponent(new Label("Lieu d'arriveé:"));
        f.add(lieuarriver);

        f.addComponent(new Label("Date de départ:"));
        f.add(datedepart);
        f.addComponent(new Label("Nombre de place:"));
        f.add(nbplace);
        f.addComponent(new Label("Prix par place:"));
        f.add(prix);
        f.addComponent(new Label("Description:"));
        f.add(description);
        f.add(btnajout);
        f.add(btnaff);
        
        btnajout.addActionListener((e) -> {
          //  Covoiturage c = new Covoiturage(DATEPUB, LIEUDEPART, LIEUARRIVE, Float.parseFloat(prix.getText()), 0;
            Covoiturage c = new Covoiturage(description.getText(), lieudepart.getSelectedItem(), lieuarriver.getSelectedItem(), datedepart.getDate(), Float.parseFloat(prix.getText()),Integer.valueOf(nbplace.getText()));
            CovoiturageService CS = new CovoiturageService();
            c.setID_USR(User.getIdOfConnectedUser());
            CS.newCov(c);
            AfficherCovoiturageForm aff= new AfficherCovoiturageForm(sender);
            aff.show();
          //  ServiceTask ser = new ServiceTask();
            //Task t = new Task(0, tnom.getText(), tetat.getText());
            //ser.ajoutTask(t);
            

        });
        btnaff.addActionListener((e)->{
       //AfficherCovoiturage a=new AfficherCovoiturage();
      // a.getF().show();
        });
        return f;
    }

    
   

    private void afficherTout() {
        ConnectionRequest req = new ConnectionRequest();
        //req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piwebService/webresources/com.mycompany.piwebservice.user/login/" + username.getText() + "");
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/get/cov/all");
        System.out.println("REQU : " + req.getUrl());
        req.setHttpMethod("POST");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                byte[] data = (byte[]) req.getResponseData();
                String s = new String(data);
                //User user = mapToJson(s);
                System.out.println("s = " + s);
            }
        });
        
        NetworkManager.getInstance().addToQueue(req);
    }

   
}
