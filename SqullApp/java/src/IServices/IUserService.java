/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.User;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author wildchild
 */
public interface IUserService extends IService<User, Integer> {

    public boolean Authentification(String login, String password);

    @Override
    public User findById(Integer id);

    public boolean update(User r, HashMap<String, String> map);

    public List<User> findByAny( HashMap<String, String> args);

    public void setConnected(int r);

    public void setNotConnected(int r);

    public void setEnabledToTrue(int idOfConnectedUser);
}
