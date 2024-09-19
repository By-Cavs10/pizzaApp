package com.pizza.app.bll;


import com.pizza.app.bo.Commande;
import com.pizza.app.bo.DetailCommande;

import java.util.List;

public interface OrderManagerImpl {
    List<Commande> getAllCommandes();
    void updateEtatCommande(Long commandeId,Boolean livraison);

    List<Commande> getCommandesByEtatIds(List<Long> etatIds);
}

