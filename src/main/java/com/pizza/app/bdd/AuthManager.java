package com.pizza.app.bdd;


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
}
