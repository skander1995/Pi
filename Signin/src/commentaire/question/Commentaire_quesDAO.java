/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commentaire.question;

import EspaceEducatif.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Skander
 */
public class Commentaire_quesDAO {

    public Commentaire_quesDAO() {

    }

    public void AjouterCommentaire_ques(Commentaire_ques o, int idusr) {

        String url = "http://localhost/FosBundleProj/web/app_dev.php/tasks/comadd/" + idusr
                + "/" + o.getId_Plan()
                + "/" + o.getContenu();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public ArrayList<Commentaire_ques> getListTask(String json) {

        ArrayList<Commentaire_ques> listeAides = new ArrayList<>();

        try {
            //System.out.println("json:    "+json);
            JSONParser j = new JSONParser();
            Map<String, Object> aides = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) aides.get("root");
            //System.out.println("lista :   "+list);
            for (Map<String, Object> obj : list) {
                Commentaire_ques e = new Commentaire_ques();

                // System.out.println(obj.get("id"));
                float id = Float.parseFloat(obj.get("idPub").toString());
                Map<String, Object> dates = (Map<String, Object>) obj.get("dateCom");
                float date = Float.parseFloat(dates.get("timestamp").toString());
                Date datepub = new Date((long) (date - 3600) * 1000);
                User us = new User();

                Map<String, Object> user = (Map<String, Object>) obj.get("idUsr");

                float idu = Float.parseFloat(user.get("id").toString());
                us.setId((int) idu);

                us.setLogin(user.get("username").toString());

                us.setPhotoProfil(user.get("photoProfile").toString());

                //System.out.println(id);
                e.setId_Plan((int) id);
                e.setId_user(us);
                e.setContenu(obj.get("contenuCom").toString());
                e.setDate_creation(datepub);
                //System.out.println(e);
                listeAides.add(e);

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(listeAides);
        return listeAides;

    }

    ArrayList<Commentaire_ques> e = new ArrayList();

    public List<Commentaire_ques> consulterespace() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/FosBundleProj/web/app_dev.php/question/comments/all");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Commentaire_quesDAO ser = new Commentaire_quesDAO();
                e = ser.getListTask(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return e;
    }

    private void zeyed() {
        /**
         *
         * @param c
         */
        /*
    
    public void ModifierCommentaire_ques(Commentaire_ques c) {

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
            Logger.getLogger(Commentaire_quesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ma temchich ");
        }

    }

    
    public void SupprimerCommentaire_ques(Commentaire_ques c) {

        try {
            String req = "DELETE FROM commentaire WHERE id = ?";
            st = dataSource.getConnection().prepareStatement(req);
            st.setInt(1, c.getId());
            st.executeUpdate();
            System.out.println("tfasa5");
        } catch (SQLException ex) {
            Logger.getLogger(Commentaire_quesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("matfasa5ch");
        }

    }

    
    public void lister() {

        List<Commentaire_ques> list = new ArrayList<>();
        ResultSet rs; // pour récupérer le résultat de select
        String req = "SELECT * FROM commentaire;";

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

    
    public List<Commentaire_ques> listerCom() {

        List<Commentaire_ques> list = new ArrayList<>();
        ResultSet rs; // pour récupérer le résultat de select
        String req = "SELECT * FROM commentaire ";

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

    
    public Commentaire_ques getCommentaire_quesById(int id) {
        List<Commentaire_ques> list = listerCom();
        for (Commentaire_ques c : list) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    
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
