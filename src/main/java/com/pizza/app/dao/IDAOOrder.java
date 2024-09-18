package com.pizza.app.dao;

import com.pizza.app.bo.Commande;

import java.util.List;

public interface IDAOOrder {
    List<Commande> getAllCommandes();
    void updateEtatCommande(Long commandeId, Long etatId);

}
