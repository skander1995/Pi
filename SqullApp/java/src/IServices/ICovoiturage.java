/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Covoiturage;
import java.util.ArrayList;

/**
 *
 * @author Moez
 */
public interface ICovoiturage {
    public void insertCovoiturage(Covoiturage c);
    public void editCovoiturage(Covoiturage c);
    public void deleteCovoiturage(Covoiturage c);
    
    public ArrayList<Covoiturage> afficherCovoiturage();
    
}
