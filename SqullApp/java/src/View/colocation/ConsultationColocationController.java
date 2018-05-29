/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.colocation;

import Entities.Colocation;
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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS I7
 */
public class ConsultationColocationController implements Initializable ,MapComponentInitializedListener{

    @FXML
    private Label titre;
    @FXML
    private VBox images;
    @FXML
    private Label desc;
    public Colocation c;
    @FXML
    private AnchorPane mapGoogle;
    @FXML
    private GoogleMapView gmap;
    private GoogleMap map;
    private GeocodingService G;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
     
    } 
    
     public void createMap(){
        map = new GoogleMap();
        G = new GeocodingService();
        MapOptions mapOptions = new  MapOptions();
          mapOptions.center(new LatLong(33.8869, 9.5375))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(true)
                .panControl(true)
                .rotateControl(true)
                .scaleControl(true)
                .streetViewControl(true)
                .zoomControl(true)
                .zoom(15);

        map = gmap.createMap(mapOptions); }
    
    public void start(){
        gmap.addMapInializedListener(this);
        titre.setText(c.getTITRE());
        desc.setText("Description :\n"+c.getDESCRIPTION()+"\nDate de disponibilité :\n"+c.getDATEDISPONIBILITE()+"\n Prix :\n"+c.getLOYERMENSUEL()
        +"\nLieu :\n"+c.getLIEU());
        System.out.println("aaaaaaaaaaaaaaaaaa");
        /***************Map***********************/
         
        mapGoogle.setVisible(true);
        mapInitialized();
         System.out.println(c.getLIEU());
        }
    @Override
     public void mapInitialized() {
         try{
         createMap();
        
       String L = c.getLIEU();
        
        G.geocode(L , (GeocodingResult[] results, GeocoderStatus status) -> {

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
                System.out.println("result LG "+latLong.getLongitude()+ "   " +latLong.getLatitude());
                markerOptions.position(latLong)
                        .visible(Boolean.TRUE)
                        .title("My Marker");

                Marker marker = new Marker(markerOptions);
                map.addMarker(marker);

            }

            map.setCenter(latLong);
        
       }
        );
         }catch(Exception e){
             System.out.println(e.toString());
         }
     
     
     }
    private void mapfunction(ActionEvent event) {
        mapGoogle.setVisible(true);
       
    }
        
    }
    
