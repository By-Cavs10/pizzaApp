package com.pizza.app.ihm;

import com.pizza.app.bll.OrderManagerImpl;
import com.pizza.app.bo.Commande;
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

    @GetMapping("/commandes")
    public String getAllCommandes(Model model) {
        List<Commande> commandes = orderManager.getAllCommandes();
        model.addAttribute("commandes", commandes);
        return "order";
    }

    @PostMapping("/updateEtatCommande")
    public String updateEtatCommande(@RequestParam Long commandeId, @RequestParam Long etatId) {

        if (etatId == 2) {
            orderManager.updateEtatCommande(commandeId, 3L);
        }
        return "redirect:/commandes";
    }

}

