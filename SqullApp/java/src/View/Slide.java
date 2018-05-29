/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author cobwi
 */

import View.customComponent.SideBar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Example of a sidebar that slides in and out of view
 */
public class Slide extends Application {

    private static final String[] lyrics = {
        "And in the end,\nthe love you take,\nis equal to\nthe love\nyou make.",
        "She came in through\nthe bathroom window\nprotected by\na silver\nspoon.",
        "I've got to admit\nit's getting better,\nA little better\nall the time."
    };

    private static final String[] locs = {
        "http://www.youtube.com/watch?v=osAA8q86COY&feature=player_detailpage#t=367s",
        "http://www.youtube.com/watch?v=IM2Ttov_zR0&feature=player_detailpage#t=30s",
        "http://www.youtube.com/watch?v=Jk0dBZ1meio&feature=player_detailpage#t=25s"
    };
    WebView webView;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(final Stage stage) throws Exception {
        stage.setTitle("Slide out YouTube demo");

        // create a WebView to show to the right of the SideBar.
        webView = new WebView();
        webView.setPrefSize(800, 600);

        // create a sidebar with some content in it.
        //final Pane lyricPane = createSidebarContent();
        //SideBar sidebar = new SideBar(250, lyricPane);
        //VBox.setVgrow(lyricPane, Priority.ALWAYS);
        //*****************
        FXMLLoader loader = new FXMLLoader();
        Pane sideBarPane = (Pane) loader.load(getClass().getResourceAsStream("/View/TopBar.fxml"));
        SideBar sidebar = new SideBar(110, sideBarPane);

        // layout the scene.
        final BorderPane layout = new BorderPane();
        Pane mainPane = VBoxBuilder.create().spacing(10)
                .children(
                        sidebar.getControlButton(),
                        webView
                ).build();
        layout.setTop(sidebar);
        layout.setCenter(mainPane);

        // show the scene.
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/View/Assets/css/slideout.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Animates a node on and off screen to the left.
     */
    

}
