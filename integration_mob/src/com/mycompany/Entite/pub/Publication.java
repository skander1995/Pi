/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite.pub;

import java.util.Date;

/**
 *
 * @author SELLAMI
 */
public class Publication {
    protected int id ;
    protected int UserId;
    protected Date date_creation ;
    protected String description;
    protected String etat ;

    public Publication(int id,int UserId, Date date_creation, String description, String etat) {
        this.id = id;
        this.UserId= UserId;
        this.date_creation = date_creation;
        this.description = description;
        this.etat = etat;
    }
    
    public Publication() {
        
    }

    @Override
    public String toString() {
        return "Publication{" + "id=" + id + ", UserId=" + UserId + ", date_creation=" + date_creation + ", description=" + description + ", etat=" + etat + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }
    
    
    
}
