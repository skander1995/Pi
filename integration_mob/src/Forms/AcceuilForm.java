/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import CustomContainers.PublicationContainer;
import Utilities.ToolsUtilities;
import com.codename1.components.FloatingActionButton;
import com.codename1.io.CharArrayReader;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.Publication;
import com.mycompany.Entite.User;
import java.io.IOException;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author cobwi
 */
public class AcceuilForm extends AbstractForm {

    private Resources theme;
    ArrayList<Publication> list = new ArrayList<>();

    public AcceuilForm(Form f) {
        super("Acceuil", f);
        try {
            theme = Resources.openLayered("/theme4");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initAcceuil();
    }

    private void initAcceuil() {
        setScrollable(true);
        setScrollableY(true);
        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        content.setLayout(new BorderLayout());
        ArrayList<Publication> publication = grabLastUpdates();
        PublicationContainer container = new PublicationContainer(publication);
        //this.add(BorderLayout.SOUTH, container);
        content.addComponent(BorderLayout.EAST, container);
        this.setScrollable(true);
        this.addComponent(BorderLayout.EAST, content);

    }

    private ArrayList<Publication> mapToArrayList(Map<String, Object> pubs) {
        ArrayList<Publication> listToReturn = new ArrayList<>();
        ArrayList listOfPublication = (ArrayList) pubs.get("root");
        for (int i = 0; i < listOfPublication.size(); i++) {
            Publication pub = new Publication();
            Map pubFromList = (Map) listOfPublication.get(i);
            // let's map

            try {
                pub.setUsername(pubFromList.get("username").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setNomprenom(pubFromList.get("nomprenom").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setType(pubFromList.get("evenement").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setLinkAffiche(pubFromList.get("AFFICHE").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setTitre(pubFromList.get("titre").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setLinkPhotoProfile(pubFromList.get("photoprofile").toString());
            } catch (Exception ex) {

            }
            try {
                pub.setIdUsr(pubFromList.get("idUsr").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setETAT(pubFromList.get("ETAT").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setID_PUB(pubFromList.get("ID_PUB").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setDESCRIPTION(pubFromList.get("DESCRIPTION").toString());

            } catch (Exception ex) {

            }
            try {
                pub.setDATEPUB(pubFromList.get("DATEPUB").toString());
            } catch (Exception ex) {

            }

            listToReturn.add(pub);
            // System.out.println("PUBLICATION AFTER MAP" + pub.toString());
        }
        return listToReturn;
    }

    private ArrayList<Publication> grabLastUpdates() {

        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/acceuil/init");
        System.out.println(req.getUrl());
        req.setHttpMethod("GET");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                byte[] data = (byte[]) req.getResponseData();
                String s = new String(data);
                Map<String, Object> pubs = mapToJson(s);
                if (s.equals("fail")) { 
                    System.out.println("FAILED");
                    Dialog.show("Erreur d'initialisation", "La connexion avec le serveur de données est impossible", "Ok", null);
                } else {
                    //treat dat data : from listOF (Strings) --> Mapped To MAP --> to ListOF(Publication)
                    list = mapToArrayList(pubs);
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return list;
    }

    public Map<String, Object> mapToJson(String json) {

        Map<String, Object> pubs = null;
        try {
            JSONParser j = new JSONParser();
            pubs = j.parseJSON(new CharArrayReader(json.toCharArray()));

            if (pubs != null) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Invalid Data JsonResponse : " + json);
        }
        return pubs;

    }

}
