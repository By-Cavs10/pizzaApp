package com.pizza.app.bll;


import com.pizza.app.bo.Utilisateur;
import com.pizza.app.dao.IDAOAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthManager {

    @Autowired
    IDAOAuth daoAuth;

    public AppManagerResponse<Utilisateur> authenticate(String email, String password) {
        // On essayer de trouver le membre qui à l'email et le password envoyés
        Utilisateur foundUtilisateur = daoAuth.login(email, password);

        // Si couple email/mot de passe incorrect erreur code 756
        if (foundUtilisateur == null) {
            return AppManagerResponse.performResponse("756", "Couple email/mot de passe incorrect", null);
        }

        // Sinon code 202
        return AppManagerResponse.performResponse("202", "Vous êtes connecté(e) avec succès", foundUtilisateur);
    }


    public AppManagerResponse<Utilisateur> register(Utilisateur user) {
        // Vérifier si l'utilisateur existe déjà par email
        if (daoAuth.existsByEmail(user.getEmail())) {
            return new AppManagerResponse<>(null, "L'adresse email est déjà utilisée.", false);
        }

        // Enregistrer l'utilisateur dans la base de données
        boolean isSaved = daoAuth.save(user);
        if (isSaved) {
            return new AppManagerResponse<>(user, "Inscription réussie.", true);
        } else {
            return new AppManagerResponse<>(null, "Erreur lors de l'inscription.", false);
        }
    }
}
