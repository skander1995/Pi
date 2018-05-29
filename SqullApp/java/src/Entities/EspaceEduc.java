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
public class EspaceEduc {
    
    private String description ;
    private String type_aide;
    private String section ;
    private String Matiere ;
    private String document ;
    private int id_pub;
    private int id_usr;
    private Date datepub;
    private int ETAT;
    
    

    public EspaceEduc() {
    }

    public EspaceEduc(String description, String section, String Matiere, String document, int id_usr, Date datepub) {
        this.description = description;
        this.section = section;
        this.Matiere = Matiere;
        this.document = document;
        this.id_usr = id_usr;
        this.datepub = datepub;
    }

    

    public EspaceEduc(String description,  String section, String Matiere, String document/*,String id_usr*/) {
        this.description = description;
       
        this.section = section;
        this.Matiere = Matiere;
        this.document = document;
        //this.id_pub = id_pub;
       // this.id_usr = id_usr;
        
        this.ETAT = 0;
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

    public void setType_aide(String type_aide) {
        this.type_aide = type_aide;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setMatiere(String Matiere) {
        this.Matiere = Matiere;
    }

    public String getDescription() {
        return description;
    }

    public String getType_aide() {
        return type_aide;
    }

    public String getSection() {
        return section;
    }

    public String getMatiere() {
        return Matiere;
    }
    public String getDocument() {
        return document;
    }

    

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return description +" "+   type_aide +" " + section +" " + Matiere +" " + document +" " + id_pub +" " + id_usr  
                 +" "+ datepub +" " + ETAT +" " ;
    }

   
    
    
}
