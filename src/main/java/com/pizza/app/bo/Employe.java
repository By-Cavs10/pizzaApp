package com.pizza.app.bo;

public class Employe extends Utilisateur{
    public Employe(Long id, String nom, String prenom, String email, String password) {
        super(id, nom, prenom, email, password);
    }

    public Employe() {
    }
}
