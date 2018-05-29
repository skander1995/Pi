/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Omar Dagdoug
 */
    public class Commentaire_ques {
    
   
    private  String first_name;
    private  String last_name;
     private int id;
    private int id_user;
    private String contenu;
    private Date date_creation;
    private int id_plan;
    
   
      public  Commentaire_ques() {
        
    }
    public Commentaire_ques(String first_name,String last_name,int id,int id_user,String contenu
            ,Date date_creation,int id_plan){
        
        this.first_name = first_name;
        this.last_name = last_name;
        this.id=id;
        this.id_user=id_user;
        this.contenu=contenu;
        this.date_creation=date_creation;
        this.id_plan=id_plan;
        
    }

    
    public Commentaire_ques(String first_name,String last_name,String contenu){
         this.first_name = first_name;
        this.last_name = last_name;
        this.contenu=contenu;
    }

    
     //getters & setters & property value 
    
   
    
    
    /*
    public SimpleStringProperty firstNameProperty(){
        return first_name;
    }
    public SimpleStringProperty lastNameProperty(){
        return last_name;
    }
     */

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    
    
   
    
     public Commentaire_ques( int id_user,  String contenu, int id_plan) {
       // this.id = id;
        this.id_user = id_user;
        this.contenu = contenu;
        this.id_plan = id_plan;
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
    
    
     public int getId_user() {
        return id_user;
    }
     public void setId_user(int id_user) {
        this.id_user = id_user;
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
        return id_plan;
    }

    public void setId_Plan(int id_plan) {
        this.id_plan = id_plan;
    }

    @Override
    public String toString() {
        return "id_pub: "+id +" id user:  "+id_user+" contenu : " + contenu+" id_pub " +  id_plan+"  first_name: " + first_name+" last_name: "+ last_name+" ";
    }
    
}
