package com.pizza.app.dao;

import com.pizza.app.bo.*;

import java.util.List;

public interface IDAOBasket {


    List<Commande> selectCommande();

    Commande selectCommandeById(Long id);

    List<EtatCommande> findAll();

    EtatCommande findById(Long id);

    List<DetailCommande> findAllDetailCommande();

    DetailCommande findByIdDetailCommande(Long id);


    void ajouterProduit (Utilisateur utilisateur, Produit produit, int quantite, Boolean livraison);

    Commande getCommandeById(Long commandeId);

    void ajouterProduitACommande(Long commandeId, Produit produit, int quantite, Boolean livraison);

    Commande creerNouvelleCommande(Commande commande);

    void changerEtatCommande(Long commandeId, Long nouvelEtat);
}
