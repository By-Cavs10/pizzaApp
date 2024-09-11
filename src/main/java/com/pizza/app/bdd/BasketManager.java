package com.pizza.app.bdd;

import com.pizza.app.bo.Commande;
import com.pizza.app.dao.IDAOBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BasketManager {

    @Autowired
    IDAOBasket daoBasket;

    public AppManagerResponse<List<Commande>> getCommandes() {

        //Récupérer les commandes dans la DAO

        List<Commande> commandes = daoBasket.selectCommande();

        //Cas 1 Succès
        return AppManagerResponse.performResponse("200","Les Commandes ont été récupérés avec succès", commandes);


    }

    public AppManagerResponse<Commande> getById(Long id) {

        //Récupérer un aliment via la DAO
        Commande commande = daoBasket.selectCommandeById(id);

        //Cas 1 : Erreur 701
        if (commande == null) {
            return AppManagerResponse.performResponse("701","Impossible de récupérer la commande inexistant",commande);
        }

        //Cas 2: Succès
        return AppManagerResponse.performResponse("200","Les commandes ont été récupérées avec succès",commande);
    }
}
