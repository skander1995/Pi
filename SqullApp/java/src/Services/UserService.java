/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.User;
import IServices.IUserService;
import JDBC.JdbcInstance;
import Utilities.BCrypt;
import Utilities.ToolsUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author cobwi
 */
public class UserService implements IUserService {

    private Connection connection;

    public UserService() {
        this.connection = JdbcInstance.getInstance();
    }

    @Override
    public boolean Authentification(String username, String password) {
        boolean connected = false;
        try {
            String request = "select id,password from user where username LIKE ? or email like ? Limit 1"; // limit 1 (no redondance)
            PreparedStatement pstmnt;
            pstmnt = connection.prepareStatement(request);
            pstmnt.setString(1, username);
            pstmnt.setString(2, username);
            ResultSet rs = pstmnt.executeQuery();
            while (rs.next()) {
                if (BCrypt.checkpw(password, rs.getString(2)) == true) {
                    User.setIdOfConnectedUser(rs.getInt(1));
                    connected = true;
                } else {
                    connected = false;
                }
            }
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
        return connected;
    }

    @Override
    public User findById(Integer id) {
        User user = null;
        try {
            String request = "select id,nom,prenom,datenaissance,email,telephone,username,password,type_usr, photoprofile,enabled "
                    + "from user where id = ? ";
            PreparedStatement pstmnt = connection.prepareStatement(request);
            pstmnt.setInt(1, id);
            ResultSet rs = pstmnt.executeQuery();

            while (rs.next()) {

                user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        ToolsUtilities.formater.parse(rs.getString(4)),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getInt(11) // enabled ?
                );
            }
        } catch (SQLException | ParseException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched from findbyId user service= " + ex.getMessage());
            }
        }

        return user;
    }

    @Override
    public boolean update(User user, HashMap<String, String> args) {

        //{username : wildchild },{nom : elyes }
        // test if hash map contains 0 fields to update
        try {
            StringBuilder builder = new StringBuilder();

            /*for (int i = 0; i < args.size(); i++) {
                builder.append("?,");
            }
             */
            for (Map.Entry arg : args.entrySet()) {
                if (ToolsUtilities.DEBUG) {
                    System.out.println("print = " + arg.getKey() + " " + arg.getValue());
                }
                // + arg.getValue()
                builder.append(arg.getKey() + "=?,");
            }
            String request = "UPDATE user SET " + builder.deleteCharAt(builder.length() - 1).toString()
                    + " WHERE id = " + user.getId();

            PreparedStatement pstmnt = connection.prepareStatement(request);
            int index = 1;
            for (Map.Entry arg : args.entrySet()) {
                pstmnt.setString(index++, arg.getValue().toString());
            }
            System.out.println("request + " + request);
            pstmnt.executeUpdate();

        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
            return false;
        }
        return true;
    }

    public boolean update(User user) {
        String req = "update user set "
                + "`NAME_USR`=?,"
                + "`PRN_USR`=?,"
                + "`DATENAISSANCE`=?,"
                + "`EMAIL`=?,"
                + "`telephone`=?,"
                + "`LOGIN`=?,"
                + "`PWD`=?,"
                + "`photoprofile`=?,"
                + "`TYPE_USR`=? "
                + " WHERE id = " + user.getId();
        try {
            PreparedStatement state = connection.prepareStatement(req);
            state.setString(1, user.getNom());
            state.setString(2, user.getPrenom());
            state.setString(3, ToolsUtilities.formater.format(user.getDateNaissance()));
            state.setString(4, user.getEmail());
            state.setString(5, user.getTelephone());
            state.setString(6, user.getLogin());
            state.setString(7, user.getPassword());
            state.setString(8, user.getPhotoProfil());
            state.setString(9, user.getType());
            state.executeUpdate();
            return true;
        } catch (Exception ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched update user() userservice = " + ex.getMessage());
            }
            return false;
        }
    }

    public boolean testUserExistanceByEmail(String email) {
        try {
            String request = "select id from user where email like '" + email + "'";
            Statement state = connection.createStatement();
            ResultSet rst = state.executeQuery(request);
            if (rst.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
            return false;
        }
    }

    @Override
    public int add(User usr) {
        try {
            // test 
            // generate username
            String username = generateLogin(usr);
            //encrypted password : 
            String encryptedPass = BCrypt.hashpw(usr.getPassword(), BCrypt.gensalt(13));
            String request = "insert into user (id,nom,prenom,DATENAISSANCE,email,username,password,type_usr, profile_picture , telephone,roles,photoprofile,"
                    + "username_canonical,"
                    + "email_canonical"
                    + ")"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement state = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            state.setInt(1, usr.getId()); // null
            state.setString(2, usr.getNom());
            state.setString(3, usr.getPrenom());
            state.setString(4, ToolsUtilities.formater.format(usr.getDateNaissance()));
            state.setString(5, usr.getEmail());
            state.setString(6, username);
            //state.setString(6, usr.getLogin() == null ? usr.getNom() + usr.getPrenom() : usr.getLogin());
            state.setString(7, encryptedPass);
            state.setString(8, usr.getType());
            state.setString(9, usr.getPhotoProfil()); // une photo de profile générique serait attribué
            state.setString(10, usr.getTelephone());
            state.setString(11, "a:0:{}");
            state.setString(12, usr.getPhotoProfil());
            state.setString(13, usr.getNom()+usr.getPrenom());
            state.setString(14, usr.getEmail());

            int returnLastInsertId = state.executeUpdate();
            if (returnLastInsertId > 0) {
                ResultSet rs = state.getGeneratedKeys();
                rs.next();
                int insertedId = rs.getInt(1);
                usr.setId(insertedId);
                User.setIdOfConnectedUser(insertedId);
            }
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
            return -1;
        }
        return usr.getId();
    }

    @Override
    public boolean delete(Integer id) {
        String req = "delete from user where id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(req);
            state.setInt(1, id);
            state.executeUpdate();
            return true;
        } catch (Exception ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catcheddelete userservice = " + ex.getMessage());
            }
            return false;
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            String request = "select `id`,`NAME_USR`,`PRN_USR`,`DATENAISSANCE`,`EMAIL`,`telephone`,`LOGIN`,`TYPE_USR`,`photoprofile` "
                    + "from user ";
            ResultSet rst = connection.createStatement().executeQuery(request);
            while (rst.next()) {
                User user = new User(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getString(3),
                        ToolsUtilities.formater.parse(rst.getString(4)),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9)
                );
                users.add(user);
            }

        } catch (SQLException | ParseException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched from getAll.userService = " + ex.getMessage());
            }
        }
        return users;
    }

    public List<User> getAllTruncConnectedUser() {
        List<User> users = new ArrayList<>();
        try {
            String request = "select `id`,`nom`,`prenom`,`DATENAISSANCE`,`EMAIL`,`telephone`,`username`,`TYPE_USR`,`photoprofile` "
                    + "from user where id != " + User.getIdOfConnectedUser();
            ResultSet rst = connection.createStatement().executeQuery(request);
            while (rst.next()) {
                User user = new User(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getString(3),
                        ToolsUtilities.formater.parse(rst.getString(4)),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9)
                );
                users.add(user);
            }

        } catch (SQLException | ParseException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched from getAll.userService = " + ex.getMessage());
            }
        }
        return users;
    }

    public Map<List<TableColumn>, List<User>> getAllWithTableColumn() {
        Map<List<TableColumn>, List<User>> data = new HashMap();
        List<User> users = new ArrayList<>();
        List<TableColumn> tableColumns = new ArrayList<>();
        try {
            String request = "select * from user where 1 ";
            ResultSet rst = connection.createStatement().executeQuery(request);

            for (int i = 0; i < rst.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rst.getMetaData().getColumnName(i + 1));
                //this wors: ->
                col.setCellValueFactory(new PropertyValueFactory<>("Object"));
                /*
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().getValue().get(j).toString());
                    }
                });
                 */
                //col.setCellValueFactory(value);
                /*
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty((param.getValue()).getChildren().get(j).toString());
                    }
                });
                 */
                tableColumns.add(col);
            }

            while (rst.next()) {
                User user = new User(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getString(3),
                        ToolsUtilities.formater.parse(rst.getString(4)),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9)
                );
                users.add(user);
            }

            data.put(tableColumns, users);

        } catch (SQLException | ParseException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched from getAllWith all col.userService = " + ex.getMessage());
            }
        }
        return data;
    }

    @Override
    public List<User> findByAny(HashMap<String, String> args) {
        //{username : wildchild },{nom : elyes }
        // test if hash map contains 0 fields to update
        List<User> users = new ArrayList<>();
        try {
            StringBuilder builder = new StringBuilder();
            /*
            for (int i = 0; i < args.size(); i++) {
                builder.append("?,");
            }
             */
            for (Map.Entry arg : args.entrySet()) {
                if (ToolsUtilities.DEBUG) {
                    System.out.println("print = " + arg.getKey() + " " + arg.getValue());
                }
                // + arg.getValue()
                builder.append(arg.getKey() + "=?,");
            }
            String request = "select id, nom, prenom, DATENAISSANCE,  EMAIL,  telephone,  username,  type_usr,  photoprofile from user where " + builder.deleteCharAt(builder.length() - 1).toString();
            PreparedStatement pstmnt = connection.prepareStatement(request);
            int index = 1;
            for (Map.Entry arg : args.entrySet()) {
                pstmnt.setString(index++, arg.getValue().toString());
            }
            ResultSet rst = pstmnt.executeQuery();
            while (rst.next()) {
                User user = new User(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getString(3),
                        ToolsUtilities.formater.parse(rst.getString(4)),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9)
                );
                users.add(user);
            }
            return users;

        } catch (SQLException | ParseException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }

        }
        return users;
    }

    @Override
    public void setConnected(int idOfConnectedUser) {
        try {
            String request = "UPDATE user SET connected = 1 WHERE id = " + idOfConnectedUser;
            Statement statement = connection.createStatement();
            statement.executeUpdate(request);
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

    @Override
    public void setNotConnected(int idOfConnectedUser) {
        try {
            String request = "UPDATE user SET connected = 0 WHERE id = " + idOfConnectedUser;
            Statement statement = connection.createStatement();
            statement.executeUpdate(request);
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

    private String generateLogin(User usr) {
        try {
            String request = "select id from user where username like ?";
            PreparedStatement statement = connection.prepareStatement(request);
            String username = "";
            for (int i = 0; i < 10; i++) {
                username = usr.getNom() + usr.getPrenom();
                if (i == 0) {
                    statement.setString(1, username);
                    ResultSet rst = statement.executeQuery();
                    if (!rst.next()) {
                        System.out.println("rst . next one time ");
                        return username;
                    }
                } else {
                    System.out.println("rst . elseee");
                    statement.setString(1, username + i);
                    ResultSet rst = statement.executeQuery();
                    if (!rst.next()) {
                        return username + i;
                    }
                }
            }
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched from userservice generateLogin = " + ex.getMessage());
            }
        }
        return "";
    }

    @Override
    public void setEnabledToTrue(int idOfConnectedUser) {
        try {
            String request = "UPDATE user SET Enabled = 1 WHERE id = " + idOfConnectedUser;
            Statement statement = connection.createStatement();
            statement.executeUpdate(request);

        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
    }

    public XYChart.Series<String, Integer> generateInscriptionChart() {
        try {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            PreparedStatement st = connection.prepareStatement(""
                    + "select count(*),DATE_FORMAT(`welcomeDate`, '%M %d %Y') from user\n"
                    + " Where 1 "
                    + " GROUP BY DATE_FORMAT(`welcomeDate`, '%M %d %Y');"
                    + "");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(2), rs.getInt(1)));
            }
            return series;
        } catch (SQLException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = " + ex.getMessage());
            }
        }
        return null;
    }

    public HashMap<String, String> generateUserInfo(int id) {
        HashMap<String, String> mapData = new HashMap<>();
        String req = "SELECT Count( * ) AS count FROM covoiturage  WHERE id = " + String.valueOf(id)
                + "    UNION ALL\n"
                + "	SELECT Count( * ) AS count FROM evenement  WHERE id = " + String.valueOf(id)
                + "	UNION ALL \n"
                + "    SELECT Count( * ) AS count FROM colocation  WHERE id = " + String.valueOf(id)
                + "	UNION ALL \n"
                + "    SELECT Count( * ) AS count FROM aide  WHERE id = " + String.valueOf(id);
        try {
            Statement state = connection.createStatement();
            ResultSet rst = state.executeQuery(req);
            if (rst.next()) {
                // 4 ligne
                mapData.put("covoiturage", rst.getString(1));
                rst.next();
                mapData.put("evenement", rst.getString(1));
                rst.next();
                mapData.put("colocation", rst.getString(1));
                rst.next();
                mapData.put("aide", rst.getString(1));
                return mapData;
            }
        } catch (Exception ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catche userservice = " + ex.getMessage());
            }
        }
        return null;
    }

}
