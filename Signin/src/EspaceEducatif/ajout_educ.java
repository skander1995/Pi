/*
 * To change tfs license header, choose License Headers in Project Properties.
 * To change tfs template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceEducatif;

import AbstractClass.AbstractForm;
import Utilities.ToolsUtilities;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ext.filechooser.FileChooser;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;

import rest.file.uploader.tn.FileUploader;

/**
 *
 * @author Skander
 */
public class ajout_educ extends AbstractForm {

    Form f;
    String filenam;
    String filepath;

    TextField description;
    TextField section;
    TextField matiere;
    Button bt, ajout;
    Container cn;
    private Image img;
    private EncodedImage enc;
    private ImageViewer iv;

    public ajout_educ(Form f) {
        super("Ajout publication educative", f);
        this.addComponent(BorderLayout.CENTER, init(f));
    }

    public Container init(Form sender) {

        int idcurrentuser = com.mycompany.Entite.User.getActifUser().getId();

        f = new Form("", new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        description = new TextField("", "Description");
        section = new TextField("", "Section");
        matiere = new TextField("", "Matiére");
        bt = new Button("valider");
        ajout = new Button("ajouter fichier");
        ajout.addActionListener(e -> {

            FileChooser.showOpenDialog(".xls, .csv, text/plain,.pdf,", e2 -> {

                String file = (String) e2.getSource();
                filepath = file.substring(7);
                /*int fileNameIndex = filepath.lastIndexOf(".");
                StringBuilder sb = new StringBuilder(filepath);
                sb.deleteCharAt(fileNameIndex);
                filepath = sb.toString();
                 */
                int filenamein = filepath.lastIndexOf("/");
                filenam = filepath.substring(filenamein + 1);
                System.out.println(filenam);
                System.out.println(filepath);

            });

        });

        cn = new Container(BoxLayout.y());
        cn.add(description);
        cn.add(section);
        cn.add(matiere);
        cn.add(ajout);
        cn.add(bt);

        EspaceEduc e = new EspaceEduc();
        EspaceEducservice esp = new EspaceEducservice();

        bt.addActionListener((evt) -> {
            if (description.getText() == "") {
                Dialog d = new Dialog("Warning");
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, new SpanLabel("veiller remplir tous les champs", "DialogBody"));
                d.showPopupDialog(description);

            }
            if (matiere.getText() == "") {
                Dialog d = new Dialog("Warning");
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, new SpanLabel("veiller remplir tous les champs", "DialogBody"));
                d.showPopupDialog(matiere);

            }
            if (section.getText() == "") {
                Dialog d = new Dialog("Warning");
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, new SpanLabel("veiller remplir tous les champs", "DialogBody"));
                d.showPopupDialog(section);

            }
            if (filepath == "") {
                Dialog d = new Dialog("Warning");
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, new SpanLabel("veiller remplir tous les champs", "DialogBody"));
                d.showPopupDialog(ajout);

            }
            if (matiere.getText() != "" && description.getText() != "" && section.getText() != "" && filepath != "") {

                e.setDescription(description.getText());
                e.setMatiere(matiere.getText());
                e.setSection(section.getText());
                e.setDocument(filenam);
                FileUploader fu = new FileUploader(ToolsUtilities.ServerIp+":"+ToolsUtilities.port+"/FosBundleProj/web/upload/");
                try {
                    String filenameinserver = fu.upload(filepath);
                    System.out.println("skandeeeeeeeeeer   : " + filenameinserver);
                    e.setDocument(filenameinserver);
                    e.setId_usr(idcurrentuser);
                    esp.Createpub(e);
                    try {
                        affichage_educ fo = new affichage_educ(sender);
                        fo.show();
                    } catch (IOException ex) {
                        // Logger.getLogger(ajout_educ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (Exception ex) {
                    System.out.println("mochkla fel ajout fichier   : " + ex.getMessage());

                }
            }
        });
        Container container = new Container();

        container.add(cn);
        return container;

    }

}
