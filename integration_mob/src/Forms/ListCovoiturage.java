/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.Style;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Moez
 */
public class ListCovoiturage extends AbstractForm {

    private static final String HTML_API_KEY = "AIzaSyDcwHdDoI65jU6iHlOGv54Efo67fE_AWw0";

    public ListCovoiturage(Form sender) {
        super("Afficher  annonce", sender);
       System.out.println(AfficherCovoiturageForm.Cov);
        
        sender = new Form("Map");
        Label l1 = new Label("lieu départ: " + AfficherCovoiturageForm.Cov.getLIEUDEPART());
        sender.add(l1);
        this.sender = sender;
        Label l = new Label("lieu arrive: " + AfficherCovoiturageForm.Cov.getLIEUARRIVE());
        sender.add(l);
        Style s = new Style();
        s.setFgColor(0xFF7F50);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(1));
        FontImage markerImg1 = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(1));
        MapContainer cn = new MapContainer();

        cn.zoom(getCoords(AfficherCovoiturageForm.Cov.getLIEUDEPART()), 5);

        cn.setCameraPosition(getCoords(AfficherCovoiturageForm.Cov.getLIEUDEPART())); // since the image is iin the jar this is unlikely
        cn.addMarker(EncodedImage.createFromImage(markerImg, false), getCoords(AfficherCovoiturageForm.Cov.getLIEUDEPART()), "Hi marker", "Optional long description", new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Dialog.show("Marker Clicked!", "You clicked the marker", "OK", null);
            }
        });
        cn.setCameraPosition(getCoords(AfficherCovoiturageForm.Cov.getLIEUARRIVE())); // since the image is iin the jar this is unlikely
        cn.addMarker(EncodedImage.createFromImage(markerImg1, false), getCoords(AfficherCovoiturageForm.Cov.getLIEUARRIVE()), "Hi marker", "Optional long description", new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Dialog.show("Marker Clicked!", "You clicked the marker", "OK", null);
            }
        });

        sender.add(cn);
        sender.show();

    }

    public Form getSender() {
        return sender;
    }

    public void setSender(Form sender) {
        this.sender = sender;
    }

    public static Coord getCoords(String address) {
        Coord ret = null;
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json", false);
            request.addArgument("key", HTML_API_KEY);
            request.addArgument("address", address);

            NetworkManager.getInstance().addToQueueAndWait(request);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            if (response.get("results") != null) {
                ArrayList results = (ArrayList) response.get("results");
                if (results.size() > 0) {
                    LinkedHashMap location = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) results.get(0)).get("geometry")).get("location");
                    ret = new Coord((double) location.get("lat"), (double) location.get("lng"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
