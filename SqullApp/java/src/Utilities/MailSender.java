/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author cobwi
 */
import EnumPack.MailType;
import View.Navigator;
import java.util.Properties;
import javafx.scene.control.Alert;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    public static void send(String to, MailType type, String text) {

        final String username = "noreplyespritaide@gmail.com";
        final String password = "24608993";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));
            // Set To: header field of the header.

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            if (type == MailType.activation) {
                // Set Subject: header field
                message.setSubject("Confirmation de compte Esprit Entre Aide");
                message.setText(text);
            }

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            // prompt error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Mail couldn't be sent , please check your internet connexion");
            alert.showAndWait();
            Navigator.LoadScene(Navigator.authentification);
        }
    }
}
