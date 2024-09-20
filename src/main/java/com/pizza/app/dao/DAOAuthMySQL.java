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
            Utilisateur user = new Utilisateur();
            user.setId(rs.getLong("id_utilisateur"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRue(rs.getString("rue"));
            user.setCodePostal(rs.getString("code_postal"));
            user.setVille(rs.getString("ville"));
            user.setEmploye(rs.getBoolean("employe"));
            return user;
        }
    };

    @Override
    public Utilisateur login(String email, String password) {
        List<Utilisateur> utilisateurs = jdbcTemplate.query("SELECT * FROM UTILISATEUR WHERE email = ?", MEMBER_ROW_MAPPER, email);
        return utilisateurs.isEmpty() ? null : utilisateurs.get(0);
    }


    @Override
    public List<Utilisateur> selectUtilisateur() {
        return jdbcTemplate.query("SELECT * FROM utilisateur", MEMBER_ROW_MAPPER);
    }


    @Override
    public Utilisateur selectUtilisateurById(Long id) {
        List<Utilisateur> utilisateurs = jdbcTemplate.query("SELECT * FROM UTILISATEUR WHERE id_utilisateur = ?", MEMBER_ROW_MAPPER, id);

        //Si on trouve aucun élément on retourne null
        //Retourner le premier élément
        return utilisateurs.isEmpty() ? null : utilisateurs.get(0);
    }


    @Override
    public void saveUtilisateur(Utilisateur utilisateur) {

        //Tester si il existe en base, SI OUI => Update SINON => Insert
        if (Objects.nonNull(utilisateur.getId()) && selectUtilisateurById(utilisateur.getId()) != null) {
            jdbcTemplate.update("UPDATE utilisateur SET nom =?,prenom =?, email = ?, password = ?,rue =?,code_postal =?,ville =?,employe =? where id_utilisateur=?",
                    utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getPassword(), utilisateur.getRue(), utilisateur.getCodePostal(),
                    utilisateur.getVille(), utilisateur.isEmploye(), utilisateur.getId());


            //PS : Return = Arreter la fonction
            return;

        } else {
            jdbcTemplate.update(
                    "INSERT INTO utilisateur (nom, prenom, email, password, rue, code_postal, ville, employe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getPassword(),
                    utilisateur.getRue(), utilisateur.getCodePostal(), utilisateur.getVille(), utilisateur.isEmploye()
            );
        }

    }

    @Override
    public boolean existsByEmail(String email) {
        String sqlFindUserByEmail = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sqlFindUserByEmail, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public boolean save(Utilisateur user) {
        // Requête SQL pour insérer un nouvel utilisateur
        String sqlInsertUser = "INSERT INTO utilisateur (nom, prenom, email, password, rue, code_postal, ville, employe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int rows = jdbcTemplate.update(sqlInsertUser,
                user.getNom(), user.getPrenom(), user.getEmail(), user.getPassword(),
                user.getRue(), user.getCodePostal(), user.getVille(), user.isEmploye());
        return rows > 0;
    }

    @Override
    public Utilisateur findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM utilisateur WHERE email = ?",
                    new Object[]{email},
                    MEMBER_ROW_MAPPER
            );
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM utilisateur WHERE id_utilisateur = ?", id);
    }

    @Override
    public void deleteById(Utilisateur utilisateur) {

    }



}
