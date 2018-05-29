/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Reclamation;
import Entities.User;
import EnumPack.Etat;
import EnumPack.ReclamationType;
import JDBC.JdbcInstance;
import Utilities.ToolsUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cobwi
 */
public class ReclamationService {

    private Connection connection;

    public ReclamationService() {
        this.connection = JdbcInstance.getInstance();
    }

    public int add(Reclamation reclamation) {
        try {
            String request = "INSERT INTO `reclamation`( `ID_USR`, `USE_ID_USR`, `DATEPUB`, `SUJET_REC`, `DESCRIPTION`, `ETAT`, `TYPE_REC`) VALUES"
                    + " (?,?,?,?,?,?,?)";
            PreparedStatement state = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            //state.setInt(1, ); // null
            state.setInt(1, reclamation.getId_usr());
            state.setInt(2, reclamation.getUse_id_usr());
            state.setString(3, ToolsUtilities.formater.format(reclamation.getDatePub()));
            state.setString(4, reclamation.getSujet());
            state.setString(5, reclamation.getDescription());
            state.setString(6, reclamation.getEtat().name());
            state.setString(7, reclamation.getType().name());

            int returnLastInsertId = state.executeUpdate();

            if (returnLastInsertId > 0) {
                ResultSet rs = state.getGeneratedKeys();
                rs.next();
                int insertedId = rs.getInt(1);
                reclamation.setId_pub(insertedId);
            }
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
            return -1;
        }
        return reclamation.getId_pub();
    }

    public boolean delete(Integer id) {
        String req = "delete from user where id_reclamation = ?";
        try {
            PreparedStatement state = connection.prepareStatement(req);
            state.setInt(1, id);
            state.executeUpdate();
            return true;
        } catch (Exception ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catcheddelete rec service = " + ex.getMessage());
            }
            return false;
        }
    }

    public boolean update(Reclamation reclamation, HashMap<String, String> args) {

        //{username : wildchild },{nom : elyes }
        // test if hash map contains 0 fields to update
        try {
            StringBuilder builder = new StringBuilder();

            /*for (int i = 0; i < args.size(); i++) {
                builder.append("?,");
            }
             */
            for (Map.Entry arg : args.entrySet()) {
                if (ToolsUtilities.DEBUG) {
                    System.out.println("print = " + arg.getKey() + " " + arg.getValue());
                }
                // + arg.getValue()
                builder.append(arg.getKey() + "=?,");
            }
            String request = "UPDATE reclamation SET " + builder.deleteCharAt(builder.length() - 1).toString()
                    + " WHERE ID_PUB = " + reclamation.getId_pub();

            PreparedStatement pstmnt = connection.prepareStatement(request);
            int index = 1;
            for (Map.Entry arg : args.entrySet()) {
                pstmnt.setString(index++, arg.getValue().toString());
            }
            System.out.println("request + " + request);
            pstmnt.executeUpdate();

        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
            return false;
        }
        return true;
    }

    public List<Reclamation> getAll() {
        List<Reclamation> recs = new ArrayList<>();
        try {
            String request = "SELECT `ID_PUB`, `ID_USR`, `USE_ID_USR`, `DATEPUB`, `SUJET_REC`, `DESCRIPTION`, `ETAT`, `TYPE_REC` "
                    + " FROM `reclamation` WHERE 1";
            ResultSet rst = connection.createStatement().executeQuery(request);
            while (rst.next()) {
                Reclamation reclamation = new Reclamation(
                        rst.getInt(1),
                        rst.getInt(2),
                        rst.getInt(3),
                        ToolsUtilities.formater.parse(rst.getString(4)),
                        rst.getString(5),
                        rst.getString(6),
                        Etat.valueOf(rst.getString(7)),
                        ReclamationType.valueOf(rst.getString(8))
                );
                recs.add(reclamation);
            }

        } catch (SQLException | ParseException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched from getAll.userService = " + ex.getMessage());
            }
        }
        return recs;
    }
}
