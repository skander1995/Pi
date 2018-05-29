/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EspaceFAQ;

import EspaceEducatif.User;
import java.util.Date;

/**
 *
 * @author Skander
 */
public class EspaceFAQ {
    
    private String description ;
    private String sujet ;
 
    private int id_pub;
    private User id_usr;
    private Date datepub;
    private int ETAT;

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }
    
    

    public EspaceFAQ() {
    }

  

    

    public EspaceFAQ(String description) {
        this.description = description;
       
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

   

    public void setDatepub(Date datepub) {
        this.datepub = datepub;
    }

    public void setETAT(int ETAT) {
        this.ETAT = ETAT;
    }

    public int getId_pub() {
        return id_pub;
    }

    public User getId_usr() {
        return id_usr;
    }

    public void setId_usr(User id_usr) {
        this.id_usr = id_usr;
    }

    

    public Date getDatepub() {
        return datepub;
    }

    public int getETAT() {
        return ETAT;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "EspaceFAQ{" + "description=" + description + ", id_pub=" + id_pub + ", id_usr=" + id_usr + ", datepub=" + datepub + ", ETAT=" + ETAT + '}';
    }

  
}
