/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Commentaire;
import java.util.List;

/**
 *
 * @author Omar Dagdoug
 */
public interface ICommentaire {
     public void AjouterCommentaire(Commentaire c);
      //public void ModifierCommentaire (Commentaire c);
      //public void SupprimerCommentaire(Commentaire c);
      //public void lister();
      //public List<Commentaire> listerCom() ;
     // public Commentaire getCommentaireById(int id);
     // public List<Plan> listerMesPlanCom() ;
    public List<Commentaire> listerComdePlan(int idplan);
    
}
