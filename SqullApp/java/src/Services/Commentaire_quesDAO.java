/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Com_quest.Commentaire_ques;
import Com_quest.ICommentaire_ques;
import JDBC.MyDb;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author Omar Dagdoug
 */
public class Commentaire_quesDAO implements ICommentaire_ques {

    MyDb dataSource;
    PreparedStatement st;

    public Commentaire_quesDAO() {

        dataSource = MyDb.getinstance();
    }

    @Override
    public void AjouterCommentaire(Commentaire_ques c) {
         String format = "yyyy-MM-dd";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
        java.util.Date date = new java.util.Date();
        try {

            Calendar calendar = Calendar.getInstance();
            String req = "INSERT INTO Commentaire_ques (CONTENU_COM,idUsr,ID_PUB,Date_com) "
                    + "VALUES(?,?,?,?)";

            st = dataSource.getConnection().prepareStatement(req);
            //st.setInt(1, c.getId());
            st.setInt(2, c.getId_user());

            st.setString(1, c.getContenu());
            st.setInt(3, c.getId_Plan());
             st.setDate(4, Date.valueOf(formater.format(date)));
            st.executeUpdate();//insert update delete

            System.out.println("temchi");
        } catch (SQLException ex) {
            System.out.println(st);
            System.out.println("ma temchich "+ex.getMessage());
            
        }
    }

    public List<Commentaire_ques> datalist(int id_pub) {
        List<Commentaire_ques> list = listerComdePlan(id_pub);
        List<Commentaire_ques> data = new ArrayList<>();
        for (Commentaire_ques c : list) {

            try {
                String req;

                req = "SELECT nom,prenom FROM `user` WHERE ID= ?";

                st = dataSource.getConnection().prepareStatement(req);
                st.setInt(1, c.getId_user());

                ResultSet rs = null;

                rs = st.executeQuery();

                while (rs.next()) {
                    
                    c.setFirst_name(rs.getString("nom"));
                    
                    c.setLast_name(rs.getString("prenom"));
                    
                    data.add(c);
                    
                }

            } catch (SQLException ex) {
                Logger.getLogger(Commentaire_quesDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Commentaire_ques> listerComdePlan(int id_pub) {

        List<Commentaire_ques> list = new ArrayList<>();
        try {

            // pour récupérer le résultat de select
            String req = " SELECT * from Commentaire_ques WHERE ID_PUB='" + id_pub + "'";

            st = dataSource.getConnection().prepareStatement(req);
            //st.setInt(1,idplan);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Commentaire_ques c = new Commentaire_ques();
                c.setId(rs.getInt("ID_COM"));
                c.setContenu(rs.getString("CONTENU_COM"));
                c.setDate_creation(rs.getDate("DATE_COM"));
                c.setId_user(rs.getInt("idUsr"));
                c.setId_Plan(rs.getInt("ID_PUB"));
                list.add(c);
               
            }
        } catch (SQLException ex) {
            System.out.println("andek ghalta fel select");
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
    public void ModifierCommentaire(Commentaire_ques c) {

        try {

            String req = "UPDATE Commentaire_ques "
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
            Logger.getLogger(Commentaire_quesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ma temchich ");
        }

    }

    @Override
    public void SupprimerCommentaire(Commentaire_ques c) {

        try {
            String req = "DELETE FROM Commentaire_ques WHERE id = ?";
            st = dataSource.getConnection().prepareStatement(req);
            st.setInt(1, c.getId());
            st.executeUpdate();
            System.out.println("tfasa5");
        } catch (SQLException ex) {
            Logger.getLogger(Commentaire_quesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("matfasa5ch");
        }

    }

    @Override
    public void lister() {

        List<Commentaire_ques> list = new ArrayList<>();
        ResultSet rs; // pour récupérer le résultat de select
        String req = "SELECT * FROM Commentaire_ques;";

        try {
            st = dataSource.getConnection().prepareStatement(req);
            rs = st.executeQuery(req);

            while (rs.next()) {
                Commentaire_ques c = new Commentaire_ques();
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
            Logger.getLogger(Commentaire_quesDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public List<Commentaire_ques> listerCom() {

        List<Commentaire_ques> list = new ArrayList<>();
        ResultSet rs; // pour récupérer le résultat de select
        String req = "SELECT * FROM Commentaire_ques ";

        try {
            st = dataSource.getConnection().prepareStatement(req);
            rs = st.executeQuery(req);

            while (rs.next()) {
                Commentaire_ques c = new Commentaire_ques();
                c.setId(rs.getInt(1));
                c.setContenu(rs.getString(2));
                c.setDate_creation(rs.getDate(3));
                c.setId_user(rs.getInt(4));
                c.setId_Plan(rs.getInt(5));
                list.add(c);
            }

            return list;

        } catch (SQLException ex) {
            Logger.getLogger(Commentaire_quesDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    @Override
    public Commentaire_ques getCommentaireById(int id) {
        List<Commentaire_ques> list = listerCom();
        for (Commentaire_ques c : list) {
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
        String req = "SELECT id_p , `adresse` from `plan` WHERE id_p IN (SELECT DISTINCT id_p FROM `Commentaire_ques` where id_u = " + user + ")";

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
            Logger.getLogger(Commentaire_quesDAO.class.getName()).log(Level.SEVERE, null, ex);

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
