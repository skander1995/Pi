/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite;

import java.util.Date;

/**
 *
 * @author ASUS I7
 */
public class Colocation {
    private Integer ID_PUB;
    private Integer ID_USR;
    private Date DATEPUB;
    private String DESCRIPTION;
    private String ETAT;
    private String LIEU;
    private Double  LOYERMENSUEL;
    private String TYPELOGEMENT;
    private String TYPECHAMBRE;
    private String IMGCOUVERTURE;
    private Integer NBCHAMBRE;
    private String COMMODITE;
    private Date DATEDISPONIBILITE;
    private Integer ID_VILLE;

    public Colocation(Integer ID_PUB, Integer ID_USR, Date DATEPUB, String DESCRIPTION, String ETAT, String LIEU, Double LOYERMENSUEL, String TYPELOGEMENT, String TYPECHAMBRE, String IMGCOUVERTURE, Integer NBCHAMBRE, String COMMODITE, Date DATEDISPONIBILITE, Integer ID_VILLE) {
        this.ID_PUB = ID_PUB;
        this.ID_USR = ID_USR;
        this.DATEPUB = DATEPUB;
        this.DESCRIPTION = DESCRIPTION;
        this.ETAT = ETAT;
        this.LIEU = LIEU;
        this.LOYERMENSUEL = LOYERMENSUEL;
        this.TYPELOGEMENT = TYPELOGEMENT;
        this.TYPECHAMBRE = TYPECHAMBRE;
        this.IMGCOUVERTURE = IMGCOUVERTURE;
        this.NBCHAMBRE = NBCHAMBRE;
        this.COMMODITE = COMMODITE;
        this.DATEDISPONIBILITE = DATEDISPONIBILITE;
        this.ID_VILLE = ID_VILLE;
    }

    public Colocation() {
       
    }

    public Colocation(String text, String selectedItem, Date date, double parseFloat, Integer valueOf) {
        this.DESCRIPTION=text;
        this.LIEU=selectedItem;
        this.DATEDISPONIBILITE=date;
        this.LOYERMENSUEL=parseFloat;
        this.NBCHAMBRE=valueOf;
    }

    public Integer getID_PUB() {
        return ID_PUB;
    }

    public void setID_PUB(Integer ID_PUB) {
        this.ID_PUB = ID_PUB;
    }

    public Integer getID_USR() {
        return ID_USR;
    }

    public void setID_USR(Integer ID_USR) {
        this.ID_USR = ID_USR;
    }

    public Date getDATEPUB() {
        return DATEPUB;
    }

    public void setDATEPUB(Date DATEPUB) {
        this.DATEPUB = DATEPUB;
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

    public String getLIEU() {
        return LIEU;
    }

    public void setLIEU(String LIEU) {
        this.LIEU = LIEU;
    }

    public Double getLOYERMENSUEL() {
        return LOYERMENSUEL;
    }

    public void setLOYERMENSUEL(Double LOYERMENSUEL) {
        this.LOYERMENSUEL = LOYERMENSUEL;
    }

    public String getTYPELOGEMENT() {
        return TYPELOGEMENT;
    }

    public void setTYPELOGEMENT(String TYPELOGEMENT) {
        this.TYPELOGEMENT = TYPELOGEMENT;
    }

    public String getTYPECHAMBRE() {
        return TYPECHAMBRE;
    }

    public void setTYPECHAMBRE(String TYPECHAMBRE) {
        this.TYPECHAMBRE = TYPECHAMBRE;
    }

    public String getIMGCOUVERTURE() {
        return IMGCOUVERTURE;
    }

    public void setIMGCOUVERTURE(String IMGCOUVERTURE) {
        this.IMGCOUVERTURE = IMGCOUVERTURE;
    }

    public Integer getNBCHAMBRE() {
        return NBCHAMBRE;
    }

    public void setNBCHAMBRE(Integer NBCHAMBRE) {
        this.NBCHAMBRE = NBCHAMBRE;
    }

    public String getCOMMODITE() {
        return COMMODITE;
    }

    public void setCOMMODITE(String COMMODITE) {
        this.COMMODITE = COMMODITE;
    }

    public Date getDATEDISPONIBILITE() {
        return DATEDISPONIBILITE;
    }

    public void setDATEDISPONIBILITE(Date DATEDISPONIBILITE) {
        this.DATEDISPONIBILITE = DATEDISPONIBILITE;
    }

    public Integer getID_VILLE() {
        return ID_VILLE;
    }

    public void setID_VILLE(Integer ID_VILLE) {
        this.ID_VILLE = ID_VILLE;
    }

    @Override
    public String toString() {
        return "Colocation{" + "ID_PUB=" + ID_PUB + ", ID_USR=" + ID_USR + ", DATEPUB=" + DATEPUB + ", DESCRIPTION=" + DESCRIPTION + ", ETAT=" + ETAT + ", LIEU=" + LIEU + ", LOYERMENSUEL=" + LOYERMENSUEL + ", TYPELOGEMENT=" + TYPELOGEMENT + ", TYPECHAMBRE=" + TYPECHAMBRE + ", IMGCOUVERTURE=" + IMGCOUVERTURE + ", NBCHAMBRE=" + NBCHAMBRE + ", COMMODITE=" + COMMODITE + ", DATEDISPONIBILITE=" + DATEDISPONIBILITE + ", ID_VILLE=" + ID_VILLE + '}';
    }

    
    
    
    
    
    
    
}
