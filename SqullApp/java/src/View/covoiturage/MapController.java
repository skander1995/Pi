/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Covoiturage;

import Entities.Covoiturage;
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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import View.Covoiturage.AfficherTousCovoiturageController;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import javafx.beans.property.Property;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Linab
 */
public class MapController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    //  private WebView map;
    @FXML
    private GoogleMapView gmap;
    private GoogleMap map;
    private GeocodingService G;
    private boolean ready;
    @FXML
    private Button retour;

    private String container;
    @FXML
    private Label distance;
    LatLong departContainer = null;
    LatLong arriveContainer = null;

    public static Covoiturage c;
    protected DirectionsService directionsService;
    protected DirectionsPane directionsPane;

    protected StringProperty from = new SimpleStringProperty();
    protected StringProperty to = new SimpleStringProperty();
    @FXML
    private TextField arr;
    @FXML
    private TextField dep;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        arr.setText(c.getLIEUARRIVE());
        dep.setText(c.getLIEUDEPART());

        gmap.addMapInializedListener(this);
        from.bindBidirectional(arr.textProperty());
        to.bindBidirectional(dep.textProperty());
        try {

            DirectionsRequest request = new DirectionsRequest(from.get(), to.get(), TravelModes.BICYCLING);
            directionsService.getRoute(request, this, new DirectionsRenderer(true, gmap.getMap(), directionsPane));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    @FXML
    private void toTextFieldAction(ActionEvent event) {
        DirectionsRequest request = new DirectionsRequest(from.get(), to.get(), TravelModes.DRIVING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, gmap.getMap(), directionsPane));
        distance.setText(container);
        System.out.println(container);
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
                .zoom(10);

        map = gmap.createMap(mapOptions);
    }

    @Override
    public void mapInitialized() {

        createMap();

        G.geocode(c.getLIEUARRIVE(), (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;
            LatLong lat = null;

            if (status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                alert.show();
                return;
            } else if (results.length > 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                lat = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
                this.departContainer = lat;
                MarkerOptions markerOptions = new MarkerOptions();
                System.out.println("result LG " + lat.getLongitude() + "   " + lat.getLatitude());
                markerOptions.position(latLong)
                        .visible(Boolean.TRUE)
                        .title("My Marker");

                Marker marker = new Marker(markerOptions);
                map.addMarker(marker);

            }

            map.setCenter(latLong);
        });
        G.geocode(c.getLIEUDEPART(), (GeocodingResult[] results, GeocoderStatus status) -> {

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
                arriveContainer = latLong;
                markerOptions.position(latLong)
                        .visible(Boolean.TRUE)
                        .title("My Marker");

                Marker marker = new Marker(markerOptions);
                map.addMarker(marker);

            }
            container = "" + departContainer.distanceFrom(arriveContainer);

            map.setCenter(latLong);
        });

        directionsService = new DirectionsService();
        directionsPane = gmap.getDirec();

    }

    @FXML

    private void retour(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("AfficherTousCovoiturage.fxml"));
        Scene scene = new Scene(page_select_my);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    @Override
    public void directionsReceived(DirectionsResult dr, DirectionStatus ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
