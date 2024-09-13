package com.pizza.app.bo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class Utilisateur {

    private Long id;

    @NotEmpty(message = "Le nom est obligatoire")
    private String nom;

    @NotEmpty(message = "Le prénom est obligatoire")
    private String prenom;

    @Email(message = "L'email doit être valide")
    @NotEmpty(message = "L'email est obligatoire")
    private String email;

    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    @NotEmpty(message = "La rue est obligatoire")
    private String rue;

    @NotEmpty(message = "Le code postal est obligatoire")
    private String codePostal;

    @NotEmpty(message = "La ville est obligatoire")
    private String ville;
    private boolean employe;

    private List<Role> roles;

    public Utilisateur(Long id, boolean employe, String codePostal, String ville, String rue, String password, String email, String prenom, String nom) {
        this.id = id;
        this.employe = employe;
        this.codePostal = codePostal;
        this.ville = ville;
        this.rue = rue;
        this.password = password;
        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
    }

    public Utilisateur(Long id, String nom, String prenom, String email, String password, String rue, String codePostal, String ville, boolean employe, List<Role> roles) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.employe = employe;
        this.roles = roles;
    }



    public Utilisateur(Long id, String nom, String prenom, String email, String password, String rue, String codePostal, String ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }



    public Utilisateur(Long id, String nom, String prenom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public boolean isEmploye() {
        return employe;
    }

    public void setEmploye(boolean employe) {
        this.employe = employe;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Utilisateur() {
    }


    public Utilisateur(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", employe=" + employe +
                ", roles=" + roles +
                '}';
    }
}
