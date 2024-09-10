package com.pizza.app.bo;

public class DetailCommande {

    private Long quantite;

    private Produit produit;

    public DetailCommande() {}

    public DetailCommande(Long quantite, Produit produit) {
        this.quantite = quantite;
        this.produit = produit;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "DetailCommande{" +
                "quantite=" + quantite +
                ", produit=" + produit +
                '}';
    }
}
