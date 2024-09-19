package com.pizza.app.ihm;


import com.pizza.app.bll.AuthManager;
import com.pizza.app.bll.AppManagerResponse;
import com.pizza.app.bo.Utilisateur;
import com.pizza.app.dao.DAOAuthMySQL;
import com.pizza.app.dao.IDAOAuth;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


@SessionAttributes("loggedUser")
@Controller
public class AuthController {
    @Autowired
    IDAOAuth daoAuth;
    @Autowired
    private AuthManager authManager;

    @GetMapping("login")
    public String login(Model model, RedirectAttributes redirectAttributes) {


        // Instancier un User vide (email et password vide)
       // Utilisateur user = new Utilisateur();

        // Envoyer le user dans le Model
       // model.addAttribute("user", user);

        return "auth/login";
    }

    @GetMapping("/proccesLogin")
    public String processLogin( Model model,Principal principal, RedirectAttributes redirectAttributes, HttpSession session) {
//        // Authentification (contrôle métier)
//        AppManagerResponse<Utilisateur> response = authManager.authenticate(user.getEmail(), user.getPassword());
//
//        // Si erreur d'authentification, retourner la page de login avec un message d'erreur
//        if (response.getCode().equals("756")) {
//            // Ajouter le message d'erreur dans le modèle (si tu veux l'afficher)
//            model.addAttribute("errorMessage", response.getMessage());
//            return "auth/login";
//        }

        // Si l'authentification réussit, mettre l'utilisateur en session
        Utilisateur loggedUser = authManager.getUtilisateurByEmail(principal.getName());
        session.setAttribute("loggedUser", loggedUser);

        // Ajouter un message de succès en flash
        IHMHelpers.sendSuccessFlashMessage(redirectAttributes, "Vous êtes connecté(e) avec succès");

        // Rediriger vers la page d'accueil ou la page compte
        return "redirect:/compte";
    }


    @GetMapping("register")
    public String showRegistrationForm(Model model) {

        // Créer un nouvel utilisateur vide pour le formulaire

        Utilisateur user = new Utilisateur();

        model.addAttribute("user", user);

        return "auth/register";
    }

    @PostMapping("register")
    public String processRegistration(@Valid @ModelAttribute(name = "user") Utilisateur user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // 1 :: Contrôle de surface (validation du formulaire)

        if (bindingResult.hasErrors()) {



            return "auth/register";
        }

        // 2 :: Enregistrement de l'utilisateur

        AppManagerResponse<Utilisateur> response = authManager.register(user);

        // Gérer les erreurs d'inscription

        if (!response.isSuccess()) {

            model.addAttribute("error", response.getMessage());
            return "auth/register";
        }

        // 3 :: Envoyer un message de succès et rediriger vers la page de connexion

        redirectAttributes.addFlashAttribute("success", "Inscription réussie. Vous pouvez maintenant vous connecter.");

        return "redirect:/login";
    }

    @GetMapping("logout")
    public String logout() {
        return "index";
    }

    @GetMapping("/utilisateurs")
    public String afficherUtilisateurs(Model model) {
        List<Utilisateur> utilisateurs = daoAuth.selectUtilisateur();
        model.addAttribute("utilisateurs", utilisateurs);
        return "list-utilisateurs";
    }

    @GetMapping("/utilisateurs/edit/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = daoAuth.selectUtilisateurById(id);
        System.out.println("ulitisateur" + utilisateur);
        model.addAttribute("user", utilisateur);
        return "auth/modifier";
    }

    @PostMapping("/utilisateurs/edit")
    public String modifierUtilisateur(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }


        daoAuth.saveUtilisateur(utilisateur);
        redirectAttributes.addFlashAttribute("success", "Utilisateur modifié avec succès.");

        return "redirect:/utilisateurs";
    }

    @GetMapping("/utilisateurs/delete/{id}")
    public String supprimerUtilisateur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        daoAuth.deleteById(id);

        redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé avec succès.");

        return "redirect:/utilisateurs";
    }

    @GetMapping("compte")
    public String compte() {
        return "auth/account";
    }


}
