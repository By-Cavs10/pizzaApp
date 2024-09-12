package com.pizza.app.ihm;

import com.pizza.app.bdd.AppManagerResponse;
import com.pizza.app.bdd.BasketManager;
import com.pizza.app.bdd.BasketManagerImpl;
import com.pizza.app.bo.Commande;
import com.pizza.app.bo.DetailCommande;
import com.pizza.app.bo.EtatCommande;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

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
        model.addAttribute("commandes", response.data);

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

//    @GetMapping("show-basket-2")
//    public String showBasket() {
//        // affiche la page (qui affiche le panier)
//        return "basket/show-basket";
//    }

}
