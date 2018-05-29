/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Utilities.ToolsUtilities;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import static com.codename1.io.Log.e;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.mycompany.Entite.Covoiturage;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Moez
 */
public class CovoiturageService {

    public void newCov(Covoiturage Co) {
        ConnectionRequest con = new ConnectionRequest();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd");
        String dateFormate = formater.format(Co.getDATEDEPART());
        con.setPost(false);

        String Url = "http://localhost/piproj/web/app_dev.php/new/cov";
        System.out.println(Url);
        //con.setHttpMethod("POST");
        con.setUrl(Url);
        con.addArgument("id", String.valueOf(Co.getID_USR()));
        System.out.println(String.valueOf(User.getIdOfConnectedUser()));

        con.addArgument("dated", dateFormate);
        con.addArgument("lieudepart", Co.getLIEUDEPART());
        con.addArgument("lieuarrive", Co.getLIEUARRIVE());
        con.addArgument("nbplace", String.valueOf(Co.getNBPLACE()));
        con.addArgument("prix", String.valueOf(Co.getPRIX()));
        con.addArgument("description", Co.getDESCRIPTION());

        System.out.println("Connected USER " + User.getActifUser().getId());
        System.out.println("Connected ID USER " + User.getIdOfConnectedUser());

        con.addResponseListener((e) -> {

            System.out.println("1111111111111111" + Url);
            String str = new String(con.getResponseData());
            System.out.println(str);

            System.out.println("1111111111111111" + Url);
//            if (str.trim().equalsIgnoreCase("OK")) {
//                f2.setTitle(tlogin.getText());
//             f2.show();
//            }
//            else{
//            Dialog.show("error", "login ou pwd invalid", "ok", null);
//            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public ArrayList<Covoiturage> getListTask(String json) {

        ArrayList<Covoiturage> listEtudiants = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            //System.out.println("sssssssssss;"+etudiants);

            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("root");
            System.out.println(list);
            for (Map<String, Object> obj : list) {
                Covoiturage e = new Covoiturage();
                //System.out.println("PPPPPPPPPPP"+obj);
                //Map<String, Object> dates = (Map<String, Object>) obj.get("datedepart");
                String date = obj.get("datedepart").toString().substring(0, 10);
                System.out.println("DATE : " + date);
                //float date = Float.parseFloat(dates.get("timestamp").toString());
                //Date dated = new Date((long) (date - 3600) * 1000);
                //  System.out.println(obj.get(1));
                //   System.out.println(obj.get("id"));
                //  float id = Float.parseFloat(obj.get("id").toString());
                //  System.out.println(id);
                //     e.setID_USR((int) id);
                //  String image = obj.get("image").toString();
                // e.setImage(image);
                // e.setId(Integer.parseInt(obj.get("id").toString().trim()));

                Date dated = new Date();
                try {
                    dated = ToolsUtilities.formater.parse(date);
                } catch (ParseException ex) {
                   
                }
                
                System.out.println("DATE DEPART "+dated);

                e.setDATEPUB(dated);

                e.setLIEUDEPART(obj.get("lieudepart").toString());
                e.setLIEUDEPART(obj.get("lieudepart").toString());
                //  e.setNom(obj.get("nomproprietaire").toString());
                e.setLIEUARRIVE(obj.get("lieuarrive").toString());
                e.setDESCRIPTION(obj.get("description").toString());
                // e.setNBPLACE(obj.get("sexe").toString());
                e.setNBPLACE((int) Float.parseFloat(obj.get("nbplace").toString()));
                float prix = Float.parseFloat(obj.get("prix").toString());
                e.setPRIX((int) prix);

                //   e.setDatedenaissance(obj.get("datedenaissance.timezone.transitions.0.time").toString());
                //   String datestr = ((Map) ((Map) ((Map) obj.get("datedenaissance")).get("timezone")).get("transitions")).get("time").toString();
                // System.out.println("OBJ " + obj);
                //  System.out.println("OBJ " + ((LinkedHashMap) obj.get("datedenaissance")).get("timezone"));
                //  e.setNom(obj.get("name").toString());
                System.out.println(e);
                listEtudiants.add(e);

            }

        } catch (IOException ex) {
        }
        System.out.println(listEtudiants);
        return listEtudiants;

    }
    ArrayList<Covoiturage> listTasks = new ArrayList<>();

    public ArrayList<Covoiturage> getList2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1/piproj/web/app_dev.php/get/cov/all");
        con.addResponseListener((NetworkEvent evt) -> {
            CovoiturageService ser = new CovoiturageService();
            listTasks = ser.getListTask(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }
}
