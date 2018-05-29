/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Evaluation.Evaluation;
import Evaluation.IEvaluationservice;
import JDBC.MyDb;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Skander
 */
public class EvaluationService implements IEvaluationservice {

    MyDb conn;

    public EvaluationService() {
        conn = MyDb.getinstance();
    }

    @Override
    public ArrayList<Evaluation> getevaluations(int idpub) {
        ArrayList<Evaluation> eval = new ArrayList<>();
        try {
            Statement stm = conn.getConnection().createStatement();

            ResultSet res = stm.executeQuery("SELECT `note` FROM `evaluaion`where ID_PUB=" + idpub);

            while (res.next()) {
                Evaluation e = new Evaluation();

                e.setNote(res.getInt("note"));
                eval.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("mochkel niveau evaluation   " + ex.getCause());
        }
        return eval;
    }

    public float notetotale(int id_pub) {
        float note = 0;
        float somme = 0;
        float t = getevaluations(id_pub).size();
        for (Evaluation e : getevaluations(id_pub)) {
            System.out.println(" somme el kdima" + somme + "+ jdida" + e.getNote());
            somme += e.getNote();
        }
        note = somme / t;
        return note;
    }

    @Override
    public void ajouterev(Evaluation e, int id_current_user) {
        /*
        System.out.println(e.getId_usr());
        System.out.println(id_current_user);

        System.out.println(e.getNote());
        System.out.println(e.getId_pub());
         */
        String resi = "INSERT INTO `evaluaion`(`ID_USR`,`note`, `ID_PUB`)VALUES (?,?,?)";
        PreparedStatement prep = null;

        try {
            prep = conn.getConnection().prepareStatement(resi);
            prep.setInt(1, id_current_user);
            prep.setInt(2, e.getNote());
            prep.setInt(3, e.getId_pub());

            prep.executeUpdate();
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println("erreur niveau ajout evaluation   " + ex.getCause());
            /*
            
             System.out.println(prep);
            System.out.println();
            
             */
            updateev(e, id_current_user);
        }
    }

    @Override
    public void updateev(Evaluation e, int id) {
        try {
            Statement prep = conn.getConnection().createStatement();

            String resi = "UPDATE `evaluaion` SET `note`='" + e.getNote()
                    + "' WHERE ID_USR=" + id + " and id_pub=" + e.getId_pub();
            System.out.println(resi);
            prep.executeUpdate(resi);

            System.out.println("update done");
        } catch (SQLException ex) {

            System.out.println("update error" + ex.getCause());
        }

    }

}
