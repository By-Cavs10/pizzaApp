package com.pizza.app.bll;

import com.pizza.app.bo.*;

import java.util.List;

public interface BasketManagerImpl {


    AppManagerResponse<List<Commande>> getCommandes();

    AppManagerResponse<Commande> getById(Long id);

    EtatCommande getEtatCommande(Long id);

    List<EtatCommande> getEtatCommandes();

    DetailCommande getDetailCommande(Long id);

    List<DetailCommande> getDetailCommandes();

    void ajouterProduit(Utilisateur utilisateur, Produit produit, int quantite, Boolean livraison);

    void ajouterProduitACommande(Long commandeId, Produit produit, int quantite, Boolean livraison);

    Commande creerNouvelleCommande(Utilisateur utilisateur, Boolean livraison);

    void validerCommande(Long commandeId);


}
