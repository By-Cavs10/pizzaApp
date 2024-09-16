package com.pizza.app.ihm;


import com.pizza.app.bll.AuthManager;
import com.pizza.app.bll.AppManagerResponse;
import com.pizza.app.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthController {

    @Autowired
    private AuthManager authManager;

    @GetMapping("login")
    public String login(Model model, RedirectAttributes redirectAttributes) {


        // Instancier un User vide (email et password vide)
        Utilisateur user = new Utilisateur();

        // Envoyer le user dans le Model
        model.addAttribute("user", user);

        return "auth/login";
    }

    @PostMapping("login")
    public String processLogin(@Valid @ModelAttribute(name = "user") Utilisateur user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // 1 :: Contrôle de surface

        // Erreur : Si controle de surface pas ok
        if (bindingResult.hasErrors()) {
            // Retourner la page avec les erreurs de validation (le format)
            return "auth/login";
        }

        // 2 : Contrôle métier (le manager)
        AppManagerResponse<Utilisateur> response = authManager.authenticate(user.getEmail(), user.getPassword());

        // Erreur code 756 retourner la page avec l'erreur métier
        if (response.code.equals("756")) {
            // TODO : Pendant qu'on retourne la page de connexion (envoyer l'erreur metier)
            return "auth/login";
        }

        // 3 : Connecter l'user en session
        // Mettre l'user retrouvé en base dans la session
        model.addAttribute("loggedUser", response.data);

        // Ajouter un message temporaire (flash message)
        IHMHelpers.sendSuccessFlashMessage(redirectAttributes, "Vous êtes connecté(e) avec succès");

        // rediriger sur ta page d'accueil
        return "redirect:/";
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

        if (!response.success) {

            model.addAttribute("error", response.message);
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
}
