package com.pizza.app.ihm;

import com.pizza.app.bll.ProduitManager;
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
    @GetMapping("list")
    public String showlist(Model model) {

        model.addAttribute("produits", produitManager.getProduits());
        return "list";
    }

}
