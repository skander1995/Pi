/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import Utilities.ToolsUtilities;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
;
import com.mycompany.Entite.User;

/**
 *
 * @author cobwi
 */


public class ColocationForm extends AbstractForm {
    
    public ColocationForm(Form sender) {
        super("Colocation", sender);
        afficherTout();
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
