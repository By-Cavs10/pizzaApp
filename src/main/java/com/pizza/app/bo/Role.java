package com.pizza.app.bo;

public class Role {

    private Long id;
    private String libelle;

    public Role() {}

    public Role(Long id, String libelle) {
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
        return "TypeProduit{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
