package com.pizza.app.bll;

import com.pizza.app.bo.Produit;
import com.pizza.app.dao.IdaoProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProduitManager {
    @Autowired

    IdaoProduit daoProduit;

    public List<Produit> getProduits() {


        List<Produit> produits = daoProduit.selectProduit();

        return produits;
    }

    public Produit getById(long id) {

        Produit produit = daoProduit.selectProduitById(id);

        return produit;

    }

    public void saveProduit(Produit produit) {
        daoProduit.saveProduit(produit);
    }

    public boolean deleteProduit(Long id) {
       return daoProduit.deleteProduit(id);
    }
}
