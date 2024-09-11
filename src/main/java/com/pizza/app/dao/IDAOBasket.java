package com.pizza.app.dao;

import com.pizza.app.bo.Commande;
import com.pizza.app.bo.EtatCommande;

import java.util.List;

public interface IDAOBasket {


    List<Commande> selectCommande();

    Commande selectCommandeById(Long id);

    List<EtatCommande> findAll();

    EtatCommande findById(Long id);
}
