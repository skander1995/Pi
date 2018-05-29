/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Evenement;
import java.util.ArrayList;

/**
 *
 * @author SELLAMI
 */
public interface IEvenement {
    //event
    public boolean ajouterEvenement(Evenement e);
    public boolean supprimerEvenementById(Evenement e);
    public boolean modifierEvenement(Evenement e) ;
    public Evenement afficherEvenementById(int id);
    public ArrayList<Evenement> afficherEvenements();
    public boolean reserverEvenement(int id,int id_usr);
    
}
