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


//         Instancier un User vide (email et password vide)
         Utilisateur user = new Utilisateur();
//
//         Envoyer le user dans le Model
         model.addAttribute("user", user);

        return "auth/login";
    }

    @PostMapping("login")
    public String processLogin(@ModelAttribute(name = "user") Utilisateur user, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult, HttpSession session) {
        // 1 :: Contrôle de surface

        // Erreur : Si controle de surface pas ok
//        if (bindingResult.hasErrors()) {
//        // Si erreur d'authentification, retourner la page de login avec un message d'erreur
//        if (response.getCode().equals("756")) {
//            // Ajouter le message d'erreur dans le modèle (si tu veux l'afficher)
//            model.addAttribute("errorMessage", response.getMessage());
//            return "auth/login";
//        }

            // 2 : Contrôle métier (le manager)
            AppManagerResponse<Utilisateur> response = authManager.authenticate(user.getEmail(), user.getPassword());

            // Erreur code 756 retourner la page avec l'erreur métier
            if (response.getCode().equals("756")) {
                // TODO : Pendant qu'on retourne la page de connexion (envoyer l'erreur metier)
                return "auth/login";
            }


            // 3 : Connecter l'user en session
            // Mettre l'user retrouvé en base dans la session
            model.addAttribute("loggedUser", response.getData());

            Utilisateur loggedUser = authManager.authenticate(user.getEmail(), user.getPassword()).getData();
            if (loggedUser != null) {
                session.setAttribute("loggedUser", loggedUser);
                // Ajouter un message temporaire (flash message)
                IHMHelpers.sendSuccessFlashMessage(redirectAttributes, "Vous êtes connecté(e) avec succès");
                return "redirect:/compte";
            }
            // rediriger sur ta page d'accueil
            return "redirect:/login";
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
