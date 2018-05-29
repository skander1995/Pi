/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author SELLAMI
 */
public class Evenement extends Publication{
 
    protected String lieu;
    protected String affiche;
    protected String nom;
    protected Date date_debut;
    protected Date date_fin;
    protected int places_dispo;
    protected String participation ;
    
    public Evenement(String lieu,int userId, String affiche, String nom, Date date_debut, Date date_fin, int id, String description, String etat,int places_dispo) {
        super(id,userId,  description, etat);
        this.lieu = lieu;
        this.affiche = affiche;
        this.nom = nom;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.places_dispo=places_dispo;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }
    
    public Evenement(String lieu,int userId, String affiche, String nom, Date date_debut, Date date_fin, int id,Date date_crea, String description, String etat,int places_dispo) {
        super(id,userId,date_crea ,  description, etat);
        this.lieu = lieu;
        this.affiche = affiche;
        this.nom = nom;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.places_dispo=places_dispo;
    }

     
    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    @Override
    public String toString() {
        return "Evenement{" + "lieu=" + lieu + ", affiche=" + affiche + ", nom=" + nom + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", places_dispo=" + places_dispo + '}';
    }

   

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public int getPlaces_dispo() {
        return places_dispo;
    }

    public void setPlaces_dispo(int places_dispo) {
        this.places_dispo = places_dispo;
    }
    
    
    
}
