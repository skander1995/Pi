/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceFAQ;

import Entities.EspaceFAQ;
import Entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import EnumPack.DocumentType;

import Services.EspaceFAQservice;
import Utilities.ToolsUtilities;
import View.Navigator;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Skander
 */
public class maincontroller implements Initializable {

    EspaceFAQservice e = new EspaceFAQservice();
    @FXML
    private FlowPane fl;
    public static int retour;
    public static EspaceFAQ es;
    public static int id_current_usr = User.getIdOfConnectedUser();

    EspaceFAQservice esf = new EspaceFAQservice();
    ObservableList<EspaceFAQ> ssf = FXCollections.observableArrayList(esf.consulterespace());
    /*
    
    EspaceEducservice esp = new EspaceEducservice();
    ObservableList<EspaceEduc> sss = FXCollections.observableArrayList(esp.consulterespace());
     */
    @FXML
    private AnchorPane anchor2;
    @FXML
    private Button add;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String btns = "-fx-text-fill: white;\n"
                + "    -fx-font-family: \"Arial Narrow\";\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-background-color: linear-gradient(#61a2b1, #2A5058);\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
        String label = "-fx-font-size: 11pt;\n"
                + "    -fx-font-family: \"Segoe UI Semibold\";\n"
                + "    -fx-text-fill: black;\n"
                + "    -fx-opacity: 0.6;";

        String btneva = "-fx-text-fill    : rgb(49, 89, 23);\n"
                + "    -fx-border-color : rgb(49, 89, 23);\n"
                + "    -fx-border-radius: 5;\n"
                + "    -fx-padding      : 3 6 6 6;";

        retour = 0;
        fl.setVgap(4);
        fl.setHgap(4);
        fl.setPrefWrapLength(50); // preferred width allows for two columns
        //fl.setStyle("-fx-background-color: #191970;");
        for (EspaceFAQ f : ssf) {

            Label l1 = new Label();
            //l1.setGraphic(new ImageView("user.png"));
            Label lb66 = new Label(e.searchusername(f.getId_usr()));
            HBox v1 = new HBox();
            v1.setAlignment(Pos.CENTER);
            v1.getChildren().addAll(l1, lb66);
            v1.setPrefWidth(200);

            Label l2 = new Label("Description:  ");
            Label lb11 = new Label(f.getDescription());
            HBox v3 = new HBox();
            v3.setAlignment(Pos.CENTER);
            v3.getChildren().addAll(l2, lb11);
            v3.setPrefWidth(200);

            Label l3 = new Label("Date:  ");
            String date = new String(f.getDatepub().toString());
            System.out.println("dd");
            Label lb33 = new Label(date);

            HBox v2 = new HBox();
            v2.setAlignment(Pos.CENTER);
            v2.getChildren().addAll(l3, lb33);
            v2.setPrefWidth(200);

            lb11.setStyle(label);
            lb33.setStyle(label);

            lb66.setStyle(label);

//----------------------------------------------
            //Label lb22 = new Label(file.getName());
            //v2.setPrefWidth(200);
//----------------------------------------
            Button evaluer = new Button("evaluer");
            //valuer.setGraphic(new ImageView("comm.png"));
            evaluer.setOnAction((event) -> {
                es = f;
                retour = 1;
                try {
                   Navigator.LoadScene("/View/EspaceFAQ/Plan.fxml");
                  //  redirecting("/View/EspaceFAQ/Plan.fxml", "Commentaire");
                } catch (Exception ex) {
                    Logger.getLogger(maincontroller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            );
            

            evaluer.setStyle(btneva);
            VBox p = new VBox(v1, v2, v3, evaluer);

            evaluer.setLayoutY(evaluer.getLayoutY() + 100);
            p.setAlignment(Pos.CENTER);
            StackPane s = new StackPane(p);
            s.setPrefSize(400, 150);
            s.setStyle("-fx-background-radius:10px;-fx-border-radius:10px;-fx-background-color: #F5FFFA;-fx-border-width:1.5px;-fx-border-color: black");

            fl.getChildren().add(s);

        }

    }

    private void redirecting(String path, String title) throws Exception {
        //((Node) (event.getSource())).getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private void download(File file) {

        ToolsUtilities.downloadFromServer(DocumentType.image, file);
        String u = "C:\\Users\\Skander\\Documents\\TMP\\" + file.getName();
        System.out.println(u);
        try {

            if ((file).exists()) {
                System.out.println("ok");
                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + u);
                p.waitFor();

            } else {

                System.out.println("File does not exist");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void addfaq(ActionEvent event) {
        Navigator.LoadScene(Navigator.EspaceFAQ_user);
    }

}
