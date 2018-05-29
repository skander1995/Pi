/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import Entities.Evenement;
import Utilities.ToolsUtilities;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author SELLAMI
 */
public class ConsultEventController implements Initializable, MapComponentInitializedListener {

    @FXML
    private ImageView image;
    @FXML
    private Label titre;
    @FXML
    private Label specs;
    @FXML
    private TextArea Description;
    public Evenement ev;
    @FXML
    private GoogleMapView gmap;
    private GoogleMap map;
    private GeocodingService G;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void createMap() {
        map = new GoogleMap();
        G = new GeocodingService();
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(33.8869, 9.5375))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(true)
                .panControl(true)
                .rotateControl(true)
                .scaleControl(true)
                .streetViewControl(true)
                .zoomControl(true)
                .zoom(15);

        map = gmap.createMap(mapOptions);
    }

    public void start() {
        try {
            gmap.addMapInializedListener(this);
            Image i = new Image(ev.getAffiche());
            image.setImage(i);

            System.out.println("there");
            titre.setText(ev.getNom());
            Description.setText("Description:\n" + ev.getDescription());
            Description.setEditable(false);
            specs.setText("-Date début :\n" + ToolsUtilities.formater.format(ev.getDate_debut()) + "\n-Date de fin:\n" + ToolsUtilities.formater.format(ev.getDate_fin()) + "\n-Nombre des places encore disponble:\n" + ev.getPlaces_dispo());
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }

        try {
            System.out.println(ToolsUtilities.formater.parse(ToolsUtilities.formater.format(ev.getDate_debut())));
        } catch (ParseException ex) {
            Logger.getLogger(ConsultEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mapInitialized() {
        try {
            createMap();

            String L = ev.getLieu();

            G.geocode(L, (GeocodingResult[] results, GeocoderStatus status) -> {

                LatLong latLong = null;

                if (status == GeocoderStatus.ZERO_RESULTS) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                    alert.show();
                    return;
                } else if (results.length > 1) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                    alert.show();
                    latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
                } else {
                    latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    System.out.println("result LG " + latLong.getLongitude() + "   " + latLong.getLatitude());
                    markerOptions.position(latLong)
                            .visible(Boolean.TRUE)
                            .title("My Marker");

                    Marker marker = new Marker(markerOptions);
                    map.addMarker(marker);

                }

                map.setCenter(latLong);

            }
            );
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

}
