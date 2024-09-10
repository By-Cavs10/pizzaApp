package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("mock")
@Component
public class DAOAuthMock implements IDAOAuth {

    // Deux faux users
    List<Utilisateur> utilisateurs = Arrays.asList(
            new Utilisateur("a@a", "123"),
            new Utilisateur("thierry@gmail.com", "misericorde")
    );

    @Override
    public Utilisateur login(String email, String password) {
        Utilisateur foundUtilisateur = utilisateurs.stream().filter(
                        utilisateur -> utilisateur.getEmail().equals(email) && utilisateur.getPassword().equals(password))
                .findFirst().orElse(null);

        return foundUtilisateur;
    }
}
