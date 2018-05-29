/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author Moez
 */
public class Covoiturage {
    protected int ID_PUB;
    protected int ID_VILLE;
    protected int VIL_ID_VILLE;
    protected int ID_USR;
    protected Date DATEPUB;
    protected String DESCRIPTION;
    protected String ETAT;
    protected String LIEUDEPART;
    protected String LIEUARRIVE;
    protected Date DATEDEPART;
    protected float PRIX;
    protected String CHECKPOINTS;
    protected int NBPLACE;

    public Covoiturage() {
    }

    
    
    public Covoiturage(int ID_PUB, int ID_VILLE, int VIL_ID_VILLE, int ID_USR, Date DATEPUB, String DESCRIPTION, String ETAT, String LIEUDEPART, String LIEUARRIVE, Date DATEDEPART, float PRIX, String CHECKPOINTS, int NBPLACE) {
        this.ID_PUB = ID_PUB;
        this.ID_VILLE = ID_VILLE;
        this.VIL_ID_VILLE = VIL_ID_VILLE;
        this.ID_USR = ID_USR;
        this.DATEPUB = DATEPUB;
        this.DESCRIPTION = DESCRIPTION;
        this.ETAT = ETAT;
        this.LIEUDEPART = LIEUDEPART;
        this.LIEUARRIVE = LIEUARRIVE;
        this.DATEDEPART = DATEDEPART;
        this.PRIX = PRIX;
        this.CHECKPOINTS = CHECKPOINTS;
        this.NBPLACE = NBPLACE;
    }

    public Covoiturage(Date DATEPUB, String LIEUDEPART, String LIEUARRIVE, float PRIX, int NBPLACE,String DESCRIPTION) {
        this.DATEPUB = DATEPUB;
        this.LIEUDEPART = LIEUDEPART;
        this.LIEUARRIVE = LIEUARRIVE;
        this.PRIX = PRIX;
        this.NBPLACE = NBPLACE;
        this.DESCRIPTION = DESCRIPTION;

    }

    public Covoiturage(int ID_PUB, String LIEUDEPART, String LIEUARRIVE, Date DATEDEPART, float PRIX, int NBPLACE,String DESCRIPTION) {
        this.ID_PUB = ID_PUB;
        this.LIEUDEPART = LIEUDEPART;
        this.LIEUARRIVE = LIEUARRIVE;
        this.DATEDEPART = DATEDEPART;
        this.PRIX = PRIX;
        this.NBPLACE = NBPLACE;
        this.DESCRIPTION = DESCRIPTION;
    }

    public Covoiturage(int ID_PUB, String LIEUDEPART, String LIEUARRIVE, Date DATEDEPART) {
        this.ID_PUB = ID_PUB;
        this.LIEUDEPART = LIEUDEPART;
        this.LIEUARRIVE = LIEUARRIVE;
        this.DATEDEPART = DATEDEPART;
    }
    

    public int getID_PUB() {
        return ID_PUB;
    }

    public int getID_VILLE() {
        return ID_VILLE;
    }

    public int getVIL_ID_VILLE() {
        return VIL_ID_VILLE;
    }

    public int getID_USR() {
        return ID_USR;
    }

    public Date getDATEPUB() {
        return DATEPUB;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public String getETAT() {
        return ETAT;
    }

    public String getLIEUDEPART() {
        return LIEUDEPART;
    }

    public String getLIEUARRIVE() {
        return LIEUARRIVE;
    }

    public Date getDATEDEPART() {
        return DATEDEPART;
    }

    public float getPRIX() {
        return PRIX;
    }

    public String getCHECKPOINTS() {
        return CHECKPOINTS;
    }

    public int getNBPLACE() {
        return NBPLACE;
    }

    public void setID_PUB(int ID_PUB) {
        this.ID_PUB = ID_PUB;
    }

    public void setID_VILLE(int ID_VILLE) {
        this.ID_VILLE = ID_VILLE;
    }

    public void setVIL_ID_VILLE(int VIL_ID_VILLE) {
        this.VIL_ID_VILLE = VIL_ID_VILLE;
    }

    public void setID_USR(int ID_USR) {
        this.ID_USR = ID_USR;
    }

    public void setDATEPUB(Date DATEPUB) {
        this.DATEPUB = DATEPUB;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public void setETAT(String ETAT) {
        this.ETAT = ETAT;
    }

    public void setLIEUDEPART(String LIEUDEPART) {
        this.LIEUDEPART = LIEUDEPART;
    }

    public void setLIEUARRIVE(String LIEUARRIVE) {
        this.LIEUARRIVE = LIEUARRIVE;
    }

    public void setDATEDEPART(Date DATEDEPART) {
        this.DATEDEPART = DATEDEPART;
    }

    public void setPRIX(float PRIX) {
        this.PRIX = PRIX;
    }

    public void setCHECKPOINTS(String CHECKPOINTS) {
        this.CHECKPOINTS = CHECKPOINTS;
    }

    public void setNBPLACE(int NBPLACE) {
        this.NBPLACE = NBPLACE;
    }

    @Override
    public String toString() {
        return "Covoiturage{" + "ID PUB=" + ID_PUB + ", ID VILLE=" + ID_VILLE + ", VIL_ID_VILLE=" + VIL_ID_VILLE + ", ID_USR=" + ID_USR + ", DATEPUB=" + DATEPUB + ", DESCRIPTION=" + DESCRIPTION + ", ETAT=" + ETAT + ", LIEUDEPART=" + LIEUDEPART + ", LIEUARRIVE=" + LIEUARRIVE + ", DATEDEPART=" + DATEDEPART + ", PRIX=" + PRIX + ", CHECKPOINTS=" + CHECKPOINTS + ", NBPLACE=" + NBPLACE + '}';
    }
    
    
    
    
}
