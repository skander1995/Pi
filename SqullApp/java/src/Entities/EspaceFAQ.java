/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author Skander
 */
public class EspaceFAQ {
    
    private String description ;
 
    private int id_pub;
    private int id_usr;
    private Date datepub;
    private int ETAT;
    
    

    public EspaceFAQ() {
    }

    public EspaceFAQ(String description,int id_usr) {
        this.description = description;
        
        this.id_usr = id_usr;
       
    }

    

    public EspaceFAQ(String description) {
        this.description = description;
       
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
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

    public int getId_usr() {
        return id_usr;
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
