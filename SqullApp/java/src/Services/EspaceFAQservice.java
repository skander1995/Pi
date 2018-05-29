/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Entities.EspaceFAQ;
import JDBC.MyDb;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Skander
 */
public class EspaceFAQservice implements IServices.IEspaceFAQ {
    //private ObservableList <ObservableList> data;

    private final MyDb connection;

    public EspaceFAQservice() {
        this.connection = MyDb.getinstance();
    }

    @Override
    public void Createpub(EspaceFAQ o ,int id_currentuser) {
        PreparedStatement prep = null;
        String format = "yyyy-MM-dd"; 

java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format ); 
java.util.Date date = new java.util.Date(); 
       
System.out.println( formater.format( date ) ); 
        
    
        try {
            String resi = "INSERT INTO `question`(`idUsr`,`DESCRIPTION`,`DATEPUB`)VALUES (?,?,?)";

            prep = connection.getConnection().prepareStatement(resi);
            
            prep.setInt(1, id_currentuser);
            prep.setString(2, o.getDescription());
            
            prep.setDate(3, Date.valueOf(formater.format( date )));
            
            prep.executeUpdate();
           
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println(prep);
            System.out.println("error   " + ex.getMessage());
        }

    }

    @Override
    public ArrayList<EspaceFAQ> consulterespace() {

        ArrayList<EspaceFAQ> espaceEduc = new ArrayList<>();
        try {
            Statement stm = connection.getConnection().createStatement();

            ResultSet res = stm.executeQuery("SELECT * FROM `question` ");

            while (res.next()) {
                EspaceFAQ e = new EspaceFAQ();
                e.setId_pub(res.getInt("ID_PUB"));
                
              
                e.setDescription(res.getString("DESCRIPTION"));
                try {
                    e.setDatepub(res.getDate("DATEPUB"));
                
                } catch (SQLException ex) {
                    System.out.println("mochkla fel date ");
                }
                
                e.setId_usr(res.getInt("idUsr"));
                
                espaceEduc.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("erreur f select el question");
        }
        return espaceEduc;
    }

    @Override
    public void deletepub(int i) {
        try {
            PreparedStatement prep = connection.getConnection().prepareStatement("DELETE FROM `question` WHERE ID_PUB=?");
            prep.setInt(1, i);
            prep.executeUpdate();
            System.out.println("pub deleted");
        } catch (SQLException ex) {
            System.out.println("error deleting");

        }

    }

    @Override
    public void updatepub(EspaceFAQ o, int in) {

        try {
            Statement prep = connection.getConnection().createStatement();

            String resi = "UPDATE `question` SET `DESCRIPTION`='" + o.getDescription()
                   
                    + "' WHERE ID_PUB=" + in;
            prep.executeUpdate(resi);

            System.out.println("update done");
        } catch (SQLException ex) {

            System.out.println("update error" + ex.getCause());
        }

    }

    @Override
    public List<EspaceFAQ> consulterespace(int id) {

        ArrayList<EspaceFAQ> espaceEduc = new ArrayList<>();
        try {
            Statement stm = connection.getConnection().createStatement();
            String q = "SELECT * FROM `question` where idUsr=" + id;
            ResultSet res = stm.executeQuery(q);

            while (res.next()) {
                EspaceFAQ e = new EspaceFAQ();
                e.setId_pub(res.getInt("ID_PUB"));

                e.setDescription(res.getString("DESCRIPTION"));

               try {
                    e.setDatepub(res.getDate("DATEPUB"));
                
                } catch (SQLException ex) {
                    System.out.println("mochkla fel date ");
                }
                 e.setId_usr(res.getInt("idUsr"));

                espaceEduc.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("erreur consulter par id");
        }
        return espaceEduc;
    }

    @Override
    public String searchusername(int i) {
        String m = null;
        try {
            Statement stm = connection.getConnection().createStatement();
            String q = "SELECT `username`FROM `user` WHERE id=" + i;
            ResultSet res = stm.executeQuery(q);
            while (res.next()) {
                m = res.getString("username");
            }
        } catch (SQLException ex) {
            System.out.println("léééééééééééééééééééééééééé");
        }
        return m;
    }

   
    

}
