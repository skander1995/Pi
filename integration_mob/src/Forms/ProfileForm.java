package Forms;

import AbstractClass.AbstractForm;
import Utilities.ToolsUtilities;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.io.InputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cobwi
 */
public class ProfileForm extends AbstractForm {

    private Label myPic;

    public ProfileForm(Form f) {
        super("Login Form", f);
        this.addComponent(BorderLayout.CENTER, showMyProfile());
        //this.revalidate();

    }

    private Component showMyProfile() {
        Storage.getInstance().clearStorage();
        System.out.println("PHOTOTOOO " + User.getActifUser().getPhotoProfil());
        final Container c = new Container(new BorderLayout());
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        Container p = new Container(bl);
        p.addComponent(BorderLayout.CENTER, new InfiniteProgress());
        c.addComponent(BorderLayout.CENTER, p);

        com.mycompany.Entite.User user = com.mycompany.Entite.User.getActifUser();

        int leftCol = Display.getInstance().getDisplayWidth() / 3;
        ComponentGroup gr = new ComponentGroup();
        gr.setLayout(new GridLayout(4, 1));
        System.out.println("USER TO LOAD PROFILE : " + user);
        gr.addComponent(getPairContainer("Name", user.getNom(), leftCol));
        gr.addComponent(getPairContainer("Prenom", user.getPrenom(), leftCol));
        gr.addComponent(getPairContainer("Email", user.getEmail(), leftCol));
        gr.addComponent(getPairContainer("Birthday", ToolsUtilities.formater.format(user.getDateNaissance()), leftCol));

        c.removeAll();
        c.addComponent(BorderLayout.CENTER, gr);

        //Image i = getTheme().getImage("fbuser.jpg");
        Container imageCnt = new Container(new BorderLayout());
        //Label myPic = new Label(i);

        EncodedImage placeholder = EncodedImage.createFromImage(super.getTheme().getImage("fbuser.jpg"), true);

        Storage.getInstance().deleteStorageFile("imgprofile");
        // Image src = URLImage.createToStorage(placeholder, "imgprofile", ToolsUtilities.ServerIp + user.getPhotoProfil(), URLImage.RESIZE_SCALE);
        Image src;
        try {
            String imgLink = User.getActifUser().getPhotoProfil();
            if (imgLink.indexOf("http") > -1) {
                src = URLImage.createToStorage(placeholder, "imgprofile", User.getActifUser().getPhotoProfil(), URLImage.RESIZE_SCALE);
            } else {
                src = URLImage.createToStorage(placeholder, "imgprofile", ToolsUtilities.ServerIp + User.getActifUser().getPhotoProfil(), URLImage.RESIZE_SCALE);

            }
        } catch (NullPointerException ne) {
            src = URLImage.createToStorage(placeholder, "imgprofile", "", URLImage.RESIZE_SCALE);
            System.out.println("Photo null");
        }

        myPic = new Label(src);

        myPic.addPointerPressedListener((evt) -> {
            Display.getInstance().openImageGallery(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if (evt == null) {
                        return;
                    }
                    String filename = (String) evt.getSource();

                    if (Dialog.show("Send file?", filename, "OK", "Cancel")) {
                        MultipartRequest req = new MultipartRequest();
                        String endpoint = ToolsUtilities.ServerIp + "/piproj/web/app_dev.php/uploadphoto/" + User.getActifUser().getId();
                        //String endpoint = ToolsUtilities.ServerIp + "/piproj/web/app_dev.php/uploadphoto";
                        System.out.println("endpoint  : " + endpoint);

                        req.setUrl(endpoint);
                        req.addArgument("message", "test");
                        InputStream is = null;
                        try {
                            is = FileSystemStorage.getInstance().openInputStream(filename);
                            req.addData("file", is, FileSystemStorage.getInstance().getLength(filename), "image/jpeg");
                            req.setFilename("file", filename);//any unique name you want
                            req.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    byte[] data = (byte[]) req.getResponseData();
                                    String s = new String(data);
                                    System.out.println("s = " + s);
                                    if (s.equals("ok")) {
                                        User.getActifUser().setPhotoProfil("/espentreaide/uploadable/images/"
                                                + filename.substring(filename.lastIndexOf("/") + 1, filename.length()));
                                        Image srcnew = URLImage.createToStorage(placeholder, "imgprofile",
                                                ToolsUtilities.ServerIp + user.getPhotoProfil(),
                                                URLImage.RESIZE_SCALE);
                                        System.out.println("ToolsUtilities.ServerIp + user.getPhotoProfil() = " + ToolsUtilities.ServerIp + user.getPhotoProfil());
                                        myPic.setIcon(srcnew);
                                        c.revalidate();
                                    }
                                }
                            });
                            InfiniteProgress prog = new InfiniteProgress();
                            Dialog dlg = prog.showInifiniteBlocking();
                            req.setDisposeOnCompletion(dlg);
                            NetworkManager.getInstance().addToQueue(req);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }
            });
        });

        imageCnt.addComponent(BorderLayout.NORTH, myPic);
        c.addComponent(BorderLayout.EAST, imageCnt);
        c.revalidate();
        return c;
    }

    private Container getPairContainer(String key, String val, int padding) {
        Label keyLabel = new Label(key);
        keyLabel.setUIID("Header");
        keyLabel.setPreferredW(padding);
        keyLabel.getStyle().setAlignment(Component.RIGHT);
        Label valLabel = new Label(val);
        valLabel.getStyle().setAlignment(Component.LEFT);
        Container cnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        cnt.addComponent(keyLabel);
        cnt.addComponent(valLabel);
        return cnt;
    }

}
