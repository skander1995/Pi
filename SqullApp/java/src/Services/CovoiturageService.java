/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Covoiturage;
import JDBC.MyDb;

import Utilities.ToolsUtilities;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Moez
 */
public class CovoiturageService {

    private final MyDb connection;

    public CovoiturageService() {
        this.connection = MyDb.getinstance();
    }

    public void insertCovoiturage(Covoiturage c) {

        try {
            PreparedStatement st = connection.getConnection().prepareStatement(
                    "INSERT INTO covoiturage "
                    + "(ID_VILLE_ARR,VIL_ID_VILLE,ID_USR,DATEPUB,DESCRIPTION,ETAT,LIEUDEPART,LIEUARRIVE,DATEDEPART,PRIX,CHECKPOINTS,NBPLACE) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            st.setObject(1, c.getID_VILLE());
            st.setObject(2, c.getVIL_ID_VILLE());
            st.setObject(3, c.getID_USR());
            st.setObject(4, c.getDATEPUB());
            st.setObject(5, c.getDESCRIPTION());
            st.setObject(6, c.getETAT());
            st.setObject(7, c.getLIEUDEPART());
            st.setObject(8, c.getLIEUARRIVE());
            st.setObject(9, c.getDATEDEPART());
            st.setObject(10, c.getPRIX());
            st.setObject(11, c.getCHECKPOINTS());
            st.setObject(12, c.getNBPLACE());

            st.executeUpdate();
            System.out.println("ajouté");

        } catch (SQLException ex) {
            System.out.println("error  "+ex.getMessage());

        }
    }

    /*public void deleteCovoiturage(Covoiturage c) {
        try {
            PreparedStatement st = connection.getConnection().prepareStatement(
                    "DELETE FROM covoiturage "
                    + " WHERE ID_PUB = ?");
            st.setObject(1, c.getID_PUB());
            st.executeUpdate();

        } catch (SQLException ex) {

        }
    }*/
    
    public void deleteCovoiturage(int i) {
        try {
            PreparedStatement prep = connection.getConnection().prepareStatement("DELETE FROM `covoiturage` WHERE ID_PUB=?");
            prep.setInt(1 ,i);
            prep.executeUpdate();
            System.out.println("pub deleted");
        } catch (SQLException ex) {
            System.out.println("error deleting");
            
        }

    }

    public void editCovoiturage(Covoiturage c) {

        try {
            PreparedStatement st = connection.getConnection().prepareStatement(
                    "UPDATE covoiturage SET DATEDEPART=?,PRIX=?,LIEUDEPART=?,NBPLACE=?,	LIEUARRIVE=?,DESCRIPTION=? WHERE ID_PUB=?");
            st.setObject(1, c.getDATEDEPART());
            st.setObject(2, c.getPRIX());
            st.setObject(3, c.getLIEUDEPART());
            st.setObject(4, c.getNBPLACE());
            st.setObject(5, c.getLIEUARRIVE());
            st.setObject(6, c.getDESCRIPTION());
             st.setObject(7, c.getID_PUB());


            

            st.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.toString());

        }
    }

    public ArrayList<Covoiturage> afficherCovoiturage() {
        ArrayList<Covoiturage> Covoiturage = new ArrayList<>();
        try {
            Statement ste = connection.getConnection().createStatement();

            ResultSet result = ste.executeQuery("SELECT * FROM covoiturage where DATEDEPART>=DATE(NOW())");
            while (result.next()) {

                Covoiturage covoiturage = new Covoiturage();
                covoiturage.setID_PUB(result.getInt("ID_PUB"));
                covoiturage.setID_VILLE(result.getInt("ID_VILLE_ARR"));
                covoiturage.setVIL_ID_VILLE(result.getInt("VIL_ID_VILLE"));
                covoiturage.setID_USR(result.getInt("ID_USR"));
                covoiturage.setDATEPUB(result.getDate("DATEPUB"));
                covoiturage.setDESCRIPTION(result.getString("DESCRIPTION"));
                covoiturage.setETAT(result.getString("ETAT"));
                covoiturage.setLIEUDEPART(result.getString("LIEUDEPART"));
                covoiturage.setLIEUARRIVE(result.getString("LIEUARRIVE"));
                covoiturage.setDATEDEPART(result.getDate("DATEDEPART"));
                covoiturage.setPRIX(result.getFloat("PRIX"));
                covoiturage.setCHECKPOINTS(result.getString("CHECKPOINTS"));
                covoiturage.setNBPLACE(result.getInt("NBPLACE"));

                Covoiturage.add(covoiturage);

            }
            return Covoiturage;
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
            return null;
        }
    }
    
    
    public  ArrayList<Covoiturage> Recherche(int id) {
        ArrayList<Covoiturage> Covoiturage = new ArrayList<>();
        try {
            Statement ste = connection.getConnection().createStatement();

            ResultSet result = ste.executeQuery("SELECT *FROM `covoiturage` WHERE ID_USR="+id);
            while (result.next()) {
                Covoiturage covoiturage = new Covoiturage();
                covoiturage.setID_PUB(result.getInt("ID_PUB"));
                covoiturage.setID_VILLE(result.getInt("ID_VILLE_ARR"));
                covoiturage.setVIL_ID_VILLE(result.getInt("VIL_ID_VILLE"));
                covoiturage.setID_USR(result.getInt("ID_USR"));
                covoiturage.setDATEPUB(result.getDate("DATEPUB"));
                covoiturage.setDESCRIPTION(result.getString("DESCRIPTION"));
                covoiturage.setETAT(result.getString("ETAT"));
                covoiturage.setLIEUDEPART(result.getString("LIEUDEPART"));
                covoiturage.setLIEUARRIVE(result.getString("LIEUARRIVE"));
                covoiturage.setDATEDEPART(result.getDate("DATEDEPART"));
                covoiturage.setPRIX(result.getFloat("PRIX"));
                covoiturage.setCHECKPOINTS(result.getString("CHECKPOINTS"));
                covoiturage.setNBPLACE(result.getInt("NBPLACE"));
                Covoiturage.add(covoiturage);            }
        }catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
            return null;
        }
        return Covoiturage;
    }
    
    
    
   

}
