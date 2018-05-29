/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;
import javafx.scene.image.Image;

/**
 *
 * @author SELLAMI
 */
public class Publication {

    protected int id;
    protected int UserId;
    protected Date date_creation;
    protected String title;
    protected String description;
    protected String etat;
    protected Image image;

    public Publication(int id, int UserId, Date date_creation, String description, String etat) {
        this.id = id;
        this.UserId = UserId;
        this.date_creation = date_creation;
        this.description = description;
        this.etat = etat;
    }

    public Publication(int id, int UserId, String description, String etat) {
        this.id = id;
        this.UserId = UserId;

        this.description = description;
        this.etat = etat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Publication{" + "id=" + id + ", UserId=" + UserId + ", description=" + description + ", etat=" + etat + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
