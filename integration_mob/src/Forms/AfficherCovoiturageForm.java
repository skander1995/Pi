package Forms;

import AbstractClass.AbstractForm;
import Services.CovoiturageService;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Entite.Covoiturage;

/**
 *
 * @author Moez
 */
public class AfficherCovoiturageForm extends AbstractForm {

    private Form f;
    public static Covoiturage Cov;

    //
    public AfficherCovoiturageForm(Form sender) {
        super("Afficher tous les annonce", sender);
        this.addComponent(BorderLayout.CENTER, Afficher(sender));
    }

    public Form Afficher(Form sender) {

        Form f = new Form();
//       sender = new Form("afficher Covoiturageeeeeeeeeeeeeeeeee");

        CovoiturageService serviceTask = new CovoiturageService();

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton liste = RadioButton.createToggle("Liste des publications", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Publier", barGroup);
        partage.setUIID("SelectBar");
        for (Covoiturage Co : serviceTask.getList2()) {
            //Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            String dd = formater.format(Co.getDATEDEPART());
            Label dated = new Label("date départ: " + dd);

            //date.setText(serviceTask.getList2().get(0).getDatedenaissance());
            Label lieudepart = new Label("lieu départ: " + Co.getLIEUDEPART());
            Label lieuarrive = new Label("lieu arrive: " + Co.getLIEUARRIVE());
            Label Description = new Label("Description: " + Co.getDESCRIPTION());
            Label prix = new Label("prix: " + Co.getPRIX());
            Label nbplace = new Label("nbplace: " + Co.getNBPLACE());
            C2.add(dated);

            C2.add(lieudepart);
            C2.add(lieuarrive);
            C2.add(Description);
            C2.add(prix);
            C2.add(nbplace);
            C2.setLeadComponent(lieudepart);
            lieudepart.addPointerPressedListener((evt) -> {
                Cov = Co;
                ListCovoiturage ls = new ListCovoiturage(f);
                ls.getSender().show();
            });

//        this.hi.add(date);
            f.add(C2);
            Label l = new Label("-----------------------------------------------------");
            f.add(l);
//            f.getToolbar().addCommandToOverflowMenu("Retour", null, (evt) -> {
////             AcceuilForm acceuil = new AcceuilForm(sender);
////                    acceuil.show();   
//            });

//           this.sender.add(BorderLayout.CENTER,C2);
        }
        /* ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/get/cov/all");

        req.setHttpMethod("POST");
        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            System.out.println(s);
           Map<String,Object> events = mapToJson(s);
         //  /*this.setLayout(new GridLayout(4));
          // this.setLayout(new BorderLayout(CENTER_BEHAVIOR_CENTER_ABSOLUTE));
           //Container c = new Container(new GridLayout(1,1));
           
           this.setLayout(new GridLayout(events.size()+1, 1));
           for (int i=0;i<=events.size();i++){
               Container cc = new Container(new GridLayout(5,1));
               
               cc.addComponent(new Label((String)((Map)((ArrayList)events.get("root")).get(i)).get("lieudepart")));
               cc.addComponent(new Label((String)((Map)((ArrayList)events.get("root")).get(i)).get("lieuarrive")));
               cc.addComponent(new Label((String)((Map)((ArrayList)events.get("root")).get(i)).get("description")));
              // cc.addComponent(new Label((String)((Map)((ArrayList)events.get("root")).get(i)).get(String.valueOf("nbplace"))));
               //cc.addComponent(new Label((String)((Map)((ArrayList)events.get("root")).get(i)).get(String.valueOf("prix"))));
               cc.addComponent(new Label((String)((Map)((ArrayList)events.get("root")).get(i)).get("datedebut")));
               
            //l.setText(l.getText()+(String) ((Map) ((Map) ((ArrayList) events.get("root")).get(i)).get("id_usr")).get("email"));
             this.addComponent(cc);
           }
           
          // this.addComponent(BorderLayout.CENTER,c);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public Map<String, Object> mapToJson(String json) {
        ArrayList<Covoiturage> list = new ArrayList<Covoiturage>();
        Map<String, Object> events= null;
        try {
            JSONParser j = new JSONParser();
            events = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(events);
            System.out.println(((ArrayList) events.get("root")));
            System.out.println(((ArrayList) events.get("root")).size());

        } catch (IOException ex) {
        }
        return events ;

    }
         */
        return f;
    }

}
