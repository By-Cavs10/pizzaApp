package com.pizza.app.dto;

public class AjouterProduitRequest {

        private Long utilisateurId;
        private Long produitId;
        private Double prix;
        private int quantite;
        private Boolean livraison; // true pour livraison, false pour récupération sur place

        // Getters et setters
        public Long getUtilisateurId() {
            return utilisateurId;
        }

        public void setUtilisateurId(Long utilisateurId) {
            this.utilisateurId = utilisateurId;
        }

        public Long getProduitId() {
            return produitId;
        }

        public void setProduitId(Long produitId) {
            this.produitId = produitId;
        }

        public Double getPrix() {
            return prix;
        }

        public void setPrix(Double prix) {
            this.prix = prix;
        }

        public int getQuantite() {
            return quantite;
        }

        public void setQuantite(int quantite) {
            this.quantite = quantite;
        }

        public Boolean getLivraison() {
            return livraison;
        }

        public void setLivraison(Boolean livraison) {
            this.livraison = livraison;
        }
    }

