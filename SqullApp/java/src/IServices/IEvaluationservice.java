/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import java.util.ArrayList;

/**
 *
 * @author Skander
 */
public interface IEvaluationservice {
    public ArrayList<Evaluation> getevaluations(int id);
    public void ajouterev(Evaluation e,int id_currentuser);
    public void updateev(Evaluation e,int id);
    
}
