package com.pizza.app.bdd;

import com.pizza.app.bo.Commande;
import com.pizza.app.bo.EtatCommande;

import java.util.List;

public interface BasketManagerImpl {


    AppManagerResponse<List<Commande>> getCommandes();

    AppManagerResponse<Commande> getById(Long id);

    EtatCommande getEtatCommande(Long id);

    List<EtatCommande> getEtatCommandes();

}
