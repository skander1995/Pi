/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceFAQ;

import EspaceEducatif.EspaceEduc;
import EspaceEducatif.User;
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
public class EspaceFAQservice {
    //private ObservableList <ObservableList> data;

    public EspaceFAQservice() {

    }

    public void Createpub(EspaceFAQ o, int iduser) {
        String url = ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/app_dev.php/question/add/" + iduser
                + "/" + o.getDescription();

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    ArrayList<EspaceFAQ> e = new ArrayList();

    public ArrayList<EspaceFAQ> consulterespace() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/app_dev.php/question/all");
        System.out.println(con.getUrl());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                EspaceFAQservice ser = new EspaceFAQservice();
                e = ser.getListTask(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return e;
    }

    public ArrayList<EspaceFAQ> getListTask(String json) {

        ArrayList<EspaceFAQ> listeAides = new ArrayList<>();

        try {
            //System.out.println("json:    "+json);
            JSONParser j = new JSONParser();
            Map<String, Object> aides = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println("maaaap :   "+aides);
            List<Map<String, Object>> list = (List<Map<String, Object>>) aides.get("root");
            //System.out.println("lista :   "+list);
            for (Map<String, Object> obj : list) {
                EspaceFAQ e = new EspaceFAQ();

                // System.out.println(obj.get("id"));
                float id = Float.parseFloat(obj.get("idPub").toString());

                Map<String, Object> dates = (Map<String, Object>) obj.get("datepub");
                float date = Float.parseFloat(dates.get("timestamp").toString());
                Date datepub = new Date((long) (date - 3600) * 1000);
                User us = new User();

                Map<String, Object> user = (Map<String, Object>) obj.get("idUsr");

                float idu = Float.parseFloat(user.get("id").toString());
                us.setId((int) idu);

                us.setLogin(user.get("username").toString());

                System.out.println();
                us.setPhotoProfil(user.get("photoProfile").toString());

//System.out.println(id);
                e.setId_pub((int) id);
                e.setId_usr(us);
                e.setSujet(obj.get("sujet").toString());
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

    public void deletepub(int i) {

    }

    /* public void updatepub(EspaceFAQ o, int in) {

        try {
            Statement prep = connection.getConnection().createStatement();

            String resi = "UPDATE `question` SET `DESCRIPTION`='" + o.getDescription()
                   
                    + "' WHERE ID_PUB=" + in;
            prep.executeUpdate(resi);

            System.out.println("update done");
        } catch (SQLException ex) {

            System.out.println("update error" + ex.getCause());
        }

    }
     */
 /* public List<EspaceFAQ> consulterespace(int id) {

        ArrayList<EspaceFAQ> espaceEduc = new ArrayList<>();
        try {
            Statement stm = connection.getConnection().createStatement();
            String q = "SELECT * FROM `question` where ID_USR=" + id;
            ResultSet res = stm.executeQuery(q);

            while (res.next()) {
                EspaceFAQ e = new EspaceFAQ();
                e.setId_pub(res.getInt("ID_PUB"));

                e.setDescription(res.getString("DESCRIPTION"));

                e.setDatepub(res.getTimestamp("DATEPUB"));
                e.setId_usr(res.getInt("ID_USR"));

                espaceEduc.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("ereeeeeeeur");
        }
        return espaceEduc;
    }*/
    public String searchusername(int i) {
        String m = null;

        return m;
    }

}
