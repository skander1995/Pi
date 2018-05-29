/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import javafx.scene.control.TextField;

/**
 *
 * @author SELLAMI
 */
public class NumberTextField extends TextField{
    
   public NumberTextField(){
       
   }
   
   @Override
   public void replaceText  (int i , int il,String s){
       if(s.matches("[0-9]")||s.isEmpty()){
           super.replaceText(i,il, s);
       }
       
       
   }
    
   @Override
   public void replaceSelection(String s){
       super.replaceSelection(s);
       
   }
}
