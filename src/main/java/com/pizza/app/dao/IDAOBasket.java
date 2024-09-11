package com.pizza.app.dao;

import com.pizza.app.bo.Commande;

import java.util.List;

public interface IDAOBasket {


    List<Commande> selectCommande();

    Commande selectCommandeById(long id);
}
