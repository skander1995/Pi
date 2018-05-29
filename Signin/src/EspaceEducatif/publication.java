/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceEducatif;

import AbstractClass.AbstractForm;
import Utilities.ToolsUtilities;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;

import com.restfb.FacebookClient;
import com.restfb.types.FacebookType;
import commentaire.educ.Commentaire;
import commentaire.educ.CommentaireDAO;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Skander
 */
public class publication extends AbstractForm {

    String url = ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/FosBundleProj/web/upload/users/";

    public publication(EspaceEduc e, Form sender) throws IOException {
        super("publication", sender);
        this.addComponent(BorderLayout.CENTER, init(e, sender));
    }

    public Form init(EspaceEduc e, Form sender) throws IOException {
        int idcurrentuser = com.mycompany.Entite.User.getActifUser().getId();
        CommentaireDAO ser = new CommentaireDAO();

        Form container = new Form();
        skrating rating = new skrating();
        container.add(rating.ratingslider(idcurrentuser, e.getId_pub()));

        SpanLabel description = new SpanLabel(e.getDescription());
        Label desc = new Label("Description: ");

        Label document = new Label(e.getDocument());
        Label doc = new Label("Document: ");
        Label doc2 = new Label();
        doc2.setIcon(Image.createImage("/file.png"));
        Container cont4 = new Container(BoxLayout.x());
        doc2.addPointerPressedListener((evt) -> {
            FileSystemStorage fs = FileSystemStorage.getInstance();
            String fileName = ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/FosBundleProj/web/upload/files/" + e.getDocument();
            if (!fs.exists(fileName)) {
                Util.downloadUrlToFile(ToolsUtilities.ServerIp + ":" + ToolsUtilities.port + "/FosBundleProj/web/upload/files/", fileName, true);
            }
            Display.getInstance().execute(fileName);
        });

        cont4.add(doc2).add(document);

        Label section = new Label(e.getSection());
        Label sec = new Label("Section: ");

        Label matiere = new Label(e.getMatiere());
        Label mat = new Label("Matiére: ");

        Label username = new Label(e.getUsername());

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String dd = formater.format(e.getDatepub());
        Label date = new Label(dd);

        Container cont = new Container(BoxLayout.y());
        Container cont2 = new Container(BoxLayout.x());
        Container cont3 = new Container(BoxLayout.x());

        EncodedImage img1 = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(), 150), true);
        URLImage imgg1 = URLImage.createToStorage(img1, e.getUsername(), url + e.getUsername() + ".png");
        imgg1.fetch();
        Label profile = new Label();
        profile.setIcon(imgg1.scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 6)));

        cont3.add(profile);
        cont3.add(username);
        cont3.add(date);

        cont2.add(mat).add(matiere).add(sec).add(section);
        cont.add(cont3).add(desc).add(description).add(doc).add(cont4).add(cont2);

        Button fb = new Button("share");
        Slider s = new Slider();
        cont.add(fb);

        fb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    String accessToken = "EAACEdEose0cBAGqWBx2kyCukRY16LiK33YqTGuM9gXq3S175EAmHp5KlPlSqyLlzpbpr1eXqweu0p8ZBOqXcxt7ZAReh1CmKb7P8nztE9nOpXuppaIWLjQPZB12ELEHq2XXTL0EuCxq3MnXjCXg0EiFOGJyMROvNS5KtEm1RyZAySKoVPL6URo9NhzNo91GCZBelSA93BjMyRiWAb6NOt";

                    FacebookClient fbClient = new DefaultFacebookClient(accessToken);
                    Image screenshot = Image.createImage(container.getWidth(), container.getHeight());
                    //f.revalidate();
                    //f.setVisible(true);
                    //
                    container.paintComponent(screenshot.getGraphics(), true);
                    String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
                    try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                        ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
                        System.out.println("capture cbn + " + imageFile);
                    } catch (IOException err) {
                        Log.e(err);
                    }
                    InputStream photoInputStream;
                    photoInputStream = FileSystemStorage.getInstance().openInputStream(imageFile);
                    FacebookType photo = fbClient.publish("me/photos", FacebookType.class,
                            BinaryAttachment.with("hello", photoInputStream));
                    Dialog.show("Succes", "Votre post à été partagé sur facebook", "Fermer", null);
                } catch (IOException ex) {
                    //  Logger.getLogger(publication.class.getName()).log(Level.SEVERE, null,ex);
                }
            }

        });

        for (Commentaire c : ser.consulterespace()) {
            if (c.getId_Plan() == e.getId_pub()) {
                EncodedImage imgp = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(), 150), true);
                URLImage imggp = URLImage.createToStorage(imgp, c.getId_user().getPhotoProfil(), url + c.getId_user().getPhotoProfil());
                imggp.fetch();
                Label phot = new Label();
                phot.setIcon(imggp.scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 6)));
                Label nom = new Label(c.getId_user().getLogin()+" :");
                SpanLabel commentaire = new SpanLabel(c.getContenu());
                Container com = new Container(BoxLayout.x());
                com.addAll(phot, nom, commentaire);
                cont.add(com);
                System.out.println(url + c.getId_user().getPhotoProfil());
            }
        }
        TextField co = new TextField("", "commentaire");
        Button val = new Button("commenter");
        Container cccom = new Container(BoxLayout.y());
        cccom.addAll(co, val);
        val.addActionListener((evt) -> {
            Commentaire comi = new Commentaire();
            comi.setContenu(co.getText());
            comi.setId_Plan(e.getId_pub());
            ser.AjouterCommentaire(comi, idcurrentuser);
            publication p ;
            try {
                p = new publication(e, sender);
                 p.show();
            } catch (IOException ex) {
                System.out.println("mochkla fel raffraichir baad el add");
            }
           
        });
        cont.add(s);
        cont.add(cccom);

        container.add(cont);
        
        
        container.getContentPane().addPullToRefresh(() -> {
            try {
                new publication(e, sender).show();
            } catch (IOException ex) {
                System.out.println("erreur de raffraichir");
            }
        
        });
        return container;

    }

}
