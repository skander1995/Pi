/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import AbstractClass.AbstractForm;
import CustomContainers.EventContainer2;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.Entite.Evenement;

/**
 *
 * @author SELLAMI
 */
public class ShowEventSingleForm  extends AbstractForm{
    
    public ShowEventSingleForm(String title, Form sender) {
        super(title, sender);
        
    }
    public ShowEventSingleForm(Form sender,Evenement e,String name ) {
        super(e.getNom(), sender);
        EventContainer2 cc=  new EventContainer2(e, name);
        this.addComponent(BorderLayout.CENTER,cc);
    }
    
}
