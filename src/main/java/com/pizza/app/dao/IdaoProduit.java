package com.pizza.app.dao;

import com.pizza.app.bo.Produit;

import java.util.List;

public interface IdaoProduit {
    List<Produit> selectProduit();

    Produit selectProduitById(long id);

    boolean deleteProduit(Long id);

    void saveProduit(Produit produit);
}
