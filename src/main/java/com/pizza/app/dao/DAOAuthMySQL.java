package com.pizza.app.dao;

import com.pizza.app.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
            utilisateur.setRole(rs.getBoolean("role"));





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

        //Insérer en base un aliment

        jdbcTemplate.update("UPDATE utilisateur SET id= ?, email = ?, password = ?"
                ,utilisateur.getId(),utilisateur.getEmail(), utilisateur.getPassword());




    }

    @Override
    public void deleteById(Utilisateur utilisateur) {

    }

}
