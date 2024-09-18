package com.pizza.app.bll;

import com.pizza.app.bo.Commande;
import com.pizza.app.bo.DetailCommande;
import com.pizza.app.dao.IDAOOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManager implements OrderManagerImpl {
    @Autowired
    IDAOOrder daoOrder;

    public List<Commande> getAllCommandes() {
        List<Commande> commandes = daoOrder.getAllCommandes();
        for (Commande commande : commandes) {
            List<DetailCommande> details = daoOrder.getAllDetailByIdCommandes(commande.getId());
            commande.setDetailCommandes(details);
        }
        return commandes;

    }

    public void updateEtatCommande(Long commandeId, Boolean livraison) {
        Long nouvelEtat = livraison ? 3L : 4L;

        daoOrder.updateEtatCommande(commandeId, nouvelEtat);

    }
}
