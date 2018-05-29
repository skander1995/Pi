/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Reclamation;
import Entities.User;
import Utilities.ToolsUtilities;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class PopOverPrintController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public TableView<?> table = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void generatePdf(ActionEvent event) {
        ToolsUtilities.generatePdfFile(table);
    }

    @FXML
    private void generateExcel(ActionEvent event) {
        Vector v = new Vector();
        v.add("");
        ToolsUtilities.generateFileFromTableView(v, table);
    }

    @FXML
    private void generatePdf(MouseEvent event) {
    }

    @FXML
    private void generateExcel(MouseEvent event) {
    }

}
