/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.EspaceEduc;
import JDBC.MyDb;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Skander
 */
public class EspaceEducservice implements IServices.IEspaceEduc {
    //private ObservableList <ObservableList> data;

   
    private final Connection conn;

    public EspaceEducservice() {
      
        conn = JDBC.JdbcInstance.getInstance();
    }

    @Override
    public void Createpub(EspaceEduc o, int id_currentuser) {
         
        String format = "yyyy-MM-dd";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
        java.util.Date date = new java.util.Date();
       PreparedStatement prep=null;
        try {
            String resi = "INSERT INTO `aide`(`idUsr`,`DESCRIPTION`,`DOCUMENT`, `SECTION`, `MATIERE`,`Datepub`)VALUES (?,?,?,?,?,?)";

            prep = conn.prepareStatement(resi);

            prep.setInt(1,o.getId_usr());
            prep.setString(2, o.getDescription());
            
            prep.setString(3, o.getDocument());
            prep.setString(4, o.getSection());
            prep.setString(5, o.getMatiere());
            prep.setDate(6,Date.valueOf( formater.format( date )));
            prep.executeUpdate();
            System.out.println("insertion ok");
        } catch (SQLException ex) {
            System.out.println(prep);
           // ex.printStackTrace();
            System.out.println("error   " + ex.getMessage());
        }

    }

    @Override
    public ArrayList<EspaceEduc> consulterespace() {

        ArrayList<EspaceEduc> espaceEduc = new ArrayList<>();
        try {
            Statement stm = conn.createStatement();

            ResultSet res = stm.executeQuery("SELECT * FROM `aide` ");

            while (res.next()) {
                EspaceEduc e = new EspaceEduc();
                e.setId_pub(res.getInt("idPub"));
                e.setSection(res.getString("SECTION"));
                e.setMatiere(res.getString("MATIERE"));
                e.setDescription(res.getString("DESCRIPTION"));

                e.setDatepub(res.getTimestamp("DATEPUB"));
                e.setId_usr(res.getInt("idUsr"));
                e.setDocument(res.getString("DOCUMENT"));
                espaceEduc.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("ereeeeeeeur");
        }
        return espaceEduc;
    }

    @Override
    public void deletepub(int i) {
        try {
            PreparedStatement prep = conn.prepareStatement("DELETE FROM `aide` WHERE idPub=?");
            prep.setInt(1, i);
            prep.executeUpdate();
            System.out.println("pub deleted");
        } catch (SQLException ex) {
            System.out.println("error deleting");

        }

    }

    @Override
    public void updatepub(EspaceEduc o, int in) {

        try {
            Statement prep = conn.createStatement();

            String resi = "UPDATE `aide` SET `DESCRIPTION`='" + o.getDescription()
                    + "',`DOCUMENT`='" + o.getDocument()
                    + "',`SECTION`='" + o.getSection()
                    + "',`MATIERE`='" + o.getMatiere()
                    + "' WHERE idPub=" + in;
            prep.executeUpdate(resi);

            System.out.println("update done");
        } catch (SQLException ex) {

            System.out.println("update error" + ex.getCause());
        }

    }

    @Override
    public List<EspaceEduc> consulterespace(int id) {

        ArrayList<EspaceEduc> espaceEduc = new ArrayList<>();
        try {
            Statement stm = conn.createStatement();
            String q = "SELECT * FROM `aide` where idUsr=" + id;
            ResultSet res = stm.executeQuery(q);

            while (res.next()) {
                EspaceEduc e = new EspaceEduc();
                e.setId_pub(res.getInt("idPub"));
                e.setSection(res.getString("SECTION"));
                e.setMatiere(res.getString("MATIERE"));
                e.setDescription(res.getString("DESCRIPTION"));

                e.setDatepub(res.getTimestamp("DATEPUB"));
                e.setId_usr(res.getInt("idUsr"));
                e.setDocument(res.getString("DOCUMENT"));
                espaceEduc.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("ereeeeeeeur");
        }
        return espaceEduc;
    }

    @Override
    public String searchusername(int i) {
        String m = null;
        System.out.println(""+i);
        String q = "SELECT `username` FROM user WHERE id= " + i;
        System.out.println(q);
        try {
            
            Statement stm = conn.createStatement();
            
            ResultSet res = stm.executeQuery(q);
            while (res.next()) {
                m = res.getString("username");
            }
        } catch (SQLException ex) {
            System.out.println("search user name error"+ex.getMessage());
        }
        return m;
    }

}
