package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;

public interface IDAOAuth {

    /**
     * Permettera de récupérer un utilisateur dans les données
     * @param email
     * @param password
     * @return
     */
    Utilisateur login(String email, String password);
}
