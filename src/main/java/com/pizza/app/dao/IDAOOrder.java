package com.pizza.app.dao;

import com.pizza.app.bo.Commande;
import com.pizza.app.bo.DetailCommande;

import java.util.List;

public interface IDAOOrder {
    List<Commande> getAllCommandes();
    void updateEtatCommande(Long commandeId, Long etatId);

    List<DetailCommande> getAllDetailByIdCommandes(Long  idCommande);


    Long getEtatCommande(Long commandeId);

    List<Commande> getCommandesByEtatIds(List<Long> etatIds);
}
