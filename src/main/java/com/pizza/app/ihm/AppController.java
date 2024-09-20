package com.pizza.app.ihm;

import com.pizza.app.bll.ProduitManager;
import com.pizza.app.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("loggedUser")
@Controller
public class AppController {
    @Autowired
    ProduitManager produitManager;


    @GetMapping("list2")
    public String showlist(Model model, HttpSession session, Utilisateur loggedUser) {

        model.addAttribute("produits", produitManager.getProduits());

        // Récupérer l'utilisateur connecté depuis la session
       loggedUser = (Utilisateur) session.getAttribute("loggedUser");
        model.addAttribute("loggedUser", loggedUser);

        return "list2";
    }


    @GetMapping("list")
    public String showlist(Model model) {

        model.addAttribute("produits", produitManager.getProduits());


        return "list";
    }
    @GetMapping("histoire")
    public String showhistoire(Model model) {
        return "histoire";
    }

}
