package com.mycompany.myapp;

import Forms.LoginForm;
import Forms.ProfileForm;
import Forms.SignInUi;
import Utilities.ToolsUtilities;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.io.URL;
import com.codename1.io.URL.HttpURLConnection;
import com.codename1.social.FacebookConnect;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
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
import com.codename1.util.StringUtil;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import org.mindrot.jbcrypt.BCrypt;

public class MyApplication {

    private Form current;
    private static Resources theme;
    private Form main;
    Label myPic = null;

    private Command back = new Command("Back") {
        public void actionPerformed(ActionEvent evt) {
            killNetworkAccess();
            main.showBack();
        }
    };

    public void init(Object context) {
        try {
            theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        /*
        System.out.println("started");
        main = new Form("Acceuil");
        main.setScrollable(false);
        main.setLayout(new BorderLayout());
         */
        //redirect to login

        /*
        SignInUi test = new SignInUi(current);
        test.show();
        */
        LoginForm login = new LoginForm(main);
        login.show();
        //main.show();
    }

    public void destroy() {
    }

    public void stop() {
    }

    private void killNetworkAccess() {
        Enumeration e = NetworkManager.getInstance().enumurateQueue();
        while (e.hasMoreElements()) {
            ConnectionRequest r = (ConnectionRequest) e.nextElement();
            r.kill();
        }
    }

    public void updateLoginPhoto() {

        com.mycompany.Entite.User user = com.mycompany.Entite.User.getActifUser();
        //Image src = (Image) user.getPhotoProfil();
        System.out.println(user.getPhotoProfil());

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(100, 100), true);
        Storage.getInstance().deleteStorageFile(user.getPhotoProfil());
        Storage.getInstance().clearStorage();
        // Storage.getInstance().clearCache();
        Image src = URLImage.createToStorage(placeholder, "fileNameInStorage", ToolsUtilities.ServerIp + user.getPhotoProfil(), URLImage.RESIZE_SCALE);

        if (src != null) {
            Command pc = new Command("", src) {
                public void actionPerformed(ActionEvent evt) {
                    main.getContentPane().removeAll();
                    main.addComponent(BorderLayout.CENTER, showMyProfile());
                    main.revalidate();
                }
            };
            main.addCommand(pc, main.getCommandCount());
        }
    }

    private Component uploadPhoto() {
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        final Container c = new Container(bl);
        c.addComponent(BorderLayout.CENTER, new Button(new Command("Pick Photo") {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().openImageGallery(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        if (evt == null) {
                            return;
                        }
                        String filename = (String) evt.getSource();
                        if (Dialog.show("Send file?", filename, "OK", "Cancel")) {
                            MultipartRequest req = new MultipartRequest();

                            String endpoint = ToolsUtilities.ServerIp + "/piproj/web/app_dev.php/uploadphoto/" + User.getActifUser().getId();
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

            }
        }));
        return c;
    }

    static Resources getTheme() {
        return theme;
    }

    private Component showMyProfile() {
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
        gr.addComponent(getPairContainer("Name", user.getNom(), leftCol));
        gr.addComponent(getPairContainer("Prenom", user.getPrenom(), leftCol));
        gr.addComponent(getPairContainer("Email", user.getEmail(), leftCol));
        gr.addComponent(getPairContainer("Birthday", ToolsUtilities.formater.format(user.getDateNaissance()), leftCol));

        c.removeAll();
        c.addComponent(BorderLayout.CENTER, gr);

        //Image i = getTheme().getImage("fbuser.jpg");
        Container imageCnt = new Container(new BorderLayout());
        //Label myPic = new Label(i);

        EncodedImage placeholder = EncodedImage.createFromImage(getTheme().getImage("fbuser.jpg"), true);
        Storage.getInstance().clearStorage();
        // Storage.getInstance().clearCache();
        Image src = URLImage.createToStorage(placeholder, "fileNameInStorage", ToolsUtilities.ServerIp + user.getPhotoProfil(), URLImage.RESIZE_SCALE);

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
                        String endpoint = ToolsUtilities.ServerIp + "/piproj/web/app_dev.php/uploadphoto";
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
                                        Image srcnew = URLImage.createToStorage(placeholder, "fileNameInStorage",
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
