/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Colocation;
import IServices.IColocationService;
import JDBC.JdbcInstance;
import Utilities.ToolsUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS I7
 */
public class ColocationService implements IColocationService {

    private static IColocationService icolocation;
    private Connection connection;

    public ColocationService() {
        this.connection = JdbcInstance.getInstance();
    }

    @Override
    public void ajouterColocation(Colocation o) {
        //String request = "INSERT INTO `colocation` (`DATEPUB`, `DESCRIPTION`, `LIEU`, `LOYERMENSUEL`) VALUES ( ?, ?, ?, ?)";
        try {
            Statement prep = connection.createStatement();
            String res = "INSERT INTO `colocation`(`DESCRIPTION`,`idUsr`, `LIEU`, `LOYERMENSUEL`,`DATEDISPONIBILITE`,`DATEPUB`) VALUES('"
                    + o.getDESCRIPTION()
                    + "','" + o.getID_USR()
                    + "','" + o.getLIEU()
                    + "','" + o.getLOYERMENSUEL()
                    + "','" + o.getDATEDISPONIBILITE()
                    + "','" + o.getDATEPUB()
                    + "')";

            prep.executeUpdate(res);
            System.out.println("ok");
        } catch (SQLException e) {
            System.out.println(o.toString());
            System.out.println("erreur ajout colocation" + e.toString());

        }
    }

    @Override
    public boolean supprimerColocation(Colocation c) {
        if (c != null) {
            String req1 = "DELETE FROM `colocation` WHERE `ID_PUB`=" + c.getID_PUB() + "";
            try {
                Statement state = connection.createStatement();
                state.executeUpdate(req1);
                return true;
            } catch (SQLException e) {
                if (ToolsUtilities.DEBUG) {
                    System.out.println("suppression Catched = " + e.getMessage());
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public void updatecoloc(Colocation o, int in) {

        try {
            Statement prep = connection.createStatement();

            String resi = "UPDATE colocation SET `DESCRIPTION`='" + o.getDESCRIPTION()
                    + "',`LIEU`='" + o.getLIEU()
                    + "',`LOYERMENSUEL`='" + o.getLOYERMENSUEL()
                    + "',`DATEDISPONIBILITE`='" + o.getDATEDISPONIBILITE()
                    + "' WHERE ID_PUB=" + in;
            prep.executeUpdate(resi);
            System.out.println("update done");
        } catch (SQLException ex) {

            System.out.println(ex.toString());
        }

    }

    @Override
    public ArrayList<Colocation> afficherColocation() {

        ArrayList<Colocation> Colocations = new ArrayList<>();
        try {
            Statement stm = connection.createStatement();

            ResultSet res = stm.executeQuery("SELECT * FROM `colocation` WHERE (DATEDISPONIBILITE> CURRENT_DATE)");

            while (res.next()) {
                Colocation e = new Colocation();
                e.setID_PUB(res.getInt("ID_PUB"));
                e.setDESCRIPTION(res.getString("DESCRIPTION"));
                e.setLIEU(res.getString("LIEU"));
                e.setDATEPUB(res.getDate("DATEPUB"));
                e.setID_USR(res.getInt("idUsr"));
                e.setDATEDISPONIBILITE(res.getDate("DATEDISPONIBILITE"));
                e.setLOYERMENSUEL(res.getFloat("LOYERMENSUEL"));
                
                Colocations.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("erreur afficher collocation");
        }
        return Colocations;
    }

    @Override
    public ArrayList<Colocation> getColocation(int idUsr) {
        ArrayList<Colocation> Colocations = new ArrayList<>();
        try {
            Statement stm = connection.createStatement();

            ResultSet res = stm.executeQuery("SELECT * FROM `colocation` where idUsr=" + idUsr);

            while (res.next()) {
                Colocation e = new Colocation();
                e.setID_PUB(res.getInt("ID_PUB"));
                e.setDESCRIPTION(res.getString("DESCRIPTION"));
                e.setLIEU(res.getString("LIEU"));
                e.setDATEPUB(res.getDate("DATEPUB"));
                e.setID_USR(res.getInt("idUsr"));
                e.setDATEDISPONIBILITE(res.getDate("DATEDISPONIBILITE"));
                e.setLOYERMENSUEL(res.getFloat("LOYERMENSUEL"));
                Colocations.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("erreur get colocation");
        }
        return Colocations;
    }

    public String getUserMail(int id) {

        try {
            Statement st;
            st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from user where id =" + id);
            rs.next();
            return (rs.getString("email"));

        } catch (Exception ex) {
            System.out.println(ex.toString());

        }
        return ("");
    }

    public ArrayList<Colocation> Statistique() {
        ArrayList<Colocation> Colocations = new ArrayList<>();
        try {
            Statement stm = connection.createStatement();

            ResultSet res = stm.executeQuery("SELECT LIEU,COUNT(LIEU) FROM `colocation` GROUP by LIEU ");

            while (res.next()) {
                Colocation e = new Colocation();
                e.setLieustat(res.getString(1));
                e.setNblieu(res.getInt(2));
                Colocations.add(e);

            }

        } catch (SQLException ex) {
            System.out.println("ereeeeeeeur");
        }
        return Colocations;
    }

    public String getMail(int id) {

        try {
            String q = "select * from user where (id= ?)";
            PreparedStatement prep = connection.prepareStatement(q);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return (rs.getString("EMAIL"));
        } catch (SQLException ex) {
            Logger.getLogger(ColocationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (null);

    }

    public int getTelephone(int id) {

        try {
            String q = "select * from user where (id= ?)";
            PreparedStatement prep = connection.prepareStatement(q);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return (rs.getInt("telephone"));
        } catch (SQLException ex) {
            Logger.getLogger(ColocationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (0);

    }

}
