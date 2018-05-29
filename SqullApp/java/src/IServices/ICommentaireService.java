/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Commentaire;
import java.util.ArrayList;

/**
 *
 * @author cobwi
 */
public interface ICommentaireService {

    public Commentaire selectOne(int id);

    public void insertComment(Commentaire e);

    public void deleteComment(Commentaire e);

    public ArrayList<Commentaire> selectAll();
}
