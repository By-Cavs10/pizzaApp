package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("mock")
@Component
public class DAOAuthMock implements IDAOAuth {

    // Deux faux users
//    List<Utilisateur> utilisateurs = Arrays.asList(
//            new Utilisateur("a@a", "123"),
//            new Utilisateur("thierry@gmail.com", "misericorde")
//    );
    List<Utilisateur> utilisateurs;

    @Override
    public Utilisateur login(String email, String password) {
        Utilisateur foundUtilisateur = utilisateurs.stream().filter(
                        utilisateur -> utilisateur.getEmail().equals(email) && utilisateur.getPassword().equals(password))
                .findFirst().orElse(null);

        return foundUtilisateur;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Utilisateur> selectUtilisateur() {
        return List.of();
    }

    @Override
    public Utilisateur selectUtilisateurById(Long id) {
        return null;
    }

    @Override
    public void saveUtilisateur(Utilisateur utilisateur) {

    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean save(Utilisateur user) {
        return false;
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return null;
    }

    @Override
    public void deleteById(Utilisateur utilisateur) {

    }
}
