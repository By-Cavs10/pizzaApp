package com.pizza.app.bll;

import com.pizza.app.bo.Utilisateur;
import com.pizza.app.dao.IDAOAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthManager {

    @Autowired
    IDAOAuth daoAuth;



    // 2 : Contrôle métier (le manager)
    public AppManagerResponse<Utilisateur> authenticate(String email, String password) {
        // On essayer de trouver le membre qui à l'email et le password envoyés
        Utilisateur foundUtilisateur = daoAuth.login(email, password);

        // Si couple email/mot de passe incorrect erreur code 756
        if (foundUtilisateur == null) {
            return AppManagerResponse.performResponse("756", "Couple email/mot de passe incorrect", null, false);
        }

        // Sinon code 202
        return AppManagerResponse.performResponse("202", "Vous êtes connecté(e) avec succès", foundUtilisateur, true);
//    public Utilisateur getUtilisateurByEmail(String email) {
//
//        return daoAuth.findByEmail(email);
//    }
    }

    public AppManagerResponse<Utilisateur> register(Utilisateur user) {
        try {
            // Appeler le DAO pour enregistrer l'utilisateur dans la base

            // Enregistrer l'utilisateur dans la base
            daoAuth.saveUtilisateur(user);
// Si l'insertion est réussie, renvoyer une réponse de succès
            return AppManagerResponse.performResponse("200", "Inscription réussie", user, true);
        } catch (Exception e) {
            // En cas d'exception (ex: violation de contrainte), renvoyer une réponse d'échec
            return AppManagerResponse.performResponse("500", "Erreur lors de l'inscription : " + e.getMessage(), null, false);
        }
    }
}
