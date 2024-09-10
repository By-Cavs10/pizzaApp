package com.pizza.app.bdd;

import com.pizza.app.bo.Produit;
import com.pizza.app.dao.IdaoProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProduitManager {
    @Autowired

    IdaoProduit daoProduit;

    public  List<Produit> getProduits() {


        List<Produit> produits = daoProduit.selectProduit();

        return produits;
    }
}
