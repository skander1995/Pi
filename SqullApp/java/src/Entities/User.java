/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import EnumPack.TypeUser;
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
    private Date dateNaissance;
    private String email;
    private String telephone;
    private String login;
    private String password;
    private String type;
    private String photoProfil;
    private int connect;
    private static int enabled;
    private static int validationCode;
    private static int IdOfConnectedUser;
    private static User actifUser;

    // photo profil , type , .. should be predifined
    public User(int id, String nom, String prenom, Date dateNaissance, String email, String phone, String login, String type, String photoProfil) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.login = login;
        this.telephone = phone;
        this.type = type;
        this.photoProfil = photoProfil;
    }

    public User(int id, String nom, String prenom, Date dateNaissance, String email, String password, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public User(int id, String nom, String prenom, String mail, String phone, String photoProfil) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = mail;
        this.telephone = phone;
        this.photoProfil = photoProfil;
    }

    public User(int id, String nom, String prenom, Date dateNaissance, String email, String login, String password, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public User(int id, String nom, String prenom, Date dateNaissance, String email, String telephone, String login, String password, String type, String photoProfil, int enabled) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.login = login;
        this.password = password;
        this.type = type;
        this.photoProfil = photoProfil;
        this.enabled = enabled;
    }

    public User(String nom, String prenom, Date dateNaissance, String email, String telephone, String password, TypeUser type) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.type = type.name();
    }

    public static User getActifUser() {
        return actifUser;
    }

    public static void setActifUser(User actifUser) {
        User.actifUser = actifUser;
    }

    public User(int id, String nom, String prenom, Date dateNaissance, String phone, String photoProfil) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = phone;
        this.photoProfil = photoProfil;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public HashMap<String, String> getPropMap() {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(id));
        userMap.put("nom", nom);
        userMap.put("prenom", prenom);
        userMap.put("dateNaissance", dateNaissance.toString());
        userMap.put("email", email);
        userMap.put("login", login);
        userMap.put("password", password);
        userMap.put("type", type);
        // need to add more
        return userMap;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
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
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", type=" + type + '}';
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
