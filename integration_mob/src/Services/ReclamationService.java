/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Forms.ReclamationSwipeForm;
import Utilities.ToolsUtilities;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.mycompany.Entite.Reclamation;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cobwi
 */
public class ReclamationService {

    Reclamation persistedPub;
    Map<String, Object> recs = null;
    List<Reclamation> listOfRec = null;

    public Reclamation mapToJson(String json) {
        Reclamation pub = new Reclamation();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> pubs = j.parseJSON(new CharArrayReader(json.toCharArray()));
            if (pubs != null) {
                //Map get = (Map) pubs.get("root");
                pub.setId_pub(Integer.valueOf(pubs.get("id").toString().substring(0, pubs.get("id").toString().indexOf('.'))));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Invalid Data JsonResponse : " + json);
        }
        return pub;

    }

    public Reclamation persist(Reclamation reclamation) {
        // let's send it
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/reclamation/api/new/");
        //  req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/reclamation/");

        req.setHttpMethod("POST");
        req.addArgument("description", reclamation.getDescription());
        req.addArgument("sujet", reclamation.getSujet());
        req.addArgument("type", reclamation.getType());
        req.addArgument("idreclamation", String.valueOf(User.getIdOfConnectedUser()));

        if (reclamation.getUse_id_usr() > -1) {
            req.addArgument("id_use_reclamation", String.valueOf(reclamation.getUse_id_usr()));
        }

        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            persistedPub = mapToJson(s);
            System.out.println("RESPON" + s);
            if (s.indexOf("fail") > -1) {
                System.out.println("Persist : success");

            } else {
                System.out.println("Persist : failed");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return persistedPub;
    }

    public List<Reclamation> getAllByUser() {
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/reclamation/api/get");
        req.addArgument("idusr", String.valueOf(User.getIdOfConnectedUser()));

        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            listOfRec = new ArrayList<Reclamation>();
            try {
                JSONParser j = new JSONParser();
                recs = j.parseJSON(new CharArrayReader(s.toCharArray()));
                List listMappedUser = (List) recs.get("root");
                for (int i = 0; i < listMappedUser.size(); i++) {
                    Map get = (Map) listMappedUser.get(i);
                    Reclamation reclamation = new Reclamation();
                    reclamation.setId_pub(Integer.valueOf(get.get("ID_PUB").toString()));

                    //.substring(0, get.get("ID_PUB").toString().indexOf('.'))));
                    try {
                        reclamation.setDatePub(ToolsUtilities.formater.parse(get.get("DATEPUB").toString()));
                    } catch (ParseException ex) {
                    }
                    reclamation.setSujet(get.get("SUJET_REC").toString());
                    reclamation.setDescription(get.get("DESCRIPTION").toString());

                    reclamation.setType(get.get("TYPE_REC").toString());
                    reclamation.setEtat(get.get("ETAT").toString());
                    listOfRec.add(reclamation);
                }
            } catch (IOException ex) {
            }
            if (s.equals("fail")) {
                System.out.println("Persist : failed");
            } else {
                System.out.println("Persist : success");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return listOfRec;
    }

    public void deleteReclamation(int id_pub) {
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/reclamation/api/delete/");
        //  req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/reclamation/");
        req.setHttpMethod("POST");
        req.addArgument("idpub", String.valueOf(id_pub));

        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            System.out.println("RESPON" + s);
            if (s.indexOf("fail") == -1) {
                System.out.println("delete : success");
                //init from here
            } else {
                System.out.println("delete : failed");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

}
