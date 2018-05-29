/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author ASUS I7
 */
public class Colocation {

    protected int ID_PUB;
    protected int ID_VILLE;
    protected int ID_USR;
    protected Date DATEPUB; 
    protected String DESCRIPTION;
    protected String ETAT;
    protected String LIEU;
    protected float LOYERMENSUEL;
    protected String TYPELOGEMENT;
    protected String TYPECHAMBRE;
    protected String IMGCOUVERTURE;
    protected int NBCHAMBRE;
    protected String COMMODITE;
    protected Date DATEDISPONIBILITE;
    protected String TITRE;
    protected String PHOTOS;

    public String getPHOTOS() {
        return PHOTOS;
    }
    protected String lieustat;
    protected int nblieu;

    public void setLieustat(String lieustat) {
        this.lieustat = lieustat;
    }

    public void setNblieu(int nblieu) {
        this.nblieu = nblieu;
    }

    public String getLieustat() {
        return lieustat;
    }

    public int getNblieu() {
        return nblieu;
    }

    public Colocation() {
    }

    public Colocation(int ID_PUB, int ID_VILLE, int ID_USR, Date DATEPUB, String DESCRIPTION,
            String ETAT, String LIEU, float LOYERMENSUEL, String TYPELOGEMENT, String TYPECHAMBRE,
            String IMGCOUVERTURE, int NBCHAMBRE, String COMMODITE, Date DATEDISPONIBILITE,String TITRE) {
        this.ID_PUB = ID_PUB;
        this.ID_VILLE = ID_VILLE;
        this.ID_USR = ID_USR;
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
        this.TITRE = TITRE;
    }

    

    public Colocation(String DESCRIPTION, String LIEU, float LOYERMENSUEL, Date DATEDISPONIBILITE, String TITRE) {
        this.DESCRIPTION = DESCRIPTION;
        this.LIEU = LIEU;
        this.LOYERMENSUEL = LOYERMENSUEL;
        this.DATEDISPONIBILITE = DATEDISPONIBILITE;
        this.TITRE = TITRE;
    }
    public Colocation(String TITRE, String LIEU, Date DATEDISPONIBILITE,float LOYERMENSUEL, String DESCRIPTION ){
              this.TITRE = TITRE;
               this.LIEU = LIEU;
               this.DATEDISPONIBILITE = DATEDISPONIBILITE;
               this.LOYERMENSUEL = LOYERMENSUEL;
               this.DESCRIPTION = DESCRIPTION;
    }
    public Colocation(String text, String text0, Date datex, Float valueOf, String text1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Colocation(String text, Date datex, String value, Float valueOf, String text0) {
    this.TITRE=text;
    this.DATEDISPONIBILITE=datex;
    this.LIEU=value;
    this.LOYERMENSUEL=valueOf;
    this.DESCRIPTION=text0;
    }

  

   
    public int getID_PUB() {
        return ID_PUB;
    }

    public int getID_VILLE() {
        return ID_VILLE;
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

    public String getLIEU() {
        return LIEU;
    }

    public float getLOYERMENSUEL() {
        return LOYERMENSUEL;
    }

    public String getTYPELOGEMENT() {
        return TYPELOGEMENT;
    }

    public String getTYPECHAMBRE() {
        return TYPECHAMBRE;
    }

    public String getIMGCOUVERTURE() {
        return IMGCOUVERTURE;
    }

    public int getNBCHAMBRE() {
        return NBCHAMBRE;
    }

    public String getCOMMODITE() {
        return COMMODITE;
    }

    public Date getDATEDISPONIBILITE() {
        return DATEDISPONIBILITE;
    }

    public void setID_PUB(int ID_PUB) {
        this.ID_PUB = ID_PUB;
    }

    public void setID_VILLE(int ID_VILLE) {
        this.ID_VILLE = ID_VILLE;
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

    public void setLIEU(String LIEU) {
        this.LIEU = LIEU;
    }

    public void setLOYERMENSUEL(float LOYERMENSUEL) {
        this.LOYERMENSUEL = LOYERMENSUEL;
    }

    public void setTYPELOGEMENT(String TYPELOGEMENT) {
        this.TYPELOGEMENT = TYPELOGEMENT;
    }

    public void setTYPECHAMBRE(String TYPECHAMBRE) {
        this.TYPECHAMBRE = TYPECHAMBRE;
    }

    public void setIMGCOUVERTURE(String IMGCOUVERTURE) {
        this.IMGCOUVERTURE = IMGCOUVERTURE;
    }

    public void setNBCHAMBRE(int NBCHAMBRE) {
        this.NBCHAMBRE = NBCHAMBRE;
    }

    public void setCOMMODITE(String COMMODITE) {
        this.COMMODITE = COMMODITE;
    }

    public void setDATEDISPONIBILITE(Date DATEDISPONIBILITE) {
        this.DATEDISPONIBILITE = DATEDISPONIBILITE;
    }

    public String getTITRE() {
        return TITRE;
    }

    public void setTITRE(String TITRE) {
        this.TITRE = TITRE;
    }

    @Override
    public String toString() {
        return "Colocation{" + "ID_PUB=" + ID_PUB + ", ID_VILLE=" + ID_VILLE + ", ID_USR=" + ID_USR + ", DATEPUB=" + DATEPUB + ", DESCRIPTION=" + DESCRIPTION + ", ETAT=" + ETAT + ", LIEU=" + LIEU + ", LOYERMENSUEL=" + LOYERMENSUEL + ", TYPELOGEMENT=" + TYPELOGEMENT + ", TYPECHAMBRE=" + TYPECHAMBRE + ", IMGCOUVERTURE=" + IMGCOUVERTURE + ", NBCHAMBRE=" + NBCHAMBRE + ", COMMODITE=" + COMMODITE + ", DATEDISPONIBILITE=" + DATEDISPONIBILITE + ", TITRE=" + TITRE + ", PHOTOS=" + PHOTOS + ", lieustat=" + lieustat + ", nblieu=" + nblieu + '}';
    }

    

  
    
    
}
