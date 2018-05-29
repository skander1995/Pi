/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Entities.Reclamation;
import Entities.User;
import EnumPack.DocumentType;
import EnumPack.MailType;
import JDBC.JdbcInstance;
import View.PopOverPrintController;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
//import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author cobwi
 */
public class ToolsUtilities {

    public static boolean DEBUG = true;
    public static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

    public static void generatePdfFile(TableView<?> table) {
        try {
            Document my_pdf_report = new Document();
            FileOutputStream fs = new FileOutputStream(System.getProperty("user.home") + "\\Documents\\TMP\\" + "pdf_report_" + formater.format(new Date()) + ".pdf");
            System.out.println("fs = " + System.getProperty("user.home") + "\\Documents\\TMP\\" + "pdf_report_" + formater.format(new Date()) + ".pdf");
            PdfWriter.getInstance(my_pdf_report, fs);
            my_pdf_report.open();
            //we have four columns in our table
            PdfPTable my_report_table = new PdfPTable(4);

            PdfPCell table_cell;

            for (int i = 0; i < table.getItems().size(); i++) {
                if (table.getItems().get(i) instanceof Reclamation) {
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((Reclamation) table.getItems().get(i)).getSujet())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((Reclamation) table.getItems().get(i)).getDescription())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((Reclamation) table.getItems().get(i)).getDatePub())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((Reclamation) table.getItems().get(i)).getType())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((Reclamation) table.getItems().get(i)).getEtat())));
                    my_report_table.addCell(table_cell);
                } else if (table.getItems().get(i) instanceof User) {
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((User) table.getItems().get(i)).getNom())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((User) table.getItems().get(i)).getPrenom())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((User) table.getItems().get(i)).getDateNaissance())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((User) table.getItems().get(i)).getEmail())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Phrase(String.valueOf(((User) table.getItems().get(i)).getType())));
                    my_report_table.addCell(table_cell);
                }
            }
            my_pdf_report.add(my_report_table);
            my_pdf_report.close();
            System.out.println(fs.toString());
            ToolsUtilities.openFile(System.getProperty("user.home") + "\\Documents\\TMP\\" + "pdf_report_" + ToolsUtilities.formater.format(new Date()) + ".pdf");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopOverPrintController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PopOverPrintController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ToolsUtilities() {

    }

    public static void sendValidationMail(User user, MailType sujet) {
        int vDigit = generate5Digit();
        User.setValidationCode(vDigit);
        //Mail.send(user.getEmail(), sujet, "Bienvenu \nCeci est votre code de validation : " + vDigit);
        try {
            MailSender.send(user.getEmail(), sujet, "Bienvenu à la communauté d' Esprit entre aide " + user.getLogin()
                    + "\nCeci est votre code de validation : " + vDigit);
        } catch (Exception ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
        System.out.println("Code 5 digit = " + vDigit);
    }

    public static void sendMailTo(String email, String content, MailType sujet) {
        
        //Mail.send(user.getEmail(), sujet, "Bienvenu \nCeci est votre code de validation : " + vDigit);
        try {
            MailSender.send(email, sujet, content);
        } catch (Exception ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

    private static int generate5Digit() {
        Random rand = new Random();
        int randomNum = 10000 + rand.nextInt((99999 - 10000) + 1);
        return randomNum;
    }

    public static String uploadToServer(DocumentType fileType, File file) {
        try {
            String type = "";
            if (DocumentType.image == fileType) {
                type = "images";
            } else if (fileType.document == fileType) {
                type = "docs";
            } else {
                type = "others";
            }
            FileUpload uploader = new FileUpload();

            String url = JdbcInstance.getUrl().substring(12, JdbcInstance.getUrl().length());
            url = url.substring(0, url.indexOf(':'));

            System.out.println("Connecting to : " + url);
            String realLink = "";
            /*
            if (JdbcInstance.getUrl().contains("localhost")) {
                realLink = "127.0.0.1";
            } else {
                // treat ip addresses
            }
             */
            System.out.println("url = " + url);
            System.out.println("realLink = " + url + ":21" + "/piproj/web/uploads/" + type + "/" + file.getName());

            // functionnal one
            // can i use url
            uploader.upload("127.0.0.1" + ":21", "root", "root", "/piproj/web/uploads/" + type + "/" + file.getName(), file);

            return "http:/" + url + "/piproj/web/uploads/" + type + "/" + file.getName();
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
                ex.printStackTrace();
            }
            return "";
        }
    }

    // this could be useful to store in tmp file ( cache )
    public static String downloadFromServer(DocumentType fileType, File file) {
        try {
            String type = "";
            if (DocumentType.image == fileType) {
                type = "images";
            } else if (fileType.document == fileType) {
                type = "docs";
            } else {
                type = "others";
            }

            //************      create tmp
            File tmp = new File(System.getProperty("user.home") + "\\Documents\\TMP");
            if (!tmp.exists()) {
                System.out.println("./dossier n'existe pas, création d'un ..");
                if (tmp.mkdir()) {
                    System.out.println("Parent facture Directory created!");
                } else {
                    System.out.println("Failed to create facture directory!");
                }
            }

            String url = JdbcInstance.getUrl().substring(12, JdbcInstance.getUrl().length());
            url = url.substring(0, url.indexOf(':'));

            FileUpload uploader = new FileUpload();
// use url
            uploader.download("127.0.0.1" + ":21", "root", "root", "espentreaide/uploadable/" + type + "/" + file.getName(), file);

            return "http:/" + url + "espentreaide/uploadable/" + type + "/" + file.getName();
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
                ex.printStackTrace();
            }
            return "";
        }

    }

    public static void openFile(String url) {
        if (Desktop.isDesktopSupported()) {
            try {
                System.out.println(System.getProperty("user.home") + "\\TMP");
                //Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\Documents\\TMP\\"));
                Desktop.getDesktop().open(new File(url));
            } catch (IOException ex) {
                if (ToolsUtilities.DEBUG) {
                    System.out.println("Catched from open file= " + ex.getMessage());
                }
            }
        }
    }

    /*
    *check esprit mail
     */
    public static boolean mailValidator(String email) {
        //Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Pattern pattern = Pattern.compile("^.+@.+(.[^.]+)+$");
//espit.tn
        //Pattern pattern = Pattern.compile("^.+@esprit.+(.[.tn]+)+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*
    *@param title : annexe data to put in xls ( like date, filter , .. )
     */
    public static File generateFileFromTableView(Vector title, TableView<?> table) {
        //Map<String, Object> hash = new HashMap<String, Object>();
        String docName = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);

        File file = new File(System.getProperty("user.home") + "\\Documents\\EspritEntreAideDocs");
        if (!file.exists()) {
            System.out.println("./dossier n'existe pas, création d'un ..");
            if (file.mkdir()) {
                System.out.println("Parent facture Directory created!");
            } else {
                System.out.println("Failed to create facture directory!");
            }
        }

        file = new File(System.getProperty("user.home") + "\\Documents\\EspritEntreAideDocs\\" + docName);
        if (!file.exists()) {
            System.out.println("Subfile : " + docName + " n'existe pas , creation d'un ..");
            if (file.mkdir()) {
                System.out.println("sub file created");
            }
        }

        Map<String, List> hasha = new HashMap<>();
        List<Object> data = new ArrayList<>();
        Map hash = new HashMap();

        //title
        for (int i = 0; i < title.size(); i++) {
            System.out.println("title.elementAt(i) = " + title.elementAt(i));
            hash.put("param" + i, title.elementAt(i));

        }

        System.out.println(table.getColumns().size());
        for (int i = 0; i < table.getColumns().size(); i++) {
            //hash.put("col" + i, table. getColumns().get(i).getCellData(1).toString());
            hash.put("col", "id");
            hash.put("col", "name");
            hash.put("col", "prenom");
            hash.put("col", "Date de naissance");
            hash.put("col", "type");
        }

        List<Object> lignes = new ArrayList<>();

        int count = table.getColumns().size();

        for (int j = 0; j < table.getItems().size(); j++) {
            // pour le moment use only format ( this format depend from columns affiché in table view

            if (table.getItems().get(j) instanceof User) {
                System.out.println("de type userrrrr");
                User user = (User) table.getItems().get(j);
                lignes.add(new LigneEtat(
                        String.valueOf(user.getId()),
                        user.getLogin(),
                        user.getNom(),
                        user.getPrenom(),
                        user.getTelephone(),
                        user.getDateNaissance().toString(),
                        user.getTelephone(),
                        user.getEmail(),
                        "", ""));
            } else if (table.getItems().get(j) instanceof Reclamation) {
                System.out.println("de type RECLAMATION");
                Reclamation reclamation = (Reclamation) table.getItems().get(j);
                lignes.add(new LigneEtat(
                        String.valueOf(reclamation.getId_pub()),
                        reclamation.getSujet(),
                        reclamation.getDescription(),
                        reclamation.getEtat().name(),
                        reclamation.getType().name(),
                        reclamation.getDatePub().toString(),
                        String.valueOf(reclamation.getUse_id_usr()),
                        "",
                        "", ""));
            }

        }

        //dateformater
        String nameDate = formater.format(new Date());
        hash.put("LigneEtat", lignes);
        hash.put("Result", "");

        String srcFilePath = "./etatTemplate.xls";
        String destFilePath = System.getProperty("user.home") + "\\Documents\\EspritEntreAideDocs\\" + docName + "\\" + title.elementAt(0).toString() + nameDate + ".xls";

        File varTmpDir = new File(srcFilePath);
       /* if (varTmpDir.exists()) {
            XLSTransformer former = new XLSTransformer();
            try {
                former.transformXLS(srcFilePath, hash, destFilePath);
                System.out.println("Data extraction end !!! ");
                if (Desktop.isDesktopSupported()) {
                    System.out.println(System.getProperty("user.home") + "\\Documents\\EspritEntreAideDocs\\" + docName + "\\" + title.elementAt(0).toString() + nameDate + ".xls");
                    Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\Documents\\EspritEntreAideDocs\\" + docName + "\\" + title.elementAt(0).toString() + nameDate + ".xls"));
                    //fileFound = true;
                }
                return new File(destFilePath);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de l'utilisation de la template.\nContacter l'administrateur.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Template utilisé pour généré non trouvé.\nVeuillez placer la template dans le dossier système");
            alert.showAndWait();
        }*/
        return (null);
    }
}
