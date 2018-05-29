/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import View.Navigator;
import java.net.UnknownHostException;
import java.util.Properties;
import javafx.scene.control.Alert;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author wildchild
 */
public class Mail {

    public static void send(String to, String subject, String text) {

        final String username = "noreplyespritaide@gmail.com";
        final String password = "24608993";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText("de la part de: EspritEntreAideApp" + " " + text);
            Transport.send(message);
            System.out.println("Mail sent ");
        } catch (MessagingException | RuntimeException ex) {
            // pretend no connexion
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
            // prompt error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Mail couldn't be sent , please check your internet connexion");
            alert.showAndWait();
            Navigator.LoadScene(Navigator.authentification);
        }
    }

}
