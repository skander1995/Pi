/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import Services.ColocationService;
import Utilities.ToolsUtilities;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.Entite.Colocation;
;
import com.mycompany.Entite.User;
import java.io.IOException;
import java.io.InputStream;



public class ColocationForm extends AbstractForm {

    Colocation c = new Colocation();
    TextField photoField = new TextField("", "Importer une photo", 10, TextArea.ANY);
    private Form f;
    TextField LOYERMONSUEL;
    TextField description;
    TextField NBCHAMBRE;
    TextField COMMODITE;
    Button btnajout, btnaff;
    ComboBox<String> LIEU;
    Picker DATEDISPONIBILITE;

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    public ColocationForm(Form sender) {
        super("Colocation", sender);

        f = new Form("Ajouter Colocation");
        LOYERMONSUEL = new TextField();
        description = new TextField();
        COMMODITE = new TextField();
        Label photoLabel = new Label("Photo");
        Button selectPhoto = new Button("parcourir");

        photoField.setEditable(false);
        selectPhoto.addActionListener((evt) -> {
            if (Dialog.show("Photo!", "une annonce avec des  photos est 10 fois plus visible", "app photo", "Gallerie") == false) {
                Display.getInstance().openGallery((Co) -> {
                    if (Co != null && Co.getSource() != null) {
                        String file = (String) Co.getSource();
                        photoField.setText(file.substring(file.lastIndexOf('/') + 1));
                    }
                }, Display.GALLERY_IMAGE);
            } else {
                System.out.println("ici on va accerder à l'appareille photo");
            }
        });
        Button button = new Button("Upload");
        button.addPointerPressedListener((evt) -> {
            Display.getInstance().openImageGallery(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if (evt == null) {
                        return;
                    }
                    String filename = (String) evt.getSource();

                    if (Dialog.show("Send file?", filename, "OK", "Cancel")) {
                        MultipartRequest req = new MultipartRequest();
                        String endpoint = ToolsUtilities.ServerIp + "/piproj/web/app_dev.php/api/uploadphoto/";
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
// entité = nom de la photo
                                        System.out.println("SUCCESS , Let's update colocation");
                                        String newfilename = filename.substring(filename.lastIndexOf("/") + 1, filename.length());
                                        System.out.println("Tesytin : " + newfilename);
                                        photoField.setText(filename);
                                        c.setIMGCOUVERTURE("\\espentreaide\\uploadable\\images\\" + newfilename);
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueue(req);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }
            });
        });

        Container photoContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        photoContainer.add(photoLabel);
        photoContainer.add(photoField);
//        photoContainer.add(selectPhoto);
        photoContainer.add(button);

        btnajout = new Button("ajouter");
        btnaff = new Button("Affichage");
        NBCHAMBRE = new TextField();
        LIEU = new ComboBox();
        LIEU.addItem("Ariana");
        LIEU.addItem("Ariana Soghra");
        LIEU.addItem("TUNIS");
        LIEU.addItem("Noor Jaafer");
        LIEU.addItem("Ghazela");
        LIEU.addItem("Nasr");

        DATEDISPONIBILITE = new Picker();

        this.setLayout(new GridLayout(10, 1));
        LOYERMONSUEL.setHint("Loyer");
        this.addComponent(LOYERMONSUEL);

        description.setHint("Description");
        this.addComponent(description);
        this.addComponent(new Label("Date de disponibilté:"));
        this.addComponent(DATEDISPONIBILITE);
        NBCHAMBRE.setHint("Nombre de chambres");
        this.addComponent(NBCHAMBRE);
        COMMODITE.setHint("Commodite");
        this.add(COMMODITE);
        this.addComponent(LIEU);

        this.add(photoContainer);

        this.addComponent(btnajout);
        this.addComponent(btnaff);

        btnajout.addActionListener((e) -> {
            //  Covoiturage c = new Covoiturage(DATEPUB, LIEUDEPART, LIEUARRIVE, Float.parseFloat(prix.getText()), 0;

            c.setDESCRIPTION(description.getText());
            c.setID_USR(User.getIdOfConnectedUser());
            c.setLOYERMENSUEL(Double.valueOf(LOYERMONSUEL.getText()));
            c.setLIEU(LIEU.getSelectedItem());
            c.setNBCHAMBRE(Integer.valueOf(NBCHAMBRE.getText()));
            c.setDATEDISPONIBILITE(DATEDISPONIBILITE.getDate());
            c.setCOMMODITE(COMMODITE.getText());

            //***************************
            ColocationService CS = new ColocationService();
            CS.newCol(c);
            //  ServiceTask ser = new ServiceTask();
            //Task t = new Task(0, tnom.getText(), tetat.getText());
            //ser.ajoutTask(t);

        });
        btnaff.addActionListener((e) -> {
            //AfficherCovoiturage a=new AfficherCovoiturage();
            // a.getF().show();
        });
    }

    private void afficherTout() {
        ConnectionRequest req = new ConnectionRequest();
        //req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piwebService/webresources/com.mycompany.piwebservice.user/login/" + username.getText() + "");
        req.setUrl(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/piproj/web/app_dev.php/get/cov/all");
        System.out.println("REQU : " + req.getUrl());
        req.setHttpMethod("POST");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                byte[] data = (byte[]) req.getResponseData();
                String s = new String(data);
                //User user = mapToJson(s);
                System.out.println("s = " + s);
            }
        });

        NetworkManager.getInstance().addToQueue(req);
    }

}
