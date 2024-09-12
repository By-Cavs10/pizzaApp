package com.pizza.app.bo;

public class Client extends Utilisateur{
  private String rue;
  private String codePostal;
  private String ville;

    public Client(String rue, String codePostal, String ville) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }
    public Client(Long id, String nom, String prenom, String email, String password, String rue, String codePostal, String ville) {
        super(id, nom, prenom, email, password);
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }
    public Client() {
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
    @Override
    public String toString() {
        return "Client{" +
                "rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
