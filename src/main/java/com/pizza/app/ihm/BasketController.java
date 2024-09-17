package com.pizza.app.ihm;

import com.pizza.app.bll.AppManagerResponse;
import com.pizza.app.bll.BasketManagerImpl;
import com.pizza.app.bo.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes("loggedUser")
@Controller
public class BasketController {

    @Autowired
    BasketManagerImpl basketManager;


    @GetMapping("panier")
    public String showBasket(
    Model model, HttpSession session) {
        Long commandeId = (Long) session.getAttribute("commandeId");
        Commande commande = null;

        if (commandeId != null) {
            commande = basketManager.getById(commandeId).getData();
        }

        model.addAttribute("commande", commande);



        //V1 Envoyer la liste d'aliments dans le Modèle
//        model.addAttribute("aliments", alimentManager.getAliments());

        // On récupère les data commandes
        AppManagerResponse<List<Commande>> response = basketManager.getCommandes();
        model.addAttribute("commandes", response.getData());

        //récupère data Etat commande

        List<EtatCommande> etatCommandes = basketManager.getEtatCommandes();
        model.addAttribute("etatCommandes",etatCommandes);


        //récupère data détail commande

        List<DetailCommande> detailCommandes = basketManager.getDetailCommandes();
        model.addAttribute("detailsCommandes",detailCommandes);

        //Afficher la page
        // return "v2/aliment-page-v2" ;
        return "basket/show-basket" ;

    }

    @PostMapping("/ajouterProduit")
    public String ajouterProduit(@RequestParam Long utilisateurId,
                                 @RequestParam Long produitId,
                                 @RequestParam Double prix,
                                 @RequestParam int quantite,
                                 @RequestParam Boolean livraison,
                                 HttpSession session,
                                 Model model) {
        // Récupérer l'utilisateur connecté depuis la session
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurId);

        Produit produit = new Produit();
        produit.setId(produitId);
        produit.setPrix(prix);

        // Récupérer l'ID de la commande en cours depuis la session
        Long commandeId = (Long) session.getAttribute("commandeId");

        // Si aucune commande en cours, créer une nouvelle commande
        if (commandeId == null) {
            Commande nouvelleCommande = basketManager.creerNouvelleCommande(utilisateur);
            commandeId = nouvelleCommande.getId();
            session.setAttribute("commandeId", commandeId);
        }

        model.addAttribute("commande", commandeId);
        // Ajouter le produit à la commande
        basketManager.ajouterProduitACommande(commandeId, produit, quantite, livraison);

        return "redirect:/panier";
    }
}

