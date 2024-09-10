package com.pizza.app.bo;

import java.util.List;

public class Commande {

    private Long id;
    private String date;
    private String heure;
    private String livraison;
    private Double prixTotal;
    private Double montantPaye;

    private List<DetailCommande> detailCommandes;
    private EtatCommande etatCommande;

    public Commande() {}

    public Commande(Long id, String date, String heure, String livraison, Double prixTotal, Double montantPaye) {
        this.id = id;
        this.date = date;
        this.heure = heure;
        this.livraison = livraison;
        this.prixTotal = prixTotal;
        this.montantPaye = montantPaye;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getLivraison() {
        return livraison;
    }

    public void setLivraison(String livraison) {
        this.livraison = livraison;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<DetailCommande> getDetailCommandes() {
        return detailCommandes;
    }

    public void setDetailCommandes(List<DetailCommande> detailCommandes) {
        this.detailCommandes = detailCommandes;
    }

    public Double getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(Double montantPaye) {
        this.montantPaye = montantPaye;
    }

    public EtatCommande getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", heure='" + heure + '\'' +
                ", livraison='" + livraison + '\'' +
                ", prixTotal=" + prixTotal +
                ", montantPaye=" + montantPaye +
                ", detailCommandes=" + detailCommandes +
                ", etatCommande=" + etatCommande +
                '}';
    }
}
