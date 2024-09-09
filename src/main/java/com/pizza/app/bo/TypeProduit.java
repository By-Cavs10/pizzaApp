package com.pizza.app.bo;

public class TypeProduit extends Produit{

    private Long id;
    private String libelle;

    public TypeProduit() {}

    public TypeProduit(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public TypeProduit(Long id, String nom, String image, String description, Double prix, Long id1, String libelle) {
        super(id, nom, image, description, prix);
        this.id = id1;
        this.libelle = libelle;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "TypeProduit{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
