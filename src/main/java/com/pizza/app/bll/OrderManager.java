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

//    public void updateEtatCommande(Long commandeId, Boolean livraison) {
//        Long nouvelEtat = livraison ? 3L : 4L;
//
//        daoOrder.updateEtatCommande(commandeId, nouvelEtat);
//
//    }

    public void updateEtatCommande(Long commandeId, Boolean livraison) {
        // Vérification de l'état actuel de la commande pour ne pas passer d'un mauvais état
        Long etatActuel = daoOrder.getEtatCommande(commandeId);

        // Si l'état actuel est 2, on procède au changement
        if (etatActuel == 2L) {
            Long nouvelEtat = livraison ? 3L : 4L;
            daoOrder.updateEtatCommande(commandeId, nouvelEtat);
        } else if (etatActuel == 3L && livraison) {
            // Si déjà à l'état 3 et livraison est true, passer à 4
            daoOrder.updateEtatCommande(commandeId, 4L);
        }
    }

    // Service
    public List<Commande> getCommandesByEtatIds(List<Long> etatIds) {
        return daoOrder.getCommandesByEtatIds(etatIds);
    }
}
