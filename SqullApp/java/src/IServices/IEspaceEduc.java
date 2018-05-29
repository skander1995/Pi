/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.EspaceEduc;
import java.util.List;

/**
 *
 * @author Skander
 */
public interface IEspaceEduc {
    public void Createpub(EspaceEduc o,int id_currentuser);
    public void deletepub(int i);
    public List<EspaceEduc>consulterespace();
    public List<EspaceEduc>consulterespace(int id);
    public void updatepub(EspaceEduc o,int i);
    public String searchusername(int i);
    
}
