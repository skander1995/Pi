/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.EspaceEducatif;

import Entities.EspaceEduc;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import EnumPack.DocumentType;
import Services.EspaceEducservice;
import Utilities.ToolsUtilities;
import View.Navigator;
import java.io.File;
import java.io.IOException;
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

    EspaceEducservice e = new EspaceEducservice();
    @FXML
    private FlowPane fl;
    public static int retour;
    public static EspaceEduc es;
    public static int id_usr = 2;

    EspaceEducservice esp = new EspaceEducservice();
    ObservableList<EspaceEduc> sss = FXCollections.observableArrayList(esp.consulterespace());
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
//scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        retour = 0;
        fl.setVgap(4);
        fl.setHgap(4);
        fl.setPrefWrapLength(50); // preferred width allows for two columns
        // fl.setStyle("-fx-background-color: #191970;");

        for (EspaceEduc f : sss) {
            HBox h1 = new HBox();
            HBox h2 = new HBox();
            HBox h3 = new HBox();
            HBox h4 = new HBox();
            HBox h5 = new HBox();
            Label l1 = new Label();
            //l1.setGraphic(new ImageView("user.png"));
            Label lb66 = new Label(e.searchusername(f.getId_usr()));
            h1.setAlignment(Pos.CENTER);
            h1.getChildren().addAll(l1, lb66);
            h1.setPrefWidth(200);

            Label l2 = new Label("Description:  ");

            Label lb11 = new Label(f.getDescription());

            h2.setAlignment(Pos.CENTER);
            h2.getChildren().addAll(l2, lb11);
            h2.setPrefWidth(200);

            Label l3 = new Label("Date:  ");
            Label lb33 = new Label(f.getDatepub().toString());
            h3.setAlignment(Pos.CENTER);
            h3.getChildren().addAll(l3, lb33);
            h3.setPrefWidth(200);

            Label l5 = new Label("Matiére:  ");

            Label lb55 = new Label(f.getMatiere());
            h4.setAlignment(Pos.CENTER);
            h4.getChildren().addAll(l5, lb55);
            h4.setPrefWidth(200);

            Label l6 = new Label();
            //l6.setGraphic(new ImageView("file.png"));
            File file = new File(f.getDocument());
            //Label lb22 = new Label(file.getName());
            Button bt = new Button(file.getName());
            //bt.setGraphic(new ImageView("upload.png"));
            bt.setOnAction((event) -> {
                try {
                    download(file);
                } catch (IOException ex) {
                    Logger.getLogger(maincontroller.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            bt.setStyle(btns);

            //bt.setStyle(fileURI);
            h5.setAlignment(Pos.CENTER);
            h5.getChildren().addAll(l6, bt);
            h5.setPrefWidth(200);

            Button evaluer = new Button("evaluer");
            evaluer.setStyle(btneva);
            //evaluer.setGraphic(new ImageView("comm.png"));
            evaluer.setOnAction((event) -> {
                es = f;
                retour = 1;
               
                try {
                    redirecting("/View/EspaceEducatif/Plan.fxml", "Commentaire");
// Navigator.LoadScene("/View/EspaceEducatif/Plan.fxml");
                } catch (Exception ex) {
                    Logger.getLogger(maincontroller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            );

            VBox v1 = new VBox();

            v1.setAlignment(Pos.CENTER);
            v1.getChildren().addAll(h1, h2, h3, h4, h5, evaluer);

            lb11.setStyle(label);
            lb33.setStyle(label);
            lb55.setStyle(label);
            lb66.setStyle(label);

//----------------------------------------
            StackPane s = new StackPane(v1);
            s.setPrefSize(400, 200);

            s.setStyle("-fx-background-radius:10px;-fx-border-radius:10px;-fx-background-color: #F5FFFA;-fx-border-width:1.5px;-fx-border-color: black");

            fl.getChildren().add(s);
           // fl.getStylesheets().add("style.css");

        }
        System.out.println();

        // TODO
    }

    private void download(File file) throws IOException {

       // ToolsUtilities.downloadFromServer(DocumentType.image, file);
        String u = "C:\\wamp\\www\\FosBundleProj\\web\\upload\\files\\" + file.getName();
        System.out.println(u);
        
        File f = new File(u);
         if ((f).exists()) {
            try {
                System.out.println("ok");
                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + u);
                p.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(maincontroller.class.getName()).log(Level.SEVERE, null, ex);
            }

            } else {

                System.out.println("File does not exist");

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

    @FXML
    private void addeduc(ActionEvent event) {
        Navigator.LoadScene(Navigator.EspaceEducatif_user);
    }

}
