/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomContainers;

import Utilities.ToolsUtilities;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.Publication;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author cobwi
 * @param <K>
 * @param <T>
 */
public class PublicationContainer<K, T> extends Container {

    private User user = null;
    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.components.MultiButton gui_Multi_Button_1 = new com.codename1.components.MultiButton();
    private com.codename1.components.MultiButton gui_LA = new com.codename1.components.MultiButton();
    private com.codename1.ui.Container gui_imageContainer1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.TextArea gui_Text_Area_1 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Button gui_Button_1 = new com.codename1.ui.Button();
    private com.codename1.ui.Label gui_separator1 = new com.codename1.ui.Label();
    private com.codename1.ui.Container gui_null_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.components.MultiButton gui_null_1_1_1 = new com.codename1.components.MultiButton();
    private com.codename1.components.MultiButton gui_newYork = new com.codename1.components.MultiButton();
    private com.codename1.ui.Container gui_imageContainer2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.Container gui_Container_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
    private com.codename1.ui.TextArea gui_Text_Area_2 = new com.codename1.ui.TextArea();
    private com.codename1.ui.Button gui_Button_2 = new com.codename1.ui.Button();
    private com.codename1.ui.Label gui_Label_1_1_1 = new com.codename1.ui.Label();
    private Resources theme;

    public PublicationContainer(ArrayList<Publication> publication) {

        /*
        theme = UIManager.initNamedTheme("/theme", "Theme 1");
        UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
         */
        try {
            theme = Resources.openLayered("/theme4");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
        setName("AcceuilForm");
        setScrollableY(true);

        for (Publication pub : publication) {
            gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            gui_Multi_Button_1 = new com.codename1.components.MultiButton();
            gui_LA = new com.codename1.components.MultiButton();
            gui_imageContainer1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            gui_Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            gui_Text_Area_1 = new com.codename1.ui.TextArea();
            gui_Button_1 = new com.codename1.ui.Button();
            gui_separator1 = new com.codename1.ui.Label();
            gui_null_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            gui_null_1_1_1 = new com.codename1.components.MultiButton();
            gui_newYork = new com.codename1.components.MultiButton();
            gui_imageContainer2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            gui_Container_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            gui_Text_Area_2 = new com.codename1.ui.TextArea();
            gui_Button_2 = new com.codename1.ui.Button();
            gui_Label_1_1_1 = new com.codename1.ui.Label();
            addComponent(gui_Container_1);
            gui_Container_1.setName("Container_1");
            gui_Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Multi_Button_1);
            gui_Container_1.addComponent(com.codename1.ui.layouts.BorderLayout.EAST, gui_LA);
            gui_Multi_Button_1.setUIID("Label");
            gui_Multi_Button_1.setName("Multi_Button_1");

//            gui_Multi_Button_1.setIcon(pub.getLinkPhotoProfile());
            EncodedImage profileplaceholder = EncodedImage.createFromImage(theme.getImage("fbuser.jpg"), true);

            Storage.getInstance().deleteStorageFile(pub.getLinkPhotoProfile());
            
            
                if (pub.getLinkPhotoProfile().indexOf("http") > -1) {
                    gui_Multi_Button_1.setIcon(URLImage.createToStorage(profileplaceholder,
                            pub.getLinkPhotoProfile(), pub.getLinkPhotoProfile(), URLImage.RESIZE_SCALE));
                } else {
                    gui_Multi_Button_1.setIcon(URLImage.createToStorage(profileplaceholder, pub.getLinkPhotoProfile(),
                            ToolsUtilities.ServerIp + ToolsUtilities.port + pub.getLinkPhotoProfile(),
                            URLImage.RESIZE_SCALE
                    ));
                }

            gui_Multi_Button_1.setPropertyValue("line1", pub.getNomprenom());
            gui_Multi_Button_1.setPropertyValue("line2", "@" + pub.getUsername());
            gui_Multi_Button_1.setPropertyValue("uiid1", "Label");
            gui_Multi_Button_1.setPropertyValue("uiid2", "RedLabel");
            gui_LA.setUIID("Label");
            gui_LA.setName("LA");
            gui_LA.setPropertyValue("line1", pub.getDATEPUB());
            gui_LA.setPropertyValue("line2", pub.getType());
            gui_LA.setPropertyValue("uiid1", "SlightlySmallerFontLabel");
            gui_LA.setPropertyValue("uiid2", "RedLabelRight");
            addComponent(gui_imageContainer1);
            gui_imageContainer1.setName("imageContainer1");
            gui_imageContainer1.addComponent(com.codename1.ui.layouts.BorderLayout.SOUTH, gui_Container_2);

            gui_Container_2.setName("Container_2");
            gui_Container_2.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Text_Area_1);
            gui_Container_2.addComponent(com.codename1.ui.layouts.BorderLayout.EAST, gui_Button_1);
            gui_Text_Area_1.setText(pub.getDESCRIPTION());
            gui_Text_Area_1.setUIID("SlightlySmallerFontLabelLeft");
            gui_Text_Area_1.setName("Text_Area_1");
            gui_Button_1.setText("");
            gui_Button_1.setUIID("Label");
            gui_Button_1.setName("Button_1");
            com.codename1.ui.FontImage.setMaterialIcon(gui_Button_1, "".charAt(0));
            gui_Container_2.setName("Container_2");
            addComponent(gui_separator1);

            //initing
            gui_separator1.setShowEvenIfBlank(true);
            gui_Label_1_1_1.setShowEvenIfBlank(true);
            EncodedImage placeholder = EncodedImage.createFromImage(pub.getPlaceholder(), true);
            System.out.println("AFFICHE LINK : " + ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + pub.getLinkAffiche());
            ScaleImageLabel sl = new ScaleImageLabel(URLImage.createToStorage(placeholder, "imgpub",
                    ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + pub.getLinkAffiche(),
                    URLImage.RESIZE_SCALE
            )
            );

            sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            gui_imageContainer1.add(BorderLayout.CENTER, sl);

            FontImage.setMaterialIcon(gui_LA, FontImage.MATERIAL_LOCATION_ON);
            gui_LA.setIconPosition(BorderLayout.EAST);

            FontImage.setMaterialIcon(gui_newYork, FontImage.MATERIAL_LOCATION_ON);
            gui_newYork.setIconPosition(BorderLayout.EAST);

            gui_Text_Area_1.setRows(2);
            gui_Text_Area_1.setColumns(100);
            gui_Text_Area_1.setGrowByContent(false);
            gui_Text_Area_1.setEditable(false);

        }

    }

}
