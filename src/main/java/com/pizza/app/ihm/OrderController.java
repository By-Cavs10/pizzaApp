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

    @GetMapping("/commandes")
    public String getAllCommandes(Model model) {
        List<Commande> commandes = orderManager.getAllCommandes();
        model.addAttribute("commandes", commandes);
        return "basket/order";
    }

    @PostMapping("/updateEtatCommande")
    public String updateEtatCommande(@RequestParam Long etat,Long id ) {

        return "redirect:/commandes";
    }

}

