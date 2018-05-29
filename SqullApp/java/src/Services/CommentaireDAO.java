/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;



import Entities.Commentaire;

import IServices.ICommentaire;
import JDBC.MyDb;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Skander
 */
public class CommentaireDAO implements ICommentaire {

    MyDb dataSource;
    PreparedStatement st;

    public CommentaireDAO() {

        dataSource = MyDb.getinstance();
    }

    @Override
    public void AjouterCommentaire(Commentaire c) {
         String format = "yyyy-MM-dd";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
        java.util.Date date = new java.util.Date();
        try {

            Calendar calendar = Calendar.getInstance();
            String req = "INSERT INTO commentaire (CONTENU_COM,idUsr,ID_PUB,`DATE_COM`) "
                    + "VALUES(?,?,?,?)";

            st = dataSource.getConnection().prepareStatement(req);
            //st.setInt(1, c.getId());
            st.setInt(2, c.getId_user());

            st.setString(1, c.getContenu());
            st.setInt(3, c.getId_Plan());
            st.setDate(4, Date.valueOf(formater.format(date)));
            
            st.executeUpdate();//insert update delete
System.out.println(st);
            System.out.println("temchi");
        } catch (SQLException ex) {
            System.out.println(st);
            System.out.println("ma temchich ");
        }
    }

    public List<Commentaire> datalist(int id_pub) {
        List<Commentaire> list = listerComdePlan(id_pub);
        List<Commentaire> data = new ArrayList<>();
        System.out.println("from data list id pub: "+id_pub);
        for (Commentaire c : list) {
               
            try {
                String req;

                req = "SELECT nom,prenom FROM `user` WHERE ID= ?";

                st = dataSource.getConnection().prepareStatement(req);
                st.setInt(1, c.getId_user());

                ResultSet rs = null;

                rs = st.executeQuery();

                while (rs.next()) {
                    Commentaire a = new Commentaire();
                    a.setFirst_name(rs.getString("nom"));

                    a.setLast_name(rs.getString("prenom"));
                    a.setContenu(c.getContenu());
                    data.add(a);
                    

                }

            } catch (SQLException ex) {
                System.out.println("error datalist "+ex.getMessage());
            }

        }
        return data;

    }

    public String selectmail(int id_usr) {
        String mail = "";
        String req = "select email from `user` where id=?";

        try {
            st = dataSource.getConnection().prepareStatement(req);
            st.setInt(1, id_usr);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                mail = rs.getString("email");
            }

        } catch (SQLException ex) {
            System.out.println("mail ghalet");
        }
        return mail;
    }

    @Override
    public List<Commentaire> listerComdePlan(int id_pub) {

        List<Commentaire> list = new ArrayList<>();
        try {

            // pour récupérer le résultat de select
            String req = " SELECT * from Commentaire WHERE ID_PUB=" + id_pub;

            st = dataSource.getConnection().prepareStatement(req);
            //st.setInt(1,idplan);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setId(rs.getInt("ID_COM"));
                c.setContenu(rs.getString("CONTENU_COM"));
                c.setDate_creation(rs.getDate("DATE_COM"));
                c.setId_user(rs.getInt("idUsr"));
                c.setId_Plan(rs.getInt("ID_PUB"));
                list.add(c);
                System.out.println(id_pub);
            }
        } catch (SQLException ex) {
            System.out.println("andek ghalta fel select comm");
        }
        return list;

    }

    void zeyed() {
        /**
         *
         * @param c
         */
        /*
    @Override
    public void ModifierCommentaire(Commentaire c) {

        try {

            String req = "UPDATE commentaire "
                    + "SET description=?,date=?, id_user=?, id_plan=? where id=? ";

            st = dataSource.getConnection().prepareStatement(req);

            st.setInt(3, c.getId_user());
            st.setDate(2, c.getDate_creation());
            st.setString(1, c.getContenu());
            st.setInt(4, c.getId_Plan());
            st.setInt(5, c.getId());
            st.executeUpdate();//insert update delete

            System.out.println("temchi");
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ma temchich ");
        }

    }

    @Override
    public void SupprimerCommentaire(Commentaire c) {

        try {
            String req = "DELETE FROM commentaire WHERE id = ?";
            st = dataSource.getConnection().prepareStatement(req);
            st.setInt(1, c.getId());
            st.executeUpdate();
            System.out.println("tfasa5");
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("matfasa5ch");
        }

    }

    @Override
    public void lister() {

        List<Commentaire> list = new ArrayList<>();
        ResultSet rs; // pour récupérer le résultat de select
        String req = "SELECT * FROM commentaire;";

        try {
            st = dataSource.getConnection().prepareStatement(req);
            rs = st.executeQuery(req);

            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setId(rs.getInt(1));
                c.setContenu(rs.getString(2));
                c.setDate_creation(rs.getDate(3));
                c.setId_user(rs.getInt(4));
                c.setId_Plan(rs.getInt(5));

                list.add(c);
            }
            list.forEach((c) -> {
                System.out.println(c);
            });

        } catch (SQLException ex) {
            Logger.getLogger(CommentaireDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public List<Commentaire> listerCom() {

        List<Commentaire> list = new ArrayList<>();
        ResultSet rs; // pour récupérer le résultat de select
        String req = "SELECT * FROM commentaire ";

        try {
            st = dataSource.getConnection().prepareStatement(req);
            rs = st.executeQuery(req);

            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setId(rs.getInt(1));
                c.setContenu(rs.getString(2));
                c.setDate_creation(rs.getDate(3));
                c.setId_user(rs.getInt(4));
                c.setId_Plan(rs.getInt(5));
                list.add(c);
            }

            return list;

        } catch (SQLException ex) {
            Logger.getLogger(CommentaireDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    @Override
    public Commentaire getCommentaireById(int id) {
        List<Commentaire> list = listerCom();
        for (Commentaire c : list) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Plan> listerMesPlanCom() {
//        badel User  user = getCurrentUser();
        int user = 1;

        List<Plan> list = new ArrayList<>();

        ResultSet rs; // pour récupérer le résultat de select
        String req = "SELECT id_p , `adresse` from `plan` WHERE id_p IN (SELECT DISTINCT id_p FROM `commentaire` where id_u = " + user + ")";

        try {
            st = dataSource.getConnection().prepareStatement(req);
            rs = st.executeQuery(req);

            while (rs.next()) {
                Plan p = new Plan();
                p.setIdPlan(rs.getInt(1));
                p.setAdresse(rs.getString(2));

                list.add(p);
            }

            return list;

        } catch (SQLException ex) {
            Logger.getLogger(CommentaireDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    /* public List <String> getNameUserById(int id) throws SQLException{
         List <String > ls = new ArrayList<>() ;
            String req;
            
            req = "SELECT nom,prenom FROM `user` WHERE id_u= ?";
            
            st = dataSource.getConnection().prepareStatement(req);
            
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            
            
            while(rs.next()){
               
                ls.add(rs.getString(1));
                ls.add(rs.getString(2));
            }
            
        return ls ;  
       
     } */
    }
}
