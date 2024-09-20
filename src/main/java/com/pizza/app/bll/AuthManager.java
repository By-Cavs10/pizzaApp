package com.pizza.app.bll;

import com.pizza.app.bo.Utilisateur;
import com.pizza.app.dao.IDAOAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager {

    @Autowired
    IDAOAuth daoAuth;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    public AppManagerResponse<Utilisateur> authenticate(String email, String password) {
//        // Récupérer l'utilisateur par email
//        Utilisateur foundUtilisateur = daoAuth.findByEmail(email);
//
//        // Si aucun utilisateur n'est trouvé, retourner une erreur
//        if (foundUtilisateur == null) {
//            return AppManagerResponse.performResponse("756", "Email incorrect", null, false);
//        }
//
//        // Vérifier si le mot de passe correspond à celui en base (haché)
//        if (!passwordEncoder.matches(password, foundUtilisateur.getPassword())) {
//            return AppManagerResponse.performResponse("756", "Mot de passe incorrect", null, false);
//        }
//
//        // Si l'utilisateur est trouvé et le mot de passe correspond, retourner le succès
//        return AppManagerResponse.performResponse("202", "Vous êtes connecté(e) avec succès", foundUtilisateur, true);
//    }
    public Utilisateur getUtilisateurByEmail(String email) {

        return daoAuth.findByEmail(email);
    }

    public AppManagerResponse<Utilisateur> register(Utilisateur user) {
        try {
            // Hacher le mot de passe avec BCrypt avant de l'enregistrer

            String hashedPassword = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword());
            user.setPassword(hashedPassword);

            // Enregistrer l'utilisateur dans la base
            daoAuth.saveUtilisateur(user);

            return AppManagerResponse.performResponse("200", "Inscription réussie", user, true);
        } catch (Exception e) {
            return AppManagerResponse.performResponse("500", "Erreur lors de l'inscription : " + e.getMessage(), null, false);
        }
    }
}
