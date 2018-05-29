/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import java.util.List;

/**
 *
 * @author wildchild
 */
public interface IService<T, R> {

    int add(T t);

    boolean delete(R r);

    T findById(R r);

    List<T> getAll();

}
