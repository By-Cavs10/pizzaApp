package com.pizza.app.ihm;

import com.pizza.app.bll.ITypeManager;
import com.pizza.app.bll.ProduitManager;
import com.pizza.app.bo.Produit;
import com.pizza.app.bo.TypeProduit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@SessionAttributes("loggedUser")
@Controller
public class AddProduitController {

    @Autowired
    ProduitManager produitManager;
    @Autowired
    ITypeManager typeManager;


    public AddProduitController(ProduitManager produitManager) {
        this.produitManager = produitManager;
    }

    @GetMapping({"create-produit/{id}", "create-produit"})
    public String showformulaire(@PathVariable(required = false) Long id, Model model) {

        Produit produit = new Produit();
//        afficher un produit existant dans le formulaire
        if (id != null) {
            produit = produitManager.getById(id);
        }
        model.addAttribute("produit", produit);

        List<TypeProduit> types = typeManager.getTypes();
        model.addAttribute("types", types);


        return "create-produit";
    }

    @PostMapping("create-produit")
    public String postFormulaire(@Valid @ModelAttribute("produit") Produit produit, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        System.out.println(produit);
        if (bindingResult.hasErrors()) {
            System.out.println("Erreur de controle");


            return "create-produit";
        }
        model.addAttribute("createProduit", produit);


        IHMHelpers.sendSuccessFlashMessage(redirectAttributes, "Le produit est enregistrer avec succés");
        produitManager.saveProduit(produit);
        return "redirect:/list";
    }

    @GetMapping("/supprimer-produits/{id}")

        public String deleteProduit(@PathVariable Long id) {

            produitManager.deleteProduit(id);

            return "redirect:/list";
        }

    }

