/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.evenement;

import Entities.Evenement;
import Entities.User;
import Services.EvenementService;
import Utilities.ToolsUtilities;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author SELLAMI
 */
public class ConsultMyEventController implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Label titre;
    @FXML
    private Label specs;
    @FXML
    private TextArea Description;
    public Evenement ev;
    @FXML
    private Button imprimePDF;
    private ArrayList<User> table;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // System.out.println("here+"+ev.getAffiche());

    }

    public void start() {
        try {
            Image i = new Image(ev.getAffiche());
            image.setImage(i);

            System.out.println("there");
            titre.setText(ev.getNom());
            Description.setText("Description:\n" + ev.getDescription());
            Description.setEditable(false);
            specs.setText("-Date début :\n" + ev.getDate_debut() + "\n-Date de fin:\n" + ev.getDate_fin() + "\n-Nombre des places encore disponble:\n" + ev.getPlaces_dispo());
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
    }

    @FXML
    private void ImprimerPdf(ActionEvent event) {
        try {
            EvenementService es = new EvenementService();
            table = es.getListParticipants(ev.getId());
            Document my_pdf_report = new Document();
            FileOutputStream fs = new FileOutputStream(System.getProperty("user.home") + "\\Documents\\TMP\\" + "pdf_report_" + ev.getNom() + ToolsUtilities.formater.format(new Date()) + ".pdf");
            PdfWriter.getInstance(my_pdf_report, fs);
            my_pdf_report.open();
            //we have four columns in our table
            PdfPTable my_report_table = new PdfPTable(5);

            PdfPCell table_cell;

            for (int i = 0; i < table.size(); i++) {
                System.out.println("aa");
                table_cell = new PdfPCell(new Phrase(String.valueOf(table.get(i).getId())));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Phrase(String.valueOf(table.get(i).getEmail())));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Phrase(String.valueOf(table.get(i).getNom())));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Phrase(String.valueOf(table.get(i).getPrenom())));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Phrase(String.valueOf(table.get(i).getTelephone())));
                my_report_table.addCell(table_cell);
            }
            my_pdf_report.add(my_report_table);
            my_pdf_report.close();
            System.out.println(fs.toString());
            //ToolsUtilities.openFile(fs.toString());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (DocumentException ex) {
            System.out.println(ex.toString());
        }
    }

}
