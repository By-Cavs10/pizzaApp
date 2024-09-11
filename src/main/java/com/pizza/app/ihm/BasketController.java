package com.pizza.app.ihm;

import com.pizza.app.bdd.AppManagerResponse;
import com.pizza.app.bdd.BasketManager;
import com.pizza.app.bo.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BasketController {

    @Autowired
    BasketManager basketManager;


    @GetMapping("panier")
    public String showBasket(Model model){

        //V1 Envoyer la liste d'aliments dans le Modèle
//        model.addAttribute("aliments", alimentManager.getAliments());

        // V2 On récupère la réponse métier (contrôle métier)
        AppManagerResponse<List<Commande>> response = basketManager.getCommandes();
        model.addAttribute("commandes", response.data);

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
