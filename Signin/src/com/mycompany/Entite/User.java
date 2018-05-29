/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author wildchild
 */
public class User {

    private int id;
    private String nom;
    private String prenom;
    private Date datenaissance;
    private String email;
    private String telephone;
    private String username;
    private String password;
    private String type;
    private String photo_profile;
    private int connect;
    private static int enabled;
    private static int validationCode;
    private static int IdOfConnectedUser;
    private static User actifUser;
    private String dateinString;
    private String socialid;

    public User(int id, String nom, String prenom, Date datenaissance, String email, String username, String password, String photo_profile, String dateinString, String socialid) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = datenaissance;
        this.email = email;
        this.username = username;
        this.password = password;
        this.photo_profile = photo_profile;
        this.dateinString = dateinString;
        this.socialid = socialid;
    }

    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateinString() {
        return dateinString;
    }

    public void setDateinString(String dateinString) {
        this.dateinString = dateinString;
    }

    public String getSocialid() {
        return socialid;
    }

    public void setSocialid(String socialid) {
        this.socialid = socialid;
    }

    // photo profil , type , .. should be predifined
    public User(int id, String nom, String prenom, Date datenaissance, String email, String phone, String username, String type, String photo_profile) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = datenaissance;
        this.email = email;
        this.username = username;
        this.telephone = phone;
        this.type = type;
        this.photo_profile = photo_profile;
    }

    public User(int id, String username, String nom, String prenom, String datenaissance, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.datenaissance = null;
        this.dateinString = datenaissance;
        this.email = email;
        this.password = password;
        //this.type = type;
    }

    public User(int id, String nom, String prenom, String mail, String phone, String photo_profile) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = mail;
        this.telephone = phone;
        this.photo_profile = photo_profile;
    }

    public User(int id, String nom, String prenom, Date datenaissance, String email, String username, String password, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = datenaissance;
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(int id, String nom, String prenom, Date datenaissance, String email, String telephone, String username, String password, String type, String photo_profile, int enabled) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = datenaissance;
        this.email = email;
        this.telephone = telephone;
        this.username = username;
        this.password = password;
        this.type = type;
        this.photo_profile = photo_profile;
        this.enabled = enabled;
    }

    public static User getActifUser() {
        return actifUser;
    }

    public static void setActifUser(User actifUser) {
        User.actifUser = actifUser;
    }

    public User(int id, String nom, String prenom, Date datenaissance, String phone, String photo_profile) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = datenaissance;
        this.telephone = phone;
        this.photo_profile = photo_profile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhotoProfil() {
        return photo_profile;
    }

    public void setPhotoProfil(String photo_profile) {
        this.photo_profile = photo_profile;
    }

    public HashMap<String, String> getPropMap() {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(id));
        userMap.put("nom", nom);
        userMap.put("prenom", prenom);
        userMap.put("datenaissance", datenaissance.toString());
        userMap.put("email", email);
        userMap.put("username", username);
        userMap.put("password", password);
        userMap.put("type", type);
        // need to add more
        return userMap;
    }

    public String getLogin() {
        return username;
    }

    public void setLogin(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateNaissance() {
        return datenaissance;
    }

    public void setDateNaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static int getIdOfConnectedUser() {
        return IdOfConnectedUser;
    }

    public static void setIdOfConnectedUser(int IdOfConnectedUser) {
        User.IdOfConnectedUser = IdOfConnectedUser;
    }

    public static int getEnabled() {
        return User.enabled;
    }

    public static void setEnabled(int enabled) {
        User.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", type= " + type + " photo = " + photo_profile + "social id : " + socialid + '}';
    }

    public User() {
    }

    public int getConnect() {
        return connect;
    }

    public void setConnect(int connect) {
        this.connect = connect;
    }

    public static void setValidationCode(int vDigit) {
        User.validationCode = vDigit;
    }

    public static int getValidationCode() {
        return validationCode;
    }

}
