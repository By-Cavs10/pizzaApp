package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;

import java.util.List;

public interface IdaoUtilisateur {
    List<Utilisateur> findAll();

    Utilisateur findById(Long id);
}
