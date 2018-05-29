/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.EspaceFAQ;
import java.util.List;

/**
 *
 * @author Skander
 */
public interface IEspaceFAQ {
    public void Createpub(EspaceFAQ o,int id_currentuser);
    public void deletepub(int i);
    public List<EspaceFAQ>consulterespace();
    public List<EspaceFAQ>consulterespace(int id);
    public void updatepub(EspaceFAQ o,int i);
    public String searchusername(int i);
    
}
