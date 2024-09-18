package com.pizza.app.bll;

import com.pizza.app.bo.Commande;
import com.pizza.app.dao.IDAOOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManager implements OrderManagerImpl {
    @Autowired
    IDAOOrder daoOrder;

    public List<Commande> getAllCommandes() {
        return daoOrder.getAllCommandes();
    }

    public void updateEtatCommande(Long commandeId, Long etatId) {
        daoOrder.updateEtatCommande(commandeId, etatId);
    }
}
