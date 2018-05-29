/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Utilities.ToolsUtilities;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.google.gson.Gson;
import com.mycompany.Entite.User;
import java.text.ParseException;
import java.util.Map;

/**
 *
 * @author cobwi
 */
public class UserManager {

    private User persistedUser = null;

    public UserManager() {
    }

    public User persist(User user) {
        Gson g = new Gson();
        String jsonString = g.toJson(user);
        // let's send it
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/new/user/");

        req.setHttpMethod("POST");
        req.addArgument("object", jsonString);
        req.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req.getResponseData();
            String s = new String(data);
            persistedUser = mapToJson(s);
            System.out.println("RESPON" + s);
            if (s.equals("fail")) {
                //  main.show();
                System.out.println("Persist : failed");
            } else {
                System.out.println("Persist : success");
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return persistedUser;
    }

    public User persist(User user, String socialId) {
        // perform addition check before persisting
        ConnectionRequest req1 = new ConnectionRequest();
        req1.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/check/user/" + socialId);
        req1.setHttpMethod("GET");
        req1.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req1.getResponseData();
            String s = new String(data);
            if (s.equals("notfound")) {
                //  main.show();
                System.out.println("NOT FOUND SO CREATIG NEW USER ...");
                //we can call the original persist
                user.setSocialid(socialId);
                persistedUser = persist(user);
            } else {
                persistedUser = mapToJson(s);
                System.out.println("FOUND WITH ID : success");
            }

        });
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(req1);
        req1.setDisposeOnCompletion(dlg);
        return persistedUser;
    }

    public User checkExistanceBySocialId(String id) {
        ConnectionRequest req1 = new ConnectionRequest();
        req1.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/check/user/" + id);
        req1.setHttpMethod("GET");
        req1.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) req1.getResponseData();
            String s = new String(data);
            System.out.println("RESPON" + s);
            if (s.equals("notfound")) {
                //  main.show();
                System.out.println("NOT FOUND ");
                //we can call the original persist
                persistedUser = null;
            } else {
                persistedUser = mapToJson(s);
                System.out.println("FOUND WITH SOCIAL ID : success");
            }
        });
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(req1);
        req1.setDisposeOnCompletion(dlg);
        System.out.println("RETURNED THIS FROM CHECK WITH SOCIAL ID : " + persistedUser);
        return persistedUser;
    }

    public User mapToJson(String json) {
        User user = new User();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> users = j.parseJSON(new CharArrayReader(json.toCharArray()));
            int userId = userId = Integer.valueOf(users.get("id").toString().substring(0, users.get("id").toString().indexOf('.')));

            if (users != null) {
                user.setId(userId);
                user.setPassword(users.get("password").toString());
                user.setLogin(users.get("username").toString());
                try {
                    user.setDateNaissance(ToolsUtilities.formater.parse(users.get("datenaissance").toString()));
                } catch (ParseException ex) {

                }
                user.setEmail(users.get("email").toString());
                user.setEnabled(users.get("enabled").toString().equals("true") ? 1 : 0);
                user.setNom(users.get("nom").toString());
                user.setPrenom(users.get("prenom").toString());
                try {
                    user.setPhotoProfil(users.get("photo_profile").toString());
                    System.out.println("profile" + user.getPhotoProfil());
                } catch (Exception ex) {

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Invalid Data JsonResponse : " + json);
        }
        return user;

    }

}
