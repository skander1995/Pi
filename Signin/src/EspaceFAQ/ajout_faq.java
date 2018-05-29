/*
 * To change tfs license header, choose License Headers in Project Properties.
 * To change tfs template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceFAQ;

import AbstractClass.AbstractForm;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Entite.User;

/**
 *
 * @author Skander
 */
public class ajout_faq extends AbstractForm{

    Form f;
    String filenam;
    String filepath;

    TextField description;
    Button bt;
    Container cn;
    private Image img;
    private EncodedImage enc;
    private ImageViewer iv;

    
    public ajout_faq(Form f)
    {
        super("Ajout question", f);
        this.addComponent(BorderLayout.CENTER,init(f));
    }
    
    public Component init(Form sender) {
    int idcurrentuser=User.getActifUser().getId();
        System.out.println(idcurrentuser);
        f = new Form("", new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        description = new TextField("", "Description");
        bt = new Button("valider");

        cn = new Container(BoxLayout.y());
        cn.add(description);
        cn.add(bt);

        EspaceFAQ e = new EspaceFAQ();
        EspaceFAQservice esp = new EspaceFAQservice();

        bt.addActionListener((evt) -> {
            if (description.getText() == "") {
                Dialog d = new Dialog("Warning");
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, new SpanLabel("veiller remplir tous les champs", "DialogBody"));
                d.showPopupDialog(description);

            } else {

                e.setDescription(description.getText()); //  Logger.getLogger(ajout_faq.class.getName()).log(Level.SEVERE, null, ex);
                esp.Createpub(e,idcurrentuser);
                 affichage_faq colocForm = new affichage_faq(sender);
                colocForm.show();
                // affichage_faq f = new affichage_faq();
                //   f.getF().show();
                    
                
            }
        });
        return cn;
    
        
    }

   
}
