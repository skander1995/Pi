/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import Utilities.ToolsUtilities;
import View.ConfigForm;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wildchild
 */
public class JdbcInstance {

    private static String url = "";
    private static String user = "";
    private static String passwd = "";
    private static Connection connect = null;

    public JdbcInstance() {

    }

    public JdbcInstance(String url, String user, String passwd) {
        this.url = url;
        this.user = user;
        this.passwd = passwd;
    }

    public static Connection getInstance() {
        if (connect == null) {
            //line were missing
            initFromPrefs();
            try {
                DriverManager.setLoginTimeout(10);
                connect = DriverManager.getConnection(url, user, passwd);
                System.out.println("connexion DB principale retournée");
            } catch (Exception exc) {
                System.out.println("invoking config");
                System.out.println(exc);
                int yes = JOptionPane.showConfirmDialog(null, "Il semblerait que la liaison avec le serveur est impossible\nVoullez vous vérifier vos données de connexion ?", "Voullez vous continuer ?", JOptionPane.YES_NO_OPTION);
                if (yes == JOptionPane.YES_OPTION) {
                    ConfigForm fp = new ConfigForm();
                    fp.setLocationRelativeTo(null);
                    fp.setVisible(true);
                }
            }
        }

        Statement state;
        try {
            if (connect == null) {
                connect = DriverManager.getConnection(url, user, passwd);
                System.out.println("connexion DB principale retournée");
            }
            state = connect.createStatement();
            ResultSet res = state.executeQuery("show open tables WHERE In_use > 0");
            if (res.next()) {
                JOptionPane.showConfirmDialog(null, "ERREUR !", "Veuillez redemarrer le logciel tout de suite et refaire votre dernière action\n"
                        + "Se problème sera corrigé prochainement.", JOptionPane.WARNING_MESSAGE);
            } else {
                //System.out.println("okkkkkk");
            }
        } catch (SQLException ex) {
            //  Logger.getLogger(JdbcInstance.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connect;
    }

    public static Connection getInstance(String url, String user, String passwd) {
        Connection connecti = null;
        try {
            connecti = DriverManager.getConnection(url, user, passwd);
        } catch (Exception exc) {
            System.out.println(exc);
            Logger.getLogger(JdbcInstance.class.getName()).log(Level.SEVERE, null, exc);
            return null;
        }

        return connecti;
    }

    public static void initFromPrefs() {
        FileInputStream input = null;
        try {
            System.out.println("initing from prefs");
            input = new FileInputStream("config.properties");
            // load a properties file
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            passwd = prop.getProperty("passwd");
        } catch (Exception ex) {
            url = "jdbc:MySQL://localhost:3306/hasbd?useOldAliasMetadataBehavior=true&socketTimeout=13000";
            user = "root";
            passwd = "";
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(JdbcInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void setUrl(String url) {
        JdbcInstance.url = url;
    }

    public static void setUser(String user) {
        JdbcInstance.user = user;
    }

    public static void setPasswd(String passwd) {
        JdbcInstance.passwd = passwd;
    }

    public static String getUrl() {
        if (url.isEmpty()) {
            FileInputStream input = null;
            try {
                input = new FileInputStream("config.properties");
                // load a properties file
                Properties prop = new Properties();
                prop.load(input);
                return prop.getProperty("url").substring(13, prop.getProperty("url").indexOf(":", 13));
            } catch (Exception ex) {
                if (ToolsUtilities.DEBUG) {
                    System.out.println("Catched fom getUrl= " + ex.getMessage());
                }
                return "127.0.0.1";
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(JdbcInstance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            return url;
        }
    }

    public static String getUser() {
        return user;
    }

    public static String getPasswd() {
        return passwd;
    }

    public static Connection getConnect() {
        return connect;
    }

}
