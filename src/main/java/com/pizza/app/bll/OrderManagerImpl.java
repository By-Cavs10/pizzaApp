package com.pizza.app.bll;


import com.pizza.app.bo.Commande;

import java.util.List;

public interface OrderManagerImpl {
    List<Commande> getAllCommandes();
    void updateEtatCommande(Long commandeId, Long etatId);
}

