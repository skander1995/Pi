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
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entite.Colocation;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS I7
 */
public class ColocationService {

    public void newCol(Colocation Co) {
        ConnectionRequest con = new ConnectionRequest();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd");
        String dateFormate = ToolsUtilities.formater.format(Co.getDATEDISPONIBILITE());
        con.setPost(false);

        String Url = "http://localhost/piproj/web/app_dev.php/add/col/";
        System.out.println(Url);
        //con.setHttpMethod("POST");
        con.setUrl(Url);
        con.addArgument("id", String.valueOf(User.getIdOfConnectedUser()));
        System.out.println(String.valueOf(User.getIdOfConnectedUser()));

        con.addArgument("datededisponibilite", dateFormate);
        con.addArgument("lieu", Co.getLIEU());
        con.addArgument("description", Co.getDESCRIPTION());
        con.addArgument("Loyermensuel", String.valueOf(Co.getLOYERMENSUEL()));
        con.addArgument("Nbchambre", String.valueOf(Co.getNBCHAMBRE()));
        con.addArgument("Commodite", Co.getCOMMODITE());
        con.addArgument("imagcouverture", Co.getIMGCOUVERTURE());
        System.err.println("______" + Co.getIMGCOUVERTURE());
        System.out.println("Connected USER " + User.getActifUser().getId());
        System.out.println("Connected ID USER " + User.getIdOfConnectedUser());
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    private void afficherTout() {
        ConnectionRequest req = new ConnectionRequest();
        //req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piwebService/webresources/com.mycompany.piwebservice.user/login/" + username.getText() + "");
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/collocation");
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

    public ArrayList<Colocation> getListTask(String json) throws com.codename1.l10n.ParseException, IOException {

        ArrayList<Colocation> listeColocation = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> colocation = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(colocation);

            List<Map<String, Object>> list = (List<Map<String, Object>>) colocation.get("root");

            for (Map<String, Object> obj : list) {
                Colocation c = new Colocation();

                // System.out.println(obj.get("id"));
                float id = Float.parseFloat(obj.get("id").toString());
                float idusr = Float.parseFloat(obj.get("idUsr").toString());

//                String datedisponibilite= obj.get("date de disponibilite").toString();
                Double LOYERMENSUEL = Double.valueOf(obj.get("loyermensuel").toString());
                float NBCHAMBRE = Float.parseFloat(obj.get("nbchambre").toString());
                String LIEU = obj.get("lieu").toString();
                String DESCRIPTION = obj.get("description").toString();
                String Commodite = obj.get("commodite").toString();
                String image = obj.get("imgcouverture").toString();

                // System.out.println("DD/"+date);
                //System.out.println("DF/"+datefin);
                Map<String, Object> date2 = (Map<String, Object>) obj.get("datedisponibilite");
                float da = Float.parseFloat(date2.get("timestamp").toString());
                Date d = new Date((long) (da - 3600) * 1000);
//                  Map<String, Object> date3  = (Map<String, Object>) obj.get("datefin");
//                 float da2 = Float.parseFloat(date3.get("timestamp").toString());
//                 Date d2 = new Date((long)(da2-3600)*1000);
                System.out.println(id);
                //  u.setEmail(email);
                //  u.setNom(nom);

                c.setID_PUB((int) id);
                c.setID_USR((int) idusr);
                c.setDESCRIPTION(DESCRIPTION);
                c.setLOYERMENSUEL(LOYERMENSUEL);
                c.setNBCHAMBRE((int) NBCHAMBRE);
                c.setLIEU(LIEU);
                c.setDESCRIPTION(DESCRIPTION);
                c.setDATEDISPONIBILITE(d);
                c.setIMGCOUVERTURE(image);
                System.out.println(c);
                listeColocation.add(c);
            }

        } catch (IOException ex) {
        }
        System.out.println(listeColocation);
        return listeColocation;

    }
    ArrayList<Colocation> listTasks = new ArrayList<>();

    public ArrayList<Colocation> getList2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/piproj/web/app_dev.php/collocation");
        con.addResponseListener((NetworkEvent evt) -> {
            ColocationService ser = new ColocationService();
            try {
                listTasks = ser.getListTask(new String(con.getResponseData()));
            } catch (ParseException ex) {
            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    public void DeleteColoc(int co) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/piproj/web/app_dev.php/collocation/supp/";
        con.setUrl(Url);
        con.addArgument("id", String.valueOf(co));
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

}
