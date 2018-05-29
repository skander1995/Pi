/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import JDBC.JdbcInstance;
import Services.UserService;
import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import org.jxls.builder.xml.UserCommandAction;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class UserAdminChartController implements Initializable {

    @FXML
    private JFXButton userButton;
    @FXML
    private JFXButton pubButton;
    @FXML
    private JFXButton reportedButton;
    @FXML
    private BarChart<String, Integer> inscriChart;
    @FXML
    private LineChart<?, ?> pubChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // init charts and data
        Connection connection = JdbcInstance.getInstance();
        try {
            //user button
            String request = "SELECT COUNT(`ID_USR`) FROM `user` WHERE 1";
            Statement state = connection.createStatement();
            ResultSet rst = state.executeQuery(request);
            if (rst.next()) {
                userButton.setText("Utilisateurs " + rst.getString(1));
            }

            request = "SELECT\n"
                    + "    Sum( a.count )\n"
                    + "FROM(\n"
                    + "    SELECT Count( * ) AS count FROM covoiturage\n"
                    + "    UNION ALL\n"
                    + "    SELECT Count( * ) AS count FROM evenement\n"
                    + "    UNION ALL\n"
                    + "    SELECT Count( * ) AS count FROM colocation\n"
                    + "    UNION ALL\n"
                    + "    SELECT Count( * ) AS count FROM user\n"
                    + ") a";
            state = connection.createStatement();
            rst = state.executeQuery(request);
            if (rst.next()) {
                pubButton.setText("Publications " + rst.getString(1));
            }

            request = "SELECT COUNT(*) FROM `reclamation` "
                    + "WHERE `TYPE_REC` = \"user\" AND `ETAT` LIKE \"enattente\"";
            state = connection.createStatement();
            rst = state.executeQuery(request);
            if (rst.next()) {
                reportedButton.setText("Signalé " + rst.getString(1));
            }

            // init charts
            UserService usrService = new UserService();
            inscriChart.getData().clear();
            inscriChart.getData().add(usrService.generateInscriptionChart());
            inscriChart.setLegendVisible(false);
            inscriChart.setAnimated(true);

        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

    @FXML
    private void fireUserAdminUI(ActionEvent event) {
    }

    @FXML
    private void fireReportedUser(ActionEvent event) {
    }

}
