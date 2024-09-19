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
import java.util.Map;
import java.util.stream.Collectors;

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
            AppManagerResponse<Commande> response = basketManager.getById(commandeId);
            if (response != null && response.getData() != null) {
                commande = response.getData();
            } else {
                // Gérer le cas où la commande est nulle
                model.addAttribute("error", "Commande non trouvée");
            }
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
//                                 @RequestParam Boolean livraison,
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

        // Récupérer le choix de livraison de la session
         Boolean livraison = (Boolean) session.getAttribute("livraison");
        if (livraison == null) {
            livraison = false; // Valeur par défaut si pas encore défini
        }

        // Si aucune commande en cours, créer une nouvelle commande
        if (commandeId == null) {
            Commande nouvelleCommande = basketManager.creerNouvelleCommande(utilisateur, livraison);
            commandeId = nouvelleCommande.getId();
            session.setAttribute("commandeId", commandeId);
        }

        model.addAttribute("commande", commandeId);
        // Ajouter le produit à la commande
        basketManager.ajouterProduitACommande(commandeId, produit, quantite, livraison);

        return "redirect:/panier";
    }
    @GetMapping("/choisirLivraison")
    public String afficherChoixLivraison() {
        // Affiche la page où l'utilisateur peut choisir livraison ou à emporter
        return "basket/choice-delivery";
    }
    @PostMapping("/choisirLivraison")
    public String choixLivraison(@RequestParam Boolean livraison, HttpSession session) {
        // Stocke le choix de livraison dans la session
        session.setAttribute("livraison", livraison);
        return "redirect:/list2"; // Redirige vers la page de la liste des pizzas
    }

    @PostMapping("/validerCommande")
    public String validerCommande(HttpSession session) {
        Long commandeId = (Long) session.getAttribute("commandeId");

        if (commandeId != null) {
            basketManager.validerCommande(commandeId); // Appelle la méthode pour valider la commande
            session.removeAttribute("commandeId"); // Optionnel : retirer la commande de la session après validation
        }

        return "redirect:/compte"; // Rediriger vers une page de confirmation, par exemple
    }
}

