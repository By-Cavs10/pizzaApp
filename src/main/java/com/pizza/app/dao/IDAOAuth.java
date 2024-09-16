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

    boolean existsByEmail(String email);

    boolean save(Utilisateur user);

    Utilisateur findByEmail(String email);


    Utilisateur login(String email, String password);

    void deleteById(Long id);

    void deleteById(Utilisateur utilisateur);
}
