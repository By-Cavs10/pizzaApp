package com.pizza.app.ihm;

import com.pizza.app.bll.OrderManagerImpl;
import com.pizza.app.bo.Commande;
import com.pizza.app.bo.DetailCommande;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@SessionAttributes("loggedUser")
@Controller
public class OrderController {
    @Autowired
    OrderManagerImpl orderManager;

//    @GetMapping("/commandes")
//    public String getAllCommandes(Model model) {
//        List<Commande> commandes = orderManager.getAllCommandes();
//        model.addAttribute("commandes", commandes);
//        return "basket/order";
//    }

//    @PostMapping("/updateEtatCommande")
//    public String updateEtatCommande(@RequestParam Long etat,Long id ) {
//
//        return "redirect:/commandes";
//    }

    @PostMapping("/updateEtatCommande")
    public String updateEtatCommande(@RequestParam Long commandeId, @RequestParam Boolean livraison) {
        // Appel du Manager pour mettre à jour l'état de la commande
        orderManager.updateEtatCommande(commandeId, livraison);
        return "redirect:/commandes";
    }

    @GetMapping("/commandes")
    public String afficherCommandes(
            @RequestParam(required = false) List<Long> etatIds, // Les états sélectionnés pour filtrer
            Model model) {

        // Débogage : afficher la liste des états reçus
        System.out.println("Etat IDs reçus : " + etatIds);

        List<Commande> commandes;
        if (etatIds == null || etatIds.isEmpty()) {
            commandes = orderManager.getAllCommandes();
        } else {
            commandes = orderManager.getCommandesByEtatIds(etatIds);
        }

        // Débogage : afficher le nombre de commandes récupérées
        System.out.println("Nombre de commandes : " + commandes.size());

        // Ajouter les commandes et les états sélectionnés au modèle
        model.addAttribute("commandes", commandes);
        model.addAttribute("etatIds", etatIds);

        return "basket/order"; // Nom de la vue pour afficher les commandes
    }

}

