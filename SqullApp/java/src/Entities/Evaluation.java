/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

/**
 *
 * @author Skander
 */
public class Evaluation {
    private int id_usr;
    private int id_ev;
    private int id_pub;
    private int note;

    public Evaluation(int id_usr, int id_pub, int note) {
        this.id_usr = id_usr;
        this.id_pub = id_pub;
        this.note = note;
    }

    public Evaluation() {
    }
    

    public int getId_usr() {
        return id_usr;
    }

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }

    public int getId_ev() {
        return id_ev;
    }

    public void setId_ev(int id_ev) {
        this.id_ev = id_ev;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
    
    
}
