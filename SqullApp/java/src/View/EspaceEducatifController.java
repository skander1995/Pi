/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import JDBC.JdbcInstance;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class EspaceEducatifController implements Initializable {

    private final Connection connection = JdbcInstance.getInstance();

    @FXML
    private Button btn = new Button();

    public void diplay(ActionEvent e) {
        try {

            Statement stm = connection.createStatement();

            ResultSet res = stm.executeQuery("SELECT * FROM `machine 1` WHERE nom='A-0275'");

            while (res.next()) {
                HBox hb = new HBox(10);
                Pane p = new Pane();

                Label LB = new Label(res.getString("nom"));
                Label LB2 = new Label(res.getString("valeur"));
                hb.getChildren().addAll(LB, LB2);
                Scene scene = new Scene(hb, 400, 400, Color.SILVER);

            }

        } catch (SQLException ex) {

        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
