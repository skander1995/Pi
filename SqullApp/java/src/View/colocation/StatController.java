/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.colocation;

import Entities.Colocation;
import Services.ColocationService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS I7
 */
public class StatController implements Initializable {

    @FXML
    private Pane paneview;
    @FXML
    private Button retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDataBarChart();
    }    
    private void loadDataBarChart(){
        System.out.println("ooooooooooooooooooooooooooooooooooooooo");
        
        
        paneview.getChildren().clear();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Lieux des collocations");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de colocation ");
        BarChart statsChart = new BarChart(xAxis, yAxis);
        statsChart.setTitle("Statitisques des lieux les plus demandés");
        XYChart.Series series = new XYChart.Series();
        series.setName("stats prod");
        
     ColocationService colocaService = new ColocationService();
        Colocation lcmd = new Colocation();
       ArrayList<Colocation> statsLCMD = colocaService.Statistique();
        
        System.out.println(statsChart);
        
        for(Colocation l : statsLCMD){
            series.getData().add(new XYChart.Data<>(l.getLieustat(),l.getNblieu()));
        }
           
       
        statsChart.getData().add(series);
        paneview.getChildren().add(statsChart);
        
    }

    @FXML
    private void retourner(ActionEvent event) throws IOException {
        Parent page_select_my = FXMLLoader.load(getClass().getResource("/View/colocation/Colocation_afficher.fxml"));        
        Scene scene = new Scene(page_select_my);
        Stage app=(Stage)((Node)event.getSource() ).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    
}
