/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Evenement;
import Entities.User;
import EnumPack.TypeUser;
import IServices.IEvenement;
import JDBC.JdbcInstance;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utilities.BCrypt;
import Utilities.ToolsUtilities;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author SELLAMI
 */
public class EvenementService implements IEvenement {

    private final Connection conn;
    Statement st;
    PreparedStatement prepSt;

    public EvenementService() {
        conn = JdbcInstance.getInstance();
    }

    @Override
    public boolean ajouterEvenement(Evenement e) {
        int rows = 0;
        // System.out.println("dateeee   " + date);
        String date = ToolsUtilities.formater.format(new Date());
        String query = "insert into evenements (idUsr,description,etat,nom_event,datedebut,datefin,lieu,affiche,place_dispo,datepub) values(?,?,?,?,?,?,?,?,?,?);";

        try {
            /*String query=" insert into evenement (id_pub,idUsr) values('"+e.getId()+"','"+e.getUserId()+"');";
             st = conn.createStatement();*/
            prepSt = conn.prepareStatement(query);

            prepSt.setInt(1, e.getUserId());
            //prepSt.setString(3, ToolsUtilities.formater.format(e.getDate_creation()));
            prepSt.setString(2, e.getDescription());
            prepSt.setString(3, e.getEtat());
            prepSt.setString(4, e.getNom());
            prepSt.setString(5, ToolsUtilities.formater.format(e.getDate_debut()));
            prepSt.setString(6, ToolsUtilities.formater.format(e.getDate_fin()));
            prepSt.setString(7, e.getLieu());
            prepSt.setString(8, e.getAffiche());
            prepSt.setInt(9, e.getPlaces_dispo());
            prepSt.setString(10, date);

            rows = prepSt.executeUpdate();
        } catch (SQLException ex) {

            System.out.println(ex.toString());
            return (false);
        }
        return (rows > 0);
    }

    public boolean supprimerEvenementById(Evenement e) {
        int rows = 0;
        String query = "delete from evenements where (id_pub ='" + e.getId() + "')";
        try {
            st = conn.createStatement();

            rows = st.executeUpdate(query);
        } catch (SQLException ex) {

            System.out.println(ex.toString());
            return (false);
        }
        return (rows > 0);

    }

    public boolean supprimerEvenementById(int x) {
        int rows = 0;
        String query = "delete from evenements where (id_pub ='" + x + "')";
        try {
            st = conn.createStatement();

            rows = st.executeUpdate(query);
        } catch (SQLException ex) {

            System.out.println(ex.toString());
            return (false);
        }
        return (rows > 0);

    }

    public boolean modifierEvenement(String nom, String lieu, int nb, String desc, Date dated, Date datef, int id, String affiche) {
        try {
            String query = "UPDATE evenements SET "
                    + "description=?,nom_event=?,datedebut=?,datefin=?,lieu=?,place_dispo=? , affiche =?"
                    + " where id_pub = ? ";

            prepSt = conn.prepareStatement(query);
            prepSt.setString(1, desc);
            prepSt.setString(2, nom);

            prepSt.setString(3, ToolsUtilities.formater.format(dated));
            prepSt.setString(4, ToolsUtilities.formater.format(datef));
            prepSt.setString(5, lieu);
            prepSt.setInt(6, nb);
            prepSt.setInt(8, id);
            prepSt.setString(7, affiche);

            prepSt.executeUpdate();
            return (true);
        } catch (SQLException ex) {

            System.out.println(ex.toString());
            return (false);
        }

    }

    @Override
    public boolean modifierEvenement(Evenement e) {
        try {
            String query = "UPDATE evenements SET "
                    + "description=?,etat=?,nom_event=?,datedebut=?,datefin=?,lieu=?,affiche=?"
                    + "where (id_pub=?) AND (idUsr=?)";

            prepSt = conn.prepareStatement(query);
            prepSt.setString(1, e.getDescription());
            prepSt.setString(2, e.getEtat());
            prepSt.setString(3, e.getNom());
            prepSt.setString(4, ToolsUtilities.formater.format(e.getDate_debut()));
            prepSt.setString(5, ToolsUtilities.formater.format(e.getDate_fin()));
            prepSt.setString(6, e.getLieu());
            prepSt.setString(7, e.getAffiche());
            prepSt.setInt(8, e.getId());
            prepSt.setInt(9, e.getUserId());

            prepSt.executeUpdate();
            return (true);
        } catch (SQLException ex) {

            System.out.println(ex.toString());
            return (false);
        }

    }

    @Override
    public Evenement afficherEvenementById(int id) {
        Evenement ev;
        try {
            String query = "select * from evenements where (id_pub=?);";
            prepSt = conn.prepareStatement(query);
            prepSt.setInt(1, id);
            ResultSet rs = prepSt.executeQuery();
            rs.next();
            ev = new Evenement(
                    rs.getString("lieu"),
                    rs.getInt("idUsr"),
                    rs.getString("affiche"),
                    rs.getString("nom_event"),
                    ToolsUtilities.formater.parse(rs.getString("datedebut")),
                    ToolsUtilities.formater.parse(rs.getString("datefin")),
                    rs.getInt("id_pub"),
                    ToolsUtilities.formater.parse(rs.getString("datepub")),
                    rs.getString("description"),
                    rs.getString("etat"),
                    rs.getInt("place_dispo")
            );

            return (ev);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return (null);
    }

    @Override
    public ArrayList<Evenement> afficherEvenements() {
        Evenement ev;
        ArrayList<Evenement> events = new ArrayList<>();
        try {

            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from evenements ");
            while (rs.next()) {

                ev = new Evenement(
                        rs.getString("lieu"),
                        rs.getInt("idUsr"),
                        rs.getString("affiche"),
                        rs.getString("nom_event"),
                        ToolsUtilities.formater.parse(rs.getString("datedebut")),
                        ToolsUtilities.formater.parse(rs.getString("datefin")),
                        rs.getInt("id_pub"),
                        ToolsUtilities.formater.parse(rs.getString("datepub")),
                        rs.getString("description"),
                        rs.getString("etat"),
                        rs.getInt("place_dispo")
                );
                System.out.println(ev.getDate_debut());
                events.add(ev);

            }
            //return (events);
        } catch (Exception ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return (events);
    }

    public ArrayList<Evenement> afficherEvenementsAdmin() {
        Evenement ev;
        ArrayList<Evenement> events = new ArrayList<>();
        try {

            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from evenement");
            while (rs.next()) {

                ev = new Evenement(
                        rs.getString("lieu"),
                        rs.getInt("idUsr"),
                        rs.getString("affiche"),
                        rs.getString("nom_event"),
                        ToolsUtilities.formater.parse(rs.getString("datedebut")),
                        ToolsUtilities.formater.parse(rs.getString("datefin")),
                        rs.getInt("id_pub"),
                        ToolsUtilities.formater.parse(rs.getString("datepub")),
                        rs.getString("description"),
                        rs.getString("etat"),
                        rs.getInt("place_dispo")
                );
                events.add(ev);

            }
            //return (events);
        } catch (Exception ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return (events);
    }

    public ArrayList<Evenement> afficherEvenements(int userid) {
        Evenement ev;
        ArrayList<Evenement> events = new ArrayList<>();
        try {

            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from evenement ");
            while (rs.next()) {
                Statement stt = conn.createStatement();
                ResultSet part = stt.executeQuery("select * from reservation where(( idUsr=" + userid + ")and(id_pub=" + rs.getString("id_pub") + "))");

                ev = new Evenement(
                        rs.getString("lieu"),
                        rs.getInt("idUsr"),
                        rs.getString("affiche"),
                        rs.getString("nom_event"),
                        ToolsUtilities.formater.parse(rs.getString("datedebut")),
                        ToolsUtilities.formater.parse(rs.getString("datefin")),
                        rs.getInt("id_pub"),
                        ToolsUtilities.formater.parse(rs.getString("datepub")),
                        rs.getString("description"),
                        rs.getString("etat"),
                        rs.getInt("place_dispo")
                );
                if (part.next()) {
                    ev.setParticipation("participé");
                } else {
                    ev.setParticipation(" non participé");
                }
                events.add(ev);

            }
            //return (events);
        } catch (Exception ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return (events);
    }

    public ArrayList<Evenement> afficherEvenementsById(int id) {
        Evenement ev;
        ArrayList<Evenement> events = new ArrayList<>();
        try {

            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from evenements where idUsr= " + id);
            while (rs.next()) {

                ev = new Evenement(
                        rs.getString("lieu"),
                        rs.getInt("idUsr"),
                        rs.getString("affiche"),
                        rs.getString("nom_event"),
                        ToolsUtilities.formater.parse(rs.getString("datedebut")),
                        ToolsUtilities.formater.parse(rs.getString("datefin")),
                        rs.getInt("id_pub"),
                        ToolsUtilities.formater.parse(rs.getString("datepub")),
                        rs.getString("description"),
                        rs.getString("etat"),
                        rs.getInt("place_dispo")
                );
                events.add(ev);

            }
            //return (events);
        } catch (Exception ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return (events);
    }

    @Override
    public boolean reserverEvenement(int id, int idUsr) {
        try {
            String query = "UPDATE evenement SET "
                    + "place_dispo = place_dispo - 1 "
                    + "where (id_pub=?)and(place_dispo>0)";
            prepSt = conn.prepareStatement(query);
            prepSt.setInt(1, id);
            int x = prepSt.executeUpdate();
            if (x == 1) {
                query = "insert into reservation (idUsr,id_pub) values (?,?)";
                prepSt = conn.prepareStatement(query);
                prepSt.setInt(1, idUsr);
                prepSt.setInt(2, id);
                prepSt.executeUpdate();
                return (true);
            } else {
                return (false);
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return (false);

        }
    }

    public boolean changerEtat(int id, String etat) {
        try {
            String query = "UPDATE evenement SET "
                    + "etat =? "
                    + " where (id_pub=?)";
            prepSt = conn.prepareStatement(query);
            prepSt.setString(1, etat);
            prepSt.setInt(2, id);
            prepSt.executeUpdate();
            return (true);

        } catch (Exception ex) {
            return (false);
        }
    }

    public String getUserMail(int id) {

        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from user where idUsr =" + id);
            rs.next();
            return (rs.getString("email"));

        } catch (Exception ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return ("");
    }

    public boolean AnnulerReserverEvenement(int idpub, int idOfConnectedUser) {
        int rows = 0;
        String query = "delete from reservation where ((id_pub ='" + idpub + "')and(idUsr='" + idOfConnectedUser + "'))";
        try {
            st = conn.createStatement();

            rows = st.executeUpdate(query);
            query = "UPDATE evenement SET "
                    + "place_dispo = place_dispo + 1 "
                    + "where (id_pub=?)";
            prepSt = conn.prepareStatement(query);
            prepSt.setInt(1, idpub);
            prepSt.executeUpdate();
        } catch (SQLException ex) {

            System.out.println(ex.toString());
            return (false);
        }
        return (rows > 0);
    }

    public ArrayList<User> getListParticipants(int id) {
        User user;
        ArrayList<User> users = new ArrayList<>();
        try {

            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from reservation where id_pub= " + id);
            while (rs.next()) {
                String query = "select * from user where (idUsr= ?)";
                prepSt = conn.prepareStatement(query);
                prepSt.setInt(1, rs.getInt("idUsr"));
                ResultSet rsu = prepSt.executeQuery();
                rsu.next();
                user = new User(rsu.getInt("idUsr"), rsu.getString("NAME_USR"), rsu.getString("PRN_USR"), rsu.getString("EMAIL"), rsu.getString("telephone_usr"), rsu.getString("PHOTO_PROFILE"));
                users.add(user);

            }
            //return (events);
        } catch (Exception ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return (users);

    }
}
