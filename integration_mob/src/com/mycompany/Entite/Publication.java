/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite;

import com.codename1.ui.Image;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author cobwi
 */
public class Publication {

    protected String ID_PUB = "";
    protected String idUsr = "";
    protected String DATEPUB = "";
    protected String titre = "";
    protected String DESCRIPTION = "";
    protected String ETAT = "";
    protected String linkPhotoProfile = "";
    protected String linkAffiche = "";
    protected String AFFICHE = "";
    protected String username = "";
    protected String nomprenom = "";
    protected String type = "";
    private Image placeholder;
    private Resources theme;

    String lieu;
    int userId;
    String affiche;
    String nom;
    Date date_debut;
    Date date_fin;
    int id;
    Date date_creation;
    String description;
    String etat;
    int places_dispo;

    public Publication(int id, int userId, Date date_creation, String description, String etat) {
        this.ID_PUB = String.valueOf(id);
        this.idUsr = String.valueOf(userId);
        this.date_creation = date_creation;
        this.description = description;
        this.ETAT = etat;
    }

    public String getID_PUB() {
        return ID_PUB;
    }

    public void setID_PUB(String ID_PUB) {
        this.ID_PUB = ID_PUB;
    }

    public String getIdUsr() {
        return idUsr;
    }

    public void setIdUsr(String idUsr) {
        this.idUsr = idUsr;
    }

    public String getDATEPUB() {
        return DATEPUB;
    }

    public void setDATEPUB(String DATEPUB) {
        this.DATEPUB = DATEPUB;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getETAT() {
        return ETAT;
    }

    public void setETAT(String ETAT) {
        this.ETAT = ETAT;
    }

    public String getLinkPhotoProfile() {
        return linkPhotoProfile;
    }

    public void setLinkPhotoProfile(String linkPhotoProfile) {
        this.linkPhotoProfile = linkPhotoProfile;
    }

    public String getLinkAffiche() {
        return linkAffiche;
    }

    public void setLinkAffiche(String linkAffiche) {
        this.linkAffiche = linkAffiche;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomprenom() {
        return nomprenom;
    }

    public void setNomprenom(String nomprenom) {
        this.nomprenom = nomprenom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        //System.out.println("TYPE :" + type);
        switch (type) {
            case "evenement": {
                this.placeholder = theme.getImage("placeholderevent.png");
                break;
            }
            case "colocation": {
                this.placeholder = theme.getImage("placeholdercoloc.jpg");
                break;
            }
            case "aide": {
                this.placeholder = theme.getImage("placeholderaide.png");
                break;
            }
            case "covoiturage": {
                this.placeholder = theme.getImage("covplaceholder.jpg");
                break;
            }
            default: {
                this.placeholder = theme.getImage("default-placeholder.png");
            }
        }
    }

    public Image getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Image placeholder) {
        this.placeholder = placeholder;
    }

    public Publication() {
        try {
            this.theme = Resources.openLayered("/theme4");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Publication{" + "ID_PUB=" + ID_PUB + ", idUsr=" + idUsr + ", DATEPUB=" + DATEPUB + ", titre=" + titre + ", DESCRIPTION=" + DESCRIPTION + ", ETAT=" + ETAT + ", linkPhotoProfile=" + linkPhotoProfile + ", linkAffiche=" + linkAffiche + ", AFFICHE=" + AFFICHE + ", username=" + username + ", nomprenom=" + nomprenom + ", type=" + type + ", placeholder=" + placeholder + ", theme=" + theme + '}';
    }

}
