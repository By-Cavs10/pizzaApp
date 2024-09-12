package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;

import java.util.List;

public interface IDAOAuth {

    /**
     * Permettera de récupérer un utilisateur dans les données
     * @param email
     * @param password
     * @return
     */
//    Utilisateur login(String email, String password);

    List<Utilisateur> selectUtilisateur();

    Utilisateur selectUtilisateurById(Long id);



    void saveUtilisateur(Utilisateur utilisateur);

    void deleteById(Utilisateur utilisateur);

    Utilisateur login(String email, String password);
}
