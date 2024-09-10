package com.pizza.app.bo;

public class EtatCommande {

    private Long id;
    private String libelle;

    public EtatCommande() {}

    public EtatCommande(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

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
        return "EtatCommande{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
