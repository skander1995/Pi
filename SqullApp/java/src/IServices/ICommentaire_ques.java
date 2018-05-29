/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Com_quest;

import java.util.List;

/**
 *
 * @author Omar Dagdoug
 */
public interface ICommentaire_ques {
     public void AjouterCommentaire(Commentaire_ques c);
      //public void ModifierCommentaire (Commentaire_ques c);
      //public void SupprimerCommentaire(Commentaire_ques c);
      //public void lister();
      //public List<Commentaire_ques> listerCom() ;
     // public Commentaire_ques getCommentaireById(int id);
     // public List<Plan> listerMesPlanCom() ;
    public List<Commentaire_ques> listerComdePlan(int idplan);
    
}
