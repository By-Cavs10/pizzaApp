package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Profile("mysql")
@Component
public class DAOAuthMySQL implements IDAOAuth {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final RowMapper<Utilisateur> MEMBER_ROW_MAPPER = new RowMapper<Utilisateur>() {

        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur utilisateur = new Utilisateur() {};
            utilisateur.setId(rs.getLong("id"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setPassword(rs.getString("password"));
            utilisateur.setEmploye(rs.getBoolean("employe"));





            return utilisateur;
        }
    };

    @Override
    public Utilisateur login(String email, String password) {
        List<Utilisateur> utilisateurs = jdbcTemplate.query("SELECT * FROM UTILISATEUR WHERE email = ?", MEMBER_ROW_MAPPER, email);
        return utilisateurs.isEmpty() ? null : utilisateurs.get(0);
    }



    @Override
    public List<Utilisateur> selectUtilisateur() {return List.of();
        }

    /*
    Le code qui permet de savoir comment convertir/mapper un résultat en SQL en

    Comment mppaer un résultat SQL en Aliment
     */



    @Override
    public Utilisateur selectUtilisateurById(Long id) {
        List<Utilisateur> utilisateurs = jdbcTemplate.query("SELECT * FROM UTILISATEUR WHERE id = ?", MEMBER_ROW_MAPPER, id);

        //Si on trouve aucun élément on retourne null
        //Retourner le premier élément
        return utilisateurs.isEmpty() ? null : utilisateurs.get(0);
    }



    @Override
    public void saveUtilisateur(Utilisateur utilisateur) {

        //Tester si il existe en base, SI OUI => Update SINON => Insert
        if (Objects.nonNull(utilisateur.getId()) && selectUtilisateurById(utilisateur.getId()) != null) {
            jdbcTemplate.update("UPDATE utilisateur SET id= ?, email = ?, password = ?"
               ,utilisateur.getId(),utilisateur.getEmail(), utilisateur.getPassword());



            //PS : Return = Arreter la fonction
            return;
//
        }

    }
    @Override
    public boolean existsByEmail(String email) {
        // Requête SQL pour vérifier si l'email existe déjà
        String sqlFindUserByEmail = "SELECT * FROM utilisateurs WHERE email = ?";

        try {
            Utilisateur user = jdbcTemplate.queryForObject(sqlFindUserByEmail, new Object[]{email}, new UtilisateurRowMapper());
            return user != null;
        } catch (Exception e) {
            return false;  // Pas trouvé ou une erreur est survenue
        }
    }

    @Override
    public boolean save(Utilisateur user) {
        // Requête SQL pour insérer un nouvel utilisateur
        String sqlInsertUser = "INSERT INTO utilisateurs (nom, prenom, email, password, rue, codePostal, ville, employe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int rows = jdbcTemplate.update(sqlInsertUser,
                user.getNom(), user.getPrenom(), user.getEmail(), user.getPassword(),
                user.getRue(), user.getCodePostal(), user.getVille(), user.isEmploye());
        return rows > 0;
    }

    @Override
    public Utilisateur findByEmail(String email) {
        // Requête SQL pour trouver un utilisateur par email
        String sqlFindUserByEmail = "SELECT * FROM utilisateurs WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sqlFindUserByEmail, new Object[]{email}, new UtilisateurRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    private static class UtilisateurRowMapper implements RowMapper<Utilisateur> {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur user = new Utilisateur();
            user.setId(rs.getLong("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRue(rs.getString("rue"));
            user.setCodePostal(rs.getString("codePostal"));
            user.setVille(rs.getString("ville"));
            user.setEmploye(rs.getBoolean("employe"));
            return user;
        }
    }
    @Override
    public void deleteById(Utilisateur utilisateur) {

    }

}
