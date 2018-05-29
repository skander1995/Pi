/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Colocation;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ASUS I7
 */
public interface IColocationService {

    public void ajouterColocation(Colocation c);

    public boolean supprimerColocation(Colocation c);

    public void updatecoloc(Colocation o,int in);

    public ArrayList<Colocation> getColocation(int ID_PUB);

    public ArrayList<Colocation> afficherColocation();
}
