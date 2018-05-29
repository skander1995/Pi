/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import Utilities.ToolsUtilities;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER_BEHAVIOR_CENTER_ABSOLUTE;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridBagLayout;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.Entite.Evenement;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author SELLAMI
 */
public class AjoutEvtForm extends AbstractForm {
    int nb=0;
    int imagepl =0;
    private Label myPic;
    public String filename;
    public Evenement e = new Evenement();
    final TextField desc = new TextField();
    final TextField titre = new TextField();
    final TextField numb = new TextField();
    //final TextField nom = new TextField();
    final TextField lieu = new TextField();
    final Picker dateD = new Picker();
    final Picker dateF = new Picker();

    public AjoutEvtForm(Form f) {
        super("Créerr un évenement", f);
        this.setLayout(new GridLayout(4));

        titre.setHint("Titre");
        desc.setHint("Description");
        lieu.setHint("Lieu");
        //Label l = new Label("Créer un évenement");
        this.setLayout(new BorderLayout(CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Container c = new Container(new GridLayout(11, 1));
        c.setScrollableY(true);
        /////////////////////
        
        Button button = new Button("choisir une image");
        button.addPointerPressedListener((evt) -> {
            Display.getInstance().openImageGallery(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if (evt == null) {
                        return;
                    }
                    String filename = (String) evt.getSource();

                    if (Dialog.show("Send file?", filename, "OK", "Cancel")) {
                        MultipartRequest req = new MultipartRequest();
                        String endpoint = ToolsUtilities.ServerIp + "/piproj/web/app_dev.php/event/uploadphoto/";
                        //String endpoint = ToolsUtilities.ServerIp + "/piproj/web/app_dev.php/uploadphoto";
                        System.out.println("endpoint  : " + endpoint);

                        req.setUrl(endpoint);
                        req.addArgument("message", "test");
                        InputStream is = null;
                        try {
                            
                            is = FileSystemStorage.getInstance().openInputStream(filename);
                            req.addData("file", is, FileSystemStorage.getInstance().getLength(filename), "image/jpeg");
                            req.setFilename("file", filename);//any unique name you want
                            req.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    byte[] data = (byte[]) req.getResponseData();
                                    String s = new String(data);
                                    System.out.println("s = " + s);
                                    if (s.equals("ok")) {
// entité = nom de la photo
                                        System.out.println("SUCCESS , Let's update colocation");
                                        String newfilename = filename.substring(filename.lastIndexOf("/") + 1, filename.length());
                                        System.out.println("Tesytin : " + newfilename);
                                        e.setAffiche("http://localhost/piproj/web/uploads/images/" + newfilename);
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueue(req);
                            imagepl=1;
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }
            });
        });

        

        ////////////
        
        Slider s = new Slider();
                s.setEditable(true);
                s.setEnabled(true);
                Label t = new Label("Nombres des places : ");
                s.setMaxValue(250);
                
                s.addDataChangedListener(new DataChangedListener() {
                    @Override
                    public void dataChanged(int type, int index) {
                        t.setText("Nombres des places :"+index);
                        nb=index;
                    }
                });
        numb.setHint("Nombre des places ");
        numb.setConstraint(TextArea.NUMERIC);
        //c.addComponent(l);
        c.addComponent(titre);
        c.addComponent(desc);
        c.addComponent(lieu);
        c.addComponent(t);
        c.addComponent(s);
        c.addComponent(new Label("Date début"));
        c.addComponent(dateD);
        c.addComponent(new Label("Date fin"));
        c.addComponent(dateF);
        c.addComponent(button);
        Button b = new Button("Créer");
        //c.addComponent(myPic);
        c.addComponent(b);
        

        b.addActionListener((evt) -> {
            if(lieu.getText().length()==0||titre.getText().length()==0||desc.getText().length()==0||nb==0||imagepl==0){
                if(nb==0)
         Dialog.show("Erreur", "Le nombre des places dispos doit etre superieur à 0", "Ok", null);

                    else
         Dialog.show("Erreur", "Tous les champs doivent etre remplis", "OK", null);
                if(imagepl==0)
         Dialog.show("Erreur", "Il faut ajouter un image", "OK", null);
            }else{
                
            if ((int)(dateD.getDate().getTime()-dateF.getDate().getTime())>0){
            Dialog.show("Erreur", "La date de début doit etre anterieure à celle de fin", "Ok", null);
 
            }else{
            //Evenement e = new Evenement(lieu.getText(), 1, filename, titre.getText(), dateD.getDate(), dateF.getDate(), desc.getText(), 25);
            e.setLieu(lieu.getText());
            e.setUserId(User.getIdOfConnectedUser());
            e.setNom(titre.getText());
            e.setDate_debut(dateD.getDate());
            e.setDate_fin(dateF.getDate());
            e.setDescription(desc.getText());
            e.setPlaces_dispo(nb);
                System.out.println(e.getAffiche());           
 AjouterEvent(e);
            }
        }});
        this.addComponent(BorderLayout.CENTER, c);

    }

    public boolean AjouterEvent(Evenement e) {
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/event/");

        req.setHttpMethod("POST");
        req.addArgument("placedispo", String.valueOf(e.getPlaces_dispo()));
        req.addArgument("lieu", e.getLieu());
        req.addArgument("titre", e.getNom());
        req.addArgument("iduser", String.valueOf(User.getIdOfConnectedUser()));
        req.addArgument("dated", ToolsUtilities.formater.format(e.getDate_debut()));
        req.addArgument("datef", ToolsUtilities.formater.format(e.getDate_fin()));
        req.addArgument("desc", e.getDescription());
        req.addArgument("affiche", e.getAffiche());
        System.out.println(filename);

        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            System.out.println("RETURN " + s);
            //////////
            Map<String,Object> events = mapToJson(s);
            System.out.println(events);
            System.out.println("_______"+(String)events.get("affiche"));
            System.out.println((String)events.get("description"));
            //System.out.println("this is the name ____"+(((ArrayList)events.get("id_usr")).get(0)));
            //System.out.println("this is the name ____"+((ArrayList)(((ArrayList)events.get("id_usr")).get(0))).get(1));
            
               Evenement ev = new Evenement();
               ev.setAffiche((String)events.get("affiche"));
               ev.setDescription((String)events.get("description"));
               ev.setNom((String)events.get("nom_event"));
               String dd = (String)(String)events.get("datedebut");
               dd = dd.substring(0,10 );
               String df = (String)(String)events.get("datefin");
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
               ev.setDate_debut(dated);
              ev.setDate_fin(datef);
               
               int nbp = ((Double)events.get("place_dispo")).intValue();
               ev.setPlaces_dispo(nbp);
               ev.setId(((Double) events.get("id")).intValue() );
               String username ="root";
               Dialog.show("Succés","Evenement ajouté avec succés","Consulter",null);
               ////mail
               
               ShowEventSingleForm ses = new ShowEventSingleForm(this.getComponentForm(), ev, username);
            ses.show();
            
            
            
            /////////

        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return false;
    }
    
    public Map<String, Object> mapToJson(String json) {
        ArrayList<Evenement> list = new ArrayList<Evenement>();
        Map<String, Object> events= null;
        try {
            JSONParser j = new JSONParser();
            events = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(events);
            System.out.println(((Double) events.get("id")));
            //System.out.println(((ArrayList) events.get("root")).size());

            
            //System.out.println((String) ((Map) ((Map) ((ArrayList) events.get("root")).get(0)).get("id_usr")).get("email"));
        } catch (IOException ex) {
        }
        return events ;

    }

}
