package com.pizza.app.ihm;

import com.pizza.app.bll.AppManagerResponse;
import com.pizza.app.bll.BasketManagerImpl;
import com.pizza.app.bo.*;
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
    public String showBasket(@Valid @ModelAttribute("etatCommande") EtatCommande etatCommande,
                             @Valid @ModelAttribute("detailCommande") DetailCommande detailCommande,
    Model model){

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
                                 @RequestParam Boolean livraison) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurId);

        Produit produit = new Produit();
        produit.setId(produitId);
        produit.setPrix(prix);

        basketManager.ajouterProduit(utilisateur, produit, quantite, livraison);

        return "Produit ajouté avec succès dans la commande.";
    }
}
