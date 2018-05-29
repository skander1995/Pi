/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commentaire.question;

import EspaceEducatif.User;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Omar Dagdoug
 */
    public class Commentaire_ques {
    
   
    
     private int id;
    private User id_user;
    private String contenu;
    private Date date_creation;
    private int id_pub;
    
   
      public  Commentaire_ques() {
        
    }
  
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.getId();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Commentaire_ques other = (Commentaire_ques) obj;
        return this.getId() == other.getId();
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

   
    
    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

   
    
    
    public int getId_Plan() {
        return id_pub;
    }

    public void setId_Plan(int id_pub) {
        this.id_pub = id_pub;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

   

    
    
}
