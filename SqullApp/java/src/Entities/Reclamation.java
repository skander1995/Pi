/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import EnumPack.Etat;
import EnumPack.ReclamationType;
import java.util.Date;

/**
 *
 * @author wildchild
 */
public class Reclamation {

    private int id_pub;
    private int id_usr;
    private String sujet;
    private Date datePub;
    private String description;
    private Etat etat;
    private ReclamationType type;
    // a list maybe
    private int use_id_usr;

    public Reclamation(int id_pub, int id_usr, int use_id_usr, Date datePub, String sujet, String description, Etat etat, ReclamationType type) {
        this.id_pub = id_pub;
        this.id_usr = id_usr;
        this.sujet = sujet;
        this.datePub = datePub;
        this.description = description;
        this.etat = etat;
        this.type = type;
        this.use_id_usr = use_id_usr;
    }

    public Reclamation() {
        
    }

    public ReclamationType getType() {
        return type;
    }

    public void setType(ReclamationType type) {
        this.type = type;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public int getId_usr() {
        return id_usr;
    }

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Date getDatePub() {
        return datePub;
    }

    public void setDatePub(Date datePub) {
        this.datePub = datePub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUse_id_usr() {
        return use_id_usr;
    }

    public void setUse_id_usr(int use_id_usr) {
        this.use_id_usr = use_id_usr;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id_pub=" + id_pub + ", id_usr=" + id_usr + ", sujet=" + sujet + ", datePub=" + datePub + ", description=" + description + ", target user=" + use_id_usr + '}';
    }

}
