package com.pizza.app.bo;

public class Utilisateur {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String rue;
    private String codePostal;
    private String ville;
    private boolean role;

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

    public Utilisateur(Long id, boolean role, String ville, String codePostal, String rue, String password, String email, String prenom, String nom) {
        this.id = id;
        this.role = role;
        this.ville = ville;
        this.codePostal = codePostal;
        this.rue = rue;
        this.password = password;
        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
    }

    public Utilisateur(Long id, String nom, String prenom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
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
                ", role=" + role +
                '}';
    }


}
