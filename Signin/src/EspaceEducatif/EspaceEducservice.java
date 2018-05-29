/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceEducatif;

import Utilities.ToolsUtilities;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Skander
 */
public class EspaceEducservice {
    //private ObservableList <ObservableList> data;

    public EspaceEducservice() {

    }

    public void Createpub(EspaceEduc o) {

        String url = ToolsUtilities.ServerIp+ToolsUtilities.port+"/FosBundleProj/web/app_dev.php/tasks/add/" + o.getId_usr()
                + "/" + o.getDocument()
                + "/" + o.getDescription()
                + "/" + o.getSection()
                + "/" + o.getMatiere();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public void deletepub(int i) {

    }

    public ArrayList<EspaceEduc> getListTask(String json) {

        ArrayList<EspaceEduc> listeAides = new ArrayList<>();

        try {
            //System.out.println("json:    "+json);
            JSONParser j = new JSONParser();
            Map<String, Object> aides = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) aides.get("root");
            //System.out.println("lista :   "+list);
            for (Map<String, Object> obj : list) {
                EspaceEduc e = new EspaceEduc();

                // System.out.println(obj.get("id"));
                float id = Float.parseFloat(obj.get("idpub").toString());
                Map<String, Object> dates = (Map<String, Object>) obj.get("datepub");
                float date = Float.parseFloat(dates.get("timestamp").toString());
                Date datepub = new Date((long) (date - 3600) * 1000);
                User us = new User();

                Map<String, Object> user = (Map<String, Object>) obj.get("idusr");

                String idusr = user.get("username").toString();

                //System.out.println(id);
                e.setId_pub((int) id);
                e.setUsername((String) idusr);
                e.setMatiere(obj.get("matiere").toString());
                e.setSection(obj.get("section").toString());
                e.setDocument(obj.get("document").toString());
                e.setDescription(obj.get("description").toString());
                e.setDatepub(datepub);
                //System.out.println(e);
                listeAides.add(e);

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(listeAides);
        return listeAides;

    }

    ArrayList<EspaceEduc> e = new ArrayList();

    public List<EspaceEduc> consulterespace() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/app_dev.php/tasks/all");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                EspaceEducservice ser = new EspaceEducservice();
                e = ser.getListTask(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return e;
    }

    public List<EspaceEduc> consulterespace(int id) {
        ArrayList e = new ArrayList();
        return e;
    }

    public void updatepub(EspaceEduc o, int i) {

    }

    public String searchusername(int i) {
        String ska = "";
        return ska;
    }

}
