package com.pizza.app.bll;

import com.pizza.app.bo.*;
import com.pizza.app.dao.IDAOBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.List;


@Service
public class BasketManager implements BasketManagerImpl{

    @Autowired
    IDAOBasket daoBasket;

    @Override
    public AppManagerResponse<List<Commande>> getCommandes() {

        //Récupérer les commandes dans la DAO

        List<Commande> commandes = daoBasket.selectCommande();

        //Cas 1 Succès
        return AppManagerResponse.performResponse("200","Les Commandes ont été récupérés avec succès", commandes,true);


    }
@Override
public AppManagerResponse<Commande> getById(Long id) {

        //Récupérer un aliment via la DAO
        Commande commande = daoBasket.selectCommandeById(id);

        //Cas 1 : Erreur 701
        if (commande == null) {
            return AppManagerResponse.performResponse("701","Impossible de récupérer la commande inexistant",commande,false);
        }

        //Cas 2: Succès
        return AppManagerResponse.performResponse("200","Les commandes ont été récupérées avec succès",commande,true);
    }

    @Override
    public EtatCommande getEtatCommande(Long id) {
        return daoBasket.findById(id);
    }

    @Override
    public List<EtatCommande> getEtatCommandes() {
        return daoBasket.findAll();
    }

    @Override
    public DetailCommande getDetailCommande(Long id) {
        return daoBasket.findByIdDetailCommande(id);
    }

    @Override
    public List<DetailCommande> getDetailCommandes() {
        return daoBasket.findAllDetailCommande();
    }

    public void ajouterProduit(Utilisateur utilisateur, Produit produit, int quantite, Boolean livraison) {
        // Ajoute ici toute logique métier supplémentaire si nécessaire
        daoBasket.ajouterProduit(utilisateur, produit, quantite, livraison);
    }

    public Commande creerNouvelleCommande(Utilisateur utilisateur, Boolean livraison) {
        Commande commande = new Commande();
        commande.setUtilisateur(utilisateur);
        commande.setPrixTotal(0.0);
        commande.setMontantPaye(0.0);
//        commande.setLivraison(false);
        commande.setLivraison(livraison);
        commande.setEtatCommande(new EtatCommande(1L, "En cours"));
        return daoBasket.creerNouvelleCommande(commande);
    }

    public void ajouterProduitACommande(Long commandeId, Produit produit, int quantite, Boolean livraison) {
       daoBasket.ajouterProduitACommande(commandeId, produit, quantite, livraison);
    }

    // Méthode pour valider la commande et changer l'état
    public void validerCommande(Long commandeId) {
        daoBasket.changerEtatCommande(commandeId, 2L); // 2 = En préparation
    }

}
