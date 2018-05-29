/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Skander
 */
public class MyDb {
    String URL="jdbc:mysql://localhost:3306/espentreaideintegre";
    String name="root";
    String pass="";
    public static MyDb instance;
    private Connection con ;
    private MyDb()
    {
        try {
            con=DriverManager.getConnection(URL,name,pass);
            System.out.println("connexion établie");
        } catch (SQLException ex) {
            Logger.getLogger(MyDb.class.getName()).log(Level.SEVERE, null, ex);
           System.out.println("erreur de connexion");        
        }
        
    }
    public static MyDb getinstance()
    {
        if (instance==null)
        { instance =new MyDb();}
        return  instance ;
    }
    public Connection getConnection(){
    return con ;}
    
}
