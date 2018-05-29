/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package squllapp;

import View.MainAdminInterfaceController;
import View.MainInterfaceController;
import View.Navigator;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author cobwi
 */
public class SqullApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //stage.getIcons().add(new Image("/IMG/logo2.png"));
        stage.setScene(createScene(loadMainPane()));
        stage.show();
    }

    private Pane loadMainPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        //normale one
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.authentification));
//-------------------exemple kifeh tjareb temchi l interface toul ------------------------

//Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream("/View/EspaceEducatif/main.fxml"));
// tekteb athika ihezek leha toul ! 
// fama el colocation wel covoiturage yekhdmou toul ma test7a9ech tokeed tbadel kol mara  badalhom mara houni 7ot
//         "/View/colocation/AccueilColocation.fxml"
// taw tal9a l crud wel add w kol 
// el coloc feha kolchay test7a9lou netsawer

//-----------------------------------------------------------------------------------------------





        //***********           Admin panel     ***************************
        /*Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.AdminMainInterface));
        MainAdminInterfaceController mainController = loader.getController();
        Navigator.setMainController(mainController);
         */
 /*
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.MainInterface));
        MainInterfaceController mainController = loader.getController();
        Navigator.setMainController(mainController);
         */
        /**
         * **************** NORMAL USE ***********************
         */
        //Navigator.LoadScene(Navigator.authentification);
        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );
        return scene;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
