package com.pizza.app.bo;

public class Produit {

        private Long id;
        private String nom;
        private String description;
        private Double prix;
        private String image;
    private TypeProduit typeProduit;



    public Produit(Long id, String nom, String description, Double prix, String image, TypeProduit typeProduit) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.typeProduit = typeProduit;
    }



    public Produit(Long id, String nom, String description, Double prix, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }

    public Produit() {}

    public Produit(Long id, String nom, String image, String description, Double prix) {
        this.id = id;
        this.nom = nom;
        this.image = image;
        this.description = description;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public TypeProduit getTypeProduit() {return typeProduit;}

    public void setTypeProduit(TypeProduit typeProduit) {this.typeProduit = typeProduit;}

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", typeProduit=" + typeProduit +
                '}';
    }
}
