/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package Forms;

import AbstractClass.AbstractForm;
import Services.ReclamationService;
import Services.UserManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.Entite.Reclamation;
import com.mycompany.Entite.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The newsfeed form
 *
 * @author cobwi
 */
public class ReclamationAddForm extends AbstractForm {

    private Form sender;

    private Resources theme;

    public ReclamationAddForm(Form res) {
        super("Passer une reclamation", res);
        LocalNotification n = new LocalNotification();
        Picker membresPicker = new Picker();
        Picker sujetPicker = new Picker();

        setScrollable(true);
        setScrollableY(true);
        theme = UIManager.initNamedTheme("/theme4", "Theme");
        UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        sender = res;
        setLayout(BoxLayout.y());

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        TextField titre = new TextField("", "Taper votre Sujet");
        titre.setUIID("TextFieldBlack");
        addStringValue("Sujet", titre);
        TextArea description = new TextArea(3, 10, TextArea.ANY);
        description.setUIID("TextFieldBlack");

        Picker box = new Picker();
        box.setStrings("perte", "application", "administration", "contenuinapproprié");
        box.setSelectedStringIndex(0);

        membresPicker.setType(Display.PICKER_TYPE_STRINGS);
        sujetPicker.setType(Display.PICKER_TYPE_STRINGS);

        List<User> targetMs = new UserManager().getAllButNotMe();

        if (targetMs.isEmpty()) {
            sujetPicker.setStrings("Application");
        } else {
            sujetPicker.setStrings("Application", "Membre");
        }
        sujetPicker.setSelectedString("Application");

        //************* init memebre picker 
        List<String> membersNames = new ArrayList<>();
        for (User targetM : targetMs) {
            membersNames.add(targetM.getId() + " - " + targetM.getNom() + " " + targetM.getPrenom());
        }

        membresPicker.setStrings(membersNames.toArray(new String[0]));
        membresPicker.setSelectedString(membersNames.get(0));

        container.add(membresPicker);

        sujetPicker.addActionListener((e) -> {
            if (sujetPicker.getSelectedString().equals("Membre")) {
                container.setVisible(true);
            } else {
                container.setVisible(false);
            }
        });

        Button btnFile = new Button("Envoyer");
        btnFile.addActionListener((evt) -> {
            ReclamationService service = new ReclamationService();
            Reclamation reclamation = new Reclamation();
            reclamation.setDatePub(new Date());
            reclamation.setType(box.getSelectedString());
            reclamation.setDescription(description.getText());
            reclamation.setSujet(titre.getText());
            if (sujetPicker.getSelectedString().equals("Membre")) {
                reclamation.setUse_id_usr(Integer.valueOf(
                        membresPicker.getSelectedString().substring(0, membresPicker.getSelectedString().indexOf("-") - 1))
                );
            }
            Reclamation persisted = service.persist(reclamation);
            if (persisted.getId_pub() > -1) {
                System.out.println("SUCCESSFULLY SUBMITED");
                // for reclamation viewer of success

                if (Display.getInstance().isMinimized()) {
                    Display.getInstance().callSerially(() -> {
                        Dialog.show("Esprit Entr'Aide ", "Reclamation recu", "OK", null);
                    });
                } else {
                    LocalNotification ln = new LocalNotification();
                    ln.setId("LnMessage");
                    ln.setAlertTitle("Welcome");
                    ln.setAlertBody("Thanks for arriving!");
                    Display.getInstance().scheduleLocalNotification(ln, System.currentTimeMillis() + 100, LocalNotification.REPEAT_NONE);
                }

                Dialog.show("Notice", "Reclamation Envoyée", "OK", null);
                new ReclamationForm(res).show();

            } else {
                Dialog.show("Erreur d'envoi", "Echec d'envoie de votre reclamation", "Cancel", "OK");
            }
        });

        //***************** VALIDATOR
        Validator v = new Validator();
        v
                .addConstraint(titre, new LengthConstraint(5))
                .addConstraint(description, new LengthConstraint(5));
        v.setShowErrorMessageForFocusedComponent(true);
        v.addSubmitButtons(btnFile);

        container.setVisible(false);
        add(BorderLayout.center(box));
        add(BorderLayout.center(sujetPicker));
        add(container);
        addStringValue("Description", description);
        add(BorderLayout.center(btnFile));
//        add(BoxLayout.Y_AXIS, btnFile);
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "Padding2")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "Separator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }
}
